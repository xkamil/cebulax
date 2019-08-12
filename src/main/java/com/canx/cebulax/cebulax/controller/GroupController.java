package com.canx.cebulax.cebulax.controller;

import com.canx.cebulax.cebulax.controller.body.ResponseFactory;
import com.canx.cebulax.cebulax.dto.GroupCreateDTO;
import com.canx.cebulax.cebulax.dto.GroupSecretDTO;
import com.canx.cebulax.cebulax.security.CurrentUser;
import com.canx.cebulax.cebulax.security.UserPrincipal;
import com.canx.cebulax.cebulax.service.GroupService;
import com.canx.cebulax.cebulax.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService groupService;
    private final UserService userService;
    private final ResponseFactory responseFactory;

    public GroupController(GroupService groupService, UserService userService, ResponseFactory responseFactory) {
        this.groupService = groupService;
        this.userService = userService;
        this.responseFactory = responseFactory;
    }

    @PostMapping()
    ResponseEntity<?> createGroup(@Valid @RequestBody GroupCreateDTO group, @CurrentUser UserPrincipal user) {
        return responseFactory.created(groupService.createGroup(group, user.getId()));
    }

    @GetMapping()
    ResponseEntity<?> getAll() {
        return responseFactory.ok(groupService.findAll());
    }

    @PatchMapping("/join/{id}")
    ResponseEntity<?> joinGroup(@PathVariable("id") Long groupId, @RequestBody GroupSecretDTO secret, @CurrentUser UserPrincipal user) {
        groupService.joinGroup(groupId, secret.getSecret(), user.getId());
        return responseFactory.ok();
    }

    @PatchMapping("/leave/{id}")
    ResponseEntity<?> joinGroup(@PathVariable("id") Long groupId, @CurrentUser UserPrincipal user) {
        groupService.leaveGroup(groupId, user.getId());
        return responseFactory.ok();
    }

}
