package co.edu.uniquindio.SOLID.Controlador;

import co.edu.uniquindio.SOLID.Model.DTO.EmpleadoDTO;
import co.edu.uniquindio.SOLID.Model.Empleado;
import co.edu.uniquindio.SOLID.Service.Fachadas.EmpresaAdminFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;


public class EmpleadosController implements Initializable {

    @FXML private TextField txtEmpId;
    @FXML private TextField txtEmpNombre;
    @FXML private ComboBox<String> cmbEmpRol;
    @FXML private TableView<EmpleadoDTO> tblEmpleados;
    @FXML private TableColumn<EmpleadoDTO, String> colEmpId;
    @FXML private TableColumn<EmpleadoDTO, String> colEmpNombre;
    @FXML private TableColumn<EmpleadoDTO, String> colEmpRol;
    @FXML private TableColumn<EmpleadoDTO, String> colEmpEstado;

    private final EmpresaAdminFacade empresaAdminFacade;
    private ObservableList<EmpleadoDTO> empleados;

    public EmpleadosController() {
        this.empresaAdminFacade = new EmpresaAdminFacade();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Configurar ComboBox de roles
        cmbEmpRol.setItems(FXCollections.observableArrayList("CAJERO", "BODEGUERO"));
        cmbEmpRol.setValue("CAJERO");

        // Configurar columnas de tabla
        colEmpId.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(cd.getValue().getId()));
        colEmpNombre.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(cd.getValue().getNombre()));
        colEmpRol.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(cd.getValue().getRol().name()));
        colEmpEstado.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(cd.getValue().isActivo() ? "Activo" : "Inactivo"));

        // Cargar lista de empleados desde la fachada
        empleados = FXCollections.observableArrayList(empresaAdminFacade.listarEmpleados());
        tblEmpleados.setItems(empleados);
    }



    @FXML
    void crearEmpleado() {
        try {
            if (txtEmpId.getText().isBlank() || txtEmpNombre.getText().isBlank() || cmbEmpRol.getValue() == null) {
                mostrarError("Todos los campos son obligatorios");
                return;
            }

            EmpleadoDTO nuevo = new EmpleadoDTO(
                    txtEmpId.getText().trim(),
                    txtEmpNombre.getText().trim(),
                    Empleado.Rol.valueOf(cmbEmpRol.getValue()),
                    true
            );
            empresaAdminFacade.crearEmpleado(nuevo);
            empleados.add(nuevo);
            limpiarCampos();
            tblEmpleados.refresh();

        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    @FXML
    void actualizarEmpleado() {
        try {
            boolean estadoActual = empleados.stream()
                    .filter(e -> e.getId().equals(txtEmpId.getText()))
                    .map(EmpleadoDTO::isActivo)
                    .findFirst()
                    .orElse(true);

            EmpleadoDTO actualizado = new EmpleadoDTO(
                    txtEmpId.getText(),
                    txtEmpNombre.getText(),
                    Empleado.Rol.valueOf(cmbEmpRol.getValue()),
                    estadoActual
            );
            empresaAdminFacade.actualizarEmpleado(actualizado);

            // Refrescar tabla
            for (int i = 0; i < empleados.size(); i++) {
                if (empleados.get(i).getId().equals(actualizado.getId())) {
                    empleados.set(i, actualizado);
                    break;
                }
            }
            tblEmpleados.refresh();
        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    @FXML
    void eliminarEmpleado() {
        String id = txtEmpId.getText();
        try {
            empresaAdminFacade.eliminarEmpleado(id);
            empleados.removeIf(e -> e.getId().equals(id));
            tblEmpleados.refresh();
        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    @FXML
    void activarEmpleado() {
        String id = txtEmpId.getText();
        try {
            empresaAdminFacade.activarEmpleado(id);
            recargarTabla();
        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    @FXML
    void inactivarEmpleado() {
        String id = txtEmpId.getText();
        try {
            empresaAdminFacade.inactivarEmpleado(id);
            recargarTabla();
        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }


    private void recargarTabla() {
        empleados.setAll(empresaAdminFacade.listarEmpleados());
        tblEmpleados.refresh();
    }

    private void limpiarCampos() {
        txtEmpId.clear();
        txtEmpNombre.clear();
        cmbEmpRol.setValue("CAJERO");
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
