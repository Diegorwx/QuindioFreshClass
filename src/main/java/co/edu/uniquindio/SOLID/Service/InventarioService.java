package co.edu.uniquindio.SOLID.Service;

import co.edu.uniquindio.SOLID.Model.*;
import co.edu.uniquindio.SOLID.Model.DTO.EntradaInventarioDTO;
import co.edu.uniquindio.SOLID.Model.DTO.MovimientoInventarioDTO;
import co.edu.uniquindio.SOLID.utils.Mappers.MovimientoInventarioMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;


public class InventarioService {

    private final Minimercado minimercado;
    private final ProveedorService proveedorService;
    private final ProductoService productoService;

    public InventarioService(Minimercado minimercado,
                             ProveedorService proveedorService,
                             ProductoService productoService) {
        this.minimercado = minimercado;
        this.proveedorService = proveedorService;
        this.productoService = productoService;
    }


    public boolean registrarEntrada(EntradaInventarioDTO dto) {
        if (dto == null || dto.getProveedorNit() == null || dto.getItems() == null || dto.getItems().isEmpty()) {
            throw new IllegalArgumentException("Datos de la entrada de inventario incompletos");
        }


        Proveedor proveedor = minimercado.getProveedores().stream()
                .filter(p -> p.getNit().equalsIgnoreCase(dto.getProveedorNit()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Proveedor no encontrado: " + dto.getProveedorNit()));

        for (EntradaInventarioDTO.ItemEntradaDTO item : dto.getItems()) {

            if (item.getCantidad() <= 0) {
                throw new IllegalArgumentException("Cantidad inválida para el producto: " + item.getSku());
            }

            Producto producto = minimercado.getProductos().stream()
                    .filter(p -> p.getSku().equalsIgnoreCase(item.getSku()))
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException("Producto no encontrado: " + item.getSku()));


            producto.aumentarStock(item.getCantidad());

            String movId = "MOV-" + UUID.randomUUID().toString();
            String referencia = "Entrada desde proveedor " + proveedor.getNit();

            MovimientoInventario movimiento = new MovimientoInventario(
                    movId,
                    MovimientoInventario.Tipo.ENTRADA,
                    producto,
                    item.getCantidad(),
                    referencia
            );

            // Registrar el movimiento en el minimercado
            minimercado.registrarMovimiento(movimiento);
        }

        return true;
    }


    public int consultarStock(String sku) {
        Producto producto = minimercado.getProductos().stream()
                .filter(p -> p.getSku().equalsIgnoreCase(sku))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Producto no encontrado: " + sku));
        return producto.getStock();
    }


    public List<MovimientoInventarioDTO> listarMovimientos() {
        List<MovimientoInventarioDTO> lista = new ArrayList<>();
        for (MovimientoInventario mov : minimercado.getMovimientos()) {
            lista.add(MovimientoInventarioMapper.toDTO(mov));
        }
        return lista;
    }
}
