package com.omahkoebatik.entity;

public class BajuEntity {
    public class Baju extends Produk {
        private String motif;
        private String ukuran;
        private String jenisKain;
        private Supplier supplier;

        public Baju(int id, String namaProduk, String motif, String ukuran,
                    double harga, int stok, String jenisKain, Supplier supplier) {
            super(id, namaProduk, "Baju Batik", harga, stok);
            this.motif = motif;
            this.ukuran = ukuran;
            this.jenisKain = jenisKain;
            this.supplier = supplier;
        }

        // Getter & Setter untuk atribut spesifik
        public String getMotif() { return motif; }
        public void setMotif(String motif) { this.motif = motif; }

        public String getUkuran() { return ukuran; }
        public void setUkuran(String ukuran) { this.ukuran = ukuran; }

        public String getJenisKain() { return jenisKain; }
        public void setJenisKain(String jenisKain) { this.jenisKain = jenisKain; }

        public Supplier getSupplier() { return supplier; }
        public void setSupplier(Supplier supplier) { this.supplier = supplier; }

        // POLYMORPHISM: Override method dari Produk
        @Override
        public boolean validasiData() {
            return getNamaProduk() != null && !getNamaProduk().trim().isEmpty() &&
                    getHarga() > 0 &&
                    getStok() >= 0 &&
                    motif != null && !motif.trim().isEmpty() &&
                    ukuran != null && !ukuran.trim().isEmpty() &&
                    jenisKain != null && !jenisKain.trim().isEmpty() &&
                    supplier != null;
        }

        @Override
        public String getDetailProduk() {
            return "Baju Batik - " + getNamaProduk() +
                    " | Motif: " + motif +
                    " | Ukuran: " + ukuran +
                    " | Jenis Kain: " + jenisKain +
                    " | Harga: Rp " + getHarga() +
                    " | Stok: " + getStok();
        }

        @Override
        public String toString() {
            return "Baju{" +
                    "id=" + getId() +
                    ", nama='" + getNamaProduk() + '\'' +
                    ", motif='" + motif + '\'' +
                    ", ukuran='" + ukuran + '\'' +
                    ", harga=" + getHarga() +
                    ", stok=" + getStok() +
                    ", jenisKain='" + jenisKain + '\'' +
                    ", supplier=" + (supplier != null ? supplier.getNamaSupplier() : "-") +
                    '}';
        }
    }
}
