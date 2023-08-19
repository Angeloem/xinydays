package com.lightema.xinydays.modules.files.services;

import com.lightema.xinydays.modules.files.repositories.FileRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FileService {
    private FileRepository fileRepository;

    public void saveFile() {
    }
}
