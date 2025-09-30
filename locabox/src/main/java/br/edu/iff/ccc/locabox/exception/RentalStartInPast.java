package br.edu.iff.ccc.locabox.exception;

import java.time.LocalDate;

public class RentalStartInPast extends RuntimeException {
    public RentalStartInPast(LocalDate startDate) {
        super("Não é possível iniciar um aluguel com uma data passada: " + startDate);
    }
}