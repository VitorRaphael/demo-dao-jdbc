package model.entities;

import java.util.Date;
import java.util.Objects;

// Implements Serializable serve para que o nosso objeto possa ser garvado em arquivo, trafegado em rede
// e assim por diante
import java.io.Serializable;

public class Seller implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String email;
    private Date birthDate;
    private Double baseSalary;

    // Chamando o department
    private Department department;

    // Construtor sem argumentos
    public Seller() {
    }

    // Construtor com argumentos
    public Seller(Integer id, Department department, Double baseSalary, String email, Date birthDate, String name) {
        this.id = id;
        this.department = department;
        this.baseSalary = baseSalary;
        this.email = email;
        this.birthDate = birthDate;
        this.name = name;
    }

    // Getters e Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(Double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // HashCode e Equals Somente o Id
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Seller seller = (Seller) o;
        return Objects.equals(id, seller.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    // To string

    @Override
    public String toString() {
        return "Seller{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", birthDate=" + birthDate +
                ", baseSalary=" + baseSalary +
                ", department=" + department +
                '}';
    }
}
