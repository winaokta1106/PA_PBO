package com.omahkoebatik.entity;

public class ProdukEntity {
    private int id;
    private String namaProduk;
    private String kategori;
    private double harga;
    private int stok;

    public Produk(int id, String namaProduk, String kategori, double harga, int stok) {
        this.id = id;
        this.namaProduk = namaProduk;
        this.kategori = kategori;
        this.harga = harga;
        this.stok = stok;
    }

    // Getter
    public int getId() { return id; }
    public String getNamaProduk() { return namaProduk; }
    public String getKategori() { return kategori; }
    public double getHarga() { return harga; }
    public int getStok() { return stok; }

    // Setter
    public void setNamaProduk(String namaProduk) { this.namaProduk = namaProduk; }
    public void setHarga(double harga) { this.harga = harga; }
    public void setStok(int stok) { this.stok = stok; }

    // Abstract methods
    public abstract boolean validasiData();
    public abstract String getDetailProduk();
}