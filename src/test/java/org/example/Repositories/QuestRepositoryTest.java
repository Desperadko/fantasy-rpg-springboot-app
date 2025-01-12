package org.example.Repositories;

import org.example.Entities.Location;
import org.example.Entities.Quest;
import org.example.Helpers.StaticVariables;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class QuestRepositoryTest {

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Test
    void saveQuest_Success(){
        var location = new Location();
        location.setName(StaticVariables.startingZone);

        var quest = new Quest();
        quest.setName("Don't goon - Impossible");
        quest.setDescriptionFilePath("src\\main\\resources\\QuestDescriptions\\don't_goon-impossible.json");
        quest.setLocation(location);

        var savedQuest = questRepository.save(quest);

        assertThat(savedQuest.getId()).isNotNull();
        assertThat(savedQuest.getName()).isEqualTo("Don't goon - Impossible");
        assertThat(savedQuest.getDescriptionFilePath()).isEqualTo("src\\main\\resources\\QuestDescriptions\\don't_goon-impossible.json");
        assertThat(savedQuest.getLocation().getName()).isEqualTo(StaticVariables.startingZone);
    }

    @Test
    void findAllQuestIdsByLocation_Success() {
        var location = new Location();
        location.setName(StaticVariables.startingZone);
        locationRepository.save(location);

        var quest1 = new Quest();
        quest1.setName("A new beginning");
        quest1.setDescriptionFilePath("src\\main\\resources\\QuestDescriptions\\a_new_beginning.json");
        quest1.setLocation(location);

        var quest2 = new Quest();
        quest2.setName("A second new beginning");
        quest2.setDescriptionFilePath("src\\main\\resources\\QuestDescriptions\\a_second_new_beginning.json");
        quest2.setLocation(location);

        var quest3 = new Quest();
        quest3.setName("Last new beginning and im quitting");
        quest3.setDescriptionFilePath("src\\main\\resources\\QuestDescriptions\\last_new_beginning_and_im_quitting.json");

        questRepository.saveAll(Arrays.asList(quest1, quest2, quest3));

        var questIds = questRepository.findAllQuestIdsByLocation(location);

        assertThat(questIds.size()).isEqualTo(2);
    }

    @Test
    void updateQuestName_Success() {
        var location = new Location();
        location.setName(StaticVariables.startingZone);
        locationRepository.save(location);

        var quest = new Quest();
        quest.setName("A new beginning");
        quest.setDescriptionFilePath("src\\main\\resources\\QuestDescriptions\\a_new_beginning.json");
        quest.setLocation(location);

        var savedQuest = questRepository.save(quest);

        var rowsUpdated = questRepository.updateQuestName("Maybe another beginning", savedQuest.getId());

        assertThat(rowsUpdated).isEqualTo(1);
    }

    @Test
    void deleteQuest(){
        var location = new Location();
        location.setName(StaticVariables.startingZone);
        locationRepository.save(location);

        var quest = new Quest();
        quest.setName("A new beginning");
        quest.setDescriptionFilePath("src\\main\\resources\\QuestDescriptions\\a_new_beginning.json");
        quest.setLocation(location);
        var savedQuest = questRepository.save(quest);

        questRepository.delete(savedQuest);

        var deletedQuest = questRepository.findById(savedQuest.getId());
        assertThat(deletedQuest).isEmpty();
    }
}