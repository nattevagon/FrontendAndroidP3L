package com.tubes.kouveepetshop.Model;

public class ServiceDAO {
    String id_layanan, nama, id_ukuran_hewan, ukuran_hewan, harga;

    public ServiceDAO(String id_layanan, String nama, String id_ukuran_hewan, String ukuran_hewan, String harga) {
        this.id_layanan = id_layanan;
        this.nama = nama;
        this.id_ukuran_hewan = id_ukuran_hewan;
        this.ukuran_hewan = ukuran_hewan;
        this.harga = harga;
    }

    public String getId_layanan() {
        return id_layanan;
    }

    public String getNama() {
        return nama;
    }

    public String getId_ukuran_hewan() {
        return id_ukuran_hewan;
    }

    public String getUkuran_hewan() {
        return ukuran_hewan;
    }

    public String getHarga() {
        return harga;
    }

    public void setId_layanan(String id_layanan) {
        this.id_layanan = id_layanan;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setId_ukuran_hewan(String id_ukuran_hewan) {
        this.id_ukuran_hewan = id_ukuran_hewan;
    }

    public void setUkuran_hewan(String ukuran_hewan) {
        this.ukuran_hewan = ukuran_hewan;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }
}
