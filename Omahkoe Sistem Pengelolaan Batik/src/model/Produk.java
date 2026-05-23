package model;

import java.text.DecimalFormat;

public abstract class Produk {

    public enum Kategori {
        ANAK_ANAK("Anak-Anak"),
        WANITA("Wanita"),
        PRIA("Pria");

        private final String label;

        Kategori(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    private String namaProduk;
    private double harga;
    private int stok;
    private Kategori kategori;
    private Supplier supplier;

    public Produk(String namaProduk, double harga, int stok, Kategori kategori, Supplier supplier) {
        setNamaProduk(namaProduk);
        setHarga(harga);
        setStok(stok);
        setKategori(kategori);
        setSupplier(supplier);
    }

    public String getNamaProduk() { return namaProduk; }
    public double getHarga() { return harga; }
    public int getStok() { return stok; }
    public Kategori getKategori() { return kategori; }
    public Supplier getSupplier() { return supplier; }

    public void setNamaProduk(String namaProduk) {
        if (namaProduk == null || namaProduk.trim().isEmpty())
            throw new IllegalArgumentException("[!] Nama produk tidak boleh kosong");
        this.namaProduk = namaProduk.trim();
    }

    public void setHarga(double harga) {
        if (harga < 0)
            throw new IllegalArgumentException("[!] Harga tidak boleh negatif");
        this.harga = harga;
    }

    public void setStok(int stok) {
        if (stok < 0)
            throw new IllegalArgumentException("[!] Stok tidak boleh negatif");
        this.stok = stok;
    }

    public void setKategori(Kategori kategori) {
        if (kategori == null)
            throw new IllegalArgumentException("[!] Kategori tidak boleh null");
        this.kategori = kategori;
    }

    public void setSupplier(Supplier supplier) {
        if (supplier == null)
            throw new IllegalArgumentException("[!] Supplier tidak boleh null");
        this.supplier = supplier;
    }

    public void tambahStok(int jumlah) {
        if (jumlah <= 0)
            throw new IllegalArgumentException("[!] Jumlah tambah stok harus lebih dari 0");
        this.stok += jumlah;
    }

    private String formatRupiah(double nilai) {
        DecimalFormat df = new DecimalFormat("Rp#,###");
        return df.format(nilai);
    }

    protected String getFormattedHarga() {
        return formatRupiah(this.harga);
    }

    public abstract String tampilInfo();
}