package ru.lepeshka.laba1.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.lepeshka.laba1.repository.FileRepository;
import ru.lepeshka.laba1.Exceptions;
import ru.lepeshka.laba1.models.*;

@Service
public class FolderService {
    @Autowired
    private FileRepository fileRepository;

    public File createFolder(String name, String path) {
        if (isFolderExist(getFullPath(path, name))) {
            throw Exceptions.folderAlreadyExist(name, path);
        }

        File newFolder = new File(name, path, true);

        if (!path.equals("/")) {
            File parentFolder = fileRepository.findByFullPath(path);

            if (parentFolder == null) {
                throw Exceptions.folderNotExists(path);
            }

            newFolder.setParent(parentFolder);
        }

        return fileRepository.save(newFolder);
    }

    public File getFolderByName(String folderPath) {
        return fileRepository.findByFullPath(folderPath);
    }

    public boolean isFolderExist(String folderPath) {
        return folderPath.equals("/") || fileRepository.findIfFileExist(folderPath);
    }

    public List<File> getAll(String folderPath) {
        return fileRepository.findByPath(folderPath);
    }

    public Map<String, Object> getFolderTree(String path) {
        if (path.equals("/")) {
            return getTree(null);
        }

        File baseFolder = fileRepository.findByFullPath(path);
        if (baseFolder == null) {
            throw Exceptions.folderNotExists(path);
        }
        return getTree(baseFolder);
    }

    public void renameFolder(String fullPath, String newName) {
        File folder = fileRepository.findByFullPath(fullPath);
        if (folder == null) {
            throw Exceptions.folderNotExists(fullPath);
        }
        folder.setName(newName);
        fileRepository.save(folder);
    }

    Map<String, Object> getTree(File folder) {
        Map<String, Object> tree = new HashMap<>();
        for (File f : folder == null ? fileRepository.findByPath("/") : folder.getFiles()) {
            if (f.getIsDir()) {
                tree.put("/" + f.getName(), getTree(f));
            } else {
                tree.put(f.getName(), "[FILE]");
            }
        }
        return tree;
    }

    String getFullPath(String path, String name) {
        if (path.equals("/")) {
            return "/" + name;
        } else {
            return path + "/" + name;
        }
    }
}
