package co.edu.uniquindio.SOLID.Model.DTO;

import java.time.LocalDateTime;

public class MovimientoInventarioDTO {

    private String id;
    private String tipo; // "ENTRADA", etc.
    private LocalDateTime fecha;
    private String skuProducto;
    private int cantidad;
    private String referencia;

    public MovimientoInventarioDTO(String id, String tipo, LocalDateTime fecha, String skuProducto, int cantidad, String referencia) {
        this.id = id;
        this.tipo = tipo;
        this.fecha = fecha;
        this.skuProducto = skuProducto;
        this.cantidad = cantidad;
        this.referencia = referencia;
    }

    public String getId() { return id; }
    public String getTipo() { return tipo; }
    public LocalDateTime getFecha() { return fecha; }
    public String getSkuProducto() { return skuProducto; }
    public int getCantidad() { return cantidad; }
    public String getReferencia() { return referencia; }
}


