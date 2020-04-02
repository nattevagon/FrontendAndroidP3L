package com.tubes.kouveepetshop.Model;

public class CustomerDAO {
    String ID_CUSTOMER, NAMA, TGL_LAHIR, ALAMAT, NO_TELP;

    public CustomerDAO(String ID_CUSTOMER, String NAMA, String TGL_LAHIR, String ALAMAT, String NO_TELP) {
        this.ID_CUSTOMER = ID_CUSTOMER;
        this.NAMA = NAMA;
        this.TGL_LAHIR = TGL_LAHIR;
        this.ALAMAT = ALAMAT;
        this.NO_TELP = NO_TELP;
    }

    public String getID_CUSTOMER() {
        return ID_CUSTOMER;
    }

    public String getNAMA() {
        return NAMA;
    }

    public String getTGL_LAHIR() {
        return TGL_LAHIR;
    }

    public String getALAMAT() {
        return ALAMAT;
    }

    public String getNO_TELP() {
        return NO_TELP;
    }

    public void setID_CUSTOMER(String ID_CUSTOMER) {
        this.ID_CUSTOMER = ID_CUSTOMER;
    }

    public void setNAMA(String NAMA) {
        this.NAMA = NAMA;
    }

    public void setTGL_LAHIR(String TGL_LAHIR) {
        this.TGL_LAHIR = TGL_LAHIR;
    }

    public void setALAMAT(String ALAMAT) {
        this.ALAMAT = ALAMAT;
    }

    public void setNO_TELP(String NO_TELP) {
        this.NO_TELP = NO_TELP;
    }
}
