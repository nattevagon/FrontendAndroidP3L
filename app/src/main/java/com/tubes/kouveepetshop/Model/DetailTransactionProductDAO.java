package com.tubes.kouveepetshop.Model;

public class DetailTransactionProductDAO {
    String ID_DETAIL_TP, ID_TP, ID_PRODUK, KODE_TRANSAKSI, PRODUK, JUMLAH, TOTAL;

    public DetailTransactionProductDAO(String ID_DETAIL_TP, String ID_TP, String ID_PRODUK, String KODE_TRANSAKSI, String PRODUK, String JUMLAH, String TOTAL) {
        this.ID_DETAIL_TP = ID_DETAIL_TP;
        this.ID_TP = ID_TP;
        this.ID_PRODUK = ID_PRODUK;
        this.KODE_TRANSAKSI = KODE_TRANSAKSI;
        this.PRODUK = PRODUK;
        this.JUMLAH = JUMLAH;
        this.TOTAL = TOTAL;
    }

    public String getID_DETAIL_TP() {
        return ID_DETAIL_TP;
    }

    public String getID_TP() {
        return ID_TP;
    }

    public String getID_PRODUK() {
        return ID_PRODUK;
    }

    public String getKODE_TRANSAKSI() {
        return KODE_TRANSAKSI;
    }

    public String getPRODUK() {
        return PRODUK;
    }

    public String getJUMLAH() {
        return JUMLAH;
    }

    public String getTOTAL() {
        return TOTAL;
    }

    public void setID_DETAIL_TP(String ID_DETAIL_TP) {
        this.ID_DETAIL_TP = ID_DETAIL_TP;
    }

    public void setID_TP(String ID_TP) {
        this.ID_TP = ID_TP;
    }

    public void setID_PRODUK(String ID_PRODUK) {
        this.ID_PRODUK = ID_PRODUK;
    }

    public void setKODE_TRANSAKSI(String KODE_TRANSAKSI) {
        this.KODE_TRANSAKSI = KODE_TRANSAKSI;
    }

    public void setPRODUK(String PRODUK) {
        this.PRODUK = PRODUK;
    }

    public void setJUMLAH(String JUMLAH) {
        this.JUMLAH = JUMLAH;
    }

    public void setTOTAL(String TOTAL) {
        this.TOTAL = TOTAL;
    }
}
