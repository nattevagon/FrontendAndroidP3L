package com.tubes.kouveepetshop.Model;

public class EmployeeDAO {
    String id_pegawai, nama, tgl_lahir, alamat, peran, no_telp, username, password;

    public EmployeeDAO(String id_pegawai, String nama, String tgl_lahir, String alamat, String peran, String no_telp, String username, String password) {
        this.id_pegawai = id_pegawai;
        this.nama = nama;
        this.tgl_lahir = tgl_lahir;
        this.alamat = alamat;
        this.peran = peran;
        this.no_telp = no_telp;
        this.username = username;
        this.password = password;
    }

    public String getId_pegawai() {
        return id_pegawai;
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

    public String getPeran() {
        return peran;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setId_pegawai(String id_pegawai) {
        this.id_pegawai = id_pegawai;
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

    public void setPeran(String peran) {
        this.peran = peran;
    }

    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
