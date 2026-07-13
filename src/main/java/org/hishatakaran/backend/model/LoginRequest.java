package org.hishatakaran.backend.model;

public record LoginRequest(
        String login,
        String password
) {
}