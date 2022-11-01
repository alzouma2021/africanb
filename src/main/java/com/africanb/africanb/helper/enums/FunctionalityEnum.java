package com.africanb.africanb.helper.enums;

public enum FunctionalityEnum {

    DEFAULT("DEFAULT"),

    // PAYS
    VIEW_PAYS("VIEW_PAYS"),
    CREATE_PAYS("CREATE_PAYS"),
    UPDATE_PAYS("UPDATE_PAYS"),
    DELETE_PAYS("DELETE_PAYS"),

    //FamilleStatusUtil
    VIEW_FAMILLESTATUSUTIL("VIEW_FAMILLESTATUSUTIL"),
    CREATE_FAMILLESTATUSUTIL("CREATE_FAMILLESTATUSUTIL"),
    UPDATE_FAMILLESTATUSUTIL("UPDATE_FAMILLESTATUSUTIL"),
    DELETE_FAMILLESTATUSUTIL("DELETE_FAMILLESTATUSUTIL");


    private final String value;
    public String getValue() {
        return value;
    }
    FunctionalityEnum(String value) {
        this.value = value;
    }

}
