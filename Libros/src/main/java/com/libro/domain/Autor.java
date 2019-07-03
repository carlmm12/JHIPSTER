package com.libro.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Autor.
 */
@Entity
@Table(name = "autor")
public class Autor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Size(max = 100)
    @Column(name = "web", length = 100)
    private String web;

    @Size(max = 50)
    @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")
    @Column(name = "email", length = 50)
    private String email;

    @ManyToMany
    @JoinTable(name = "autor_libro",
               joinColumns = @JoinColumn(name = "autor_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "libro_id", referencedColumnName = "id"))
    private Set<Libro> libros = new HashSet<>();

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

    public Autor nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getWeb() {
        return web;
    }

    public Autor web(String web) {
        this.web = web;
        return this;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getEmail() {
        return email;
    }

    public Autor email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Libro> getLibros() {
        return libros;
    }

    public Autor libros(Set<Libro> libros) {
        this.libros = libros;
        return this;
    }

    public Autor addLibro(Libro libro) {
        this.libros.add(libro);
        libro.getAutors().add(this);
        return this;
    }

    public Autor removeLibro(Libro libro) {
        this.libros.remove(libro);
        libro.getAutors().remove(this);
        return this;
    }

    public void setLibros(Set<Libro> libros) {
        this.libros = libros;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Autor)) {
            return false;
        }
        return id != null && id.equals(((Autor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Autor{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", web='" + getWeb() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
