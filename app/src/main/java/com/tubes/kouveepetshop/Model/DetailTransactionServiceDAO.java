package com.tubes.kouveepetshop.Model;

public class DetailTransactionServiceDAO {
    String id_detail_tl, id_tl, id_layanan, kode_transaksi, hewan, layanan, ukuran_hewan, jumlah, harga, total;

    public DetailTransactionServiceDAO(String id_detail_tl, String id_tl, String id_layanan, String kode_transaksi, String hewan, String layanan, String ukuran_hewan, String jumlah, String harga, String total) {
        this.id_detail_tl = id_detail_tl;
        this.id_tl = id_tl;
        this.id_layanan = id_layanan;
        this.kode_transaksi = kode_transaksi;
        this.hewan = hewan;
        this.layanan = layanan;
        this.ukuran_hewan = ukuran_hewan;
        this.jumlah = jumlah;
        this.harga = harga;
        this.total = total;
    }

    public String getId_detail_tl() {
        return id_detail_tl;
    }

    public String getId_tl() {
        return id_tl;
    }

    public String getId_layanan() {
        return id_layanan;
    }

    public String getKode_transaksi() {
        return kode_transaksi;
    }

    public String getHewan() {
        return hewan;
    }

    public String getLayanan() {
        return layanan;
    }

    public String getUkuran_hewan() {
        return ukuran_hewan;
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

    public void setId_detail_tl(String id_detail_tl) {
        this.id_detail_tl = id_detail_tl;
    }

    public void setId_tl(String id_tl) {
        this.id_tl = id_tl;
    }

    public void setId_layanan(String id_layanan) {
        this.id_layanan = id_layanan;
    }

    public void setKode_transaksi(String kode_transaksi) {
        this.kode_transaksi = kode_transaksi;
    }

    public void setHewan(String hewan) {
        this.hewan = hewan;
    }

    public void setLayanan(String layanan) {
        this.layanan = layanan;
    }

    public void setUkuran_hewan(String ukuran_hewan) {
        this.ukuran_hewan = ukuran_hewan;
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
