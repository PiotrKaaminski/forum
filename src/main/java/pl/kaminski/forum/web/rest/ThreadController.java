package pl.kaminski.forum.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kaminski.forum.users.infrastructure.SecurityUtils;
import pl.kaminski.forum.thread.application.contract.CreateThreadRequest;
import pl.kaminski.forum.thread.application.contract.CreateThreadResult;
import pl.kaminski.forum.thread.application.contract.IThreadService;

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
}
