package view;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Baju;
import model.Celana;
import model.Supplier;
import repository.*;
import service.*;

import java.text.DecimalFormat;
import java.util.Optional;

public class DashboardController {

    // ==================== SUPPLIER ====================
    @FXML private TextField tfNamaSupplier;
    @FXML private TextField tfTeleponSupplier;
    @FXML private TextField tfAlamatSupplier;
    @FXML private TableView<Supplier> tableSupplier;
    @FXML private TableColumn<Supplier, Integer> colSupplierId;
    @FXML private TableColumn<Supplier, String> colSupplierNama;
    @FXML private TableColumn<Supplier, String> colSupplierTelepon;
    @FXML private TableColumn<Supplier, String> colSupplierAlamat;
    @FXML private Label lblPesanSupplier;

    // ==================== BAJU ====================
    @FXML private TextField tfNamaBaju;
    @FXML private TextField tfHargaBaju;
    @FXML private TextField tfStokBaju;
    @FXML private TextField tfMotifBaju;
    @FXML private ComboBox<String> cbUkuranBaju;
    @FXML private ComboBox<String> cbKategoriBaju;
    @FXML private TextField tfIdSupplierBaju;
    @FXML private TableView<Baju> tableBaju;
    @FXML private TableColumn<Baju, String> colBajuNama;
    @FXML private TableColumn<Baju, Double> colBajuHarga;
    @FXML private TableColumn<Baju, Integer> colBajuStok;
    @FXML private TableColumn<Baju, String> colBajuMotif;
    @FXML private TableColumn<Baju, String> colBajuUkuran;
    @FXML private TableColumn<Baju, String> colBajuKategori;
    @FXML private TableColumn<Baju, String> colBajuSupplier;
    @FXML private Label lblPesanBaju;

    // ==================== CELANA (DENGAN BAHAN) ====================
    @FXML private TextField tfNamaCelana;
    @FXML private TextField tfHargaCelana;
    @FXML private TextField tfStokCelana;
    @FXML private TextField tfModelCelana;
    @FXML private ComboBox<String> cbUkuranCelana;
    @FXML private TextField tfBahanCelana;          // ← BAHAN ADA
    @FXML private ComboBox<String> cbKategoriCelana;
    @FXML private TextField tfIdSupplierCelana;
    @FXML private TableView<Celana> tableCelana;
    @FXML private TableColumn<Celana, String> colCelanaNama;
    @FXML private TableColumn<Celana, Double> colCelanaHarga;
    @FXML private TableColumn<Celana, Integer> colCelanaStok;
    @FXML private TableColumn<Celana, String> colCelanaModel;
    @FXML private TableColumn<Celana, String> colCelanaUkuran;
    @FXML private TableColumn<Celana, String> colCelanaBahan;   // ← BAHAN ADA
    @FXML private TableColumn<Celana, String> colCelanaKategori;
    @FXML private TableColumn<Celana, String> colCelanaSupplier;
    @FXML private Label lblPesanCelana;

    private SupplierService supplierService;
    private BajuService bajuService;
    private CelanaService celanaService;
    private ProdukService produkService;

    private int selectedSupplierId = -1;
    private String selectedBajuNama = null;
    private String selectedCelanaNama = null;

    private final DecimalFormat df = new DecimalFormat("Rp#,###");

    @FXML
    public void initialize() {
        SupplierRepository supplierRepo = new SupplierRepository();
        BajuRepository bajuRepo = new BajuRepository();
        CelanaRepository celanaRepo = new CelanaRepository();
        ProdukRepository produkRepo = new ProdukRepository();

        supplierService = new SupplierService(supplierRepo, bajuRepo, celanaRepo);
        bajuService = new BajuService(bajuRepo, supplierRepo, produkRepo);
        celanaService = new CelanaService(celanaRepo, supplierRepo, produkRepo);
        produkService = new ProdukService(produkRepo);

        setupTableSupplier();
        setupTableBaju();
        setupTableCelana();
        setupComboBoxes();
        setupTableListeners();

        refreshTableSupplier();
        refreshTableBaju();
        refreshTableCelana();
    }

    private void setupTableSupplier() {
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSupplierNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colSupplierTelepon.setCellValueFactory(new PropertyValueFactory<>("telepon"));
        colSupplierAlamat.setCellValueFactory(new PropertyValueFactory<>("alamat"));
    }

    private void setupTableBaju() {
        colBajuNama.setCellValueFactory(new PropertyValueFactory<>("namaProduk"));
        colBajuStok.setCellValueFactory(new PropertyValueFactory<>("stok"));
        colBajuMotif.setCellValueFactory(new PropertyValueFactory<>("motif"));
        colBajuUkuran.setCellValueFactory(new PropertyValueFactory<>("ukuran"));

        colBajuHarga.setCellValueFactory(new PropertyValueFactory<>("harga"));
        colBajuHarga.setCellFactory(col -> new TableCell<Baju, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : df.format(item));
            }
        });

        colBajuKategori.setCellFactory(col -> new TableCell<Baju, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setText(null);
                } else {
                    Baju b = (Baju) getTableRow().getItem();
                    setText(b.getKategori() != null ? b.getKategori().getLabel() : "");
                }
            }
        });
        colBajuKategori.setCellValueFactory(new PropertyValueFactory<>("namaProduk"));

        colBajuSupplier.setCellFactory(col -> new TableCell<Baju, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setText(null);
                } else {
                    Baju b = (Baju) getTableRow().getItem();
                    setText(b.getSupplier() != null ? b.getSupplier().getNama() : "");
                }
            }
        });
        colBajuSupplier.setCellValueFactory(new PropertyValueFactory<>("namaProduk"));
    }

    private void setupTableCelana() {
        colCelanaNama.setCellValueFactory(new PropertyValueFactory<>("namaProduk"));
        colCelanaStok.setCellValueFactory(new PropertyValueFactory<>("stok"));
        colCelanaModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colCelanaUkuran.setCellValueFactory(new PropertyValueFactory<>("ukuran"));
        colCelanaBahan.setCellValueFactory(new PropertyValueFactory<>("bahan"));  // ← BAHAN ADA

        colCelanaHarga.setCellValueFactory(new PropertyValueFactory<>("harga"));
        colCelanaHarga.setCellFactory(col -> new TableCell<Celana, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : df.format(item));
            }
        });

        colCelanaKategori.setCellFactory(col -> new TableCell<Celana, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setText(null);
                } else {
                    Celana c = (Celana) getTableRow().getItem();
                    setText(c.getKategori() != null ? c.getKategori().getLabel() : "");
                }
            }
        });
        colCelanaKategori.setCellValueFactory(new PropertyValueFactory<>("namaProduk"));

        colCelanaSupplier.setCellFactory(col -> new TableCell<Celana, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setText(null);
                } else {
                    Celana c = (Celana) getTableRow().getItem();
                    setText(c.getSupplier() != null ? c.getSupplier().getNama() : "");
                }
            }
        });
        colCelanaSupplier.setCellValueFactory(new PropertyValueFactory<>("namaProduk"));
    }

    private void setupComboBoxes() {
        cbKategoriBaju.setItems(FXCollections.observableArrayList("Anak-Anak", "Wanita", "Pria"));
        cbKategoriCelana.setItems(FXCollections.observableArrayList("Anak-Anak", "Wanita", "Pria"));
        cbUkuranBaju.setItems(FXCollections.observableArrayList("S", "M", "L", "XL", "XXL"));
        cbUkuranCelana.setItems(FXCollections.observableArrayList("S", "M", "L", "XL", "XXL"));
    }

    private void setupTableListeners() {
        tableSupplier.getSelectionModel().selectedItemProperty().addListener((obs, old, s) -> {
            if (s != null) {
                selectedSupplierId = s.getId();
                tfNamaSupplier.setText(s.getNama());
                tfTeleponSupplier.setText(s.getTelepon());
                tfAlamatSupplier.setText(s.getAlamat());
                lblPesanSupplier.setText("");
            }
        });

        tableBaju.getSelectionModel().selectedItemProperty().addListener((obs, old, b) -> {
            if (b != null) {
                selectedBajuNama = b.getNamaProduk();
                tfNamaBaju.setText(b.getNamaProduk());
                tfHargaBaju.setText(String.valueOf((int) b.getHarga()));
                tfStokBaju.setText(String.valueOf(b.getStok()));
                tfMotifBaju.setText(b.getMotif());
                cbUkuranBaju.setValue(b.getUkuran());
                cbKategoriBaju.setValue(b.getKategori().getLabel());
                tfIdSupplierBaju.setText(String.valueOf(b.getSupplier().getId()));
                lblPesanBaju.setText("");
            }
        });

        tableCelana.getSelectionModel().selectedItemProperty().addListener((obs, old, c) -> {
            if (c != null) {
                selectedCelanaNama = c.getNamaProduk();
                tfNamaCelana.setText(c.getNamaProduk());
                tfHargaCelana.setText(String.valueOf((int) c.getHarga()));
                tfStokCelana.setText(String.valueOf(c.getStok()));
                tfModelCelana.setText(c.getModel());
                cbUkuranCelana.setValue(c.getUkuran());
                tfBahanCelana.setText(c.getBahan());  // ← BAHAN ADA
                cbKategoriCelana.setValue(c.getKategori().getLabel());
                tfIdSupplierCelana.setText(String.valueOf(c.getSupplier().getId()));
                lblPesanCelana.setText("");
            }
        });
    }

    private void refreshTableSupplier() {
        tableSupplier.setItems(FXCollections.observableArrayList(supplierService.getAllSupplier()));
    }

    private void refreshTableBaju() {
        tableBaju.setItems(FXCollections.observableArrayList(bajuService.getAllBaju()));
    }

    private void refreshTableCelana() {
        tableCelana.setItems(FXCollections.observableArrayList(celanaService.getAllCelana()));
    }

    private void showAlert(String title, String header, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private boolean showConfirmation(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi");
        alert.setHeaderText(header);
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private void setPesan(Label label, String pesan) {
        label.setText(pesan);
        if (pesan.startsWith("[v]")) {
            label.setStyle("-fx-text-fill: #2E7D32; -fx-font-size: 13px; -fx-font-weight: bold;");
        } else if (pesan.startsWith("[!]")) {
            label.setStyle("-fx-text-fill: #C62828; -fx-font-size: 13px; -fx-font-weight: bold;");
        }
    }

    // ==================== SUPPLIER HANDLERS ====================
    @FXML private void handleTambahSupplier() {
        String nama = tfNamaSupplier.getText();
        String telepon = tfTeleponSupplier.getText();
        String alamat = tfAlamatSupplier.getText();

        if (nama.trim().isEmpty() || telepon.trim().isEmpty() || alamat.trim().isEmpty()) {
            showAlert("Peringatan", "Field Kosong", "Semua field harus diisi!", Alert.AlertType.WARNING);
            return;
        }

        for (char c : telepon.toCharArray()) {
            if (Character.isLetter(c)) {
                showAlert("Error", "Telepon Tidak Valid", "[!] Telepon tidak boleh mengandung huruf!", Alert.AlertType.ERROR);
                return;
            }
        }

        String hasil = supplierService.tambahSupplier(nama, telepon, alamat);
        setPesan(lblPesanSupplier, hasil);

        if (hasil.startsWith("[v]")) {
            showAlert("Sukses", "Supplier Ditambahkan", hasil, Alert.AlertType.INFORMATION);
            refreshTableSupplier();
            handleClearSupplier();
        } else {
            showAlert("Error", "Gagal Menambahkan", hasil, Alert.AlertType.ERROR);
        }
    }

    @FXML private void handleUpdateSupplier() {
        if (selectedSupplierId == -1) {
            showAlert("Peringatan", "Pilih Supplier", "Pilih supplier dari tabel terlebih dahulu!", Alert.AlertType.WARNING);
            return;
        }

        String telepon = tfTeleponSupplier.getText();
        if (!telepon.trim().isEmpty()) {
            for (char c : telepon.toCharArray()) {
                if (Character.isLetter(c)) {
                    showAlert("Error", "Telepon Tidak Valid", "[!] Telepon tidak boleh mengandung huruf!", Alert.AlertType.ERROR);
                    return;
                }
            }
        }

        String hasil = supplierService.editSupplier(selectedSupplierId,
                tfNamaSupplier.getText(), tfTeleponSupplier.getText(), tfAlamatSupplier.getText());
        setPesan(lblPesanSupplier, hasil);

        if (hasil.startsWith("[v]")) {
            showAlert("Sukses", "Supplier Diupdate", hasil, Alert.AlertType.INFORMATION);
            refreshTableSupplier();
            handleClearSupplier();
        } else {
            showAlert("Error", "Gagal Update", hasil, Alert.AlertType.ERROR);
        }
    }

    @FXML private void handleHapusSupplier() {
        if (selectedSupplierId == -1) {
            showAlert("Peringatan", "Pilih Supplier", "Pilih supplier dari tabel terlebih dahulu!", Alert.AlertType.WARNING);
            return;
        }

        boolean konfirmasi = showConfirmation("Hapus Supplier",
                "Yakin ingin menghapus supplier ID " + selectedSupplierId + "?\nData yang terhapus tidak bisa dikembalikan.");
        if (!konfirmasi) return;

        String hasil = supplierService.hapusSupplier(selectedSupplierId);
        setPesan(lblPesanSupplier, hasil);

        if (hasil.startsWith("[v]")) {
            showAlert("Sukses", "Supplier Dihapus", hasil, Alert.AlertType.INFORMATION);
            refreshTableSupplier();
            handleClearSupplier();
        } else {
            showAlert("Error", "Gagal Hapus", hasil, Alert.AlertType.ERROR);
        }
    }

    @FXML private void handleClearSupplier() {
        tfNamaSupplier.clear();
        tfTeleponSupplier.clear();
        tfAlamatSupplier.clear();
        selectedSupplierId = -1;
        tableSupplier.getSelectionModel().clearSelection();
        lblPesanSupplier.setText("");
    }

    // ==================== BAJU HANDLERS ====================
    @FXML private void handleTambahBaju() {
        String nama = tfNamaBaju.getText();
        if (nama.trim().isEmpty()) {
            showAlert("Peringatan", "Nama Merk Kosong", "[!] Nama merk tidak boleh kosong!", Alert.AlertType.WARNING);
            return;
        }

        String hargaStr = tfHargaBaju.getText();
        String stokStr = tfStokBaju.getText();

        try { Double.parseDouble(hargaStr.trim()); }
        catch (NumberFormatException e) {
            showAlert("Error", "Harga Tidak Valid", "[!] Harga harus berupa angka!", Alert.AlertType.ERROR); return;
        }
        try { Integer.parseInt(stokStr.trim()); }
        catch (NumberFormatException e) {
            showAlert("Error", "Stok Tidak Valid", "[!] Stok harus berupa angka!", Alert.AlertType.ERROR); return;
        }

        String ukuran = cbUkuranBaju.getValue();
        if (ukuran == null) {
            showAlert("Error", "Ukuran Belum Dipilih", "[!] Pilih ukuran baju!", Alert.AlertType.ERROR); return;
        }

        String kategori = cbKategoriBaju.getValue();
        if (kategori == null) {
            showAlert("Peringatan", "Kategori Belum Dipilih", "[!] Pilih kategori baju!", Alert.AlertType.WARNING); return;
        }

        String idSupplierStr = tfIdSupplierBaju.getText();
        int idSupplier;
        try { idSupplier = Integer.parseInt(idSupplierStr.trim()); }
        catch (NumberFormatException e) {
            showAlert("Error", "ID Supplier Tidak Valid", "[!] ID Supplier harus berupa angka!", Alert.AlertType.ERROR); return;
        }

        String hasil = bajuService.tambahBaju(nama, hargaStr, stokStr,
                tfMotifBaju.getText(), ukuran, kategori, idSupplier);
        setPesan(lblPesanBaju, hasil);

        if (hasil.startsWith("[v]")) {
            showAlert("Sukses", "Baju Ditambahkan", hasil, Alert.AlertType.INFORMATION);
            refreshTableBaju();
            handleClearBaju();
        } else {
            showAlert("Error", "Gagal Menambahkan", hasil, Alert.AlertType.ERROR);
        }
    }

    @FXML private void handleUpdateBaju() {
        if (selectedBajuNama == null) {
            showAlert("Peringatan", "Pilih Baju", "Pilih baju dari tabel terlebih dahulu!", Alert.AlertType.WARNING);
            return;
        }

        String hargaStr = tfHargaBaju.getText();
        String stokStr = tfStokBaju.getText();

        if (!hargaStr.trim().isEmpty()) {
            try { double h = Double.parseDouble(hargaStr.trim()); if (h < 0) { showAlert("Error", "Harga Negatif", "[!] Harga tidak boleh negatif!", Alert.AlertType.ERROR); return; } }
            catch (NumberFormatException e) { showAlert("Error", "Harga Tidak Valid", "[!] Harga harus berupa angka!", Alert.AlertType.ERROR); return; }
        }
        if (!stokStr.trim().isEmpty()) {
            try { int s = Integer.parseInt(stokStr.trim()); if (s < 0) { showAlert("Error", "Stok Negatif", "[!] Stok tidak boleh negatif!", Alert.AlertType.ERROR); return; } }
            catch (NumberFormatException e) { showAlert("Error", "Stok Tidak Valid", "[!] Stok harus berupa angka!", Alert.AlertType.ERROR); return; }
        }

        String idStr = tfIdSupplierBaju.getText();
        if (!idStr.trim().isEmpty()) {
            try { Integer.parseInt(idStr.trim()); }
            catch (NumberFormatException e) { showAlert("Error", "ID Supplier Tidak Valid", "[!] ID Supplier harus berupa angka!", Alert.AlertType.ERROR); return; }
        }

        String hasil = bajuService.editBaju(selectedBajuNama,
                tfNamaBaju.getText(), hargaStr, stokStr,
                tfMotifBaju.getText(),
                cbUkuranBaju.getValue(),
                cbKategoriBaju.getValue(),
                idStr);
        setPesan(lblPesanBaju, hasil);

        if (hasil.startsWith("[v]")) {
            showAlert("Sukses", "Baju Diupdate", hasil, Alert.AlertType.INFORMATION);
            refreshTableBaju();
            handleClearBaju();
        } else {
            showAlert("Error", "Gagal Update", hasil, Alert.AlertType.ERROR);
        }
    }

    @FXML private void handleHapusBaju() {
        if (selectedBajuNama == null) {
            showAlert("Peringatan", "Pilih Baju", "Pilih baju dari tabel terlebih dahulu!", Alert.AlertType.WARNING);
            return;
        }

        boolean konfirmasi = showConfirmation("Hapus Baju",
                "Yakin ingin menghapus baju '" + selectedBajuNama + "'?");
        if (!konfirmasi) return;

        String hasil = bajuService.hapusBaju(selectedBajuNama);
        setPesan(lblPesanBaju, hasil);

        if (hasil.startsWith("[v]")) {
            showAlert("Sukses", "Baju Dihapus", hasil, Alert.AlertType.INFORMATION);
            refreshTableBaju();
            handleClearBaju();
        } else {
            showAlert("Error", "Gagal Hapus", hasil, Alert.AlertType.ERROR);
        }
    }

    @FXML private void handleClearBaju() {
        tfNamaBaju.clear();
        tfHargaBaju.clear();
        tfStokBaju.clear();
        tfMotifBaju.clear();
        tfIdSupplierBaju.clear();
        cbUkuranBaju.setValue(null);
        cbKategoriBaju.setValue(null);
        selectedBajuNama = null;
        tableBaju.getSelectionModel().clearSelection();
        lblPesanBaju.setText("");
    }

    // ==================== CELANA HANDLERS (DENGAN BAHAN) ====================
    @FXML private void handleTambahCelana() {
        String nama = tfNamaCelana.getText();
        if (nama.trim().isEmpty()) {
            showAlert("Peringatan", "Nama Merk Kosong", "[!] Nama merk tidak boleh kosong!", Alert.AlertType.WARNING);
            return;
        }

        String model = tfModelCelana.getText();
        if (model.trim().isEmpty()) {
            showAlert("Peringatan", "Model Kosong", "[!] Model tidak boleh kosong!", Alert.AlertType.WARNING);
            return;
        }

        String bahan = tfBahanCelana.getText();  // ← BAHAN ADA
        if (bahan.trim().isEmpty()) {
            showAlert("Peringatan", "Bahan Kosong", "[!] Bahan tidak boleh kosong!", Alert.AlertType.WARNING);
            return;
        }

        String hargaStr = tfHargaCelana.getText();
        String stokStr = tfStokCelana.getText();

        try { Double.parseDouble(hargaStr.trim()); }
        catch (NumberFormatException e) { showAlert("Error", "Harga Tidak Valid", "[!] Harga harus berupa angka!", Alert.AlertType.ERROR); return; }
        try { Integer.parseInt(stokStr.trim()); }
        catch (NumberFormatException e) { showAlert("Error", "Stok Tidak Valid", "[!] Stok harus berupa angka!", Alert.AlertType.ERROR); return; }

        String ukuran = cbUkuranCelana.getValue();
        if (ukuran == null) { showAlert("Error", "Ukuran Belum Dipilih", "[!] Pilih ukuran celana!", Alert.AlertType.ERROR); return; }

        String kategori = cbKategoriCelana.getValue();
        if (kategori == null) { showAlert("Peringatan", "Kategori Belum Dipilih", "[!] Pilih kategori!", Alert.AlertType.WARNING); return; }

        String idStr = tfIdSupplierCelana.getText();
        int idSupplier;
        try { idSupplier = Integer.parseInt(idStr.trim()); }
        catch (NumberFormatException e) { showAlert("Error", "ID Supplier Tidak Valid", "[!] ID Supplier harus berupa angka!", Alert.AlertType.ERROR); return; }

        String hasil = celanaService.tambahCelana(nama, hargaStr, stokStr, model, ukuran, bahan, kategori, idSupplier);
        setPesan(lblPesanCelana, hasil);

        if (hasil.startsWith("[v]")) {
            showAlert("Sukses", "Celana Ditambahkan", hasil, Alert.AlertType.INFORMATION);
            refreshTableCelana();
            handleClearCelana();
        } else {
            showAlert("Error", "Gagal Menambahkan", hasil, Alert.AlertType.ERROR);
        }
    }

    @FXML private void handleUpdateCelana() {
        if (selectedCelanaNama == null) {
            showAlert("Peringatan", "Pilih Celana", "Pilih celana dari tabel terlebih dahulu!", Alert.AlertType.WARNING);
            return;
        }

        String hargaStr = tfHargaCelana.getText();
        String stokStr = tfStokCelana.getText();

        if (!hargaStr.trim().isEmpty()) {
            try { double h = Double.parseDouble(hargaStr.trim()); if (h < 0) { showAlert("Error", "Harga Negatif", "[!] Harga tidak boleh negatif!", Alert.AlertType.ERROR); return; } }
            catch (NumberFormatException e) { showAlert("Error", "Harga Tidak Valid", "[!] Harga harus berupa angka!", Alert.AlertType.ERROR); return; }
        }
        if (!stokStr.trim().isEmpty()) {
            try { int s = Integer.parseInt(stokStr.trim()); if (s < 0) { showAlert("Error", "Stok Negatif", "[!] Stok tidak boleh negatif!", Alert.AlertType.ERROR); return; } }
            catch (NumberFormatException e) { showAlert("Error", "Stok Tidak Valid", "[!] Stok harus berupa angka!", Alert.AlertType.ERROR); return; }
        }

        String idStr = tfIdSupplierCelana.getText();
        if (!idStr.trim().isEmpty()) {
            try { Integer.parseInt(idStr.trim()); }
            catch (NumberFormatException e) { showAlert("Error", "ID Supplier Tidak Valid", "[!] ID Supplier harus berupa angka!", Alert.AlertType.ERROR); return; }
        }

        String hasil = celanaService.editCelana(selectedCelanaNama,
                tfNamaCelana.getText(), hargaStr, stokStr,
                tfModelCelana.getText(), cbUkuranCelana.getValue(),
                tfBahanCelana.getText(), cbKategoriCelana.getValue(), idStr);
        setPesan(lblPesanCelana, hasil);

        if (hasil.startsWith("[v]")) {
            showAlert("Sukses", "Celana Diupdate", hasil, Alert.AlertType.INFORMATION);
            refreshTableCelana();
            handleClearCelana();
        } else {
            showAlert("Error", "Gagal Update", hasil, Alert.AlertType.ERROR);
        }
    }

    @FXML private void handleHapusCelana() {
        if (selectedCelanaNama == null) {
            showAlert("Peringatan", "Pilih Celana", "Pilih celana dari tabel terlebih dahulu!", Alert.AlertType.WARNING);
            return;
        }

        boolean konfirmasi = showConfirmation("Hapus Celana",
                "Yakin ingin menghapus celana '" + selectedCelanaNama + "'?");
        if (!konfirmasi) return;

        String hasil = celanaService.hapusCelana(selectedCelanaNama);
        setPesan(lblPesanCelana, hasil);

        if (hasil.startsWith("[v]")) {
            showAlert("Sukses", "Celana Dihapus", hasil, Alert.AlertType.INFORMATION);
            refreshTableCelana();
            handleClearCelana();
        } else {
            showAlert("Error", "Gagal Hapus", hasil, Alert.AlertType.ERROR);
        }
    }

    @FXML private void handleClearCelana() {
        tfNamaCelana.clear();
        tfHargaCelana.clear();
        tfStokCelana.clear();
        tfModelCelana.clear();
        tfBahanCelana.clear();  // ← BAHAN ADA
        tfIdSupplierCelana.clear();
        cbUkuranCelana.setValue(null);
        cbKategoriCelana.setValue(null);
        selectedCelanaNama = null;
        tableCelana.getSelectionModel().clearSelection();
        lblPesanCelana.setText("");
    }
}