package com.lightema.xinydays.modules.accountability_partner.entities;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.lightema.xinydays.core.data.entities.Auditable;
import com.lightema.xinydays.modules.projects.entities.Project;
import com.lightema.xinydays.modules.users.entities.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@JsonPropertyOrder({"user_id", "accountability_partner", "project"})
@Table(name = "accountability_partners")
public class AccountabilityPartner extends Auditable {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    public User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountability_partner", nullable = false)
    public User accountabilityPartner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project", nullable = false)
    public Project project;
}
