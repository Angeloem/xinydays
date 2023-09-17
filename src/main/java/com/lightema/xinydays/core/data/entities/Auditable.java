package com.lightema.xinydays.core.data.entities;

import com.lightema.xinydays.modules.users.entities.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
    @Column(name = "id")
    public Long id;

    @Column(name="active", nullable = true)
    public boolean active = true;

    @CreatedDate
    @Column(name = "createdAt")
    public java.time.LocalDateTime createdAt;

    @Column(name = "updatedAt")
    @LastModifiedDate
    public java.time.LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    public User createdBy;
}
