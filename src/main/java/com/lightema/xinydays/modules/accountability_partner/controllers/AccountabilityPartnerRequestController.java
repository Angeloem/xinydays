package com.lightema.xinydays.modules.accountability_partner.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/accountability-partner-request")
public class AccountabilityPartnerRequestController {
    @PostMapping("/request-user/{id}")
    public boolean requestUserToBeAccountabilityPartner(@PathVariable String id, Principal principal) {
        System.out.printf("The principal is %s%n", principal);
        return false;
    }

    @PostMapping("/respond-request")
    public boolean respondToAccountabilityPartnerRequest() {
        return false;
    }
}
