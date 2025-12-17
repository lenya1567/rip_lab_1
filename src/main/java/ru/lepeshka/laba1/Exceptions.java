package ru.lepeshka.laba1;

public class Exceptions {
    public static RuntimeException folderNotExists(String fullPath) {
        return new RuntimeException(String.format("Directory [%s] does not exist!", fullPath));
    }

    public static RuntimeException folderAlreadyExist(String name, String path) {
        return new RuntimeException(String.format("Directory [%s/%s] already exists!", path, name));
    }

    public static RuntimeException fileAlreadyExist(String name, String path) {
        return new RuntimeException(String.format("File [%s/%s] already exists!", path, name));
    }

    public static RuntimeException fileNotExists(String fullPath) {
        return new RuntimeException(String.format("File [%s] does not exist!", fullPath));
    }

    public static RuntimeException objectIsFolder(String fullPath) {
        return new RuntimeException(String.format("[%s] is directory!", fullPath));
    }

    public static RuntimeException invalidName(String name) {
        return new RuntimeException(String.format("Invalid name [%s]!", name));
    }
}
