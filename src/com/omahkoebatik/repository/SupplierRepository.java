package com.omahkoebatik.repository;
import com.omahkoebatik.entity.Supplier;
import java.util.ArrayList;

/**
 * Repository untuk mengelola data Supplier
 * Implementasi dari IRepository interface
 * Menerapkan konsep Encapsulation dengan private ArrayList
 */
public class SupplierRepository implements IRepository<Supplier> {
    private ArrayList<Supplier> listSupplier = new ArrayList<>();

    @Override
    public void tambah(Supplier supplier) {
        listSupplier.add(supplier);
    }

    @Override
    public ArrayList<Supplier> getAll() {
        return new ArrayList<>(listSupplier); // Return copy untuk encapsulation
    }

    @Override
    public Supplier getById(int id) {
        for (Supplier supplier : listSupplier) {
            if (supplier.getId() == id) {
                return supplier;
            }
        }
        return null;
    }

    @Override
    public void update(int id, Supplier supplierBaru) {
        for (int i = 0; i < listSupplier.size(); i++) {
            if (listSupplier.get(i).getId() == id) {
                listSupplier.set(i, supplierBaru);
                return;
            }
        }
    }

    @Override
    public void hapus(Supplier supplier) {
        listSupplier.remove(supplier);
    }

    @Override
    public int getNextId() {
        return listSupplier.isEmpty() ? 1 : listSupplier.get(listSupplier.size() - 1).getId() + 1;
    }

    @Override
    public int getTotal() {
        return listSupplier.size();
    }

    public void clearAll() {
        listSupplier.clear();
    }
}