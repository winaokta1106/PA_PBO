package com.omahkoebatik.util;

public class Validasi {package com.omahkoebatik.util;
        public static boolean isValidNama(String nama) {
            return nama != null && !nama.trim().isEmpty() && nama.length() >= 3;
        }

        public static boolean isValidHarga(String hargaStr) {
            try {
                double harga = Double.parseDouble(hargaStr);
                return harga > 0;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        public static boolean isValidStok(String stokStr) {
            try {
                int stok = Integer.parseInt(stokStr);
                return stok >= 0;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        public static boolean isValidAlamat(String alamat) {
            return alamat != null && !alamat.trim().isEmpty() && alamat.length() >= 5;
        }

        public static boolean isValidEmail(String email) {
            String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
            return email != null && email.matches(emailRegex);
        }

        public static String getValidationMessage(String field, String value) {
            if (field.equals("nama")) {
                if (!isValidNama(value)) {
                    return Constants.ERROR_NAMA_INVALID;
                }
            } else if (field.equals("harga")) {
                if (!isValidHarga(value)) {
                    return Constants.ERROR_HARGA_INVALID;
                }
            } else if (field.equals("stok")) {
                if (!isValidStok(value)) {
                    return Constants.ERROR_STOK_INVALID;
                }
            } else if (field.equals("alamat")) {
                if (!isValidAlamat(value)) {
                    return Constants.ERROR_ALAMAT_INVALID;
                }
            }
            return "";
        }
    }
}
