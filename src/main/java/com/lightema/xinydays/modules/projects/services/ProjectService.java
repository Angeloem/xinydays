package com.lightema.xinydays.modules.projects.services;

import com.lightema.xinydays.modules.projects.dtos.ProjectCreateDto;
import com.lightema.xinydays.modules.projects.entities.Project;
import com.lightema.xinydays.modules.projects.repositories.ProjectRepository;
import com.lightema.xinydays.modules.users.entities.User;
import com.lightema.xinydays.modules.users.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class ProjectService {
    public ProjectRepository projectRepository;
    public UserService userService;

    public List<Project> findAll() {
        return this.projectRepository.findAll();
    }

    public Project getProjectById(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        System.out.println("authentication: === " + authentication.getCredentials());

        return this.projectRepository.findById(id).orElse(null);
    }

    public Project createProject(ProjectCreateDto projectCreateDto) {
        final Project project = new Project();
        project.setName(projectCreateDto.getName());
        project.setDescription(projectCreateDto.getDescription());

        final Long userId = projectCreateDto.getUserId();

        System.out.println("projectCreateDto.getUserId(): " + projectCreateDto.getDueDate());

        //
        final Optional<User> user = this.userService.getUserById(userId);

        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        project.setUser(user.get());

        project.createdBy = project.getUser();

        final Date date = new Date(projectCreateDto.getDueDate().getTime());
        project.setDueDate(date);

        System.out.println("project.getDueDate(): " + project.getDueDate());

        final Project project1 = this.projectRepository.save(project);

        System.out.println(project1.toString());

        return project1;
    }

    public Optional<Project> updateProject(ProjectCreateDto projectCreateDto, Long id) {
        final Optional<Project> task = this.projectRepository.findById(id);

        if (task.isPresent()) {
            task.get().setName(projectCreateDto.getName());
            task.get().setDescription(projectCreateDto.getDescription());
            return Optional.of(this.projectRepository.save(task.get()));
        } else {
            return Optional.empty();
        }
    }

    public List<Project> getProjectsByUserId(Long userId) {
        return this.projectRepository.findByUserId(userId);
    }
}
