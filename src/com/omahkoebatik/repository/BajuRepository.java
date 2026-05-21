package com.omahkoebatik.repository;
import com.omahkoebatik.entity.Baju;
import java.util.ArrayList;

public class BajuRepository implements IRepository<Baju> {
    private ArrayList<Baju> listBaju = new ArrayList<>();

    @Override
    public void tambah(Baju baju) {
        listBaju.add(baju);
    }

    @Override
    public ArrayList<Baju> getAll() {
        return new ArrayList<>(listBaju); // Return copy untuk encapsulation
    }

    @Override
    public Baju getById(int id) {
        for (Baju baju : listBaju) {
            if (baju.getId() == id) {
                return baju;
            }
        }
        return null;
    }

    @Override
    public void update(int id, Baju bajuBaru) {
        for (int i = 0; i < listBaju.size(); i++) {
            if (listBaju.get(i).getId() == id) {
                listBaju.set(i, bajuBaru);
                return;
            }
        }
    }

    @Override
    public void hapus(Baju baju) {
        listBaju.remove(baju);
    }

    @Override
    public int getNextId() {
        return listBaju.isEmpty() ? 1 : listBaju.get(listBaju.size() - 1).getId() + 1;
    }

    @Override
    public int getTotal() {
        return listBaju.size();
    }

    public void clearAll() {
        listBaju.clear();
    }
}