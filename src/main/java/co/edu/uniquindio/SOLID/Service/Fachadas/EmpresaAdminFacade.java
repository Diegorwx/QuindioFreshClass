package co.edu.uniquindio.SOLID.Service.Fachadas;

import co.edu.uniquindio.SOLID.Model.DTO.EmpleadoDTO;
import co.edu.uniquindio.SOLID.Model.Minimercado;
import co.edu.uniquindio.SOLID.Service.EmpleadoService;

import java.util.List;

public class EmpresaAdminFacade {

    private final EmpleadoService empleadoService;

    public EmpresaAdminFacade() {
        this.empleadoService = new EmpleadoService(Minimercado.getInstancia());
    }

    public boolean crearEmpleado(EmpleadoDTO dto) { return empleadoService.crearEmpleado(dto); }
    public boolean actualizarEmpleado(EmpleadoDTO dto) { return empleadoService.actualizarEmpleado(dto); }
    public boolean eliminarEmpleado(String id) { return empleadoService.eliminarEmpleado(id); }
    public boolean activarEmpleado(String id) { return empleadoService.activarEmpleado(id); }
    public boolean inactivarEmpleado(String id) { return empleadoService.inactivarEmpleado(id); }
    public List<EmpleadoDTO> listarEmpleados() { return empleadoService.listarEmpleados(); }
}
