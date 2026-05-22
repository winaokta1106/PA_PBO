package view;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Baju;
import model.Produk;
import model.Supplier;
import repository.BajuRepository;
import repository.SupplierRepository;
import service.DataStorage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    // ========== REPOSITORY & SERVICE ==========
    private BajuRepository bajuRepo = new BajuRepository();
    private SupplierRepository supplierRepo = new SupplierRepository();
    private DataStorage dataStorage;

    // ========== KOMPONEN TAB SUPPLIER ==========
    @FXML private TableView<Supplier> tabelSupplier;
    @FXML private TableColumn<Supplier, Integer> kolSupplierId;
    @FXML private TableColumn<Supplier, String> kolSupplierNama;
    @FXML private TableColumn<Supplier, String> kolSupplierTelepon;
    @FXML private TableColumn<Supplier, String> kolSupplierAlamat;
    @FXML private TextField tfSupplierNama;
    @FXML private TextField tfSupplierTelepon;
    @FXML private TextField tfSupplierAlamat;
    @FXML private Label lblSupplierMsg;

    // ========== KOMPONEN TAB BAJU ==========
    @FXML private TableView<Baju> tabelBaju;
    @FXML private TableColumn<Baju, String> kolBajuNama;
    @FXML private TableColumn<Baju, Double> kolBajuHarga;
    @FXML private TableColumn<Baju, Integer> kolBajuStok;
    @FXML private TableColumn<Baju, String> kolBajuMotif;
    @FXML private TableColumn<Baju, String> kolBajuUkuran;
    @FXML private TableColumn<Baju, String> kolBajuKategori;
    @FXML private TableColumn<Baju, String> kolBajuSupplier;
    @FXML private TextField tfBajuNama;
    @FXML private TextField tfBajuHarga;
    @FXML private TextField tfBajuStok;
    @FXML private TextField tfBajuMotif;
    @FXML private TextField tfBajuUkuran;
    @FXML private ComboBox<String> cbBajuKategori;
    @FXML private TextField tfBajuSupplierId;
    @FXML private Label lblBajuMsg;

    // Menyimpan nama baju yang dipilih (untuk update)
    private String selectedBajuNama = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Dependency injection
        dataStorage = new DataStorage(bajuRepo, supplierRepo);

        setupKolom();
        setupTableListener();
        setupKategoriComboBox();

        loadSupplierData();
        loadBajuData();
    }

    private void setupKolom() {
        // Kolom Supplier
        kolSupplierId.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getId()).asObject());
        kolSupplierNama.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getNama()));
        kolSupplierTelepon.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getTelepon()));
        kolSupplierAlamat.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getAlamat()));

        // Kolom Baju
        kolBajuNama.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getNamaProduk()));
        kolBajuHarga.setCellValueFactory(data ->
                new SimpleDoubleProperty(data.getValue().getHarga()).asObject());
        kolBajuStok.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getStok()).asObject());
        kolBajuMotif.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getMotif()));
        kolBajuUkuran.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getUkuran()));
        kolBajuKategori.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getKategori() != null
                                ? data.getValue().getKategori().getLabel() : "-"));
        kolBajuSupplier.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getSupplier() != null
                                ? data.getValue().getSupplier().getNama() : "-"));
    }

    private void setupTableListener() {
        // Klik baris supplier → isi form
        tabelSupplier.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        tfSupplierNama.setText(newVal.getNama());
                        tfSupplierTelepon.setText(newVal.getTelepon());
                        tfSupplierAlamat.setText(newVal.getAlamat());
                        lblSupplierMsg.setText("");
                    }
                });

        // Klik baris baju → isi form
        tabelBaju.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        selectedBajuNama = newVal.getNamaProduk();
                        tfBajuNama.setText(newVal.getNamaProduk());
                        tfBajuHarga.setText(String.valueOf(newVal.getHarga()));
                        tfBajuStok.setText(String.valueOf(newVal.getStok()));
                        tfBajuMotif.setText(newVal.getMotif());
                        tfBajuUkuran.setText(newVal.getUkuran());
                        if (newVal.getKategori() != null) {
                            cbBajuKategori.setValue(newVal.getKategori().getLabel());
                        }
                        tfBajuSupplierId.setText(newVal.getSupplier() != null
                                ? String.valueOf(newVal.getSupplier().getId()) : "");
                        lblBajuMsg.setText("");
                    }
                });
    }

    private void setupKategoriComboBox() {
        ObservableList<String> kategoriList = FXCollections.observableArrayList();
        for (Produk.Kategori k : dataStorage.getAllKategori()) {
            kategoriList.add(k.getLabel());
        }
        cbBajuKategori.setItems(kategoriList);
    }

    // ==================== HANDLER SUPPLIER ====================

    @FXML
    private void tambahSupplier() {
        String nama = tfSupplierNama.getText();
        String telepon = tfSupplierTelepon.getText();
        String alamat = tfSupplierAlamat.getText();
        String msg = dataStorage.tambahSupplier(nama, telepon, alamat);
        tampilPesan(lblSupplierMsg, msg);
        loadSupplierData();
        clearSupplierForm();
    }

    @FXML
    private void updateSupplier() {
        Supplier selected = tabelSupplier.getSelectionModel().getSelectedItem();
        if (selected == null) {
            tampilPesan(lblSupplierMsg, "[!] Pilih supplier dari tabel terlebih dahulu");
            return;
        }
        String namaBaru = tfSupplierNama.getText();
        String teleponBaru = tfSupplierTelepon.getText();
        String alamatBaru = tfSupplierAlamat.getText();
        String msg = dataStorage.editSupplier(selected.getId(), namaBaru, teleponBaru, alamatBaru);
        tampilPesan(lblSupplierMsg, msg);
        loadSupplierData();
        clearSupplierForm();
    }

    @FXML
    private void hapusSupplier() {
        Supplier selected = tabelSupplier.getSelectionModel().getSelectedItem();
        if (selected == null) {
            tampilPesan(lblSupplierMsg, "[!] Pilih supplier dari tabel terlebih dahulu");
            return;
        }
        String msg = dataStorage.hapusSupplier(selected.getId());
        tampilPesan(lblSupplierMsg, msg);
        loadSupplierData();
        clearSupplierForm();
    }

    private void loadSupplierData() {
        ObservableList<Supplier> data = FXCollections.observableArrayList(
                dataStorage.getAllSupplier());
        tabelSupplier.setItems(data);
    }

    private void clearSupplierForm() {
        tfSupplierNama.clear();
        tfSupplierTelepon.clear();
        tfSupplierAlamat.clear();
        tabelSupplier.getSelectionModel().clearSelection();
    }

    // ==================== HANDLER BAJU ====================

    @FXML
    private void tambahBaju() {
        try {
            String nama = tfBajuNama.getText();
            double harga = Double.parseDouble(tfBajuHarga.getText());
            int stok = Integer.parseInt(tfBajuStok.getText());
            String motif = tfBajuMotif.getText();
            String ukuran = tfBajuUkuran.getText();
            String kategoriLabel = cbBajuKategori.getValue();
            int supplierId = Integer.parseInt(tfBajuSupplierId.getText());

            if (kategoriLabel == null || kategoriLabel.isEmpty()) {
                tampilPesan(lblBajuMsg, "[!] Pilih kategori terlebih dahulu");
                return;
            }

            String msg = dataStorage.tambahBaju(nama, harga, stok, motif, ukuran,
                    kategoriLabel, supplierId);
            tampilPesan(lblBajuMsg, msg);
            loadBajuData();
            clearBajuForm();
        } catch (NumberFormatException e) {
            tampilPesan(lblBajuMsg, "[!] Format angka salah (Harga, Stok, ID Supplier harus angka)");
        }
    }

    @FXML
    private void updateBaju() {
        if (selectedBajuNama == null) {
            tampilPesan(lblBajuMsg, "[!] Pilih baju dari tabel terlebih dahulu");
            return;
        }
        try {
            String namaBaru = tfBajuNama.getText();
            String hargaStr = tfBajuHarga.getText();
            String stokStr = tfBajuStok.getText();
            String motifBaru = tfBajuMotif.getText();
            String ukuranBaru = tfBajuUkuran.getText();
            String kategoriLabel = cbBajuKategori.getValue();

            Integer supplierId = null;
            String supplierIdStr = tfBajuSupplierId.getText();
            if (supplierIdStr != null && !supplierIdStr.trim().isEmpty()) {
                supplierId = Integer.parseInt(supplierIdStr.trim());
            }

            String msg = dataStorage.editBaju(selectedBajuNama, namaBaru, hargaStr, stokStr,
                    motifBaru, ukuranBaru, kategoriLabel, supplierId);
            tampilPesan(lblBajuMsg, msg);
            loadBajuData();
            clearBajuForm();
        } catch (NumberFormatException e) {
            tampilPesan(lblBajuMsg, "[!] Format angka salah (ID Supplier harus angka)");
        }
    }

    @FXML
    private void hapusBaju() {
        Baju selected = tabelBaju.getSelectionModel().getSelectedItem();
        if (selected == null) {
            tampilPesan(lblBajuMsg, "[!] Pilih baju dari tabel terlebih dahulu");
            return;
        }
        String msg = dataStorage.hapusBaju(selected.getNamaProduk());
        tampilPesan(lblBajuMsg, msg);
        loadBajuData();
        clearBajuForm();
    }

    @FXML
    private void tambahStok() {
        Baju selected = tabelBaju.getSelectionModel().getSelectedItem();
        if (selected == null) {
            tampilPesan(lblBajuMsg, "[!] Pilih baju dari tabel terlebih dahulu");
            return;
        }

        TextInputDialog dialog = new TextInputDialog("1");
        dialog.setTitle("Tambah Stok");
        dialog.setHeaderText("Tambah Stok: " + selected.getNamaProduk());
        dialog.setContentText("Jumlah tambah:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                int jumlah = Integer.parseInt(result.get().trim());
                String msg = dataStorage.tambahStok(selected.getNamaProduk(), jumlah);
                tampilPesan(lblBajuMsg, msg);
                loadBajuData();
            } catch (NumberFormatException e) {
                tampilPesan(lblBajuMsg, "[!] Jumlah harus berupa angka");
            }
        }
    }

    private void loadBajuData() {
        ObservableList<Baju> data = FXCollections.observableArrayList(
                dataStorage.getAllBaju());
        tabelBaju.setItems(data);
    }

    private void clearBajuForm() {
        tfBajuNama.clear();
        tfBajuHarga.clear();
        tfBajuStok.clear();
        tfBajuMotif.clear();
        tfBajuUkuran.clear();
        cbBajuKategori.setValue(null);
        tfBajuSupplierId.clear();
        selectedBajuNama = null;
        tabelBaju.getSelectionModel().clearSelection();
    }

    // ========== HELPER ==========

    private void tampilPesan(Label label, String pesan) {
        label.setText(pesan);
        if (pesan.startsWith("[v]")) {
            label.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
        } else if (pesan.startsWith("[!]")) {
            label.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        } else {
            label.setStyle("-fx-text-fill: black;");
        }
    }
}