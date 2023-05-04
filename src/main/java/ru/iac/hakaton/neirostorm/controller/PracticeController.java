package ru.iac.hakaton.neirostorm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.iac.hakaton.neirostorm.model.Practice;
import ru.iac.hakaton.neirostorm.repository.PracticeRepository;

import java.util.Comparator;
import java.util.List;

@Controller
public class PracticeController {

    @Autowired
    private PracticeRepository practiceRepository;

    @GetMapping("/practices")
    public String getPractices(@RequestParam(value = "rating", required = false) String rating, Model model) {
        List<Practice> practices = practiceRepository.findAll();


        model.addAttribute("practices", practices);
        model.addAttribute("emptyList", practices.isEmpty());

        return "practices";
    }
}
