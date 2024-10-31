package com.br.fatec.rotamemorias.dto;

public class ErrorField {

    @SuppressWarnings("unused")
    private String field;
    @SuppressWarnings("unused")
    private String message;

    public ErrorField(String field, String message) {
        this.field = field;
        this.message = message;
    }

    // getters and setters
}

