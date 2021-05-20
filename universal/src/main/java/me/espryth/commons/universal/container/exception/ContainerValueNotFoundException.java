package me.espryth.commons.universal.container.exception;

import team.unnamed.reflect.identity.TypeReference;

public class ContainerValueNotFoundException extends RuntimeException {

    public ContainerValueNotFoundException(TypeReference<?> type) {
        super("An error occurred while getting the value " + type.getTypeName());
    }

}
