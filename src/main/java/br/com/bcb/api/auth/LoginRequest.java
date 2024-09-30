package br.com.bcb.api.auth;

public record LoginRequest (String name, String email, String password) {}
