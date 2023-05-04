package ru.iac.hakaton.neirostorm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.iac.hakaton.neirostorm.model.Practice;
import ru.iac.hakaton.neirostorm.model.Topic;
import ru.iac.hakaton.neirostorm.repository.PracticeRepository;

import java.util.List;

@Controller
public class PracticeController {

    @Autowired
    private PracticeRepository practiceRepository;

    @GetMapping("/")
    public String getPractices(@RequestParam(value = "rating", required = false) String rating, Model model) {
//        List<Practice> practices = practiceRepository.findAll();

        Practice practice1 = getPractice1();
        Practice practice2 = getPractice2();

        List<Practice> practices = List.of(practice1, practice2);

        model.addAttribute("practices", practices);
        model.addAttribute("emptyList", practices.isEmpty());

        return "practices";
    }


    @GetMapping("/practice/{id}")
    public String showPractice(@PathVariable("id") Long id, Model model) {
        Practice practice = getPractice1();
        if(1 != id) {
            practice = getPractice2();
        }

        if (practice == null) {
            throw new IllegalArgumentException("Invalid practice id: " + id);
        }

        model.addAttribute("practice", practice);

        return "practice";
    }

    private Practice getPractice1 () {
        Practice practice1 = new Practice();
        practice1.setId(1L);
        practice1.setTitle("Создание Dockerfile для Spring Boot приложения, собранного при помощи Gradle");
        practice1.setDescription("Использование ChatGPT для создания Dockerfile для Spring Boot приложения. На выходе получаем готовый Dockerfile, который используется для создания Docker образа и последующего развертывания приложения.");
        practice1.setTopic(Topic.DEVOPS);
        practice1.setPreviewImage("https://mherman.org/presentations/microservices-flask-docker/images/docker-logo.png");

        return practice1;
    }

    private Practice getPractice2 () {
        Practice practice2 = new Practice();
        practice2.setId(2L);
        practice2.setTitle("Формирование логического описание БД на основе DDL (Quick documentation idea)");
        practice2.setDescription("Если уже есть база данных, и в ней присутствую какие-то записи, то можно методом Reverse Engineering получить логическое описание БД / Таблицы с помощью ChatGPT");
        practice2.setTopic(Topic.DOCUMENTATION);
        practice2.setPreviewImage("https://ww1.prweb.com/prfiles/2010/03/11/1448654/DDLgenericlogocmyk.jpg");

        return practice2;
    }

}
