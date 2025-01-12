package org.example.Helpers;

import jdk.jfr.Name;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class QuestFilePathManager {
    private final Path questDescriptionsDirectoryPath = Paths.get("src", "main", "resources", "QuestDescriptions");

    @Named("createQuestFilePath")
    public String createQuestDescriptionFilePath(String questName){
        var path = getPathByQuestName(questName);

        if(Files.exists(path))
            throw new IllegalStateException("File for quest: '" + questName + "' already exists..");

        return path.toString();
    }
    @Named("getQuestFilePath")
    public String getQuestDescriptionFilePath(String questName){
        var path = getPathByQuestName(questName);

        if(!Files.exists(path))
            throw new IllegalStateException("File for quest: '" + questName + "' does not exist..");

        return path.toString();
    }
    public boolean questDescriptionFilePathExists(String questName){
        var path = getPathByQuestName(questName);
        return Files.exists(path);
    }

    private Path getPathByQuestName(String questName) {
        try{
            Files.createDirectories(questDescriptionsDirectoryPath);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't create directory at filepath: '" + questDescriptionsDirectoryPath.toAbsolutePath() + "'..", e);
        }

        return questDescriptionsDirectoryPath.resolve(questName.trim().replaceAll("\\s", "_").toLowerCase() + ".json");
    }
}
