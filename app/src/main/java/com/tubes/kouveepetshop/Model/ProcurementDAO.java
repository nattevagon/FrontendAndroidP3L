package com.tubes.kouveepetshop.Model;

public class ProcurementDAO {
    String id_pengadaan, id_supplier, supplier, kode, tanggal, status, alamat, total_harga;

    public ProcurementDAO(String id_pengadaan, String id_supplier, String supplier, String kode, String tanggal, String status, String alamat, String total_harga) {
        this.id_pengadaan = id_pengadaan;
        this.id_supplier = id_supplier;
        this.supplier = supplier;
        this.kode = kode;
        this.tanggal = tanggal;
        this.status = status;
        this.alamat = alamat;
        this.total_harga = total_harga;
    }

    public String getId_pengadaan() {
        return id_pengadaan;
    }

    public String getId_supplier() {
        return id_supplier;
    }

    public String getSupplier() {
        return supplier;
    }

    public String getKode() {
        return kode;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getStatus() {
        return status;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getTotal_harga() {
        return total_harga;
    }

    public void setId_pengadaan(String id_pengadaan) {
        this.id_pengadaan = id_pengadaan;
    }

    public void setId_supplier(String id_supplier) {
        this.id_supplier = id_supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setTotal_harga(String total_harga) {
        this.total_harga = total_harga;
    }
}
