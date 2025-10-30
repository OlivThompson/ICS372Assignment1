package org.FoodHub;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileAccesser {
    private List<String> allOrderFiles = new ArrayList<>();

    public FileAccesser(){}

    public List<String> fetechOrderFolderList(){
        Path directory = Paths.get("orders");
        try(Stream<Path> stream = Files.list(directory)){
            allOrderFiles = stream.filter(file ->file.toString().endsWith(".json") || file.toString().endsWith(".xml")).map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toList());
            return allOrderFiles;
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Error");
            return List.of();
        }
    }

    public String getExtension(String fileName){
        int indexOfDot = fileName.lastIndexOf('.');
        if (indexOfDot > 0 && indexOfDot < fileName.length() - 1){
            return fileName.substring(indexOfDot + 1);
        }
        return "";
    }

}
