package model;

public class Baju extends Produk implements Produk.IDiskon {

    // ========== FIELD (semua private - Encapsulation) ==========
    private String motif;
    private String ukuran;
    private Produk.Kategori kategori;
    private Supplier supplier;

    // ========== CONSTRUCTOR LENGKAP (Inheritance - panggil super()) ==========
    public Baju(String namaProduk, double harga, int stok, String motif, String ukuran,
                Produk.Kategori kategori, Supplier supplier) {
        super(namaProduk, harga, stok);
        setMotif(motif);
        setUkuran(ukuran);
        setKategori(kategori);
        setSupplier(supplier);
    }

    // ========== CONSTRUCTOR OVERLOAD - tanpa supplier (Polymorphism - Overload) ==========
    public Baju(String namaProduk, double harga, int stok, String motif, String ukuran,
                Produk.Kategori kategori) {
        super(namaProduk, harga, stok);
        setMotif(motif);
        setUkuran(ukuran);
        setKategori(kategori);
        this.supplier = null;
    }

    // ========== GETTER ==========
    public String getMotif() {
        return motif;
    }

    public String getUkuran() {
        return ukuran;
    }

    public Produk.Kategori getKategori() {
        return kategori;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    // ========== SETTER dengan validasi (Encapsulation) ==========
    public void setMotif(String motif) {
        if (motif == null || motif.trim().isEmpty()) {
            throw new IllegalArgumentException("[!] Motif tidak boleh kosong");
        }
        this.motif = motif.trim();
    }

    public void setUkuran(String ukuran) {
        if (ukuran == null || ukuran.trim().isEmpty()) {
            throw new IllegalArgumentException("[!] Ukuran tidak boleh kosong");
        }
        this.ukuran = ukuran.trim();
    }

    public void setKategori(Produk.Kategori kategori) {
        if (kategori == null) {
            throw new IllegalArgumentException("[!] Kategori tidak boleh null");
        }
        this.kategori = kategori;
    }

    public void setSupplier(Supplier supplier) {
        if (supplier == null) {
            throw new IllegalArgumentException("[!] Supplier tidak boleh null");
        }
        this.supplier = supplier;
    }

    // ========== IMPLEMENTASI IDiskon (Abstraction - interface) ==========
    @Override
    public double hitungDiskon(double persenDiskon) {
        if (persenDiskon < 0 || persenDiskon > 100) {
            throw new IllegalArgumentException("[!] Persen diskon harus antara 0 dan 100");
        }
        return getHarga() * (persenDiskon / 100);
    }

    // ========== OVERRIDE tampilInfo() dari Produk (Polymorphism - Override) ==========
    @Override
    public String tampilInfo() {
        String namaKategori = (kategori != null) ? kategori.getLabel() : "-";
        String namaSupplier = (supplier != null) ? supplier.getNama() : "-";
        return "[Baju] " + getNamaProduk()
                + " | Motif: " + motif
                + " | Ukuran: " + ukuran
                + " | Harga: " + formatRupiah(getHarga())
                + " | Stok: " + getStok()
                + " | Kategori: " + namaKategori
                + " | Supplier: " + namaSupplier;
    }

    @Override
    public String toString() {
        return tampilInfo();
    }
}