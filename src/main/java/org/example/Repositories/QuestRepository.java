package org.example.Repositories;

import org.example.Entities.Location;
import org.example.Entities.Quest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestRepository extends JpaRepository<Quest, Long> {
    Page<Quest> findAll(Pageable pageable);
    @Modifying
    @Query(value = "SELECT q.id FROM Quest q WHERE q.location = :location")
    List<Long> findAllQuestIdsByLocation(Location location);
    @Query(value = "SELECT q.id FROM Quest q WHERE q.name = :questName")
    Long findQuestIdByName(String questName);
    @Modifying
    @Query(value = "UPDATE Quest q SET q.name = :questName WHERE q.id = :questId")
    int updateQuestName(String questName, Long questId);
    @Modifying
    @Query(value = "UPDATE Quest q SET q.descriptionFilePath = :questDescriptionFilePath WHERE q.id = :questId")
    int updateQuestDescriptionFilePath(String questDescriptionFilePath, Long questId);
}
