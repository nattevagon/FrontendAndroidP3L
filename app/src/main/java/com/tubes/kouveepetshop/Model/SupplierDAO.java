package com.tubes.kouveepetshop.Model;

public class SupplierDAO {
    String ID_SUPPLIER, NAMA, NO_TELP, ALAMAT;

    public SupplierDAO(String ID_SUPPLIER, String NAMA, String NO_TELP, String ALAMAT) {
        this.ID_SUPPLIER = ID_SUPPLIER;
        this.NAMA = NAMA;
        this.NO_TELP = NO_TELP;
        this.ALAMAT = ALAMAT;
    }

    public String getID_SUPPLIER() {
        return ID_SUPPLIER;
    }

    public String getNAMA() {
        return NAMA;
    }

    public String getNO_TELP() {
        return NO_TELP;
    }

    public String getALAMAT() {
        return ALAMAT;
    }

    public void setID_SUPPLIER(String ID_SUPPLIER) {
        this.ID_SUPPLIER = ID_SUPPLIER;
    }

    public void setNAMA(String NAMA) {
        this.NAMA = NAMA;
    }

    public void setNO_TELP(String NO_TELP) {
        this.NO_TELP = NO_TELP;
    }

    public void setALAMAT(String ALAMAT) {
        this.ALAMAT = ALAMAT;
    }
}
