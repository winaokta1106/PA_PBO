package com.omahkoebatik.entity;

public class SupplierEntity {
        private int id;
        private String namaSupplier;
        private String noTelp;
        private String alamat;

        public Supplier(int id, String namaSupplier, String noTelp, String alamat) {
            this.id = id;
            this.namaSupplier = namaSupplier;
            this.noTelp = noTelp;
            this.alamat = alamat;
        }

        // Getter
        public int getId() { return id; }
        public String getNamaSupplier() { return namaSupplier; }
        public String getNoTelp() { return noTelp; }
        public String getAlamat() { return alamat; }

        // Setter
        public void setNamaSupplier(String namaSupplier) { this.namaSupplier = namaSupplier; }
        public void setNoTelp(String noTelp) { this.noTelp = noTelp; }
        public void setAlamat(String alamat) { this.alamat = alamat; }

        @Override
        public String toString() {
            return namaSupplier + " (" + alamat + ")";
        }
    }
}
