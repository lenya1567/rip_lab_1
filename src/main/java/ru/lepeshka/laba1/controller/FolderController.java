package ru.lepeshka.laba1.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ru.lepeshka.laba1.Exceptions;
import ru.lepeshka.laba1.models.File;
import ru.lepeshka.laba1.service.FolderService;

@RestController
@RequestMapping("/api/dir")
public class FolderController {
    @Autowired
    private FolderService folderService;

    @GetMapping("/ls")
    List<File> findAllInFolder(@RequestParam(value = "path", defaultValue = "/") String path) {
        return folderService.getAll(path);
    }

    @GetMapping("/exist")
    boolean isFolderExist(@RequestParam(value = "path", defaultValue = "/") String path) {
        return folderService.isFolderExist(path);
    }

    @GetMapping("/tree")
    Map<String, Object> findFolderTree(@RequestParam(value = "path", defaultValue = "/") String path) {
        return folderService.getFolderTree(path);
    }

    @PostMapping("/create")
    File makeFolder(
            @RequestParam(value = "name", defaultValue = "example") String name,
            @RequestParam(value = "path", defaultValue = "/") String path) {

        if (name.contains("/")) {
            throw Exceptions.invalidName(name);
        }

        return folderService.createFolder(name, path);
    }
}
