package com.example.sportszilla;

public class contactList {
    private String contactName, phoneNo, contactPerson;
    private boolean expanded;


    public contactList(String contactName, String phoneNo, String contactPerson) {
        this.contactName = contactName;
        this.phoneNo = phoneNo;
        this.contactPerson = contactPerson;

    }

    public String getContactName() {

        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }
}
