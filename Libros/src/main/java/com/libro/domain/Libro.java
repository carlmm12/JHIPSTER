package com.libro.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Libro.
 */
@Entity
@Table(name = "libro")
public class Libro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "editorial", nullable = false)
    private String editorial;

    @NotNull
    @Column(name = "titulo", nullable = false)
    private String titulo;

    @OneToMany(mappedBy = "libro")
    private Set<Ejemplar> ejemplars = new HashSet<>();

    @ManyToMany(mappedBy = "libros")
    @JsonIgnore
    private Set<Autor> autors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEditorial() {
        return editorial;
    }

    public Libro editorial(String editorial) {
        this.editorial = editorial;
        return this;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getTitulo() {
        return titulo;
    }

    public Libro titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Set<Ejemplar> getEjemplars() {
        return ejemplars;
    }

    public Libro ejemplars(Set<Ejemplar> ejemplars) {
        this.ejemplars = ejemplars;
        return this;
    }

    public Libro addEjemplar(Ejemplar ejemplar) {
        this.ejemplars.add(ejemplar);
        ejemplar.setLibro(this);
        return this;
    }

    public Libro removeEjemplar(Ejemplar ejemplar) {
        this.ejemplars.remove(ejemplar);
        ejemplar.setLibro(null);
        return this;
    }

    public void setEjemplars(Set<Ejemplar> ejemplars) {
        this.ejemplars = ejemplars;
    }

    public Set<Autor> getAutors() {
        return autors;
    }

    public Libro autors(Set<Autor> autors) {
        this.autors = autors;
        return this;
    }

    public Libro addAutor(Autor autor) {
        this.autors.add(autor);
        autor.getLibros().add(this);
        return this;
    }

    public Libro removeAutor(Autor autor) {
        this.autors.remove(autor);
        autor.getLibros().remove(this);
        return this;
    }

    public void setAutors(Set<Autor> autors) {
        this.autors = autors;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Libro)) {
            return false;
        }
        return id != null && id.equals(((Libro) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Libro{" +
            "id=" + getId() +
            ", editorial='" + getEditorial() + "'" +
            ", titulo='" + getTitulo() + "'" +
            "}";
    }
}
