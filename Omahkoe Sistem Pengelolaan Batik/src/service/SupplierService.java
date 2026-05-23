package service;

import model.Supplier;
import repository.BajuRepository;
import repository.CelanaRepository;
import repository.SupplierRepository;
import java.util.List;

public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final BajuRepository bajuRepository;
    private final CelanaRepository celanaRepository;

    public SupplierService(SupplierRepository supplierRepository,
                           BajuRepository bajuRepository,
                           CelanaRepository celanaRepository) {
        this.supplierRepository = supplierRepository;
        this.bajuRepository = bajuRepository;
        this.celanaRepository = celanaRepository;
    }

    public String tambahSupplier(String nama, String telepon, String alamat) {
        if (nama == null || nama.trim().isEmpty())
            return "[!] Nama supplier tidak boleh kosong";
        if (telepon == null || telepon.trim().isEmpty())
            return "[!] Telepon tidak boleh kosong";
        if (alamat == null || alamat.trim().isEmpty())
            return "[!] Alamat tidak boleh kosong";

        try {
            int id = supplierRepository.generateNextId();
            Supplier supplier = new Supplier(id, nama, telepon, alamat);
            supplierRepository.tambah(supplier);
            return "[v] Supplier berhasil ditambahkan!";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    public List<Supplier> getAllSupplier() {
        return supplierRepository.getAll();
    }

    public Supplier getSupplierById(int id) {
        return supplierRepository.cariById(id);
    }

    public String editSupplier(int id, String namaBaru, String teleponBaru, String alamatBaru) {
        Supplier existing = supplierRepository.cariById(id);
        if (existing == null)
            return "[!] Supplier dengan ID " + id + " tidak ditemukan";

        try {
            String nama = (namaBaru == null || namaBaru.trim().isEmpty()) ? existing.getNama() : namaBaru.trim();
            String telepon = (teleponBaru == null || teleponBaru.trim().isEmpty()) ? existing.getTelepon() : teleponBaru.trim();
            String alamat = (alamatBaru == null || alamatBaru.trim().isEmpty()) ? existing.getAlamat() : alamatBaru.trim();

            Supplier updated = new Supplier(id, nama, telepon, alamat);
            supplierRepository.update(updated);
            return "[v] Supplier berhasil diupdate";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    public String hapusSupplier(int id) {
        Supplier existing = supplierRepository.cariById(id);
        if (existing == null)
            return "[!] Supplier dengan ID " + id + " tidak ditemukan";

        if (bajuRepository.isSupplierDigunakan(id) || celanaRepository.isSupplierDigunakan(id))
            return "[!] Supplier masih memasok baju/celana, tidak bisa dihapus!";

        supplierRepository.hapus(id);
        return "[v] Supplier dihapus";
    }
}