package com.tubes.kouveepetshop.Model;

public class TransactionProductDAO {
    String ID_TP, ID_HEWAN, ID_PEGAWAI_K, ID_PEGAWAI_CS, HEWAN, KASIR, CUSTOMER_SERVICE, KODE, TANGGAL, SUB_TOTAL, TOTAL_HARGA, STATUS, CREATED_BY, UPDATED_BY;

    public TransactionProductDAO(String ID_TP, String ID_HEWAN, String ID_PEGAWAI_K, String ID_PEGAWAI_CS, String HEWAN, String KASIR, String CUSTOMER_SERVICE, String KODE, String TANGGAL, String SUB_TOTAL, String TOTAL_HARGA, String STATUS, String CREATED_BY, String UPDATED_BY) {
        this.ID_TP = ID_TP;
        this.ID_HEWAN = ID_HEWAN;
        this.ID_PEGAWAI_K = ID_PEGAWAI_K;
        this.ID_PEGAWAI_CS = ID_PEGAWAI_CS;
        this.HEWAN = HEWAN;
        this.KASIR = KASIR;
        this.CUSTOMER_SERVICE = CUSTOMER_SERVICE;
        this.KODE = KODE;
        this.TANGGAL = TANGGAL;
        this.SUB_TOTAL = SUB_TOTAL;
        this.TOTAL_HARGA = TOTAL_HARGA;
        this.STATUS = STATUS;
        this.CREATED_BY = CREATED_BY;
        this.UPDATED_BY = UPDATED_BY;
    }

    public String getID_TP() {
        return ID_TP;
    }

    public String getID_HEWAN() {
        return ID_HEWAN;
    }

    public String getID_PEGAWAI_K() {
        return ID_PEGAWAI_K;
    }

    public String getID_PEGAWAI_CS() {
        return ID_PEGAWAI_CS;
    }

    public String getHEWAN() {
        return HEWAN;
    }

    public String getKASIR() {
        return KASIR;
    }

    public String getCUSTOMER_SERVICE() {
        return CUSTOMER_SERVICE;
    }

    public String getKODE() {
        return KODE;
    }

    public String getTANGGAL() {
        return TANGGAL;
    }

    public String getSUB_TOTAL() {
        return SUB_TOTAL;
    }

    public String getTOTAL_HARGA() {
        return TOTAL_HARGA;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public String getCREATED_BY() {
        return CREATED_BY;
    }

    public String getUPDATED_BY() {
        return UPDATED_BY;
    }

    public void setID_TP(String ID_TP) {
        this.ID_TP = ID_TP;
    }

    public void setID_HEWAN(String ID_HEWAN) {
        this.ID_HEWAN = ID_HEWAN;
    }

    public void setID_PEGAWAI_K(String ID_PEGAWAI_K) {
        this.ID_PEGAWAI_K = ID_PEGAWAI_K;
    }

    public void setID_PEGAWAI_CS(String ID_PEGAWAI_CS) {
        this.ID_PEGAWAI_CS = ID_PEGAWAI_CS;
    }

    public void setHEWAN(String HEWAN) {
        this.HEWAN = HEWAN;
    }

    public void setKASIR(String KASIR) {
        this.KASIR = KASIR;
    }

    public void setCUSTOMER_SERVICE(String CUSTOMER_SERVICE) {
        this.CUSTOMER_SERVICE = CUSTOMER_SERVICE;
    }

    public void setKODE(String KODE) {
        this.KODE = KODE;
    }

    public void setTANGGAL(String TANGGAL) {
        this.TANGGAL = TANGGAL;
    }

    public void setSUB_TOTAL(String SUB_TOTAL) {
        this.SUB_TOTAL = SUB_TOTAL;
    }

    public void setTOTAL_HARGA(String TOTAL_HARGA) {
        this.TOTAL_HARGA = TOTAL_HARGA;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public void setCREATED_BY(String CREATED_BY) {
        this.CREATED_BY = CREATED_BY;
    }

    public void setUPDATED_BY(String UPDATED_BY) {
        this.UPDATED_BY = UPDATED_BY;
    }
}
