package com.lightema.xinydays.modules.users.entities;

import com.lightema.xinydays.core.data.entities.Auditable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table()
@Getter
@Setter
public class AccountabilityPartnerRequest extends Auditable {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requested_user_id", nullable = false)
    public User requestedUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requesting_user_id", nullable = false)
    public User requestingUser;

    @Column(nullable = false)
    public boolean status;
}
