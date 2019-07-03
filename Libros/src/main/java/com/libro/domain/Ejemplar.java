package com.libro.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Ejemplar.
 */
@Entity
@Table(name = "ejemplar")
public class Ejemplar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "edicion", nullable = false)
    private String edicion;

    @NotNull
    @Column(name = "encuadernacion", nullable = false)
    private String encuadernacion;

    @NotNull
    @Column(name = "precio", nullable = false)
    private Double precio;

    @ManyToOne
    @JsonIgnoreProperties("ejemplars")
    private Libro libro;

    @ManyToOne
    @JsonIgnoreProperties("libros")
    private Cliente cliente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEdicion() {
        return edicion;
    }

    public Ejemplar edicion(String edicion) {
        this.edicion = edicion;
        return this;
    }

    public void setEdicion(String edicion) {
        this.edicion = edicion;
    }

    public String getEncuadernacion() {
        return encuadernacion;
    }

    public Ejemplar encuadernacion(String encuadernacion) {
        this.encuadernacion = encuadernacion;
        return this;
    }

    public void setEncuadernacion(String encuadernacion) {
        this.encuadernacion = encuadernacion;
    }

    public Double getPrecio() {
        return precio;
    }

    public Ejemplar precio(Double precio) {
        this.precio = precio;
        return this;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Libro getLibro() {
        return libro;
    }

    public Ejemplar libro(Libro libro) {
        this.libro = libro;
        return this;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Ejemplar cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ejemplar)) {
            return false;
        }
        return id != null && id.equals(((Ejemplar) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Ejemplar{" +
            "id=" + getId() +
            ", edicion='" + getEdicion() + "'" +
            ", encuadernacion='" + getEncuadernacion() + "'" +
            ", precio=" + getPrecio() +
            "}";
    }
}
