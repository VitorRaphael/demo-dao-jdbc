package model.dao;

import model.entities.Department;
import model.entities.Seller;

import java.util.List;

public interface SellerDao {

    void insert (Seller obj);
    void update (Seller obj);
    void deleteById(Seller id);
    Seller findById (Integer id); // Pega esse id e consulta no banco de dados um objeto com esse id

    // Vai retornar todos os departamentos
    List<Seller> findAll();

    // Buscando por departamento
    List<Seller> findByDepartment(Department department);
}
