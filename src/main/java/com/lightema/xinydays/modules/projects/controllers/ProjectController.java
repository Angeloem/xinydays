package com.lightema.xinydays.modules.projects.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lightema.xinydays.core.domain.config.JSONConverterConfig;
import com.lightema.xinydays.modules.projects.dtos.ProjectCreateDto;
import com.lightema.xinydays.modules.projects.entities.Project;
import com.lightema.xinydays.modules.projects.services.ProjectService;
import lombok.AllArgsConstructor;
import net.minidev.json.JSONArray;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private ProjectService projectService;
    private JSONConverterConfig jsonConverterConfig;

    public ObjectMapper getObjectMapper() {
        return this.jsonConverterConfig.objectMapper();
    }

    public HttpHeaders getHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return headers;
    }

    @GetMapping("")
    public ResponseEntity<String> getAllProjects() {
        List projects = projectService.findAll();

        String out = "";
        try {
            out = getObjectMapper().writeValueAsString(projects);

            return new ResponseEntity<>(out, getHeaders(), HttpStatus.OK);
        } catch (JsonProcessingException e) {
            final String err = "Error processing: " + projects + ". Exception: " + e;
            return new ResponseEntity<>(err, getHeaders(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity getProjectById(@PathVariable String id) throws JsonProcessingException {
        final Optional<Project> project = projectService.getProjectById(Long.valueOf(id));

        if (project.isEmpty()) {
            return new ResponseEntity<String>("Project not found", getHeaders(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(
                getObjectMapper().writeValueAsString(project.get()),
                getHeaders(),
                HttpStatus.OK);
    }

    @GetMapping("user/{userId}")
    public List<Project> getProjectsByUserId(@PathVariable String userId) {
        return projectService.getProjectsByUserId(Long.valueOf(userId));
    }

    @PostMapping("")
    public Project createProject(
            @RequestBody ProjectCreateDto projectCreateDto
    ) {
        return projectService.createProject(projectCreateDto);
    }

    @PutMapping("{id}")
    public Optional<Project> updateProject(
            @RequestBody ProjectCreateDto projectCreateDto,
            @PathVariable String id
    ) {
        return projectService.updateProject(projectCreateDto, Long.valueOf(id));
    }
}
