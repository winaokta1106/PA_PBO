package com.omahkoebatik.service;

import com.omahkoebatik.model.Baju;
import com.omahkoebatik.model.Supplier;

import java.util.ArrayList;

public class DataStorage {
    private static ArrayList<Baju> listBaju = new ArrayList<>();
    private static ArrayList<Supplier> listSupplier = new ArrayList<>();

    public static void initDummyData() {
        if (listSupplier.isEmpty()) {
            listSupplier.add(new Supplier(1, "Batik Solo Jaya", "0812345", "Solo"));
            listSupplier.add(new Supplier(2, "Pekalongan Center", "0856789", "Pekalongan"));
        }
        if (listBaju.isEmpty()) {
            listBaju.add(new Baju(1, "Batik Kawung", "Kawung", "L", 500.0, 25, "Katun", listSupplier.get(0)));
            listBaju.add(new Baju(2, "Batik Megamendung", "Awan", "XL", 700.0, 40, "Sutra", listSupplier.get(1)));
        }
    }

    public static void tambahBaju(Baju b) { listBaju.add(b); }
    public static void tambahSupplier(Supplier s) { listSupplier.add(s); }

    public static ArrayList<Baju> getListBaju() { return listBaju; }
    public static ArrayList<Supplier> getListSupplier() { return listSupplier; }

    public static int nextIdBaju() { return listBaju.size() + 1; }
    public static int nextIdSupplier() { return listSupplier.size() + 1; }
}