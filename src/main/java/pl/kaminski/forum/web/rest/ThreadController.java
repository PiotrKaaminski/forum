package pl.kaminski.forum.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.kaminski.forum.thread.application.contract.*;
import pl.kaminski.forum.users.infrastructure.SecurityUtils;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ThreadController {

    private final IThreadService threadService;

    @PostMapping("/thread")
    CreateThreadResult createThread(@RequestBody CreateThreadRequest request) {
        var authenticatedUser = SecurityUtils.getAuthenticatedUser();
        return threadService.createThread(request, authenticatedUser);
    }

    @PatchMapping("/thread/{id}")
    ModifyThreadResult modifyThread(@PathVariable UUID id, @RequestBody ModifyThreadRequest request) {
        return threadService.modifyThread(id, request);
    }
}
