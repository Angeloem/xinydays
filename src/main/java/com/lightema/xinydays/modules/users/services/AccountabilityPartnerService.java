package com.lightema.xinydays.modules.users.services;

import com.lightema.xinydays.modules.users.entities.AccountabilityPartner;
import com.lightema.xinydays.modules.users.repository.AccountabilityPartnerRepository;
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
