package ru.lepeshka.laba1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.lepeshka.laba1.repository.FileRepository;
import ru.lepeshka.laba1.repository.StorageRepository;
import ru.lepeshka.laba1.Exceptions;
import ru.lepeshka.laba1.models.*;

@Service
public class FileService {
    @Autowired
    private FileRepository fileRepository;

    private StorageRepository storageRepository;

    FileService() {
        storageRepository = new StorageRepository();
    }

    public void createFile(String name, String path, String value) {
        if (fileRepository.findByFullPath(getFullPath(path, name)) != null) {
            throw Exceptions.fileAlreadyExist(name, path);
        }

        File file = new File(name, path, false);

        if (!path.equals("/")) {
            File folder = fileRepository.findByFullPath(path);
            file.setParent(folder);
        }

        String fileLink = storageRepository.createFile(name, value);
        file.setLink(fileLink);
        fileRepository.save(file);
    }

    public String readFile(String fullPath) {
        File file = fileRepository.findByFullPath(fullPath);

        if (file == null) {
            return null;
        }

        if (file.getIsDir()) {
            throw Exceptions.objectIsFolder(fullPath);
        }

        return storageRepository.readFile(file.getLink());
    }

    public void updateFile(String fullPath, String newValue) {
        File file = fileRepository.findByFullPath(fullPath);

        if (file == null) {
            throw Exceptions.fileNotExists(fullPath);
        }

        if (file.getIsDir()) {
            throw Exceptions.objectIsFolder(fullPath);
        }

        storageRepository.updateFile(file.getLink(), newValue);
    }

    public void removeFile(String fullPath) {
        File file = fileRepository.findByFullPath(fullPath);

        if (file == null) {
            throw Exceptions.fileNotExists(fullPath);
        }

        if (file.getIsDir()) {
            throw Exceptions.objectIsFolder(fullPath);
        }

        fileRepository.delete(file);
    }

    String getFullPath(String path, String name) {
        if (path.equals("/")) {
            return "/" + name;
        } else {
            return path + "/" + name;
        }
    }
}
