package com.tubes.kouveepetshop.Model;

public class SupplierDAO {
    String id_supplier, nama, no_telp, alamat;

    public SupplierDAO(String id_supplier, String nama, String no_telp, String alamat) {
        this.id_supplier = id_supplier;
        this.nama = nama;
        this.no_telp = no_telp;
        this.alamat = alamat;
    }

    public String getId_supplier() {
        return id_supplier;
    }

    public String getNama() {
        return nama;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setId_supplier(String id_supplier) {
        this.id_supplier = id_supplier;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
