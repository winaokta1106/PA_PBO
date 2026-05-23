package repository;

import model.Baju;
import java.util.ArrayList;
import java.util.List;

public class BajuRepository {

    private ArrayList<Baju> daftarBaju = new ArrayList<>();

    public void tambah(Baju baju) {
        daftarBaju.add(baju);
    }

    public List<Baju> getAll() {
        return new ArrayList<>(daftarBaju);
    }

    public Baju cariByNama(String nama) {
        if (nama == null) return null;
        for (Baju b : daftarBaju) {
            if (b.getNamaProduk().equalsIgnoreCase(nama.trim())) return b;
        }
        return null;
    }

    public Baju cariByIndex(int index) {
        if (index < 0 || index >= daftarBaju.size()) return null;
        return daftarBaju.get(index);
    }

    public void update(String namaLama, Baju bajuBaru) {
        for (int i = 0; i < daftarBaju.size(); i++) {
            if (daftarBaju.get(i).getNamaProduk().equalsIgnoreCase(namaLama.trim())) {
                daftarBaju.set(i, bajuBaru);
                return;
            }
        }
    }

    public boolean hapus(String nama) {
        return daftarBaju.removeIf(b -> b.getNamaProduk().equalsIgnoreCase(nama.trim()));
    }

    public boolean isSupplierDigunakan(int supplierId) {
        for (Baju b : daftarBaju) {
            if (b.getSupplier().getId() == supplierId) return true;
        }
        return false;
    }
}