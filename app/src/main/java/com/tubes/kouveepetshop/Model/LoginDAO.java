package com.tubes.kouveepetshop.Model;

public class LoginDAO {
    String status, id_pegawai, nama, username, password, peran, tgl_lahir, alamat,  no_telp, token;

    public LoginDAO(String status, String id_pegawai, String nama, String username, String password, String peran, String tgl_lahir, String alamat, String no_telp, String token) {
        this.status = status;
        this.id_pegawai = id_pegawai;
        this.nama = nama;
        this.username = username;
        this.password = password;
        this.peran = peran;
        this.tgl_lahir = tgl_lahir;
        this.alamat = alamat;
        this.no_telp = no_telp;
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public String getId_pegawai() {
        return id_pegawai;
    }

    public String getNama() {
        return nama;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPeran() {
        return peran;
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

    public String getToken() {
        return token;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId_pegawai(String id_pegawai) {
        this.id_pegawai = id_pegawai;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPeran(String peran) {
        this.peran = peran;
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

    public void setToken(String token) {
        this.token = token;
    }
}
