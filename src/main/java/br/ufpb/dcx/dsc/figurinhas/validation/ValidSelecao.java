package br.ufpb.dcx.dsc.figurinhas.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SelecaoValidator.class)
public @interface ValidSelecao {
    String message() default "Seleção inválida. A seleção deve ser uma das participantes da última Copa do Mundo.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}