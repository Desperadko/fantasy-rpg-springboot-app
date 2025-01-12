package org.example.Mappers;

import org.example.DTOs.QuestDTO;
import org.example.Entities.Location;
import org.example.Helpers.QuestFilePathManager;
import org.example.Helpers.QuestMapperHelper;
import org.example.Helpers.StaticVariables;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class QuestMapperTest {

    @Autowired
    private QuestMapper questMapper;

    @MockBean
    private QuestFilePathManager questFilePathManager;

    @MockBean
    private QuestMapperHelper questMapperHelper;

    @Test
    void convertDtoToEntity_Success(){
        when(questFilePathManager.getQuestDescriptionFilePath("Old Blanchy"))
                .thenReturn("src\\main\\resources\\QuestDescriptions\\old_blanchy.json");

        var location = new Location();
        location.setId(1L);
        location.setName(StaticVariables.startingZone);
        when(questMapperHelper.findLocationByName(StaticVariables.startingZone))
                .thenReturn(location);

        var questDTO = new QuestDTO(
                "Old Blanchy",
                "Collect 8 seed sacks.",
                StaticVariables.startingZone
        );

        var questId = 4L;

        var quest = questMapper.convertDtoToEntity(questDTO, questId);

        assertThat(quest).isNotNull();
        assertThat(quest.getName()).isEqualTo("Old Blanchy");
        assertThat(quest.getDescriptionFilePath()).isEqualTo("src\\main\\resources\\QuestDescriptions\\old_blanchy.json");
        assertThat(quest.getLocation().getName()).isEqualTo(StaticVariables.startingZone);
        assertThat(quest.getId()).isEqualTo(4L);
    }
}