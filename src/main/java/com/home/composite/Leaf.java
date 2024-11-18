package com.home.composite;

// Реализация элемента
public class Leaf implements Component {
    private final String name;

    // Конструктор
    public Leaf(String name) {
        this.name = name;
    }

    // Реализация действия
    @Override
    public void operation() {
        System.out.println("Выполнение операции в листе: " + name);
    }

    // Получение имени (для тестов)
    public String getName() {
        return name;
    }
}
