package br.ufpb.dcx.dsc.figurinhas.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorResponse extends ApiErrorResponse {

    private final List<FieldError> fieldErrors = new ArrayList<>();

    public ValidationErrorResponse(Integer status, String error, String path) {
        super(status, error, path);
    }

    public void addFieldError(String field, String message) {
        this.fieldErrors.add(new FieldError(field, message));
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }

    private static class FieldError {
        private final String field;
        private final String message;

        FieldError(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() { return field; }
        public String getMessage() { return message; }
    }
}