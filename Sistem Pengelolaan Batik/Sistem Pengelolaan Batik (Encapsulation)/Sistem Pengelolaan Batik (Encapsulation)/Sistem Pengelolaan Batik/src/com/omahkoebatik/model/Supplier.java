package com.omahkoebatik.model;

public class Supplier {
    private int idSupplier;
    private String namaSupplier;
    private String noTelp;
    private String alamat;

    public Supplier(int idSupplier, String namaSupplier, String noTelp, String alamat) {
        this.idSupplier = idSupplier;
        this.namaSupplier = namaSupplier;
        this.noTelp = noTelp;
        this.alamat = alamat;
    }

    public String getNamaSupplier() { return namaSupplier; }

    @Override
    public String toString() {
        return this.namaSupplier; // Biar muncul nama di ComboBox
    }
}