package service;

import model.Celana;
import model.Produk.Kategori;
import model.Supplier;
import repository.CelanaRepository;
import repository.ProdukRepository;
import repository.SupplierRepository;
import java.util.List;

public class CelanaService {

    private final CelanaRepository celanaRepository;
    private final SupplierRepository supplierRepository;
    private final ProdukRepository produkRepository;

    public CelanaService(CelanaRepository celanaRepository,
                         SupplierRepository supplierRepository,
                         ProdukRepository produkRepository) {
        this.celanaRepository = celanaRepository;
        this.supplierRepository = supplierRepository;
        this.produkRepository = produkRepository;
    }

    public String tambahCelana(String nama, String hargaStr, String stokStr,
                               String model, String ukuran, String bahan,
                               String kategoriLabel, int supplierId) {
        if (nama == null || nama.trim().isEmpty())
            return "[!] Nama merk tidak boleh kosong";

        if (celanaRepository.cariByNama(nama) != null)
            return "[!] Celana dengan nama '" + nama + "' sudah ada";

        double harga;
        int stok;
        try {
            harga = Double.parseDouble(hargaStr.trim());
            if (harga < 0) return "[!] Harga tidak boleh negatif";
        } catch (NumberFormatException e) {
            return "[!] Harga harus berupa angka";
        }
        try {
            stok = Integer.parseInt(stokStr.trim());
            if (stok < 0) return "[!] Stok tidak boleh negatif";
        } catch (NumberFormatException e) {
            return "[!] Stok harus berupa angka";
        }

        if (model == null || model.trim().isEmpty()) return "[!] Model tidak boleh kosong";
        if (bahan == null || bahan.trim().isEmpty()) return "[!] Bahan tidak boleh kosong";

        Kategori kategori = produkRepository.cariKategoriByLabel(kategoriLabel);
        if (kategori == null) return "[!] Kategori tidak valid";

        Supplier supplier = supplierRepository.cariById(supplierId);
        if (supplier == null) return "[!] Supplier dengan ID " + supplierId + " tidak ditemukan";

        try {
            Celana celana = new Celana(nama, harga, stok, model, ukuran, bahan, kategori, supplier);
            celanaRepository.tambah(celana);
            return "[v] Celana berhasil ditambahkan!";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    public List<Celana> getAllCelana() {
        return celanaRepository.getAll();
    }

    public Celana cariCelanaByNama(String nama) {
        return celanaRepository.cariByNama(nama);
    }

    public Celana cariCelanaByIndex(int index) {
        return celanaRepository.cariByIndex(index);
    }

    public String editCelana(String namaLama, String namaBaru, String hargaStr, String stokStr,
                             String modelBaru, String ukuranBaru, String bahanBaru,
                             String kategoriLabel, String supplierIdStr) {
        Celana existing = celanaRepository.cariByNama(namaLama);
        if (existing == null) return "[!] Celana '" + namaLama + "' tidak ditemukan";

        String nama = (namaBaru == null || namaBaru.trim().isEmpty()) ? existing.getNamaProduk() : namaBaru.trim();
        String model = (modelBaru == null || modelBaru.trim().isEmpty()) ? existing.getModel() : modelBaru.trim();
        String ukuran = (ukuranBaru == null || ukuranBaru.trim().isEmpty()) ? existing.getUkuran() : ukuranBaru.trim();
        String bahan = (bahanBaru == null || bahanBaru.trim().isEmpty()) ? existing.getBahan() : bahanBaru.trim();

        double harga = existing.getHarga();
        if (hargaStr != null && !hargaStr.trim().isEmpty()) {
            try {
                harga = Double.parseDouble(hargaStr.trim());
                if (harga < 0) return "[!] Harga tidak boleh negatif";
            } catch (NumberFormatException e) {
                return "[!] Harga harus berupa angka";
            }
        }

        int stok = existing.getStok();
        if (stokStr != null && !stokStr.trim().isEmpty()) {
            try {
                stok = Integer.parseInt(stokStr.trim());
                if (stok < 0) return "[!] Stok tidak boleh negatif";
            } catch (NumberFormatException e) {
                return "[!] Stok harus berupa angka";
            }
        }

        Kategori kategori = existing.getKategori();
        if (kategoriLabel != null && !kategoriLabel.trim().isEmpty()) {
            kategori = produkRepository.cariKategoriByLabel(kategoriLabel);
            if (kategori == null) return "[!] Kategori tidak valid";
        }

        Supplier supplier = existing.getSupplier();
        if (supplierIdStr != null && !supplierIdStr.trim().isEmpty()) {
            try {
                int sid = Integer.parseInt(supplierIdStr.trim());
                supplier = supplierRepository.cariById(sid);
                if (supplier == null) return "[!] Supplier dengan ID " + sid + " tidak ditemukan";
            } catch (NumberFormatException e) {
                return "[!] ID Supplier harus berupa angka";
            }
        }

        try {
            Celana celanaBaru = new Celana(nama, harga, stok, model, ukuran, bahan, kategori, supplier);
            celanaRepository.update(namaLama, celanaBaru);
            return "[v] Celana berhasil diupdate";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    public String hapusCelana(String nama) {
        if (celanaRepository.cariByNama(nama) == null)
            return "[!] Celana '" + nama + "' tidak ditemukan";
        celanaRepository.hapus(nama);
        return "[v] Celana dihapus";
    }

    public String tambahStokCelana(String nama, String jumlahStr) {
        Celana celana = celanaRepository.cariByNama(nama);
        if (celana == null) return "[!] Celana '" + nama + "' tidak ditemukan";

        int jumlah;
        try {
            jumlah = Integer.parseInt(jumlahStr.trim());
        } catch (NumberFormatException e) {
            return "[!] Jumlah tambah stok harus berupa angka";
        }

        try {
            celana.tambahStok(jumlah);
            return "[v] Stok berhasil ditambah. Stok sekarang: " + celana.getStok();
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }
}