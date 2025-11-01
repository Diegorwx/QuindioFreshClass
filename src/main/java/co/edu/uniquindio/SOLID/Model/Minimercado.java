package co.edu.uniquindio.SOLID.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que actúa como repositorio en memoria.
 * Contiene las colecciones base del sistema, sin lógica de negocio.
 * Aplica SRP (Responsabilidad Única): solo almacena y expone datos.
 */
public class Minimercado {

    private static Minimercado instancia;

    private final List<Producto> productos;
    private final List<Pedido> pedidos;
    private final List<Cliente> clientes;
    private final List<Empleado> empleados;
    private final List<Proveedor> proveedores;
    private final List<MovimientoInventario> movimientos;

    private Minimercado() {
        this.productos = new ArrayList<>();
        this.pedidos = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.empleados = new ArrayList<>();
        this.proveedores = new ArrayList<>();
        this.movimientos = new ArrayList<>();
    }

    public static Minimercado getInstancia() {
        if (instancia == null) {
            instancia = new Minimercado();
        }
        return instancia;
    }

    // --- Getters (solo lectura) ---
    public List<Producto> getProductos() { return productos; }
    public List<Pedido> getPedidos() { return pedidos; }
    public List<Cliente> getClientes() { return clientes; }
    public List<Empleado> getEmpleados() { return empleados; }
    public List<Proveedor> getProveedores() { return proveedores; }
    public List<MovimientoInventario> getMovimientos() { return movimientos; }

    // --- Métodos utilitarios simples (sin lógica de negocio) ---
    public void registrarMovimiento(MovimientoInventario movimiento) {
        movimientos.add(movimiento);
    }

    public void addProducto(Producto producto) {
        if (producto != null) productos.add(producto);
    }

    public void addCliente(Cliente cliente) {
        if (cliente != null) clientes.add(cliente);
    }

    public void addPedido(Pedido pedido) {
        if (pedido != null) pedidos.add(pedido);
    }
}
