package ru.iac.hakaton.neirostorm.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.iac.hakaton.neirostorm.model.Practice;
import ru.iac.hakaton.neirostorm.service.PracticeService;
import ru.iac.hakaton.neirostorm.repository.VoteRepository;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PracticeController.class)
public class PracticeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PracticeService practiceService;

    @MockBean
    private VoteRepository voteRepository;

    private List<Practice> practiceList;

    @BeforeEach
    void setUp() {
        Practice practice1 = new Practice();
        practice1.setId(1L);
        practice1.setOwnerName("Owner1");
        practice1.setTitle("Title1");
        practice1.setDescription("Description1");
        practice1.setSteps("Steps1");
        practice1.setExample("Example1");
        practice1.setConclusion("Conclusion1");
        practice1.setTopic("Topic1");

        Practice practice2 = new Practice();
        practice2.setId(2L);
        practice2.setOwnerName("Owner2");
        practice2.setTitle("Title2");
        practice2.setDescription("Description2");
        practice2.setSteps("Steps2");
        practice2.setExample("Example2");
        practice2.setConclusion("Conclusion2");
        practice2.setTopic("Topic2");

        this.practiceList = Arrays.asList(practice1, practice2);
    }

    @Test
    void testGetPractices() throws Exception {
        when(practiceService.searchPractices(null, null, null)).thenReturn(practiceList);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("practices"))
                .andExpect(model().attribute("practices", practiceList))
                .andExpect(model().attribute("error", false));

        verify(practiceService, times(1)).searchPractices(null, null, null);
    }

    @Test
    void testShowPractice() throws Exception {
        Practice practice = practiceList.get(0);
        when(practiceService.getPracticeById(1L)).thenReturn(practice);

        mockMvc.perform(get("/practice/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("practice"))
                .andExpect(model().attribute("practice", practice));

        verify(practiceService, times(1)).getPracticeById(1L);
    }

    // Add more test cases for other methods as needed
}
