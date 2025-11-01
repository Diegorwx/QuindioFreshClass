package co.edu.uniquindio.SOLID.Controlador;

import co.edu.uniquindio.SOLID.Model.DTO.*;
import co.edu.uniquindio.SOLID.Service.Fachadas.InventarioFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class InventarioController implements Initializable {

    @FXML private ComboBox<String> cmbProveedor;
    @FXML private ComboBox<String> cmbProducto;
    @FXML private TextField txtCantidad;
    @FXML private Button btnRegistrarEntrada;
    @FXML private TableView<MovimientoInventarioDTO> tblMovimientos;
    @FXML private TableColumn<MovimientoInventarioDTO, String> colId;
    @FXML private TableColumn<MovimientoInventarioDTO, String> colTipo;
    @FXML private TableColumn<MovimientoInventarioDTO, String> colFecha;
    @FXML private TableColumn<MovimientoInventarioDTO, String> colSku;
    @FXML private TableColumn<MovimientoInventarioDTO, Integer> colCantidad;
    @FXML private TableColumn<MovimientoInventarioDTO, String> colReferencia;

    private InventarioFacade inventarioFacade;
    private ObservableList<MovimientoInventarioDTO> movimientos;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inventarioFacade = new InventarioFacade();

        inicializarCombos();
        inicializarTabla();

        cargarMovimientos();
    }


    private void inicializarCombos() {
        // Llenar proveedores
        List<ProveedorDTO> proveedores = inventarioFacade.listarProveedores();
        List<String> nits = proveedores.stream().map(ProveedorDTO::getNit).toList();
        cmbProveedor.setItems(FXCollections.observableArrayList(nits));

        // Llenar productos
        List<ProductoDTO> productos = inventarioFacade.listarProductos();
        List<String> skus = productos.stream().map(ProductoDTO::getSku).toList();
        cmbProducto.setItems(FXCollections.observableArrayList(skus));
    }

    private void inicializarTabla() {
        colId.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(cd.getValue().getId()));
        colTipo.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(cd.getValue().getTipo()));
        colFecha.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(cd.getValue().getFecha().toString()));
        colSku.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(cd.getValue().getSkuProducto()));
        colCantidad.setCellValueFactory(cd -> new javafx.beans.property.SimpleObjectProperty<>(cd.getValue().getCantidad()));
        colReferencia.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(cd.getValue().getReferencia()));

        movimientos = FXCollections.observableArrayList();
        tblMovimientos.setItems(movimientos);
    }

    private void cargarMovimientos() {
        movimientos.setAll(inventarioFacade.listarMovimientos());
        tblMovimientos.refresh();
    }


    @FXML
    private void registrarEntrada() {
        try {
            String proveedorNit = cmbProveedor.getValue();
            String sku = cmbProducto.getValue();
            int cantidad = Integer.parseInt(txtCantidad.getText());

            if (proveedorNit == null || proveedorNit.isEmpty()) {
                mostrarError("Debe seleccionar un proveedor");
                return;
            }
            if (sku == null || sku.isEmpty()) {
                mostrarError("Debe seleccionar un producto");
                return;
            }
            if (cantidad <= 0) {
                mostrarError("La cantidad debe ser mayor a cero");
                return;
            }

            // Crear DTO de entrada
            List<EntradaInventarioDTO.ItemEntradaDTO> items = new ArrayList<>();
            items.add(new EntradaInventarioDTO.ItemEntradaDTO(sku, cantidad));
            EntradaInventarioDTO entradaDTO = new EntradaInventarioDTO(proveedorNit, items);

            // Registrar entrada en el inventario
            inventarioFacade.registrarEntrada(entradaDTO);

            // Actualizar tabla de movimientos
            cargarMovimientos();

            // Limpiar campos
            txtCantidad.clear();
            cmbProducto.setValue(null);

            mostrarInfo("Entrada registrada correctamente.");

        } catch (NumberFormatException e) {
            mostrarError("La cantidad debe ser un número entero válido");
        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    @FXML
    private void consultarStock() {
        String sku = cmbProducto.getValue();
        if (sku == null || sku.isEmpty()) {
            mostrarError("Seleccione un producto para consultar su stock");
            return;
        }

        try {
            int stock = inventarioFacade.consultarStock(sku);
            mostrarInfo("Stock actual del producto " + sku + ": " + stock);
        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }


    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarInfo(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
