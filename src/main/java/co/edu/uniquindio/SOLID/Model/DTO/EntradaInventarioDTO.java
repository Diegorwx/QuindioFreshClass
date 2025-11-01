package co.edu.uniquindio.SOLID.Model.DTO;

import java.util.List;


public class EntradaInventarioDTO {

    private String proveedorNit;
    private List<ItemEntradaDTO> items;

    public EntradaInventarioDTO(String proveedorNit, List<ItemEntradaDTO> items) {
        this.proveedorNit = proveedorNit;
        this.items = items;
    }

    public String getProveedorNit() {
        return proveedorNit;
    }

    public List<ItemEntradaDTO> getItems() {
        return items;
    }


    public static class ItemEntradaDTO {
        private String sku;
        private int cantidad;

        public ItemEntradaDTO(String sku, int cantidad) {
            this.sku = sku;
            this.cantidad = cantidad;
        }

        public String getSku() {
            return sku;
        }

        public int getCantidad() {
            return cantidad;
        }
    }
}
