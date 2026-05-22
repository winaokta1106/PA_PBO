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
        return daftarBaju;
    }

    public Baju cariByNama(String nama) {
        for (Baju b : daftarBaju) {
            if (b.getNamaProduk().equalsIgnoreCase(nama)) {
                return b;
            }
        }
        return null;
    }

    public void updateByNamaLama(String namaLama, Baju bajuBaru) {
        for (int i = 0; i < daftarBaju.size(); i++) {
            if (daftarBaju.get(i).getNamaProduk().equalsIgnoreCase(namaLama)) {
                daftarBaju.set(i, bajuBaru);
                return;
            }
        }
    }

    public boolean hapus(String nama) {
        for (int i = 0; i < daftarBaju.size(); i++) {
            if (daftarBaju.get(i).getNamaProduk().equalsIgnoreCase(nama)) {
                daftarBaju.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean isSupplierDigunakan(int supplierId) {
        for (Baju b : daftarBaju) {
            if (b.getSupplier() != null && b.getSupplier().getId() == supplierId) {
                return true;
            }
        }
        return false;
    }
}