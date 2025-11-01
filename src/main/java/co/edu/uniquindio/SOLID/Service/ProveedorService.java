package co.edu.uniquindio.SOLID.Service;

import co.edu.uniquindio.SOLID.Model.DTO.ProveedorDTO;
import co.edu.uniquindio.SOLID.Model.Minimercado;
import co.edu.uniquindio.SOLID.Model.Proveedor;
import co.edu.uniquindio.SOLID.utils.Mappers.ProveedorMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


public class ProveedorService {

    private final Minimercado minimercado;

    public ProveedorService(Minimercado minimercado) {
        this.minimercado = minimercado;
    }


    public boolean crearProveedor(ProveedorDTO dto) {
        if (dto == null || dto.getNit() == null || dto.getNombre() == null)
            throw new IllegalArgumentException("Datos del proveedor incompletos");

        if (buscarProveedor(dto.getNit()) != null)
            throw new IllegalArgumentException("Ya existe un proveedor con ese NIT");

        Proveedor nuevo = ProveedorMapper.toEntity(dto);
        minimercado.getProveedores().add(nuevo);
        return true;
    }


    public boolean actualizarProveedor(ProveedorDTO dto) {
        Proveedor proveedor = buscarProveedor(dto.getNit());
        if (proveedor == null)
            throw new NoSuchElementException("Proveedor no encontrado");

        ProveedorMapper.updateEntityFromDTO(proveedor, dto);
        return true;
    }

    
    public boolean eliminarProveedor(String nit) {
        Proveedor proveedor = buscarProveedor(nit);
        if (proveedor == null)
            throw new NoSuchElementException("Proveedor no encontrado");

        minimercado.getProveedores().remove(proveedor);
        return true;
    }


    public List<ProveedorDTO> listarProveedores() {
        List<ProveedorDTO> lista = new ArrayList<>();
        for (Proveedor p : minimercado.getProveedores()) {
            lista.add(ProveedorMapper.toDTO(p));
        }
        return lista;
    }


    private Proveedor buscarProveedor(String nit) {
        return minimercado.getProveedores()
                .stream()
                .filter(p -> p.getNit().equalsIgnoreCase(nit))
                .findFirst()
                .orElse(null);
    }
}

