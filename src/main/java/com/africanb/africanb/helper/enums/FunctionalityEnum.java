package com.africanb.africanb.helper.enums;

public enum FunctionalityEnum {

    DEFAULT("DEFAULT"),

    // PROMOTEUR
    VIEW_PROMOTEUR("VIEW_PROMOTEUR"),
    CREATE_PROMOTEUR("CREATE_PROMOTEUR"),
    UPDATE_PROMOTEUR("UPDATE_PROMOTEUR"),
    DELETE_PROMOTEUR("DELETE_PROMOTEUR"),

    // PROGRAMMEIMMOBILIER
    VIEW_PROGRAMMEIMMOBILIER("VIEW_PROGRAMMEIMMOBILIER"),
    CREATE_PROGRAMMEIMMOBILIER("CREATE_PROGRAMMEIMMOBILIER"),
    UPDATE_PROGRAMMEIMMOBILIER("UPDATE_PROGRAMMEIMMOBILIER"),
    DELETE_PROGRAMMEIMMOBILIER("DELETE_PROGRAMMEIMMOBILIER");


    private final String value;
    public String getValue() {
        return value;
    }
    FunctionalityEnum(String value) {
        this.value = value;
    }

}
