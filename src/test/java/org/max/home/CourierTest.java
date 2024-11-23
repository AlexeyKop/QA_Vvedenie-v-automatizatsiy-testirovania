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
public class CourierTest extends AbstractTest {

    @Test
    @Order(1)
    void getCouriers_whenValid_shouldReturn() throws SQLException {
        // Подготовка данных через SQL
        String sql = "SELECT * FROM courier_info WHERE delivery_type ='car'";
        Statement stmt = getConnection().createStatement();
        int countTableSize = 0;

        // Выполнение SQL-запроса
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            countTableSize++;
        }

        // Подготовка запроса через Hibernate
        final Query query = getSession()
                .createNativeQuery("SELECT * FROM courier_info")
                .addEntity(CourierInfoEntity.class);

        // Проверка результатов
        Assertions.assertEquals(3, countTableSize);
        Assertions.assertEquals(6, query.list().size());
    }

    @Order(2)
    @ParameterizedTest
    @CsvSource({"John, Rython", "Kate, Looran", "Bob, Kolaris"})
    void getCourierById_whenValid_shouldReturn(String name, String lastName) throws SQLException {
        // Подготовка SQL-запроса
        String sql = "SELECT * FROM courier_info WHERE first_name='" + name + "'";
        Statement stmt = getConnection().createStatement();
        String retrievedLastName = "";

        // Выполнение запроса
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            retrievedLastName = rs.getString("last_name");
        }

        // Проверка результатов
        Assertions.assertEquals(lastName, retrievedLastName);
    }

    @Test
    @Order(3)
    void addCourier_whenValid_shouldSave() {
        // Подготовка данных
        CourierInfoEntity entity = new CourierInfoEntity();
        entity.setCourierId((short) 6);
        entity.setFirstName("Ivan");
        entity.setLastName("Sidorov");
        entity.setPhoneNumber("+7 960 808 9090");
        entity.setDeliveryType("foot");

        // Сохранение данных через Hibernate
        Session session = getSession();
        session.beginTransaction();
        try {
            session.persist(entity);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }

        // Проверка сохраненных данных
        final Query query = getSession()
                .createNativeQuery("SELECT * FROM courier_info WHERE courier_id = 5", CourierInfoEntity.class);
        CourierInfoEntity retrievedEntity = (CourierInfoEntity) query.uniqueResult();

        Assertions.assertNotNull(retrievedEntity, "Объект должен быть сохранен");
        Assertions.assertEquals("+7 960 808 9090", retrievedEntity.getPhoneNumber());
        Assertions.assertEquals("foot", retrievedEntity.getDeliveryType());
    }


    @Test
    @Order(4)
    void deleteCourier_whenValid_shouldDelete() {
        // Получение курьера для удаления
        final Query query = getSession()
                .createNativeQuery("SELECT * FROM courier_info WHERE courier_id=" + 6)
                .addEntity(CourierInfoEntity.class);
        Optional<CourierInfoEntity> courierEntity = (Optional<CourierInfoEntity>) query.uniqueResultOptional();
        Assumptions.assumeTrue(courierEntity.isPresent());

        // Удаление данных
        Session session = getSession();
        session.beginTransaction();
        session.delete(courierEntity.get());
        session.getTransaction().commit();

        // Проверка удаления
        final Query queryAfterDelete = getSession()
                .createNativeQuery("SELECT * FROM courier_info WHERE courier_id=" + 6)
                .addEntity(CourierInfoEntity.class);
        Optional<CourierInfoEntity> courierEntityAfterDelete = (Optional<CourierInfoEntity>) queryAfterDelete.uniqueResultOptional();
        Assertions.assertFalse(courierEntityAfterDelete.isPresent());
    }

    @Test
    @Order(5)
    void addCourier_whenDuplicateId_shouldThrowException() {
        // Подготовка первой записи
        CourierInfoEntity entity1 = new CourierInfoEntity();
        entity1.setCourierId((short) 9);
        entity1.setFirstName("Ivan");
        entity1.setLastName("Ivanov");
        entity1.setPhoneNumber("+7 999 111 2222");
        entity1.setDeliveryType("bike");

        // Подготовка второй записи с тем же ID
        CourierInfoEntity entity2 = new CourierInfoEntity();
        entity2.setCourierId((short) 9); // Дублирующий ID
        entity2.setFirstName("Petr");
        entity2.setLastName("Petrov");
        entity2.setPhoneNumber("+7 999 333 4444");
        entity2.setDeliveryType("car");

        // Сохранение первой записи
        Session session = getSession();
        session.beginTransaction();
        session.persist(entity1);
        session.getTransaction().commit();

        // Попытка сохранить вторую запись
        session.beginTransaction();
        Assertions.assertThrows(PersistenceException.class, () -> session.persist(entity2));
        session.getTransaction().rollback();
    }

}
