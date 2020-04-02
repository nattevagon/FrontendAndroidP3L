package com.tubes.kouveepetshop.Model;

public class ServiceDAO {
    String ID_LAYANAN, NAMA;

    public ServiceDAO(String ID_LAYANAN, String NAMA) {
        this.ID_LAYANAN = ID_LAYANAN;
        this.NAMA = NAMA;
    }

    public String getID_LAYANAN() {
        return ID_LAYANAN;
    }

    public String getNAMA() {
        return NAMA;
    }

    public void setID_LAYANAN(String ID_LAYANAN) {
        this.ID_LAYANAN = ID_LAYANAN;
    }

    public void setNAMA(String NAMA) {
        this.NAMA = NAMA;
    }
}
