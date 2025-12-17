package ru.lepeshka.laba1.repository;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class StorageRepository {
    public String createFile(String name, String value) {
        String fileName = UUID.randomUUID().toString();
        try {
            PrintWriter writer = new PrintWriter("./src/main/resources/static/" + fileName, "UTF-8");
            writer.write(value);
            writer.close();
        } catch (Exception err) {
            System.out.println(err.toString());
        }
        return fileName;
    }

    public String updateFile(String fileName, String newValue) {
        try {
            PrintWriter writer = new PrintWriter("./src/main/resources/static/" + fileName, "UTF-8");
            writer.write(newValue);
            writer.close();
        } catch (Exception err) {
            System.out.println(err.toString());
        }
        return fileName;
    }

    public String readFile(String fileName) {
        try {
            return Files.readString(Paths.get("./src/main/resources/static/" + fileName));
        } catch (Exception err) {
            System.out.println(err.toString());
        }
        return fileName;
    }
}
