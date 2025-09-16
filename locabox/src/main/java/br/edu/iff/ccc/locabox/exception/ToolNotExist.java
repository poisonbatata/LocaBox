package br.edu.iff.ccc.locabox.exception;

public class ToolNotExist extends RuntimeException {
    public ToolNotExist(Long id) {
        super("O produto com ID " + id + " não foi encontrado.");
    }

}
