package com.tubes.kouveepetshop.Model;

public class CustomerDAO {
    String id_customer, nama, tgl_lahir, alamat, no_telp, created_by, updated_by, deleted_by;

    public CustomerDAO(String id_customer, String nama, String tgl_lahir, String alamat, String no_telp, String created_by, String updated_by, String deleted_by) {
        this.id_customer = id_customer;
        this.nama = nama;
        this.tgl_lahir = tgl_lahir;
        this.alamat = alamat;
        this.no_telp = no_telp;
        this.created_by = created_by;
        this.updated_by = updated_by;
        this.deleted_by = deleted_by;
    }

    public String getId_customer() {
        return id_customer;
    }

    public String getNama() {
        return nama;
    }

    public String getTgl_lahir() {
        return tgl_lahir;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public String getCreated_by() {
        return created_by;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public String getDeleted_by() {
        return deleted_by;
    }

    public void setId_customer(String id_customer) {
        this.id_customer = id_customer;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setTgl_lahir(String tgl_lahir) {
        this.tgl_lahir = tgl_lahir;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }

    public void setDeleted_by(String deleted_by) {
        this.deleted_by = deleted_by;
    }
}
