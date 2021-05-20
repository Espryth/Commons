package me.espryth.commons.universal.module.exception;


import team.unnamed.reflect.identity.TypeReference;

public class ContainerKeyNotFoundException extends RuntimeException {
    public ContainerKeyNotFoundException(TypeReference<?> type) {
        super("An error occurred while getting a value from key " + type.getTypeName());
    }
}
