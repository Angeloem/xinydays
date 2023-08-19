package com.lightema.xinydays.modules.projects.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lightema.xinydays.core.domain.config.JSONConverterConfig;
import com.lightema.xinydays.modules.projects.dtos.ProjectCreateDto;
import com.lightema.xinydays.modules.projects.entities.Project;
import com.lightema.xinydays.modules.projects.services.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/projects")
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
        List<Project> projects = projectService.findAll();

        String out = "";
        try {
            out = getObjectMapper().writeValueAsString(projects);
        } catch (JsonProcessingException e) {
            System.err.println("Error processing: " + projects + ". Exception: " + e);
            final String err = "Error processing: " + projects + ". Exception: " + e;
            return new ResponseEntity(err, getHeaders(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(out, getHeaders(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity getProjectById(@PathVariable String id) throws JsonProcessingException {
        final Project project = projectService.getProjectById(Long.valueOf(id));
        return new ResponseEntity(getObjectMapper().writeValueAsString(project), getHeaders(), HttpStatus.OK);
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
