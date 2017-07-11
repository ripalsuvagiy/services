package com.services.bean;

/**
 * Created by ripal on 5/3/2017.
 */

public class StateBean {

    String stateID,stateName;

    public StateBean(String stateID, String stateName) {
        this.stateID = stateID;
        this.stateName = stateName;
    }

    public String getStateID() {
        return stateID;
    }

    public void setStateID(String stateID) {
        this.stateID = stateID;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}
