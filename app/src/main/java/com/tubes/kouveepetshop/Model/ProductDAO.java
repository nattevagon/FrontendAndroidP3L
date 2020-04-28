package com.tubes.kouveepetshop.Model;

public class ProductDAO {
    String id_produk, nama, harga, minimal, stok, satuan, gambar;

    public ProductDAO(String id_produk, String nama, String harga, String minimal, String stok, String satuan, String gambar) {
        this.id_produk = id_produk;
        this.nama = nama;
        this.harga = harga;
        this.minimal = minimal;
        this.stok = stok;
        this.satuan = satuan;
        this.gambar = gambar;
    }


    public String getId_produk() {
        return id_produk;
    }

    public String getNama() {
        return nama;
    }

    public String getHarga() {
        return harga;
    }

    public String getMinimal() {
        return minimal;
    }

    public String getStok() {
        return stok;
    }

    public String getSatuan() {
        return satuan;
    }

    public String getGambar() {
        return gambar;
    }

    public void setId_produk(String id_produk) {
        this.id_produk = id_produk;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public void setMinimal(String minimal) {
        this.minimal = minimal;
    }

    public void setStok(String stok) {
        this.stok = stok;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}
