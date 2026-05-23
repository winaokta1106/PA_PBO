package model;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class Celana extends Produk {

    private static final List<String> UKURAN_VALID = Arrays.asList("S", "M", "L", "XL", "XXL");

    private String model;
    private String ukuran;
    private String bahan;

    public Celana(String namaProduk, double harga, int stok, String model, String ukuran,
                  String bahan, Kategori kategori, Supplier supplier) {
        super(namaProduk, harga, stok, kategori, supplier);
        setModel(model);
        setUkuran(ukuran);
        setBahan(bahan);
    }

    public String getModel() { return model; }
    public String getUkuran() { return ukuran; }
    public String getBahan() { return bahan; }

    public void setModel(String model) {
        if (model == null || model.trim().isEmpty())
            throw new IllegalArgumentException("[!] Model tidak boleh kosong");
        this.model = model.trim();
    }

    public void setUkuran(String ukuran) {
        if (ukuran == null || ukuran.trim().isEmpty())
            throw new IllegalArgumentException("[!] Ukuran tidak boleh kosong");
        String up = ukuran.trim().toUpperCase();
        if (!UKURAN_VALID.contains(up))
            throw new IllegalArgumentException("[!] Ukuran harus S, M, L, XL, atau XXL");
        this.ukuran = up;
    }

    public void setBahan(String bahan) {
        if (bahan == null || bahan.trim().isEmpty())
            throw new IllegalArgumentException("[!] Bahan tidak boleh kosong");
        this.bahan = bahan.trim();
    }

    private String formatRupiah(double nilai) {
        DecimalFormat df = new DecimalFormat("Rp#,###");
        return df.format(nilai);
    }

    @Override
    public String tampilInfo() {
        return String.format("[Celana] Nama Merk: %s | Model: %s | Ukuran: %s | Bahan: %s | Harga: %s | Stok: %d | Kategori: %s | Supplier: %s",
                getNamaProduk(), model, ukuran, bahan, formatRupiah(getHarga()),
                getStok(), getKategori().getLabel(), getSupplier().getNama());
    }
}