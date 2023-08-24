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
import java.util.*;


@AllArgsConstructor
@Service
public class ProjectService {
    public ProjectRepository projectRepository;
    public UserService userService;

    private User getCurrentUser() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = (User) authentication.getPrincipal();

        return this.userService.getUserById(user.getId()).orElseThrow(Exception::new);
    }

    public List findAll() {
        try {
            final User user = getCurrentUser();
            return this.projectRepository.findByUserId(user.getId());
        } catch (Exception e) {
            final Map<String, Object> out = new HashMap<>();
            out.put("error", e);
            out.put("status", "error");
            return List.of(out);
        }
    }

    public Optional<Project> getProjectById(Long id)  {
        final Project project = this.projectRepository.findById(id).orElse(null);

        assert project != null;
        try {
            if (Objects.equals(project.user.getId(), getCurrentUser().getId())) {
                return Optional.of(project);
            }
        }
        catch (Exception e) {
            return Optional.empty();
        }

        return Optional.empty();
    }

    public Project createProject(ProjectCreateDto projectCreateDto) {
        final Project project = new Project();
        project.setName(projectCreateDto.getName());
        project.setDescription(projectCreateDto.getDescription());

        final Long userId = projectCreateDto.getUserId();

        //
        final Optional<User> user = this.userService.getUserById(userId);

        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        project.setUser(user.get());

        project.createdBy = project.getUser();

        final Date date = new Date(projectCreateDto.getDueDate().getTime());
        project.setDueDate(date);

        final Project project1 = this.projectRepository.save(project);

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
