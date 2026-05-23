package repository;

import model.Produk.Kategori;

public class ProdukRepository {

    public Kategori[] getAllKategori() {
        return Kategori.values();
    }

    public Kategori cariKategoriByLabel(String label) {
        if (label == null) return null;
        for (Kategori k : Kategori.values()) {
            if (k.getLabel().equalsIgnoreCase(label.trim())) return k;
        }
        return null;
    }
}