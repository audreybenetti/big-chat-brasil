package br.com.bcb.api.auth;

public record LoginResponse(String name, String email, String token, String role) {}
