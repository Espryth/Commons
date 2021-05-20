package me.espryth.commons.universal.module.exception;

import team.unnamed.reflect.identity.TypeReference;

public class ContainerAlreadyBindedException extends RuntimeException {
    public ContainerAlreadyBindedException(TypeReference<?> type,
                                           String identifier) {
        super("An error occurred! Already existe a value " + type.getTypeName() + " with identifier " + identifier);
    }
}
