package com.example.perpus_gui.models;


import javafx.beans.property.*;

public class LibraryMember {
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty contactDetails = new SimpleStringProperty();
    private final BooleanProperty memberStatus = new SimpleBooleanProperty();

    public LibraryMember() {}

    public LibraryMember(String name, String contactDetails, boolean memberStatus) {
        this.name.set(name);
        this.contactDetails.set(contactDetails);
        this.memberStatus.set(memberStatus);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getContactDetails() {
        return contactDetails.get();
    }

    public void setContactDetails(String contactDetails) {
        this.contactDetails.set(contactDetails);
    }

    public StringProperty contactDetailsProperty() {
        return contactDetails;
    }

    public boolean isMemberStatus() {
        return memberStatus.get();
    }

    public void setMemberStatus(boolean memberStatus) {
        this.memberStatus.set(memberStatus);
    }

    public BooleanProperty memberStatusProperty() {
        return memberStatus;
    }
}

