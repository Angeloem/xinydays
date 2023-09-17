package com.lightema.xinydays.modules.users.controllers;

import com.lightema.xinydays.modules.users.entities.AccountabilityPartner;
import com.lightema.xinydays.modules.users.services.AccountabilityPartnerService;
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
