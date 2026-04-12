package pl.kaminski.forum.thread.application.contract;

import pl.kaminski.forum.users.application.contract.authentication.AuthenticatedUser;

public interface IThreadService {
    CreateThreadResult createThread(CreateThreadRequest request, AuthenticatedUser authenticatedUser);
    ModifyThreadResult modifyThread(ModifyThreadRequest request);
}
