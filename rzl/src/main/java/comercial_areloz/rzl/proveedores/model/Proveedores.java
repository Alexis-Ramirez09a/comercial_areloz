package comercial_areloz.rzl.proveedores.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Proveedores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 200)
    private String direccion;

    @Column(length = 20)
    private String telefono;

    @Column(length = 100)
    private String email;

    @Column(length = 50)
    private String tipo_proveedor;

    @Column(length = 100)
    private String representante_ventas;

    @Column(length = 20)
    private String celular_representante;

    @Column(length = 50)
    private String metodo_pago;

    @Column(length = 50)
    private String condicion_pago;

    @Column(length = 30)
    private String num_cuenta;

    @Column(length = 100)
    private String banco_asociado;

    @Column(length = 50)
    private String categoria_producto;

    @Column(length = 30)
    private String plazo_entrega;

    @Column(length = 20, unique = true, nullable = false)
    private String cedula;

    public Proveedores() {
    }

    public Proveedores(String nombre, String direccion, String telefono, String email) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }

    // Getters y Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipo_proveedor() {
        return tipo_proveedor;
    }

    public void setTipo_proveedor(String tipo_proveedor) {
        this.tipo_proveedor = tipo_proveedor;
    }

    public String getRepresentante_ventas() {
        return representante_ventas;
    }

    public void setRepresentante_ventas(String representante_ventas) {
        this.representante_ventas = representante_ventas;
    }

    public String getCelular_representante() {
        return celular_representante;
    }

    public void setCelular_representante(String celular_representante) {
        this.celular_representante = celular_representante;
    }

    public String getMetodo_pago() {
        return metodo_pago;
    }

    public void setMetodo_pago(String metodo_pago) {
        this.metodo_pago = metodo_pago;
    }

    public String getCondicion_pago() {
        return condicion_pago;
    }

    public void setCondicion_pago(String condicion_pago) {
        this.condicion_pago = condicion_pago;
    }

    public String getNum_cuenta() {
        return num_cuenta;
    }

    public void setNum_cuenta(String num_cuenta) {
        this.num_cuenta = num_cuenta;
    }

    public String getBanco_asociado() {
        return banco_asociado;
    }

    public void setBanco_asociado(String banco_asociado) {
        this.banco_asociado = banco_asociado;
    }

    public String getCategoria_producto() {
        return categoria_producto;
    }

    public void setCategoria_producto(String categoria_producto) {
        this.categoria_producto = categoria_producto;
    }

    public String getPlazo_entrega() {
        return plazo_entrega;
    }

    public void setPlazo_entrega(String plazo_entrega) {
        this.plazo_entrega = plazo_entrega;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

}
