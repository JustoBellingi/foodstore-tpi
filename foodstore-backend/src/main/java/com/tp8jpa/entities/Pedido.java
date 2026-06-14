package com.tp8jpa.entities;

import com.tp8jpa.entities.enums.Estado;
import com.tp8jpa.entities.enums.FormaPago;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Pedido extends Base implements Calculable {

    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    private Double total;

    @Enumerated(EnumType.STRING)
    private FormaPago formaPago;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    private Set<DetallePedido> detalles = new HashSet<>();

    public Pedido() {
    }

    public Pedido(LocalDate fecha, Estado estado, FormaPago formaPago) {
        this.fecha = fecha;
        this.estado = estado;
        this.formaPago = formaPago;
        this.total = 0.0;
    }

    @Override
    public void calcularTotal() {
        total = 0.0;

        for (DetallePedido detalle : detalles) {
            total += detalle.getSubtotal();
        }
    }

    public void addDetallePedido(int cantidad, Producto producto) {

        Double subtotal = producto.getPrecio() * cantidad;

        DetallePedido detalle = new DetallePedido(
                cantidad,
                subtotal,
                producto
        );

        detalles.add(detalle);

        calcularTotal();
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Double getTotal() {
        return total;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public Set<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(Set<DetallePedido> detalles) {
        this.detalles = detalles;
    }
}