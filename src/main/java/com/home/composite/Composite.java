package com.home.composite;

import java.util.ArrayList;
import java.util.List;

// Реализация контейнера
public class Composite implements Component {
    private final String name;
    private final List<Component> children = new ArrayList<>();

    // Конструктор
    public Composite(String name) {
        this.name = name;
    }

    // Добавление элемента в контейнер
    public void add(Component component) {
        if (component == null) {
            throw new NullPointerException("Невозможно добавить null компонент в контейнер.");
        }
        children.add(component);
    }


    // Удаление элемента из контейнера
    public void remove(Component component) {
        children.remove(component);
    }

    // Реализация действия
    @Override
    public void operation() {
        System.out.println("Выполнение операции в контейнере: " + name);
        for (Component child : children) {
            child.operation();
        }
    }

    // Получение дочерних элементов (для тестов)
    public List<Component> getChildren() {
        return children;
    }
}
