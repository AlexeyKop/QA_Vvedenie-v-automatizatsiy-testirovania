package org.max.home;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import jakarta.persistence.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductsTest extends AbstractTest {

    @Test
    @Order(1)
    void getProducts_whenValid_shouldReturn() throws SQLException {
        // given
        String sql = "SELECT * FROM products WHERE menu_name='FUTOMAKI'";
        Statement stmt = getConnection().createStatement();
        int countTableSize = 0;

        // when
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            countTableSize++;
        }

        final Query query = getSession()
                .createNativeQuery("SELECT * FROM products", ProductsEntity.class);

        // then
        Assertions.assertEquals(1, countTableSize);
        Assertions.assertEquals(10, query.list().size());
    }

    @Order(2)
    @ParameterizedTest
    @CsvSource({"GOJIRA ROLL, 300.0", "TOOTSY MAKI, 133.0", "COFFEE, 79.0"})
    void getProductByName_whenValid_shouldReturn(String menuName, String price) throws SQLException {
        // given
        String sql = "SELECT * FROM products WHERE menu_name='" + menuName + "'";
        Statement stmt = getConnection().createStatement();
        String retrievedPrice = "";

        // when
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            retrievedPrice = rs.getString("price");
        }

        // then
        Assertions.assertEquals(price, retrievedPrice);
    }

    @Test
    @Order(3)
    void addProduct_whenValid_shouldSave() {
        // given
        ProductsEntity entity = new ProductsEntity();
        entity.setProductId((short) 11);
        entity.setMenuName("Salad");
        entity.setPrice("25.99");

        // when
        Session session = getSession();
        session.beginTransaction();
        session.persist(entity);
        session.getTransaction().commit();

        final Query query = getSession()
                .createNativeQuery("SELECT * FROM products WHERE product_id=11", ProductsEntity.class);
        ProductsEntity retrievedProduct = (ProductsEntity) query.uniqueResult();

        // then
        Assertions.assertNotNull(retrievedProduct);
        Assertions.assertEquals("Salad", retrievedProduct.getMenuName());
        Assertions.assertEquals("25.99", retrievedProduct.getPrice());
    }

    @Test
    @Order(4)
    void deleteProduct_whenValid_shouldDelete() {
        // given
        final Query query = getSession()
                .createNativeQuery("SELECT * FROM products WHERE product_id=11", ProductsEntity.class);
        Optional<ProductsEntity> productEntity = (Optional<ProductsEntity>) query.uniqueResultOptional();
        Assumptions.assumeTrue(productEntity.isPresent());

        // when
        Session session = getSession();
        session.beginTransaction();
        session.delete(productEntity.get());
        session.getTransaction().commit();

        // then
        final Query queryAfterDelete = getSession()
                .createNativeQuery("SELECT * FROM products WHERE product_id=11", ProductsEntity.class);
        Optional<ProductsEntity> productEntityAfterDelete = (Optional<ProductsEntity>) queryAfterDelete.uniqueResultOptional();
        Assertions.assertFalse(productEntityAfterDelete.isPresent());
    }

    @Test
    @Order(5)
    void addProduct_whenNotValid_shouldThrow() {
        // given
        ProductsEntity entity = new ProductsEntity();

        // when
        Session session = getSession();
        session.beginTransaction();
        session.persist(entity);

        // then
        Assertions.assertThrows(PersistenceException.class, () -> session.getTransaction().commit());
    }
}
