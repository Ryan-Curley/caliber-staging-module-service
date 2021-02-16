package com.revature.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.backend.service.ManagerService;

@RestController
@RequestMapping("/manager")
public class ManagerController {
  private final ManagerService managerService;

  public ManagerController(ManagerService managerService) {
    this.managerService = managerService;
  }

	@PostMapping
	public ResponseEntity<String> getLoggedInManager(@RequestBody String email) {
    String managerId =
        Integer.toString(managerService.getManagerByEmail(email).getId());
    return new ResponseEntity<>(managerId, HttpStatus.OK);
	}
}
