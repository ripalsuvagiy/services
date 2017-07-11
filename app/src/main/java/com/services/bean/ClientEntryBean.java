package com.services.bean;

/**
 * Created by ripal on 4/26/2017.
 */

public class ClientEntryBean {

    String id,userId,clientName,email,mobileNo,landlineNo,address1,address2,stateId,stateName,cityId,cityName,zipcode,contactPerson,servicesOfId,servicesTypeId,typeId,typeName,capacityId,capacityValue,quantity,manufacture,refillDate,refillDueDate,rate,provider;


    public ClientEntryBean(String id, String clientName, String providerName, String city, String mobileNo, String refilDueDate, String newORrefil) {
        this.id = id;
        this.clientName = clientName;
        this.provider = providerName;
        this.cityName = city;
        this.mobileNo = mobileNo;
        this.refillDueDate = refilDueDate;
        this.servicesTypeId = newORrefil;
    }


    public ClientEntryBean(String id, String userId, String clientName, String email, String mobileNo, String landlineNo, String address1, String address2, String stateId, String stateName, String cityId, String cityName, String zipcode, String contactPerson, String servicesOfId, String servicesTypeId, String typeId, String typeName, String capacityId, String capacityValue, String quantity, String manufacture, String refillDate, String refillDueDate, String rate, String provider) {
        this.id = id;
        this.userId = userId;
        this.clientName = clientName;
        this.email = email;
        this.mobileNo = mobileNo;
        this.landlineNo = landlineNo;
        this.address1 = address1;
        this.address2 = address2;
        this.stateId = stateId;
        this.stateName = stateName;
        this.cityId = cityId;
        this.cityName = cityName;
        this.zipcode = zipcode;
        this.contactPerson = contactPerson;
        this.servicesOfId = servicesOfId;
        this.servicesTypeId = servicesTypeId;
        this.typeId = typeId;
        this.typeName = typeName;
        this.capacityId = capacityId;
        this.capacityValue = capacityValue;
        this.quantity = quantity;
        this.manufacture = manufacture;
        this.refillDate = refillDate;
        this.refillDueDate = refillDueDate;
        this.rate = rate;
        this.provider = provider;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getLandlineNo() {
        return landlineNo;
    }

    public void setLandlineNo(String landlineNo) {
        this.landlineNo = landlineNo;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {

        this.zipcode = zipcode;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getServicesOfId() {
        return servicesOfId;
    }

    public void setServicesOfId(String servicesOfId) {
        this.servicesOfId = servicesOfId;
    }

    public String getServicesTypeId() {
        return servicesTypeId;
    }

    public void setServicesTypeId(String servicesTypeId) {
        this.servicesTypeId = servicesTypeId;
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

    public String getCapacityId() {
        return capacityId;
    }

    public void setCapacityId(String capacityId) {
        this.capacityId = capacityId;
    }

    public String getCapacityValue() {
        return capacityValue;
    }

    public void setCapacityValue(String capacityValue) {
        this.capacityValue = capacityValue;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public String getRefillDate() {
        return refillDate;
    }

    public void setRefillDate(String refillDate) {
        this.refillDate = refillDate;
    }

    public String getRefillDueDate() {
        return refillDueDate;
    }

    public void setRefillDueDate(String refillDueDate) {
        this.refillDueDate = refillDueDate;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}
