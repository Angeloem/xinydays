package com.lightema.xinydays.modules.users.repository;

import com.lightema.xinydays.modules.users.entities.AccountabilityPartnerRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountabilityPartnerRequestRepository extends JpaRepository<AccountabilityPartnerRequest, Long> {
}
