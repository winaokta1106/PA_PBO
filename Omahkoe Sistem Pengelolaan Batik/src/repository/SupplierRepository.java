package repository;

import model.Supplier;
import java.util.ArrayList;
import java.util.List;

public class SupplierRepository {

    private ArrayList<Supplier> daftarSupplier = new ArrayList<>();
    private int nextId = 1;

    public void tambah(Supplier supplier) {
        daftarSupplier.add(supplier);
    }

    public List<Supplier> getAll() {
        return daftarSupplier;
    }

    public Supplier cariById(int id) {
        for (Supplier s : daftarSupplier) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    public void update(Supplier supplier) {
        for (int i = 0; i < daftarSupplier.size(); i++) {
            if (daftarSupplier.get(i).getId() == supplier.getId()) {
                daftarSupplier.set(i, supplier);
                return;
            }
        }
    }

    public boolean hapus(int id) {
        for (int i = 0; i < daftarSupplier.size(); i++) {
            if (daftarSupplier.get(i).getId() == id) {
                daftarSupplier.remove(i);
                return true;
            }
        }
        return false;
    }

    public int generateNextId() {
        return nextId++;
    }
}