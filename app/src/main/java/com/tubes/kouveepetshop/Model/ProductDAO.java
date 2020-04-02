package com.tubes.kouveepetshop.Model;

public class ProductDAO {
    String ID_PRODUK, NAMA, HARGA, MINIMAL, STOK, SATUAN, GAMBAR;

    public ProductDAO(String ID_PRODUK, String NAMA, String HARGA, String MINIMAL, String STOK, String SATUAN, String GAMBAR) {
        this.ID_PRODUK = ID_PRODUK;
        this.NAMA = NAMA;
        this.HARGA = HARGA;
        this.MINIMAL = MINIMAL;
        this.STOK = STOK;
        this.SATUAN = SATUAN;
        this.GAMBAR = GAMBAR;
    }

    public String getID_PRODUK() {
        return ID_PRODUK;
    }

    public String getNAMA() {
        return NAMA;
    }

    public String getHARGA() {
        return HARGA;
    }

    public String getMINIMAL() {
        return MINIMAL;
    }

    public String getSTOK() {
        return STOK;
    }

    public String getSATUAN() {
        return SATUAN;
    }

    public String getGAMBAR() {
        return GAMBAR;
    }

    public void setID_PRODUK(String ID_PRODUK) {
        this.ID_PRODUK = ID_PRODUK;
    }

    public void setNAMA(String NAMA) {
        this.NAMA = NAMA;
    }

    public void setHARGA(String HARGA) {
        this.HARGA = HARGA;
    }

    public void setMINIMAL(String MINIMAL) {
        this.MINIMAL = MINIMAL;
    }

    public void setSTOK(String STOK) {
        this.STOK = STOK;
    }

    public void setSATUAN(String SATUAN) {
        this.SATUAN = SATUAN;
    }

    public void setGAMBAR(String GAMBAR) {
        this.GAMBAR = GAMBAR;
    }
}
