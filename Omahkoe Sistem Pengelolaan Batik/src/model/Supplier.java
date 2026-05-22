package model;

public class Supplier {

    // ========== FIELD (semua private - Encapsulation) ==========
    private int id;
    private String nama;
    private String telepon;
    private String alamat;

    // ========== CONSTRUCTOR ==========
    public Supplier(int id, String nama, String telepon, String alamat) {
        setId(id);
        setNama(nama);
        setTelepon(telepon);
        setAlamat(alamat);
    }

    // ========== GETTER ==========
    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getTelepon() {
        return telepon;
    }

    public String getAlamat() {
        return alamat;
    }

    // ========== SETTER dengan validasi (Encapsulation) ==========
    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("[!] ID supplier harus lebih dari 0");
        }
        this.id = id;
    }

    public void setNama(String nama) {
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("[!] Nama supplier tidak boleh kosong");
        }
        this.nama = nama.trim();
    }

    public void setTelepon(String telepon) {
        if (telepon == null || telepon.trim().isEmpty()) {
            throw new IllegalArgumentException("[!] Telepon tidak boleh kosong");
        }
        // Hitung hanya karakter digit (non-digit diabaikan dalam hitungan)
        int jumlahDigit = 0;
        for (char c : telepon.toCharArray()) {
            if (Character.isDigit(c)) {
                jumlahDigit++;
            }
        }
        if (jumlahDigit < 10) {
            throw new IllegalArgumentException("[!] Telepon harus mengandung minimal 10 digit angka");
        }
        this.telepon = telepon.trim();
    }

    public void setAlamat(String alamat) {
        if (alamat == null || alamat.trim().isEmpty()) {
            throw new IllegalArgumentException("[!] Alamat tidak boleh kosong");
        }
        this.alamat = alamat.trim();
    }

    // ========== METHOD ==========
    public String tampilInfo() {
        return "ID: " + id + " | Nama: " + nama + " | Telp: " + telepon + " | Alamat: " + alamat;
    }

    @Override
    public String toString() {
        return tampilInfo();
    }
}