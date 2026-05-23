package service;

import model.Baju;
import model.Produk.Kategori;
import model.Supplier;
import repository.BajuRepository;
import repository.ProdukRepository;
import repository.SupplierRepository;
import java.util.List;

public class BajuService {

    private final BajuRepository bajuRepository;
    private final SupplierRepository supplierRepository;
    private final ProdukRepository produkRepository;

    public BajuService(BajuRepository bajuRepository,
                       SupplierRepository supplierRepository,
                       ProdukRepository produkRepository) {
        this.bajuRepository = bajuRepository;
        this.supplierRepository = supplierRepository;
        this.produkRepository = produkRepository;
    }

    public String tambahBaju(String nama, String hargaStr, String stokStr,
                             String motif, String ukuran, String kategoriLabel, int supplierId) {
        if (nama == null || nama.trim().isEmpty())
            return "[!] Nama merk tidak boleh kosong";

        if (bajuRepository.cariByNama(nama) != null)
            return "[!] Baju dengan nama '" + nama + "' sudah ada";

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

        Kategori kategori = produkRepository.cariKategoriByLabel(kategoriLabel);
        if (kategori == null) return "[!] Kategori tidak valid";

        Supplier supplier = supplierRepository.cariById(supplierId);
        if (supplier == null) return "[!] Supplier dengan ID " + supplierId + " tidak ditemukan";

        try {
            Baju baju = new Baju(nama, harga, stok, motif, ukuran, kategori, supplier);
            bajuRepository.tambah(baju);
            return "[v] Baju berhasil ditambahkan!";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    public List<Baju> getAllBaju() {
        return bajuRepository.getAll();
    }

    public Baju cariBajuByNama(String nama) {
        return bajuRepository.cariByNama(nama);
    }

    public Baju cariBajuByIndex(int index) {
        return bajuRepository.cariByIndex(index);
    }

    public String editBaju(String namaLama, String namaBaru, String hargaStr, String stokStr,
                           String motifBaru, String ukuranBaru, String kategoriLabel, String supplierIdStr) {
        Baju existing = bajuRepository.cariByNama(namaLama);
        if (existing == null) return "[!] Baju '" + namaLama + "' tidak ditemukan";

        String nama = (namaBaru == null || namaBaru.trim().isEmpty()) ? existing.getNamaProduk() : namaBaru.trim();
        String motif = (motifBaru == null || motifBaru.trim().isEmpty()) ? existing.getMotif() : motifBaru.trim();
        String ukuran = (ukuranBaru == null || ukuranBaru.trim().isEmpty()) ? existing.getUkuran() : ukuranBaru.trim();

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
            Baju bajuBaru = new Baju(nama, harga, stok, motif, ukuran, kategori, supplier);
            bajuRepository.update(namaLama, bajuBaru);
            return "[v] Baju berhasil diupdate";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    public String hapusBaju(String nama) {
        if (bajuRepository.cariByNama(nama) == null)
            return "[!] Baju '" + nama + "' tidak ditemukan";
        bajuRepository.hapus(nama);
        return "[v] Baju dihapus";
    }

    public String tambahStok(String nama, String jumlahStr) {
        Baju baju = bajuRepository.cariByNama(nama);
        if (baju == null) return "[!] Baju '" + nama + "' tidak ditemukan";

        int jumlah;
        try {
            jumlah = Integer.parseInt(jumlahStr.trim());
        } catch (NumberFormatException e) {
            return "[!] Jumlah tambah stok harus berupa angka";
        }

        try {
            baju.tambahStok(jumlah);
            return "[v] Stok berhasil ditambah. Stok sekarang: " + baju.getStok();
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }
}