
package cl.duoc.pataverde.controller;

import cl.duoc.pataverde.model.Gato;
import cl.duoc.pataverde.model.Mascota;
import cl.duoc.pataverde.model.Perro;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class RegistroController {

    @FXML private ComboBox<String> comboTipo;
    @FXML private TextField campoNombre;
    @FXML private TextField campoEdad;
    @FXML private TextField campoPeso;
    @FXML private TextField campoRaza;
    @FXML private CheckBox checkCondicion;
    @FXML private Button botonGuardar;
    @FXML private Label mensajeRegistro;

    private Consumer<Mascota> alGuardar;

    @FXML
    public void initialize() {
        comboTipo.getItems().addAll("Perro", "Gato");

        comboTipo.setOnAction(event -> {
            boolean esPerro = "Perro".equals(comboTipo.getValue());

            campoRaza.setDisable(!esPerro);

            if (esPerro) {
                checkCondicion.setText("Esta vacunado");
            } else {
                checkCondicion.setText("Vive en exterior");
                campoRaza.clear();
            }

            checkCondicion.setSelected(false);
        });

        botonGuardar.setOnAction(event -> guardarMascota());
    }

    public void setAlGuardar(Consumer<Mascota> alGuardar) {
        this.alGuardar = alGuardar;
    }

    @FXML
    private void guardarMascota() {
        try {
            String tipo = comboTipo.getValue();
            String nombre = campoNombre.getText().trim();

            if (tipo == null) {
                mensajeRegistro.setText("Selecciona perro o gato.");
                return;
            }

            int edad = Integer.parseInt(campoEdad.getText().trim());
            double peso = Double.parseDouble(
                    campoPeso.getText().trim().replace(",", "."));

            Mascota mascota;

            if (tipo.equals("Perro")) {
                String raza = campoRaza.getText().trim();

                if (raza.isEmpty()) {
                    mensajeRegistro.setText("Ingresa la raza.");
                    return;
                }

                mascota = new Perro(
                        nombre, edad, peso, raza,
                        checkCondicion.isSelected()
                );
            } else {
                mascota = new Gato(
                        nombre, edad, peso,
                        checkCondicion.isSelected()
                );
            }

            if (alGuardar != null) {
                alGuardar.accept(mascota);
            }

            mensajeRegistro.setText("Mascota registrada.");

            Stage ventana = (Stage) botonGuardar.getScene().getWindow();
            ventana.close();

        } catch (NumberFormatException e) {
            mensajeRegistro.setText(
                    "Edad y peso deben ser numeros validos.");
        } catch (IllegalArgumentException e) {
            mensajeRegistro.setText(e.getMessage());
        }
    }
}
