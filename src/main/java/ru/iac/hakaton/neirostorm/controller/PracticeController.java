package ru.iac.hakaton.neirostorm.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.iac.hakaton.neirostorm.dto.PracticeDto;
import ru.iac.hakaton.neirostorm.model.Practice;
import ru.iac.hakaton.neirostorm.model.Topic;
import ru.iac.hakaton.neirostorm.service.PracticeService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class PracticeController {

    private final PracticeService practiceService;

    @GetMapping("/")
    public String getPractices(@RequestParam(value = "rating", required = false) String rating, Model model) {
        List<Practice> practices = practiceService.getPractices();

        model.addAttribute("practices", practices);
        model.addAttribute("emptyList", practices.isEmpty());

        return "practices";
    }

    @GetMapping("/practice/{id}")
    public String showPractice(@PathVariable("id") Long id, Model model) {
        Practice practice = practiceService.getPracticeById(id);

        model.addAttribute("practice", practice);

        return "practice";
    }

    @GetMapping("/add-practice")
    public String addPractice(Model model) {
        Practice practice = new Practice();
        model.addAttribute("practice", practice);
        model.addAttribute("topics", Topic.values());
        return "add-practice";
    }

    @PostMapping("/insert")
    public String addPractice(@ModelAttribute("practice") @Valid PracticeDto practiceDto,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            return "add-practice";
        }
        Practice practice = practiceService.addPractice(practiceDto);

        model.addAttribute("practice", practice);

        return "practice";
    }

    @GetMapping("/practices")
    public String searchPractices(@RequestParam(value = "keyword", required = false) String keyword,
                                  @RequestParam(name = "topic", required = false) String topic, Model model) {

        List<Practice> practices = practiceService.searchPractices(keyword, topic);

        model.addAttribute("practices", practices);
        model.addAttribute("keyword", keyword);
        model.addAttribute("selectedTopic", topic);
        return "practices";
    }

    @PostMapping("/practices/{id}/vote")
    public ResponseEntity<String> vote(@PathVariable("id") Long id, @RequestParam("vote") int vote,
                                       HttpServletRequest request, HttpServletResponse response) {
        // проверяем, голосовал ли пользователь за эту практику
        Cookie[] cookies = request.getCookies();
        String votedPracticeIds = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("votedPracticeIds")) {
                    votedPracticeIds = cookie.getValue();
                    break;
                }
            }
        }

        if (votedPracticeIds == null || !votedPracticeIds.contains(String.valueOf(id))) {
            Optional<Practice> practiceOpt = practiceService.findById(id);

            if (!practiceOpt.isPresent()) {
                throw new IllegalArgumentException("Invalid practice id: " + id);
            }

            Practice practice = practiceOpt.get();
            // увеличиваем/уменьшаем рейтинг в зависимости от голоса
            if (vote == 1) {
                practice.setLikes(practice.getLikes() + 1);
            } else if (vote == -1) {
                practice.setDislikes(practice.getDislikes() + 1);
            } else {
                throw new IllegalArgumentException("Invalid vote value: " + vote);
            }
            practiceService.save(practice);
        } else {
            return new ResponseEntity<>("Вы уже проголосовали за эту практику!", HttpStatus.NOT_ACCEPTABLE);
        }

        // устанавливаем куку
        if (votedPracticeIds == null) {
            votedPracticeIds = String.valueOf(id);
        } else {
            votedPracticeIds += "-" + id;
        }
        Cookie votedPracticeIdsCookie = new Cookie("votedPracticeIds", votedPracticeIds);
        votedPracticeIdsCookie.setMaxAge(60 * 60 * 24 * 30 * 2); // срок действия куки - 2 месяца
        response.addCookie(votedPracticeIdsCookie);

        return ResponseEntity.ok("");
    }
}