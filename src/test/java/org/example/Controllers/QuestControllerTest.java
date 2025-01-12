package org.example.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.DTOs.QuestDTO;
import org.example.DTOs.QuestDescriptionDTO;
import org.example.Entities.Stat;
import org.example.Helpers.QuestFilePathManager;
import org.example.Helpers.StaticVariables;
import org.example.Repositories.QuestRepository;
import org.example.Services.LocationService;
import org.example.Services.QuestService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
class QuestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private QuestService questService;
    @Autowired
    private QuestRepository questRepository;
    @Autowired
    private QuestFilePathManager questFilePathManager;

    @BeforeEach
    void setup(){
        questService.createQuest(
                new QuestDTO(
                        "A new beginning",
                        "Collect 8 cactus apples. STRENGTH!",
                        StaticVariables.startingZone
                )
        );
        questService.createQuest(
                new QuestDTO(
                        "Petkov's task",
                        "Kill God.",
                        StaticVariables.startingZone
                )
        );
    }

    @AfterEach
    void cleanup(){
        jdbcTemplate.execute("DELETE FROM quests");
        jdbcTemplate.execute("ALTER TABLE quests ALTER COLUMN id RESTART WITH 1");

        deleteQuestDescription("A new beginning");
        deleteQuestDescription("Petkov's task");
    }

    @Test
    void getQuestById_Success() throws Exception {
        mockMvc.perform(get("/quest/{questId}", 2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Petkov's task"))
                .andExpect(jsonPath("$.location.name").value(StaticVariables.startingZone));

        var questDescriptionFilePath = questFilePathManager.getQuestDescriptionFilePath("Petkov's task");
        assertThat(questDescriptionFilePath).isNotEmpty();

        Map<String, String> jsonMap = objectMapper.readValue(new File(questDescriptionFilePath), Map.class);
        assertThat(jsonMap.get("description")).isEqualTo("Kill God.");
    }

    @Test
    void createQuest_Success() throws Exception {
        var questDTO = new QuestDTO(
                "A King's feast",
                "Get a Papa John's EXTRA large",
                StaticVariables.startingZone
        );

        mockMvc.perform(post("/quest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(questDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("A King's feast"))
                .andExpect(jsonPath("$.descriptionFilePath").value(questFilePathManager.getQuestDescriptionFilePath(questDTO.name())))
                .andExpect(jsonPath("$.location.name").value(StaticVariables.startingZone));

        var questDescriptionFilePath = questFilePathManager.getQuestDescriptionFilePath(questDTO.name());
        assertThat(questDescriptionFilePath).isNotEmpty();

        Map<String, String> jsonMap = objectMapper.readValue(new File(questDescriptionFilePath), Map.class);
        assertThat(jsonMap.get("description")).isEqualTo("Get a Papa John's EXTRA large");

        deleteQuestDescription(questDTO.name());
    }

    @Test
    void updateQuestName_Success() throws Exception {
        mockMvc.perform(patch("/quest/{questId}?questName={questName}", 1L, "ZUG ZUG")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.name").value("ZUG ZUG"));

        var newQuestDescriptionFilePath = questFilePathManager.getQuestDescriptionFilePath("ZUG ZUG");;
        assertThat(newQuestDescriptionFilePath).isNotEmpty();

        Map<String, String> jsonMap = objectMapper.readValue(new File(newQuestDescriptionFilePath), Map.class);
        assertThat(jsonMap.get("description")).isEqualTo("Collect 8 cactus apples. STRENGTH!");

        Files.delete(Path.of(newQuestDescriptionFilePath));
    }

    @Test
    void updateQuestDescription_Success() throws Exception {
        var questDescriptionDTO = new QuestDescriptionDTO("Slay 10 Kobold Vermin");

        mockMvc.perform(patch("/quest/{questId}/description", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(questDescriptionDTO)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.description").value("Slay 10 Kobold Vermin"));
    }

    @Test
    void deleteQuest_Success() throws Exception {
        mockMvc.perform(delete("/quest/{questId}", 2L))
                .andExpect(status().isAccepted());

        var deletedQuest = questRepository.findById(2L);
        assertThat(deletedQuest).isEmpty();
        assertThat(questFilePathManager.questDescriptionFilePathExists("Petkov's task")).isFalse();
    }

    private void deleteQuestDescription(String questName) {
        if(!questFilePathManager.questDescriptionFilePathExists(questName))
            return;

        var questPath = questFilePathManager.getQuestDescriptionFilePath(questName);

        try {
            Files.delete(Paths.get(questPath));
        }
        catch (IOException e){
            throw new RuntimeException("Error deleting quest description while deleting quest with name: '" + questName + "'..");
        }
    }
}