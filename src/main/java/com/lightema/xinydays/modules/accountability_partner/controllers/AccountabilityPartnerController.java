package com.lightema.xinydays.modules.accountability_partner.controllers;

import com.lightema.xinydays.modules.accountability_partner.entities.AccountabilityPartner;
import com.lightema.xinydays.modules.accountability_partner.services.AccountabilityPartnerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/accountability-partner")
public class AccountabilityPartnerController {
    private AccountabilityPartnerService service;

    @GetMapping("/{id}")
    public List<AccountabilityPartner> getProjectAccountabilityPartners(
            @PathVariable(name = "id") Long id
    ) {
        return service.getProjectAccountabilityPartners(id);
    }
}
