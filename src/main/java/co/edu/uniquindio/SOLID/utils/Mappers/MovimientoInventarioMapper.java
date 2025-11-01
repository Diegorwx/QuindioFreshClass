package co.edu.uniquindio.SOLID.utils.Mappers;

import co.edu.uniquindio.SOLID.Model.DTO.MovimientoInventarioDTO;
import co.edu.uniquindio.SOLID.Model.MovimientoInventario;

public class MovimientoInventarioMapper {

    public static MovimientoInventarioDTO toDTO(MovimientoInventario mov) {
        if (mov == null) return null;
        return new MovimientoInventarioDTO(
                mov.getId(),
                mov.getTipo() != null ? mov.getTipo().name() : null,
                mov.getFecha(),
                mov.getProducto() != null ? mov.getProducto().getSku() : null,
                mov.getCantidad(),
                mov.getReferencia()
        );
    }
}
