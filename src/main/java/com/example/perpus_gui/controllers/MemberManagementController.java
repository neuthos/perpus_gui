package com.example.perpus_gui.controllers;

import com.example.perpus_gui.models.LibraryMember;
import javafx.collections.ObservableList;

public class MemberManagementController {
    private ObservableList<LibraryMember> memberList;

    public MemberManagementController(ObservableList<LibraryMember> memberList) {
        this.memberList = memberList;
    }

    public void addMember(LibraryMember member) {
        memberList.add(member);
    }

    public void updateMember(LibraryMember member, int index) {
        memberList.set(index, member);
    }

    public void deleteMember(LibraryMember member) {
        memberList.remove(member);
    }
}

