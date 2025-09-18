package br.ufpb.dcx.dsc.figurinhas.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class SelecaoValidator implements ConstraintValidator<ValidSelecao, String> {

    private final List<String> selecoesValidas = Arrays.asList(
            "Qatar", "Ecuador", "Senegal", "Netherlands",
            "England", "Iran", "USA", "Wales",
            "Argentina", "Saudi Arabia", "Mexico", "Poland",
            "France", "Australia", "Denmark", "Tunisia",
            "Spain", "Costa Rica", "Germany", "Japan",
            "Belgium", "Canada", "Morocco", "Croatia",
            "Brazil", "Serbia", "Switzerland", "Cameroon",
            "Portugal", "South Korea", "Uruguay", "Ghana"
    );

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return true;
        }
        return selecoesValidas.stream().anyMatch(selecao -> selecao.equalsIgnoreCase(value));
    }
}