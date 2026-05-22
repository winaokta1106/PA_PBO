package model;

import java.text.DecimalFormat;

public abstract class Produk {

    // ========== INNER INTERFACE (Abstraction - IDiskon) ==========
    public interface IDiskon {
        double hitungDiskon(double persenDiskon);
    }

    // ========== INNER ENUM (Kategori tanpa class terpisah) ==========
    public enum Kategori {
        BATIK_TULIS("Batik Tulis"),
        BATIK_CAP("Batik Cap"),
        BATIK_PRINTING("Batik Printing"),
        BATIK_KOMBINASI("Batik Kombinasi");

        private final String label;

        Kategori(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    // ========== FIELD (semua private - Encapsulation) ==========
    private String namaProduk;
    private double harga;
    private int stok;

    // ========== CONSTRUCTOR ==========
    public Produk(String namaProduk, double harga, int stok) {
        setNamaProduk(namaProduk);
        setHarga(harga);
        setStok(stok);
    }

    // ========== GETTER ==========
    public String getNamaProduk() {
        return namaProduk;
    }

    public double getHarga() {
        return harga;
    }

    public int getStok() {
        return stok;
    }

    // ========== SETTER dengan validasi (Encapsulation) ==========
    public void setNamaProduk(String namaProduk) {
        if (namaProduk == null || namaProduk.trim().isEmpty()) {
            throw new IllegalArgumentException("[!] Nama produk tidak boleh kosong");
        }
        this.namaProduk = namaProduk.trim();
    }

    public void setHarga(double harga) {
        if (harga < 0) {
            throw new IllegalArgumentException("[!] Harga tidak boleh negatif");
        }
        this.harga = harga;
    }

    public void setStok(int stok) {
        if (stok < 0) {
            throw new IllegalArgumentException("[!] Stok tidak boleh negatif");
        }
        this.stok = stok;
    }

    // ========== METHOD ==========
    public void tambahStok(int jumlah) {
        if (jumlah <= 0) {
            throw new IllegalArgumentException("[!] Jumlah tambah stok harus lebih dari 0");
        }
        this.stok += jumlah;
    }

    protected String formatRupiah(double nilai) {
        DecimalFormat df = new DecimalFormat("Rp#,###");
        return df.format(nilai);
    }

    // ========== ABSTRACT METHOD (Abstraction) ==========
    public abstract String tampilInfo();
}