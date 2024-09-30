package br.com.bcb.api.auth;

public record RegisterRequest(String name, String email, String password, String role) {
}
