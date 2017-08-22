package com.DesignQuads.modal;



public class HospitalModel {
    private String name;
    private String phone;
    private String subTitle;

    public HospitalModel() {
    }

    public HospitalModel(String name, String phone, String subTitle) {
        this.name = name;
        this.phone = phone;
        this.subTitle = subTitle;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
}
