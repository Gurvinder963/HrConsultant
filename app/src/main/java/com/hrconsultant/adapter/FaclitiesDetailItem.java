package com.hrconsultant.adapter;



public class FaclitiesDetailItem {
    public int id ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFaclityName() {
        return faclityName;
    }

    public void setFaclityName(String faclityName) {
        this.faclityName = faclityName;
    }

    public String faclityName;

    public String getMemberRegNo() {
        return memberRegNo;
    }

    public void setMemberRegNo(String memberRegNo) {
        this.memberRegNo = memberRegNo;
    }

    public String memberRegNo;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String gender;

    public int getIsScreeningAllow() {
        return isScreeningAllow;
    }

    public void setIsScreeningAllow(int isScreeningAllow) {
        this.isScreeningAllow = isScreeningAllow;
    }

    private int isScreeningAllow;
}
