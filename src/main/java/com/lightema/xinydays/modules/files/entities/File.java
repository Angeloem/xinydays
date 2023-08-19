package com.lightema.xinydays.modules.files.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.lightema.xinydays.modules.projects.entities.Project;
import com.lightema.xinydays.modules.projects.entities.Task;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "File")
@Table(
        name = "file"
)
@JsonPropertyOrder({"id", "name", "thumbnail", "task", "project"})
public class File {
    @Id
    @SequenceGenerator(
            name = "file_sequence",
            sequenceName = "file_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "file_sequence"
    )
    @Column(
            updatable = false
    )
    private Long id;

    @Column(
            nullable = false
    )
    private String name;

    // text data type in postgres
    @Column(
            nullable = false
    )
    private String thumbnail;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnore()
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnore()
    private Project project;
}
