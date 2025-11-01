package co.edu.uniquindio.SOLID.Model.DTO;

public class ProveedorDTO {

    private String nit;
    private String nombre;
    private String contacto;
    private String email;
    private String telefono;
    private boolean activo;

    public ProveedorDTO() {}

    public ProveedorDTO(String nit, String nombre, String contacto, String email, String telefono, boolean activo) {
        this.nit = nit;
        this.nombre = nombre;
        this.contacto = contacto;
        this.email = email;
        this.telefono = telefono;
        this.activo = activo;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

}