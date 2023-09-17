package com.lightema.xinydays.modules.users.repository;

import com.lightema.xinydays.modules.users.entities.AccountabilityPartner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountabilityPartnerRepository extends JpaRepository<AccountabilityPartner, Long> {
    List<AccountabilityPartner> getByProjectId(Long projectId);
}
