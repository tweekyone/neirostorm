package ru.iac.hakaton.neirostorm.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.iac.hakaton.neirostorm.dto.PracticeDto;
import ru.iac.hakaton.neirostorm.model.Practice;
import ru.iac.hakaton.neirostorm.model.Topic;
import ru.iac.hakaton.neirostorm.model.Vote;
import ru.iac.hakaton.neirostorm.repository.VoteRepository;
import ru.iac.hakaton.neirostorm.service.PracticeService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Controller
@AllArgsConstructor
public class PracticeController {

    private final PracticeService practiceService;
    private final VoteRepository voteRepository;

    @GetMapping("/")
    public String getPractices(@RequestParam(value = "rating", required = false) String rating, Model model) {
        List<Practice> practices = practiceService.getPractices();

        model.addAttribute("practices", practices);
        model.addAttribute("emptyList", practices.isEmpty());

        return "practices";
    }

    @GetMapping("/practice/{id}")
    public String showPractice(@PathVariable("id") Long id, HttpServletRequest request, Model model) {
        Practice practice = practiceService.getPracticeById(id);
        String ipAddress = request.getRemoteAddr();

        AtomicLong likes = new AtomicLong(0L);
        AtomicLong dislikes = new AtomicLong(0L);

        voteRepository.findAllByPracticeId(id).forEach(vote -> {
            if(vote.getVoteValue() == 1){
                likes.getAndIncrement();
            } else if (vote.getVoteValue() == -1){
                dislikes.getAndIncrement();
            }
        });
        model.addAttribute("likes", likes.get());
        model.addAttribute("dislikes", dislikes.get());

        Optional<Vote> vote = voteRepository.findByPracticeIdAndIpAddress(id, ipAddress);
        if (vote.isPresent()) {
            model.addAttribute("vote", vote.get().getVoteValue());
        } else {
            model.addAttribute("vote", 0);
        }

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
    public String addPractice(@ModelAttribute("practice") @Valid PracticeDto practiceDto, Model model) {
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


    @GetMapping("/practices-rating")
    public String getPracticesRating(@RequestParam(value = "rating", required = false) String rating, Model model) {
        List<Practice> practices = practiceService.getPractices();

        model.addAttribute("practices", practices);
        model.addAttribute("emptyList", practices.isEmpty());

        return "practices-rating";
    }


    @PostMapping("/practices/{id}/vote")
    public ResponseEntity<String> vote(@PathVariable("id") Long id, @RequestParam("vote") int vote,
                                       HttpServletRequest request, HttpServletResponse response) {
        String ipAddress = request.getRemoteAddr();
        Optional<Practice> practiceOpt = practiceService.findById(id);

        if (!practiceOpt.isPresent()) {
            throw new IllegalArgumentException("Invalid practice id: " + id);
        }
        Practice practice = practiceOpt.get();

        Vote clientVote = voteRepository.findByPracticeIdAndIpAddress(id, ipAddress).orElse(getNewVote(practice, request.getRemoteAddr()));

        clientVote.setVoteValue(vote);
        voteRepository.save(clientVote);

        return ResponseEntity.ok("Vote submitted successfully.");
    }

    private Vote getNewVote(Practice practice, String remoteAddr) {
        Vote vote = new Vote();
        vote.setPractice(practice);
        vote.setIpAddress(remoteAddr);
        return vote;
    }
}