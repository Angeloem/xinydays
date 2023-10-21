package com.lightema.xinydays.modules.accountability_partner.services;

import com.lightema.xinydays.modules.accountability_partner.entities.AccountabilityPartner;
import com.lightema.xinydays.modules.accountability_partner.repositories.AccountabilityPartnerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AccountabilityPartnerService {
    public AccountabilityPartnerRepository accountabilityPartnerRepository;

    public List<AccountabilityPartner> getProjectAccountabilityPartners(Long projectId) {
        return accountabilityPartnerRepository.getByProjectId(projectId);
    }
}
