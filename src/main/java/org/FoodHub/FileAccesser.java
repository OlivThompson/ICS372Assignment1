package org.FoodHub;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
/**
 * Handles file operations for order processing.
 * Manages directories for incoming, processed, and error orders.
 * Handles fetching files, getting extensions, and moving files between directories.
 */
public class FileAccesser {
    private List<String> allOrderFiles = new ArrayList<>();
    private String sourceDir = "orders";
    private String processedOrderDir = "orders/processedOrders";
    private String errorOrderDir = "orders/ErrorOrders";

    public FileAccesser(){
        try{
            Files.createDirectories(Paths.get(processedOrderDir));
            Files.createDirectories(Paths.get(errorOrderDir));
        }catch (IOException e){
            System.err.println("Error Creating Directory");
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a list of order files with .json or .xml extensions
     * @return list of order file names in the source directory
     */
    public List<String> fetchOrderFolderList(){
        Path directory = Paths.get(sourceDir);
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

    /**
     * Gets the file extension from a file name
     * @param fileName name of the file
     * @return file extension, or empty string if none found
     */
    public String getExtension(String fileName){
        int indexOfDot = fileName.lastIndexOf('.');
        if (indexOfDot > 0 && indexOfDot < fileName.length() - 1){
            return fileName.substring(indexOfDot + 1);
        }
        return "";
    }

    /**
     * Moves a file from the source directory to the specified directory.
     * @param fileName name of the file to move
     * @param targetDir the target directory path
     * @throws IOException if moving the file fails
     */
    private void moveFile(String fileName, Path targetDir) throws IOException {
        Path sourcePath = Paths.get(sourceDir, fileName);
        Path targetPath = targetDir.resolve(fileName);
        Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * Moves a processed order file to the processed orders directory.
     * @param fileName name of the file to move
     * @throws IOException if moving the file fails
     */
    public void moveProcessedFile(String fileName) throws IOException {
        moveFile(fileName, Paths.get(processedOrderDir));
    }

    /**
     * Moves an order file with errors to error orders directory.
     * @param fileName name of the file to move.
     * @throws IOException if moving the file fails.
     */
    public void moveErrorFile(String fileName) throws IOException {
        moveFile(fileName, Paths.get(errorOrderDir));

    }

}
