package com.tubes.kouveepetshop.Model;

public class PetTypeDAO {
    String id_jenis_hewan, nama, status;

    public PetTypeDAO(String id_jenis_hewan, String nama, String status) {
        this.id_jenis_hewan = id_jenis_hewan;
        this.nama = nama;
        this.status = status;
    }

    public String getId_jenis_hewan() {
        return id_jenis_hewan;
    }

    public String getNama() {
        return nama;
    }

    public String getStatus() {
        return status;
    }

    public void setId_jenis_hewan(String id_jenis_hewan) {
        this.id_jenis_hewan = id_jenis_hewan;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
