package pl.kaminski.forum.thread.application.contract;


import java.util.UUID;

public record ModifyThreadRequest(String title, String content, UUID categoryId) {
}
