package co.edu.uniquindio.SOLID.Service.Fachadas;

import co.edu.uniquindio.SOLID.Model.DTO.*;
import co.edu.uniquindio.SOLID.Model.Minimercado;
import co.edu.uniquindio.SOLID.Service.InventarioService;
import co.edu.uniquindio.SOLID.Service.ProductoService;
import co.edu.uniquindio.SOLID.Service.ProveedorService;

import java.util.List;


public class InventarioFacade {

    private final Minimercado minimercado;
    private final ProveedorService proveedorService;
    private final ProductoService productoService;
    private final InventarioService inventarioService;

    public InventarioFacade() {
        this.minimercado = Minimercado.getInstancia();
        this.proveedorService = new ProveedorService(minimercado);
        this.productoService = new ProductoService();
        this.inventarioService = new InventarioService(minimercado, proveedorService, productoService);
    }



    public boolean crearProveedor(ProveedorDTO dto) {
        return proveedorService.crearProveedor(dto);
    }

    public boolean actualizarProveedor(ProveedorDTO dto) {
        return proveedorService.actualizarProveedor(dto);
    }

    public boolean eliminarProveedor(String nit) {
        return proveedorService.eliminarProveedor(nit);
    }

    public List<ProveedorDTO> listarProveedores() {
        return proveedorService.listarProveedores();
    }

    public List<ProductoDTO> listarProductos() {
        return productoService.obtenerTodosLosProductos();
    }


    public ProductoDTO buscarProductoPorSku(String sku) {
        return productoService.buscarProductoPorSku(sku);
    }

    public boolean existeProducto(String sku) {
        return productoService.existeProducto(sku);
    }


    public boolean registrarEntrada(EntradaInventarioDTO dto) {
        return inventarioService.registrarEntrada(dto);
    }


    public List<MovimientoInventarioDTO> listarMovimientos() {
        return inventarioService.listarMovimientos();
    }

    public int consultarStock(String sku) {
        return inventarioService.consultarStock(sku);
    }
}

