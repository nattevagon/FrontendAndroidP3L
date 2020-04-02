package com.tubes.kouveepetshop.Model;

public class PetSizeDAO {
    String ID_UKURAN_HEWAN, NAMA;

    public PetSizeDAO(String ID_UKURAN_HEWAN, String NAMA) {
        this.ID_UKURAN_HEWAN = ID_UKURAN_HEWAN;
        this.NAMA = NAMA;
    }

    public String getID_UKURAN_HEWAN() {
        return ID_UKURAN_HEWAN;
    }

    public String getNAMA() {
        return NAMA;
    }

    public void setID_UKURAN_HEWAN(String ID_UKURAN_HEWAN) {
        this.ID_UKURAN_HEWAN = ID_UKURAN_HEWAN;
    }

    public void setNAMA(String NAMA) {
        this.NAMA = NAMA;
    }
}
