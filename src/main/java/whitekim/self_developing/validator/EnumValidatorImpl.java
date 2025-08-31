package whitekim.self_developing.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.Set;

public class EnumValidatorImpl implements ConstraintValidator<EnumValidator, String> {

    Set<String> valueSet = null;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return valueSet.contains(value.toUpperCase());
    }

    @Override
    public void initialize(EnumValidator constraintAnnotation) {
        valueSet = new HashSet<>();
        Class<? extends Enum<?>> enumClass = constraintAnnotation.targetEnum();

        @SuppressWarnings("rawtypes")
        Enum[] enumValArr = enumClass.getEnumConstants();

        for (@SuppressWarnings("rawtypes") Enum enumVal : enumValArr) {
            valueSet.add(enumVal.toString().toUpperCase());
        }
    }
}