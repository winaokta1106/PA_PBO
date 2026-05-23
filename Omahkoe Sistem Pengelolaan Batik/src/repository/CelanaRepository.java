package repository;

import model.Celana;
import java.util.ArrayList;
import java.util.List;

public class CelanaRepository {

    private ArrayList<Celana> daftarCelana = new ArrayList<>();

    public void tambah(Celana celana) {
        daftarCelana.add(celana);
    }

    public List<Celana> getAll() {
        return new ArrayList<>(daftarCelana);
    }

    public Celana cariByNama(String nama) {
        if (nama == null) return null;
        for (Celana c : daftarCelana) {
            if (c.getNamaProduk().equalsIgnoreCase(nama.trim())) return c;
        }
        return null;
    }

    public Celana cariByIndex(int index) {
        if (index < 0 || index >= daftarCelana.size()) return null;
        return daftarCelana.get(index);
    }

    public void update(String namaLama, Celana celanaBaru) {
        for (int i = 0; i < daftarCelana.size(); i++) {
            if (daftarCelana.get(i).getNamaProduk().equalsIgnoreCase(namaLama.trim())) {
                daftarCelana.set(i, celanaBaru);
                return;
            }
        }
    }

    public boolean hapus(String nama) {
        return daftarCelana.removeIf(c -> c.getNamaProduk().equalsIgnoreCase(nama.trim()));
    }

    public boolean isSupplierDigunakan(int supplierId) {
        for (Celana c : daftarCelana) {
            if (c.getSupplier().getId() == supplierId) return true;
        }
        return false;
    }
}