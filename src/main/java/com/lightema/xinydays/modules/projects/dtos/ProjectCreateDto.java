package com.lightema.xinydays.modules.projects.dtos;

import lombok.Getter;

import java.util.Date;


@Getter
public class ProjectCreateDto {
    public String name;
    public String description;
    public Date dueDate;
    public Long userId;
}
