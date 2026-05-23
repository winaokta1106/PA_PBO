package service;

import model.Produk.Kategori;
import repository.ProdukRepository;
import java.util.ArrayList;
import java.util.List;

public class ProdukService {

    private final ProdukRepository produkRepository;

    public ProdukService(ProdukRepository produkRepository) {
        this.produkRepository = produkRepository;
    }

    public List<String> getAllKategori() {
        List<String> list = new ArrayList<>();
        for (Kategori k : produkRepository.getAllKategori()) {
            list.add(k.getLabel());
        }
        return list;
    }

    public Kategori getKategoriEnumByLabel(String label) {
        return produkRepository.cariKategoriByLabel(label);
    }
}