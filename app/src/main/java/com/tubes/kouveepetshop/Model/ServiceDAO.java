package com.tubes.kouveepetshop.Model;

public class ServiceDAO {
    String id_layanan, nama;

    public ServiceDAO(String id_layanan, String nama) {
        this.id_layanan = id_layanan;
        this.nama = nama;
    }

    public String getId_layanan() {
        return id_layanan;
    }

    public String getNama() {
        return nama;
    }

    public void setId_layanan(String id_layanan) {
        this.id_layanan = id_layanan;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
