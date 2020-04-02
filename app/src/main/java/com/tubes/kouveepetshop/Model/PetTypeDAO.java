package com.tubes.kouveepetshop.Model;

public class PetTypeDAO {
    String ID_JENIS_HEWAN, NAMA;

    public PetTypeDAO(String ID_JENIS_HEWAN, String NAMA) {
        this.ID_JENIS_HEWAN = ID_JENIS_HEWAN;
        this.NAMA = NAMA;
    }

    public String getID_JENIS_HEWAN() {
        return ID_JENIS_HEWAN;
    }

    public String getNAMA() {
        return NAMA;
    }

    public void setID_JENIS_HEWAN(String ID_JENIS_HEWAN) {
        this.ID_JENIS_HEWAN = ID_JENIS_HEWAN;
    }

    public void setNAMA(String NAMA) {
        this.NAMA = NAMA;
    }
}
