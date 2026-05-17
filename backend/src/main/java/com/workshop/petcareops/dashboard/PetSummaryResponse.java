package com.workshop.petcareops.dashboard;

public record PetSummaryResponse(
        String name,
        String species,
        String breed,
        String careFlag
) {
}