package ru.lepeshka.laba1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import ru.lepeshka.laba1.Exceptions;
import ru.lepeshka.laba1.dto.CreateFileDto;
import ru.lepeshka.laba1.dto.UpdateFileDto;
import ru.lepeshka.laba1.service.FileService;

@RestController
@RequestMapping("/api/file")
public class FileController {
    @Autowired
    private FileService fileService;

    @PostMapping("/create")
    void createFile(@Valid @RequestBody CreateFileDto request, BindingResult bindingResult) {
        if (bindingResult.hasErrors() || request.getName() == null || request.getName().equals("")) {
            throw Exceptions.invalidName(request.getName());
        }
        fileService.createFile(request.getName(), request.getPath(), request.getValue());
    }

    @GetMapping("/read")
    String readFile(@RequestParam(value = "path", defaultValue = "/") String path,
            HttpServletResponse response) {
        String value = fileService.readFile(path);
        if (value == null) {
            response.setStatus(404);
            return null;
        } else {
            return value;
        }
    }

    @PutMapping("/update")
    void updateFile(@Valid @RequestBody UpdateFileDto request, BindingResult bindingResult) {
        fileService.updateFile(request.getPath(), request.getValue());
    }
}
