package com.services.bean;

/**
 * Created by ripal on 5/4/2017.
 */

public class CapacityBean {

    String capacityId,typeId,capacityName;

    public CapacityBean(String capacityId, String typeId, String capacityName) {
        this.capacityId = capacityId;
        this.typeId = typeId;
        this.capacityName = capacityName;
    }

    public String getCapacityId() {
        return capacityId;
    }

    public void setCapacityId(String capacityId) {
        this.capacityId = capacityId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getCapacityName() {
        return capacityName;
    }

    public void setCapacityName(String capacityName) {
        this.capacityName = capacityName;
    }
}
