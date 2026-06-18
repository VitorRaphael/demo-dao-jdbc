package model.dao.ipml;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.SQLException;
import java.util.List;

public class SellerDaoJDBC implements SellerDao {

    // Dependência de conexão com o banco de dados
    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }
    // final da dependência

    @Override
    public void insert(Seller obj) {

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
        return List.of();
    }
}
