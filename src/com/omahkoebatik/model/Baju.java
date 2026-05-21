package com.omahkoebatik.model;

public class Baju extends Produk {
    private String motif;
    private String ukuran;
    private String jenisKain;
    private Supplier supplier;

    public Baju(int id, String namaProduk, String motif, String ukuran, double harga, int stok, String jenisKain, Supplier supplier) {
        super(id, namaProduk, "Baju Batik", harga, stok);
        this.motif = motif;
        this.ukuran = ukuran;
        this.jenisKain = jenisKain;
        this.supplier = supplier;
    }

    // Getter & Setter Supplier
    public Supplier getSupplier() { return supplier; }
    public void setSupplier(Supplier supplier) { this.supplier = supplier; }

    public String getMotif() { return motif; }
    public String getUkuran() { return ukuran; }
    public String getJenisKain() { return jenisKain; }
}