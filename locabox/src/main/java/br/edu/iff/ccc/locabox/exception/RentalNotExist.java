package br.edu.iff.ccc.locabox.exception;

public class RentalNotExist extends RuntimeException {
    public RentalNotExist(Long id) {
        super("Rental não encontrada: " + id);
    }
}