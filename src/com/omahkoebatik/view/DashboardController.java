package com.omahkoebatik.view;

import com.omahkoebatik.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.util.Callback;
import javafx.scene.control.cell.PropertyValueFactory;
import com.omahkoebatik.service.DataStorage;

public class DashboardController {
    @FXML private TableView<Baju> tableBaju;
    @FXML private TableColumn<Baju, String> colNama, colKategori, colSupplier;
    @FXML private TableColumn<Baju, Double> colHarga;
    @FXML private TableColumn<Baju, Integer> colStok;
    @FXML private TableColumn<Baju, Void> colAksi;

    @FXML
    public void initialize() {
        // Konfigurasi kolom tabel
        colNama.setCellValueFactory(new PropertyValueFactory<>("namaProduk"));
        colKategori.setCellValueFactory(new PropertyValueFactory<>("kategori"));
        colHarga.setCellValueFactory(new PropertyValueFactory<>("harga"));
        colStok.setCellValueFactory(new PropertyValueFactory<>("stok"));

        // Kolom Supplier
        colSupplier.setCellValueFactory(cellData -> {
            if (cellData.getValue().getSupplier() != null) {
                return new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getSupplier().getNamaSupplier()
                );
            }
            return new javafx.beans.property.SimpleStringProperty("-");
        });

        // Setup tombol Aksi (Edit & Hapus)
        setupActionButtons();

        DataStorage.initDummyData();
        handleRefresh();
    }

    private void setupActionButtons() {
        Callback<TableColumn<Baju, Void>, TableCell<Baju, Void>> cellFactory = param -> new TableCell<>() {
            private final Button btnEdit = new Button("Edit");
            private final Button btnHapus = new Button("Hapus");
            private final HBox pane = new HBox(5, btnEdit, btnHapus);

            {
                btnEdit.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-font-size: 10px; -fx-background-radius: 5;");
                btnHapus.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-size: 10px; -fx-background-radius: 5;");

                // Logika Hapus
                btnHapus.setOnAction(event -> {
                    Baju b = getTableView().getItems().get(getIndex());
                    DataStorage.getListBaju().remove(b);
                    handleRefresh();
                });

                // Logika Edit (Update)
                btnEdit.setOnAction(event -> {
                    Baju b = getTableView().getItems().get(getIndex());
                    showEditDialog(b);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : pane);
            }
        };
        colAksi.setCellFactory(cellFactory);
    }

    private void showEditDialog(Baju b) {
        Dialog<Baju> dialog = new Dialog<>();
        dialog.setTitle("Edit Data Baju");
        dialog.setHeaderText("Update data: " + b.getNamaProduk());

        ButtonType simpanBtn = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(simpanBtn, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField tNama = new TextField(b.getNamaProduk());
        TextField tHarga = new TextField(String.valueOf(b.getHarga()));
        TextField tStok = new TextField(String.valueOf(b.getStok()));
        ComboBox<Supplier> cbSup = new ComboBox<>();
        cbSup.getItems().addAll(DataStorage.getListSupplier());
        cbSup.setValue(b.getSupplier());

        grid.add(new Label("Nama:"), 0, 0);    grid.add(tNama, 1, 0);
        grid.add(new Label("Harga:"), 0, 1);   grid.add(tHarga, 1, 1);
        grid.add(new Label("Stok:"), 0, 2);    grid.add(tStok, 1, 2);
        grid.add(new Label("Supplier:"), 0, 3); grid.add(cbSup, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(btn -> {
            if (btn == simpanBtn) {
                try {
                    b.setNamaProduk(tNama.getText());
                    b.setHarga(Double.parseDouble(tHarga.getText()));
                    b.setStok(Integer.parseInt(tStok.getText()));
                    b.setSupplier(cbSup.getValue());
                    return b;
                } catch (Exception e) { return null; }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(res -> handleRefresh());
    }

    @FXML
    void handleTambahBaju() {
        Dialog<Baju> dialog = new Dialog<>();
        dialog.setTitle("Tambah Baju");
        dialog.setHeaderText("Input Data Baju Baru");

        ButtonType simpanBtn = new ButtonType("Simpan", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(simpanBtn, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField tNama = new TextField();
        TextField tHarga = new TextField();
        TextField tStok = new TextField();
        ComboBox<Supplier> cbSup = new ComboBox<>();
        cbSup.getItems().addAll(DataStorage.getListSupplier());

        grid.add(new Label("Nama:"), 0, 0);    grid.add(tNama, 1, 0);
        grid.add(new Label("Harga:"), 0, 1);   grid.add(tHarga, 1, 1);
        grid.add(new Label("Stok:"), 0, 2);    grid.add(tStok, 1, 2);
        grid.add(new Label("Supplier:"), 0, 3); grid.add(cbSup, 1, 3);

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(btn -> {
            if (btn == simpanBtn) {
                try {
                    return new Baju(DataStorage.nextIdBaju(), tNama.getText(), "Motif", "M",
                            Double.parseDouble(tHarga.getText()),
                            Integer.parseInt(tStok.getText()), "Batik", cbSup.getValue());
                } catch (Exception e) { return null; }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(bajuBaru -> {
            DataStorage.tambahBaju(bajuBaru);
            handleRefresh();
        });
    }

    @FXML
    void handleTambahSupplier() {
        Dialog<Supplier> dialog = new Dialog<>();
        dialog.setTitle("Tambah Supplier");
        dialog.setHeaderText("Input Data Supplier");

        ButtonType simpanBtn = new ButtonType("Simpan", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(simpanBtn, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField tNamaSup = new TextField();
        TextField tAlamat = new TextField();

        grid.add(new Label("Nama:"), 0, 0);    grid.add(tNamaSup, 1, 0);
        grid.add(new Label("Alamat:"), 0, 1);  grid.add(tAlamat, 1, 1);

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(btn -> {
            if (btn == simpanBtn) {
                return new Supplier(DataStorage.nextIdSupplier(), tNamaSup.getText(), "0812", tAlamat.getText());
            }
            return null;
        });

        dialog.showAndWait().ifPresent(s -> {
            DataStorage.tambahSupplier(s);
            new Alert(Alert.AlertType.INFORMATION, "Supplier Berhasil Ditambah!").showAndWait();
        });
    }

    @FXML void handleRefresh() {
        tableBaju.getItems().setAll(DataStorage.getListBaju());
    }

    @FXML void handleKeluar() {
        System.exit(0);
    }
}