package model;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class Baju extends Produk {

    private static final List<String> UKURAN_VALID = Arrays.asList("S", "M", "L", "XL", "XXL");

    private String motif;
    private String ukuran;

    public Baju(String namaProduk, double harga, int stok, String motif, String ukuran,
                Kategori kategori, Supplier supplier) {
        super(namaProduk, harga, stok, kategori, supplier);
        setMotif(motif);
        setUkuran(ukuran);
    }

    public String getMotif() { return motif; }
    public String getUkuran() { return ukuran; }

    public void setMotif(String motif) {
        if (motif == null || motif.trim().isEmpty())
            throw new IllegalArgumentException("[!] Motif tidak boleh kosong");
        this.motif = motif.trim();
    }

    public void setUkuran(String ukuran) {
        if (ukuran == null || ukuran.trim().isEmpty())
            throw new IllegalArgumentException("[!] Ukuran tidak boleh kosong");
        String up = ukuran.trim().toUpperCase();
        if (!UKURAN_VALID.contains(up))
            throw new IllegalArgumentException("[!] Ukuran harus S, M, L, XL, atau XXL");
        this.ukuran = up;
    }

    private String formatRupiah(double nilai) {
        DecimalFormat df = new DecimalFormat("Rp#,###");
        return df.format(nilai);
    }

    @Override
    public String tampilInfo() {
        return String.format("[Baju] Nama Merk: %s | Motif: %s | Ukuran: %s | Harga: %s | Stok: %d | Kategori: %s | Supplier: %s",
                getNamaProduk(), motif, ukuran, formatRupiah(getHarga()),
                getStok(), getKategori().getLabel(), getSupplier().getNama());
    }
}