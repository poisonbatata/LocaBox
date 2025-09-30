package br.edu.iff.ccc.locabox.exception;

import java.time.LocalDate;

public class RentalNotAvaible extends RuntimeException{
    public RentalNotAvaible(Long toolId, LocalDate startDate, LocalDate endDate) {
        super("Rental não está disponível para a tool de id " + toolId + " no período de " + startDate + " a " + endDate);
    }
}

