package br.edu.iff.ccc.locabox.exception;

public class UserNotExist extends RuntimeException {
    public UserNotExist(Long id) {
        super("Usuário não encontrado: " + id);
    }
    public UserNotExist(String email) {
        super("Usuário não encontrado por email: " + email);
    }
}