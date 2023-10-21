package com.lightema.xinydays.modules.accountability_partner.repositories;

import com.lightema.xinydays.modules.accountability_partner.entities.AccountabilityPartnerRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountabilityPartnerRequestRepository extends JpaRepository<AccountabilityPartnerRequest, Long> {
}
