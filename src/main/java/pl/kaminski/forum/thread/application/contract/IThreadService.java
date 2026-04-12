package pl.kaminski.forum.thread.application.contract;

import pl.kaminski.forum.users.application.contract.authentication.AuthenticatedUser;

import java.util.UUID;

public interface IThreadService {
    CreateThreadResult createThread(CreateThreadRequest request, AuthenticatedUser authenticatedUser);
    ModifyThreadResult modifyThread(UUID id, ModifyThreadRequest request);
}
