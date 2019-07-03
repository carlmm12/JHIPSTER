package com.libro.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Cliente.
 */
@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Size(max = 30)
    @Column(name = "direccion", length = 30)
    private String direccion;

    @Column(name = "telefono")
    private Integer telefono;

    @Size(max = 50)
    @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")
    @Column(name = "email", length = 50)
    private String email;

    @OneToMany(mappedBy = "cliente")
    private Set<Ejemplar> libros = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Cliente nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public Cliente direccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public Cliente telefono(Integer telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public Cliente email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Ejemplar> getLibros() {
        return libros;
    }

    public Cliente libros(Set<Ejemplar> ejemplars) {
        this.libros = ejemplars;
        return this;
    }

    public Cliente addLibro(Ejemplar ejemplar) {
        this.libros.add(ejemplar);
        ejemplar.setCliente(this);
        return this;
    }

    public Cliente removeLibro(Ejemplar ejemplar) {
        this.libros.remove(ejemplar);
        ejemplar.setCliente(null);
        return this;
    }

    public void setLibros(Set<Ejemplar> ejemplars) {
        this.libros = ejemplars;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cliente)) {
            return false;
        }
        return id != null && id.equals(((Cliente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Cliente{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", telefono=" + getTelefono() +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
