package application;

import java.util.Date;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;
public class Program {

    public static void main(String[] args) {

        Department obj = new Department(1, "Books");

        Seller seller = new Seller(21, obj, 3000.0, "bob@gmail.com", new Date(), "Bob");

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println(seller);
    }
}
