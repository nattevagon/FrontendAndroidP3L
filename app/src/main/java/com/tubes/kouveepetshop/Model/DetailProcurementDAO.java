package com.tubes.kouveepetshop.Model;

public class DetailProcurementDAO {
    String id_detail_p, id_pengadaan, id_produk, produk, stok, harga, jumlah, total;

    public DetailProcurementDAO(String id_detail_p, String id_pengadaan, String id_produk, String produk, String stok, String harga, String jumlah, String total) {
        this.id_detail_p = id_detail_p;
        this.id_pengadaan = id_pengadaan;
        this.id_produk = id_produk;
        this.produk = produk;
        this.stok = stok;
        this.harga = harga;
        this.jumlah = jumlah;
        this.total = total;
    }

    public String getId_detail_p() {
        return id_detail_p;
    }

    public String getId_pengadaan() {
        return id_pengadaan;
    }

    public String getId_produk() {
        return id_produk;
    }

    public String getProduk() {
        return produk;
    }

    public String getStok() {
        return stok;
    }

    public String getHarga() {
        return harga;
    }

    public String getJumlah() {
        return jumlah;
    }

    public String getTotal() {
        return total;
    }

    public void setId_detail_p(String id_detail_p) {
        this.id_detail_p = id_detail_p;
    }

    public void setId_pengadaan(String id_pengadaan) {
        this.id_pengadaan = id_pengadaan;
    }

    public void setId_produk(String id_produk) {
        this.id_produk = id_produk;
    }

    public void setProduk(String produk) {
        this.produk = produk;
    }

    public void setStok(String stok) {
        this.stok = stok;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
