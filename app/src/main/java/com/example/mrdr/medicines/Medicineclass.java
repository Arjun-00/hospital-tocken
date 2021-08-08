package com.example.mrdr.medicines;

public class Medicineclass {
    private String mdname;
    private String price;
    private String company;


    public String getMdname() {
        return mdname;
    }

    public void setMdname(String mdname) {
        this.mdname = mdname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Medicineclass(String mdname, String price, String company){
        this.mdname = mdname;
        this.price = price;
        this.company = company;


    }
}
