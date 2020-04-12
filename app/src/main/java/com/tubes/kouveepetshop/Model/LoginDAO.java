package com.tubes.kouveepetshop.Model;

public class LoginDAO {
    String id_pegawai, nama, username, password, peran, token, status;

    public LoginDAO(String id_pegawai, String nama, String username, String password, String peran, String token, String status) {
        this.id_pegawai = id_pegawai;
        this.nama = nama;
        this.username = username;
        this.password = password;
        this.peran = peran;
        this.token = token;
        this.status = status;
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

    public String getToken() {
        return token;
    }

    public String getStatus() {
        return status;
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

    public void setToken(String token) {
        this.token = token;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
