# OmahKoe Batik

OmahKoe Batik merupakan aplikasi berbasis **Java** yang digunakan untuk mengelola stok, kategori, supplier, serta simulasi penjualan produk batik melalui terminal/console.
Program ini merupakan pengembangan lanjutan dengan penerapan konsep **Object Oriented Programming (OOP)** yang lebih lengkap, khususnya:

* **Abstract Class**
* **Interface**
* **Inheritance**
* **Polymorphism**
* **Method Overloading**
* **Encapsulation**

Sistem dibuat agar pengelolaan data produk batik menjadi lebih terstruktur dan modular dengan memanfaatkan hubungan antar class dalam OOP.

---

# Konsep OOP yang Digunakan

Program ini menerapkan beberapa konsep penting dalam OOP:

1. **Class & Object**
   Digunakan untuk merepresentasikan objek seperti produk batik, kategori, dan supplier.

2. **Encapsulation**
   Seluruh atribut dibuat `private` atau `protected` dan diakses menggunakan getter dan setter.

3. **Inheritance**
   Class turunan mewarisi atribut dan method dari parent class.

4. **Abstraction (Abstract Class)**
   Digunakan untuk membuat template umum produk sebelum diturunkan menjadi jenis produk tertentu.

5. **Interface**
   Digunakan sebagai kontrak perilaku diskon pada setiap produk.

6. **Polymorphism**
   Method yang sama dapat memiliki perilaku berbeda tergantung objek yang menjalankannya.

7. **Method Overloading**
   Method dibuat dengan parameter berbeda untuk kebutuhan yang berbeda.

---

# Abstract Class

Pada program ini, class `com.omahkoebatik.model.Produk` dibuat sebagai **abstract class**.

Ciri-ciri yang diterapkan:

* Tidak dapat dibuat objek langsung
* Digunakan sebagai parent class
* Memiliki abstract method

Contoh:

```java
import com.omahkoebatik.model.IDiskon;

public abstract class com.omahkoebatik.model.Produk implements IDiskon {

   public abstract String getTipeProduk();

   public abstract void tampilInfo();

   @Override
   public abstract double hitungDiskon(int jumlah);

   @Override
   public abstract String getInfoDiskon();
}
```

Abstract method tersebut wajib diimplementasikan oleh subclass.

---

# Inheritance

Program menggunakan konsep inheritance dimana class `com.omahkoebatik.model.Baju` mewarisi class `com.omahkoebatik.model.Produk`.

Struktur class:

```text
com.omahkoebatik.model.Produk (Abstract Class)
│
└── com.omahkoebatik.model.Baju
```

Class `com.omahkoebatik.model.Baju` mendapatkan atribut dan method dari `com.omahkoebatik.model.Produk`, seperti:

* idProduk
* namaProduk
* harga
* stok
* kategori
* supplier
* tambahStok()
* kurangiStok()

---

# Interface

Program menggunakan interface bernama `com.omahkoebatik.model.IDiskon`.

Interface ini berfungsi sebagai kontrak diskon yang wajib diterapkan pada setiap produk.

Contoh:

```java
public interface com.omahkoebatik.model.IDiskon {
    double hitungDiskon(int jumlah);
    String getInfoDiskon();
}
```

Class yang mengimplementasikan interface:

* `com.omahkoebatik.model.Baju`

---

# Polymorphism

Program menerapkan polymorphism dalam beberapa bagian.

## 1. Override Method

Class `com.omahkoebatik.model.Baju` melakukan override terhadap method dari `com.omahkoebatik.model.Produk`.

Contoh:

```java
@Override
public String getTipeProduk() {
    return "com.omahkoebatik.model.Baju Batik";
}
```

Method yang dioverride:

* `getTipeProduk()`
* `tampilInfo()`
* `hitungDiskon()`
* `getInfoDiskon()`

---

## 2. Polymorphism melalui Reference Parent

Contoh pada laporan produk:

```java
import com.omahkoebatik.model.Produk;

ArrayList<Produk> semuaProduk = new ArrayList<>();
```

Walaupun tipe referensinya `com.omahkoebatik.model.Produk`, object yang disimpan adalah `com.omahkoebatik.model.Baju`.

---

## 3. Polymorphism melalui Interface

Contoh:

```java
import com.omahkoebatik.model.IDiskon;

ArrayList<IDiskon> produkDiskon = new ArrayList<>();
```

Object `com.omahkoebatik.model.Baju` diperlakukan sebagai `com.omahkoebatik.model.IDiskon`.

---

# Method Overloading

Program juga menggunakan method overloading.

Contoh pada method `hitungDiskon()`:

```java
hitungDiskon(int jumlah)
hitungDiskon(int jumlah, String tipePelanggan)
hitungDiskon(int jumlah, String tipePelanggan, boolean promoMusiman)
```

Setiap method memiliki parameter berbeda sesuai kebutuhan transaksi.

---

# Struktur Class

Program terdiri dari beberapa class utama:

---

## 1. `com.omahkoebatik.model.Produk.java` (Abstract Class)

Berfungsi sebagai template utama seluruh produk.

Atribut:

* idProduk
* namaProduk
* harga
* stok
* kategori
* supplier

Method abstract:

* `getTipeProduk()`
* `tampilInfo()`
* `hitungDiskon()`
* `getInfoDiskon()`

---

## 2. `com.omahkoebatik.model.Baju.java`

Subclass dari `com.omahkoebatik.model.Produk`.

Fungsi:

* Menyimpan data baju batik
* Mengatur diskon
* Mengelola informasi produk
* Simulasi penjualan

Atribut tambahan:

* motif
* ukuran
* tipePelanggan

---

## 3. `com.omahkoebatik.model.Kategori.java`

Digunakan untuk menyimpan data kategori produk.

Atribut:

* idKategori
* namaKategori
* deskripsi

---

## 4. `com.omahkoebatik.model.Supplier.java`

Digunakan untuk menyimpan data supplier.

Atribut:

* idSupplier
* namaSupplier
* noTelepon
* alamat

---

## 5. `com.omahkoebatik.model.IDiskon.java` (Interface)

Berisi kontrak method:

* `hitungDiskon()`
* `getInfoDiskon()`

---

## 6. `com.omahkoebatik.view.Main.java`

Merupakan class utama yang menjalankan program.

Fungsi:

* Menampilkan menu
* Mengelola kategori
* Mengelola supplier
* Mengelola stok baju
* Simulasi penjualan
* Menampilkan laporan
* Mengelola data menggunakan `ArrayList`

---

# Fitur Program

## 1. Kelola com.omahkoebatik.model.Kategori

Fitur:

* Tambah kategori
* Lihat kategori
* Edit kategori
* Hapus kategori

---

## 2. Kelola com.omahkoebatik.model.Supplier

Fitur:

* Tambah supplier
* Lihat supplier
* Edit supplier
* Hapus supplier

---

## 3. Kelola Stok com.omahkoebatik.model.Baju

Fitur:

* Tambah baju
* Lihat stok baju
* Edit data baju
* Hapus baju
* Tambah stok

---

## 4. Simulasi Penjualan

Fitur:

* Pilih produk
* Pilih tipe pelanggan
* Hitung diskon otomatis
* Promo musiman
* Pengurangan stok otomatis

Jenis pelanggan:

* Reguler
* Member
* VIP

---

## 5. Laporan & Informasi com.omahkoebatik.model.Produk

Fitur:

* Ringkasan seluruh produk
* Informasi diskon produk
* Cari detail produk
* Informasi harga dan stok

---

# Sistem Diskon

Program memiliki sistem diskon bertingkat.

## Berdasarkan Jumlah Pembelian

| Jumlah Beli | Diskon    |
| ----------- | --------- |
| < 5 pcs     | Tidak ada |
| 5 – 9 pcs   | 5%        |
| ≥ 10 pcs    | 10%       |

---

## Tambahan Diskon Pelanggan

| Tipe Pelanggan | Tambahan |
| -------------- | -------- |
| Member         | +5%      |
| VIP            | +10%     |

---

## Promo Musiman

Tambahan diskon:

```text
+15%
```

---

# Validasi Data

Program memiliki beberapa validasi input:

* Input angka harus valid
* Nama tidak boleh kosong
* Harga tidak boleh negatif
* Nomor telepon minimal 10 digit
* Stok tidak boleh kurang dari 0

---

# Struktur Penyimpanan Data

Program menggunakan:

```java
ArrayList
```

Untuk menyimpan data:

* com.omahkoebatik.model.Kategori
* com.omahkoebatik.model.Supplier
* com.omahkoebatik.model.Baju

Karena program masih berbasis console dan belum menggunakan database.

---

# Kesimpulan

Program **OmahKoe Batik – Posttest 5** berhasil menerapkan berbagai konsep penting dalam Object Oriented Programming (OOP).

Konsep yang berhasil diterapkan:

* Abstract Class sebagai template produk
* Interface sebagai kontrak diskon
* Inheritance antara `com.omahkoebatik.model.Produk` dan `com.omahkoebatik.model.Baju`
* Polymorphism pada method dan object reference
* Method Overloading untuk variasi perhitungan diskon
* Encapsulation untuk menjaga keamanan data

Dengan penerapan konsep tersebut, program menjadi lebih modular, terstruktur, mudah dikembangkan, dan lebih fleksibel untuk penambahan fitur di masa mendatang.
