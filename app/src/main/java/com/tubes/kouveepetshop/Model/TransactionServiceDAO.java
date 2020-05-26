package com.tubes.kouveepetshop.Model;

public class TransactionServiceDAO {
    String id_tl, id_hewan, id_pegawai_k, id_pegawai_cs, hewan, kasir, id_customer, customer, customer_service, kode, tanggal, sub_total, total_harga, status, no_hp, pesan, created_by, updated_by;

    public TransactionServiceDAO(String id_tl, String id_hewan, String id_pegawai_k, String id_pegawai_cs, String hewan, String kasir, String id_customer, String customer, String customer_service, String kode, String tanggal, String sub_total, String total_harga, String status, String no_hp, String pesan, String created_by, String updated_by) {
        this.id_tl = id_tl;
        this.id_hewan = id_hewan;
        this.id_pegawai_k = id_pegawai_k;
        this.id_pegawai_cs = id_pegawai_cs;
        this.hewan = hewan;
        this.kasir = kasir;
        this.id_customer = id_customer;
        this.customer = customer;
        this.customer_service = customer_service;
        this.kode = kode;
        this.tanggal = tanggal;
        this.sub_total = sub_total;
        this.total_harga = total_harga;
        this.status = status;
        this.no_hp = no_hp;
        this.pesan = pesan;
        this.created_by = created_by;
        this.updated_by = updated_by;
    }

    public String getId_tl() {
        return id_tl;
    }

    public String getId_hewan() {
        return id_hewan;
    }

    public String getId_pegawai_k() {
        return id_pegawai_k;
    }

    public String getId_pegawai_cs() {
        return id_pegawai_cs;
    }

    public String getHewan() {
        return hewan;
    }

    public String getKasir() {
        return kasir;
    }

    public String getId_customer() {
        return id_customer;
    }

    public String getCustomer() {
        return customer;
    }

    public String getCustomer_service() {
        return customer_service;
    }

    public String getKode() {
        return kode;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getSub_total() {
        return sub_total;
    }

    public String getTotal_harga() {
        return total_harga;
    }

    public String getStatus() {
        return status;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public String getPesan() {
        return pesan;
    }

    public String getCreated_by() {
        return created_by;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public void setId_tl(String id_tl) {
        this.id_tl = id_tl;
    }

    public void setId_hewan(String id_hewan) {
        this.id_hewan = id_hewan;
    }

    public void setId_pegawai_k(String id_pegawai_k) {
        this.id_pegawai_k = id_pegawai_k;
    }

    public void setId_pegawai_cs(String id_pegawai_cs) {
        this.id_pegawai_cs = id_pegawai_cs;
    }

    public void setHewan(String hewan) {
        this.hewan = hewan;
    }

    public void setKasir(String kasir) {
        this.kasir = kasir;
    }

    public void setId_customer(String id_customer) {
        this.id_customer = id_customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void setCustomer_service(String customer_service) {
        this.customer_service = customer_service;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public void setSub_total(String sub_total) {
        this.sub_total = sub_total;
    }

    public void setTotal_harga(String total_harga) {
        this.total_harga = total_harga;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }
}
