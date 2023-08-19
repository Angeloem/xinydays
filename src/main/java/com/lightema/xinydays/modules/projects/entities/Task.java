package com.lightema.xinydays.modules.projects.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.lightema.xinydays.core.data.entities.Auditable;
import com.lightema.xinydays.modules.files.entities.File;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "task")
@JsonPropertyOrder({"id", "title", "description", "completed", "project"})
public class Task extends Auditable {
    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = true)
    private String description;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean completed = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore()
    public Project project;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore()
    private List<File> files = new ArrayList<File>();


}
