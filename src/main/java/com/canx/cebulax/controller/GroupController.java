package com.canx.cebulax.controller;

import com.canx.cebulax.controller.body.ResponseFactory;
import com.canx.cebulax.dto.GroupCreateDTO;
import com.canx.cebulax.dto.GroupSecretDTO;
import com.canx.cebulax.security.CurrentUser;
import com.canx.cebulax.security.UserPrincipal;
import com.canx.cebulax.service.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService groupService;
    private final ResponseFactory responseFactory;

    public GroupController(GroupService groupService, ResponseFactory responseFactory) {
        this.groupService = groupService;
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

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteGroup(@PathVariable("id") Long groupId, @CurrentUser UserPrincipal user) {
        groupService.deleteGroup(groupId, user.getId());
        return responseFactory.ok();
    }

}
