package ru.iac.hakaton.neirostorm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.iac.hakaton.neirostorm.model.Practice;
import ru.iac.hakaton.neirostorm.model.Topic;
import ru.iac.hakaton.neirostorm.repository.PracticeRepository;

import java.util.Comparator;
import java.util.List;

@Controller
public class PracticeController {

    @Autowired
    private PracticeRepository practiceRepository;

    @GetMapping("/")
    public String getPractices(@RequestParam(value = "rating", required = false) String rating, Model model) {
//        List<Practice> practices = practiceRepository.findAll();

        Practice practice1 = new Practice();
        practice1.setId(1L);
        practice1.setTitle("Создание Dockerfile для Spring Boot приложения, собранного при помощи Gradle");
        practice1.setDescription("Использование ChatGPT для создания Dockerfile для Spring Boot приложения. На выходе получаем готовый Dockerfile, который используется для создания Docker образа и последующего развертывания приложения.");
        practice1.setTopic(Topic.DEVOPS);

        Practice practice2 = new Practice();
        practice2.setId(1L);
        practice2.setTitle("Формирование логического описание БД на основе DDL (Quick documentation idea)");
        practice2.setDescription("Если уже есть база данных, и в ней присутствую какие-то записи, то можно методом Reverse Engineering получить логическое описание БД / Таблицы с помощью ChatGPT");
        practice2.setTopic(Topic.DOCUMENTATION);

        List<Practice> practices = List.of(practice1, practice2);

        model.addAttribute("practices", practices);
        model.addAttribute("emptyList", practices.isEmpty());

        return "practices";
    }
}
