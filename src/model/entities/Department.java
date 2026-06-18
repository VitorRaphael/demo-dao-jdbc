package model.entities;

import java.util.Objects;

// Implements Serializable serve para que o nosso objeto possa ser garvado em arquivo, trafegado em rede
// e assim por diante
import java.io.Serializable;

public class Department implements Serializable {

    private static final long serialVersionUID = 1l;

    private Integer id;
    private String name;

    // Construtor sem argumentos
    public Department() {
    }

    // Construtor com argumentos
    public Department(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // HashCode e equals apenas por ID
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    // Para imprimir os valores dos objetos

    @Override
    public String toString() {
        return "department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}