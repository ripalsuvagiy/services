package com.services.bean;

/**
 * Created by ripal on 5/4/2017.
 */

public class TypeBean {
    String typeId,typeName;

    public TypeBean(String typeId, String typeName) {
        this.typeName = typeName;
        this.typeId = typeId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
