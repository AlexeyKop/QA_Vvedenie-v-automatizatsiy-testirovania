package com.home.monty_hall;

// Абстрактный класс для моделирования игры Монти Холла
public abstract class MontyHallGame {
    protected int totalDoors = 3; // Количество дверей
    protected int winningDoor;   // Дверь с призом
    protected int selectedDoor;  // Выбранная игроком дверь

    // Метод для инициализации игры (например, случайное назначение приза)
    public abstract void initializeGame();

    // Метод для выполнения одного раунда игры
    public abstract boolean playRound(boolean switchDoor);

    // Метод для запуска симуляции на n игр
    public abstract void simulateGames(int numberOfGames, boolean switchDoor);
}
