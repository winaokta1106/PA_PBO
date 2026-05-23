package repository;

import model.Supplier;
import java.util.ArrayList;
import java.util.List;

public class SupplierRepository {

    private ArrayList<Supplier> daftarSupplier = new ArrayList<>();
    private int nextId = 1;

    public void tambah(Supplier supplier) {
        daftarSupplier.add(supplier);
        nextId = supplier.getId() + 1;
    }

    public List<Supplier> getAll() {
        return new ArrayList<>(daftarSupplier);
    }

    public Supplier cariById(int id) {
        for (Supplier s : daftarSupplier) {
            if (s.getId() == id) return s;
        }
        return null;
    }

    public Supplier cariByNama(String nama) {
        if (nama == null) return null;
        for (Supplier s : daftarSupplier) {
            if (s.getNama().equalsIgnoreCase(nama.trim())) return s;
        }
        return null;
    }

    public void update(Supplier supplierBaru) {
        for (int i = 0; i < daftarSupplier.size(); i++) {
            if (daftarSupplier.get(i).getId() == supplierBaru.getId()) {
                daftarSupplier.set(i, supplierBaru);
                return;
            }
        }
    }

    public boolean hapus(int id) {
        return daftarSupplier.removeIf(s -> s.getId() == id);
    }

    public int generateNextId() {
        return nextId;
    }
}