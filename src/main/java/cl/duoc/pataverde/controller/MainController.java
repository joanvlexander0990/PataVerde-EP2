
package cl.duoc.pataverde.controller;

import cl.duoc.pataverde.model.Mascota;
import cl.duoc.pataverde.repository.MascotaRepository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML private TableView<Mascota> tablaMascotas;
    @FXML private TableColumn<Mascota, String> columnaNombre;
    @FXML private TableColumn<Mascota, String> columnaTipo;
    @FXML private TableColumn<Mascota, Integer> columnaEdad;
    @FXML private TextField campoBuscar;
    @FXML private Label mensaje;

    private final ObservableList<Mascota> mascotas =
            FXCollections.observableArrayList();

    private final MascotaRepository repository =
            new MascotaRepository();

    @FXML
    public void initialize() {

        columnaNombre.setCellValueFactory(datos ->
                new javafx.beans.property.SimpleStringProperty(
                        datos.getValue().getNombre()));

        columnaTipo.setCellValueFactory(datos ->
                new javafx.beans.property.SimpleStringProperty(
                        datos.getValue().getTipo()));

        columnaEdad.setCellValueFactory(datos ->
                new javafx.beans.property.SimpleIntegerProperty(
                        datos.getValue().getEdad()).asObject());

        FilteredList<Mascota> filtradas =
                new FilteredList<>(mascotas, mascota -> true);

        campoBuscar.textProperty().addListener(
                (observable, anterior, nuevo) -> {

                    String busqueda = nuevo.toLowerCase().trim();

                    filtradas.setPredicate(mascota ->
                            mascota.getNombre().toLowerCase().contains(busqueda)
                                    || mascota.getTipo().toLowerCase().contains(busqueda));
                });

        tablaMascotas.setItems(filtradas);

        // Cargar mascotas guardadas al iniciar
        try {
            mascotas.addAll(repository.cargar());
            mensaje.setText("Mascotas cargadas correctamente.");
        } catch (Exception e) {
            mensaje.setText("Error al cargar mascotas: " + e.getMessage());
        }
    }

    private void guardarDatos() {
        try {
            repository.guardar(mascotas);
        } catch (IOException e) {
            mensaje.setText("Error al guardar: " + e.getMessage());
        }
    }

    @FXML
    private void abrirRegistro() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/cl/duoc/pataverde/view/registro-view.fxml"
                    )
            );

            Parent root = loader.load();

            RegistroController registro = loader.getController();

            registro.setAlGuardar(mascota -> {
                mascotas.add(mascota);
                guardarDatos();
                mensaje.setText("Mascota registrada correctamente.");
            });

            Stage ventana = new Stage();
            ventana.setTitle("Registrar mascota");
            ventana.setScene(new Scene(root));
            ventana.showAndWait();

        } catch (IOException e) {
            mensaje.setText("Error al abrir el formulario.");
            e.printStackTrace();
        }
    }

    @FXML
    private void eliminarMascota() {

        Mascota seleccionada =
                tablaMascotas.getSelectionModel().getSelectedItem();

        if (seleccionada == null) {
            mensaje.setText("Selecciona una mascota para eliminar.");
            return;
        }

        mascotas.remove(seleccionada);
        guardarDatos();
        mensaje.setText("Mascota eliminada correctamente.");
    }
}
