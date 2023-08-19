package com.lightema.xinydays.modules.files.controllers;

import com.lightema.xinydays.modules.files.services.FileService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController("/files")
public class FileController {
    private FileService fileService;



}
