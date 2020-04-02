package com.tubes.kouveepetshop.Model;

public class PetDAO {
    String ID_HEWAN, ID_JENIS_HEWAN, ID_UKURAN_HEWAN, ID_CUSTOMER, NAMA, TGL_LAHIR, JENIS_HEWAN, UKURAN_HEWAN, CUSTOMER;

    public PetDAO(String ID_HEWAN, String ID_JENIS_HEWAN, String ID_UKURAN_HEWAN, String ID_CUSTOMER, String NAMA, String TGL_LAHIR, String JENIS_HEWAN, String UKURAN_HEWAN, String CUSTOMER) {
        this.ID_HEWAN = ID_HEWAN;
        this.ID_JENIS_HEWAN = ID_JENIS_HEWAN;
        this.ID_UKURAN_HEWAN = ID_UKURAN_HEWAN;
        this.ID_CUSTOMER = ID_CUSTOMER;
        this.NAMA = NAMA;
        this.TGL_LAHIR = TGL_LAHIR;
        this.JENIS_HEWAN = JENIS_HEWAN;
        this.UKURAN_HEWAN = UKURAN_HEWAN;
        this.CUSTOMER = CUSTOMER;
    }

    public String getID_HEWAN() {
        return ID_HEWAN;
    }

    public String getID_JENIS_HEWAN() {
        return ID_JENIS_HEWAN;
    }

    public String getID_UKURAN_HEWAN() {
        return ID_UKURAN_HEWAN;
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

    public String getJENIS_HEWAN() {
        return JENIS_HEWAN;
    }

    public String getUKURAN_HEWAN() {
        return UKURAN_HEWAN;
    }

    public String getCUSTOMER() {
        return CUSTOMER;
    }

    public void setID_HEWAN(String ID_HEWAN) {
        this.ID_HEWAN = ID_HEWAN;
    }

    public void setID_JENIS_HEWAN(String ID_JENIS_HEWAN) {
        this.ID_JENIS_HEWAN = ID_JENIS_HEWAN;
    }

    public void setID_UKURAN_HEWAN(String ID_UKURAN_HEWAN) {
        this.ID_UKURAN_HEWAN = ID_UKURAN_HEWAN;
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

    public void setJENIS_HEWAN(String JENIS_HEWAN) {
        this.JENIS_HEWAN = JENIS_HEWAN;
    }

    public void setUKURAN_HEWAN(String UKURAN_HEWAN) {
        this.UKURAN_HEWAN = UKURAN_HEWAN;
    }

    public void setCUSTOMER(String CUSTOMER) {
        this.CUSTOMER = CUSTOMER;
    }
}
