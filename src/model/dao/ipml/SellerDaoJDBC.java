package model.dao.ipml;

import java.sql.*;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    // Dependência de conexão com o banco de dados
    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }
    // final da dependência

    @Override
    public void insert(Seller obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO seller "
                            + " (Name, Email, BirthDate, BaseSalary, DepartmentId) "
                            + "VALUES"
                            + " (?, ?, ?, ?, ?)",
                            Statement.RETURN_GENERATED_KEYS);

            // Agora vamos configurar cada pontinho de interrogação
            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartment().getId());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(rs);
            }
            else {
                throw new DbException("Unexpected error! No rows affected!");
            }
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteById(Seller id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    " SELECT seller.*,department.Name as DepName "
                    + " FROM seller INNER JOIN department "
                    + " ON seller.DepartmentId = department.id "
                    + " WHERE seller.id = ?");

            // Configurando o ponto de interroção (?)
            st.setInt(1, id);
            rs = st.executeQuery();

            // Quando programas em oritentação a objeto por mias que busquemos os dados em tabelas
            // nos vamos querer que eles estejam associados e instanciados em memória ( diagrama )
            if (rs.next()) {
                Department dep = instantiateDepartment(rs); // Chamando o método Department
                Seller obj = instantiateSeller(rs, dep); // Chamando o método Seller
                return obj;
            }
            return null;

        }
        catch(SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    // Método Seller
    private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
        Seller obj = new Seller();
        obj.setId(rs.getInt("Id"));
        obj.setName(rs.getString("Name"));
        obj.setEmail(rs.getString("Email"));
        obj.setBaseSalary(rs.getDouble("BaseSalary"));
        obj.setBirthDate(rs.getDate("BirthDate"));
        obj.setDepartment(dep);
        return obj;
    }

    // Método Seller
    private Department instantiateDepartment(ResultSet rs) throws  SQLException{
        Department dep = new Department();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));
        return dep;
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    " SELECT seller.*,department.Name as DepName "
                            + " FROM seller INNER JOIN department "
                            + " ON seller.DepartmentId = department.Id "
                            + " ORDER BY Name");

            rs = st.executeQuery();

            // Como um departamento pode ter várias pessoas, vamos usar o Array list
            List<Seller> list = new ArrayList<>();

            // Vamos usar o map para verificar se quem eu vou chamar é igual a alguem que já chamei, para  não haver repetições
            Map<Integer, Department> map = new HashMap<>();

            // Quando programas em oritentação a objeto por mias que busquemos os dados em tabelas
            // nos vamos querer que eles estejam associados e instanciados em memória ( diagrama )
            while (rs.next()) {

                // Verificando se esse departamento já existe
                Department dep = map.get(rs.getInt("DepartmentId"));

                if (dep == null) {
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                Seller obj = instantiateSeller(rs, dep); // Chamando o método Seller
                list.add(obj);
            }
            return list;

        }
        catch(SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }




    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    " SELECT seller.*,department.Name as DepName "
                            + " FROM seller INNER JOIN department "
                            + " ON seller.DepartmentId = department.Id "
                            + " WHERE DepartmentId = ?"
                            + " ORDER BY Name");

            // Configurando o ponto de interroção (?)
            st.setInt(1, department.getId());

            rs = st.executeQuery();

            // Como um departamento pode ter várias pessoas, vamos usar o Array list
            List<Seller> list = new ArrayList<>();

            // Vamos usar o map para verificar se quem eu vou chamar é igual a alguem que já chamei, para  não haver repetições
            Map<Integer, Department> map = new HashMap<>();

            // Quando programas em oritentação a objeto por mias que busquemos os dados em tabelas
            // nos vamos querer que eles estejam associados e instanciados em memória ( diagrama )
            while (rs.next()) {

                // Verificando se esse departamento já existe
                Department dep = map.get(rs.getInt("DepartmentId"));

                if (dep == null) {
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                Seller obj = instantiateSeller(rs, dep); // Chamando o método Seller
                list.add(obj);
            }
            return list;

        }
        catch(SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
