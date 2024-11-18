package com.home.composite;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CompositeTest {

    @BeforeEach
    void setUp() {
        System.out.println("Подготовка к тесту...");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Очистка после теста...");
    }


    @Test
    void testLeafOperation() {
        // Given: Создаем лист
        Leaf leaf = new Leaf("Лист 1");

        // When: Вызываем операцию
        leaf.operation();

        // Then: Убеждаемся, что имя корректно
        assertEquals("Лист 1", leaf.getName());
    }

    @Test
    void testCompositeOperation() {
        // Given: Создаем контейнер и добавляем в него элементы
        Composite composite = new Composite("Контейнер 1");
        Leaf leaf1 = new Leaf("Лист 1");
        Leaf leaf2 = new Leaf("Лист 2");
        composite.add(leaf1);
        composite.add(leaf2);

        // When: Вызываем операцию
        composite.operation();

        // Then: Убеждаемся, что элементы добавлены
        assertEquals(2, composite.getChildren().size());
        assertTrue(composite.getChildren().contains(leaf1));
        assertTrue(composite.getChildren().contains(leaf2));
    }

    @Test
    void testRemoveComponent() {
        // Given: Создаем контейнер и добавляем элемент
        Composite composite = new Composite("Контейнер 1");
        Leaf leaf = new Leaf("Лист 1");
        composite.add(leaf);

        // When: Удаляем элемент
        composite.remove(leaf);

        // Then: Проверяем, что элемент удален
        assertFalse(composite.getChildren().contains(leaf));
    }

    @Test
    void testAddNullComponent() {
        // Given: Создаем контейнер
        Composite composite = new Composite("Контейнер 1");

        // When/Then: Добавление null должно выбрасывать исключение
        assertThrows(NullPointerException.class, () -> composite.add(null));
    }

    @Test
    void testRemoveNullComponent() {
        // Given: Создаем контейнер
        Composite composite = new Composite("Контейнер 1");

        // When/Then: Удаление null не должно вызывать ошибок
        assertDoesNotThrow(() -> composite.remove(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Лист A", "Лист B", "Лист C"})
    void testLeafWithDifferentNames(String name) {
        // Given: Создаем лист с разным именем
        Leaf leaf = new Leaf(name);

        // When: Получаем имя
        String actualName = leaf.getName();

        // Then: Проверяем корректность имени
        assertEquals(name, actualName);
    }


}
