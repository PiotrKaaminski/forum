package pl.kaminski.forum.thread.application.contract;

import pl.kaminski.forum.commons.EntityId;

public record CreateThreadRequest(String title, String content, EntityId categoryId) {
}
