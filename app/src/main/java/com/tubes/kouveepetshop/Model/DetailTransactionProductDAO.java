package com.tubes.kouveepetshop.Model;

public class DetailTransactionProductDAO {
    String id_detail_tp, id_tp, id_produk, kode_transaksi, produk, jumlah, harga, total;

    public DetailTransactionProductDAO(String id_detail_tp, String id_tp, String id_produk, String kode_transaksi, String produk, String jumlah, String harga, String total) {
        this.id_detail_tp = id_detail_tp;
        this.id_tp = id_tp;
        this.id_produk = id_produk;
        this.kode_transaksi = kode_transaksi;
        this.produk = produk;
        this.jumlah = jumlah;
        this.harga = harga;
        this.total = total;
    }

    public String getId_detail_tp() {
        return id_detail_tp;
    }

    public String getId_tp() {
        return id_tp;
    }

    public String getId_produk() {
        return id_produk;
    }

    public String getKode_transaksi() {
        return kode_transaksi;
    }

    public String getProduk() {
        return produk;
    }

    public String getJumlah() {
        return jumlah;
    }

    public String getHarga() {
        return harga;
    }

    public String getTotal() {
        return total;
    }

    public void setId_detail_tp(String id_detail_tp) {
        this.id_detail_tp = id_detail_tp;
    }

    public void setId_tp(String id_tp) {
        this.id_tp = id_tp;
    }

    public void setId_produk(String id_produk) {
        this.id_produk = id_produk;
    }

    public void setKode_transaksi(String kode_transaksi) {
        this.kode_transaksi = kode_transaksi;
    }

    public void setProduk(String produk) {
        this.produk = produk;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
