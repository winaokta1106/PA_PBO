package service;

import model.Baju;
import model.Produk;
import model.Supplier;
import repository.BajuRepository;
import repository.SupplierRepository;
import java.util.List;

public class DataStorage {

    // ========== DEPENDENCY (inject via constructor) ==========
    private BajuRepository bajuRepo;
    private SupplierRepository supplierRepo;

    public DataStorage(BajuRepository bajuRepo, SupplierRepository supplierRepo) {
        this.bajuRepo = bajuRepo;
        this.supplierRepo = supplierRepo;
    }

    // ==================== SUPPLIER ====================

    public String tambahSupplier(String nama, String telepon, String alamat) {
        if (nama == null || nama.trim().isEmpty()) {
            return "[!] Nama supplier tidak boleh kosong";
        }
        if (telepon == null || telepon.trim().isEmpty()) {
            return "[!] Telepon tidak boleh kosong";
        }
        if (alamat == null || alamat.trim().isEmpty()) {
            return "[!] Alamat tidak boleh kosong";
        }
        try {
            int id = supplierRepo.generateNextId();
            Supplier supplier = new Supplier(id, nama.trim(), telepon.trim(), alamat.trim());
            supplierRepo.tambah(supplier);
            return "[v] Supplier berhasil ditambahkan!";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    public List<Supplier> getAllSupplier() {
        return supplierRepo.getAll();
    }

    public String editSupplier(int id, String namaBaru, String teleponBaru, String alamatBaru) {
        Supplier supplier = supplierRepo.cariById(id);
        if (supplier == null) {
            return "[!] Supplier tidak ditemukan";
        }
        try {
            // Skip field kosong (KNF-01)
            if (namaBaru != null && !namaBaru.trim().isEmpty()) {
                supplier.setNama(namaBaru.trim());
            }
            if (teleponBaru != null && !teleponBaru.trim().isEmpty()) {
                supplier.setTelepon(teleponBaru.trim());
            }
            if (alamatBaru != null && !alamatBaru.trim().isEmpty()) {
                supplier.setAlamat(alamatBaru.trim());
            }
            supplierRepo.update(supplier);
            return "[v] Supplier berhasil diupdate";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    public String hapusSupplier(int id) {
        if (bajuRepo.isSupplierDigunakan(id)) {
            return "[!] Supplier masih memasok baju, tidak bisa dihapus!";
        }
        boolean ok = supplierRepo.hapus(id);
        return ok ? "[v] Supplier dihapus" : "[!] Supplier tidak ditemukan";
    }

    // ==================== BAJU ====================

    public String tambahBaju(String nama, double harga, int stok, String motif, String ukuran,
                             String kategoriLabel, int supplierId) {
        if (nama == null || nama.trim().isEmpty()) {
            return "[!] Nama baju tidak boleh kosong";
        }
        if (bajuRepo.cariByNama(nama.trim()) != null) {
            return "[!] Baju dengan nama tersebut sudah ada";
        }

        // Cari enum Kategori berdasarkan label
        Produk.Kategori kategori = cariKategoriByLabel(kategoriLabel);
        if (kategori == null) {
            return "[!] Kategori tidak valid. Pilih: Batik Tulis, Batik Cap, Batik Printing, Batik Kombinasi";
        }

        Supplier supplier = supplierRepo.cariById(supplierId);
        if (supplier == null) {
            return "[!] Supplier dengan ID " + supplierId + " tidak ditemukan";
        }

        try {
            Baju baju = new Baju(nama.trim(), harga, stok, motif, ukuran, kategori, supplier);
            bajuRepo.tambah(baju);
            return "[v] Baju berhasil ditambahkan!";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    public List<Baju> getAllBaju() {
        return bajuRepo.getAll();
    }

    public Baju cariBajuByNama(String nama) {
        return bajuRepo.cariByNama(nama);
    }

    public String editBaju(String namaLama, String namaBaru, String hargaStr, String stokStr,
                           String motifBaru, String ukuranBaru,
                           String kategoriLabel, Integer supplierId) {
        Baju baju = bajuRepo.cariByNama(namaLama);
        if (baju == null) {
            return "[!] Baju tidak ditemukan";
        }
        try {
            // Skip field kosong, gunakan nilai lama jika input kosong (KNF-01)
            String finalNama = (namaBaru != null && !namaBaru.trim().isEmpty())
                    ? namaBaru.trim() : baju.getNamaProduk();

            double finalHarga = baju.getHarga();
            if (hargaStr != null && !hargaStr.trim().isEmpty()) {
                try {
                    finalHarga = Double.parseDouble(hargaStr.trim());
                } catch (NumberFormatException e) {
                    return "[!] Format harga tidak valid";
                }
            }

            int finalStok = baju.getStok();
            if (stokStr != null && !stokStr.trim().isEmpty()) {
                try {
                    finalStok = Integer.parseInt(stokStr.trim());
                } catch (NumberFormatException e) {
                    return "[!] Format stok tidak valid";
                }
            }

            String finalMotif = (motifBaru != null && !motifBaru.trim().isEmpty())
                    ? motifBaru.trim() : baju.getMotif();
            String finalUkuran = (ukuranBaru != null && !ukuranBaru.trim().isEmpty())
                    ? ukuranBaru.trim() : baju.getUkuran();

            Produk.Kategori finalKategori = baju.getKategori();
            if (kategoriLabel != null && !kategoriLabel.trim().isEmpty()) {
                Produk.Kategori k = cariKategoriByLabel(kategoriLabel);
                if (k == null) {
                    return "[!] Kategori tidak valid";
                }
                finalKategori = k;
            }

            Supplier finalSupplier = baju.getSupplier();
            if (supplierId != null) {
                finalSupplier = supplierRepo.cariById(supplierId);
                if (finalSupplier == null) {
                    return "[!] Supplier dengan ID " + supplierId + " tidak ditemukan";
                }
            }

            Baju bajuBaru = new Baju(finalNama, finalHarga, finalStok, finalMotif,
                    finalUkuran, finalKategori, finalSupplier);
            bajuRepo.updateByNamaLama(namaLama, bajuBaru);
            return "[v] Baju berhasil diupdate";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    public String hapusBaju(String nama) {
        boolean ok = bajuRepo.hapus(nama);
        return ok ? "[v] Baju dihapus" : "[!] Baju tidak ditemukan";
    }

    public String tambahStok(String nama, int jumlahTambah) {
        Baju baju = bajuRepo.cariByNama(nama);
        if (baju == null) {
            return "[!] Baju tidak ditemukan";
        }
        if (jumlahTambah <= 0) {
            return "[!] Jumlah tambah stok harus lebih dari 0";
        }
        try {
            baju.tambahStok(jumlahTambah);
            return "[v] Stok berhasil ditambah. Stok sekarang: " + baju.getStok();
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    // ========== HELPER ==========

    public Produk.Kategori[] getAllKategori() {
        return Produk.Kategori.values();
    }

    private Produk.Kategori cariKategoriByLabel(String label) {
        for (Produk.Kategori k : Produk.Kategori.values()) {
            if (k.getLabel().equalsIgnoreCase(label.trim())) {
                return k;
            }
        }
        return null;
    }
}