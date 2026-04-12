package pl.kaminski.forum.thread.application.contract;

import pl.kaminski.forum.commons.AuthenticatedUser;

public interface IThreadService {
    CreateThreadResult createThread(CreateThreadRequest request, AuthenticatedUser authenticatedUser);
    ModifyThreadResult modifyThread(ModifyThreadRequest request);
}
