package co.edu.uniquindio.SOLID.utils.Mappers;

import co.edu.uniquindio.SOLID.Model.EntradaInventario;
import co.edu.uniquindio.SOLID.Model.DTO.EntradaInventarioDTO;

import java.util.List;
import java.util.stream.Collectors;


public class EntradaInventarioMapper {

    public static EntradaInventarioDTO toDTO(EntradaInventario entrada) {
        if (entrada == null) return null;

        List<EntradaInventarioDTO.ItemEntradaDTO> items = entrada.getItems()
                .stream()
                .map(item -> new EntradaInventarioDTO.ItemEntradaDTO(
                        item.getProducto().getSku(),
                        item.getCantidad()
                ))
                .collect(Collectors.toList());

        return new EntradaInventarioDTO(
                entrada.getProveedor().getNit(),
                items
        );
    }


    public static EntradaInventario toEntity(
            EntradaInventarioDTO dto,
            co.edu.uniquindio.SOLID.Model.Proveedor proveedor,
            List<co.edu.uniquindio.SOLID.Model.Producto> productos) {

        if (dto == null || proveedor == null || productos == null) {
            throw new IllegalArgumentException("Datos insuficientes para crear la entrada de inventario");
        }

        EntradaInventario entrada = new EntradaInventario("ENT-" + System.currentTimeMillis(), proveedor);

        for (EntradaInventarioDTO.ItemEntradaDTO itemDTO : dto.getItems()) {
            co.edu.uniquindio.SOLID.Model.Producto producto = productos.stream()
                    .filter(p -> p.getSku().equalsIgnoreCase(itemDTO.getSku()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Producto no encontrado para el SKU: " + itemDTO.getSku())
                    );

            entrada.agregarItem(producto, itemDTO.getCantidad());
        }

        return entrada;
    }
}

