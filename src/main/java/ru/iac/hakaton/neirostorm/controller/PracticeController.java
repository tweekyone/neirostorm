package ru.iac.hakaton.neirostorm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.iac.hakaton.neirostorm.dto.PracticeDto;
import ru.iac.hakaton.neirostorm.model.Practice;
import ru.iac.hakaton.neirostorm.service.PracticeService;
import ru.iac.hakaton.neirostorm.repository.PracticeRepository;
import ru.iac.hakaton.neirostorm.service.PracticeService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class PracticeController {

    @Autowired
    private PracticeService practiceService;



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
}
