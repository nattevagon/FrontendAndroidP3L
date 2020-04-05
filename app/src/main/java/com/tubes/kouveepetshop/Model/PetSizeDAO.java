package com.tubes.kouveepetshop.Model;

public class PetSizeDAO {
    String id_ukuran_hewan, nama;

    public PetSizeDAO(String id_ukuran_hewan, String nama) {
        this.id_ukuran_hewan = id_ukuran_hewan;
        this.nama = nama;
    }

    public String getId_ukuran_hewan() {
        return id_ukuran_hewan;
    }

    public String getNama() {
        return nama;
    }

    public void setId_ukuran_hewan(String id_ukuran_hewan) {
        this.id_ukuran_hewan = id_ukuran_hewan;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
