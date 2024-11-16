package com.home.monty_hall;

import java.util.Random;

public class MontyHallSimulator extends MontyHallGame {
    private Random random = new Random();

    @Override
    public void initializeGame() {
        if (totalDoors < 3) { // Проверяем, что количество дверей >= 3
            throw new IllegalArgumentException("Количество дверей должно быть >= 3");
        }
        winningDoor = random.nextInt(totalDoors); // Генерируем выигрышную дверь
    }


    @Override
    public boolean playRound(boolean switchDoor) {
        // Игрок случайно выбирает одну из дверей
        selectedDoor = random.nextInt(totalDoors);

        // Ведущий открывает дверь, не содержащую приз и не выбранную игроком
        int revealedDoor = -1;
        for (int i = 0; i < totalDoors; i++) {
            if (i != selectedDoor && i != winningDoor) {
                revealedDoor = i;
                break;
            }
        }

        // Если игрок меняет выбор, он выбирает дверь, не открытую ведущим
        if (switchDoor) {
            for (int i = 0; i < totalDoors; i++) {
                if (i != selectedDoor && i != revealedDoor) {
                    selectedDoor = i;
                    break;
                }
            }
        }

        // Возвращаем результат: совпадает ли выбранная дверь с выигрышной
        return selectedDoor == winningDoor;
    }


    @Override
    public void simulateGames(int numberOfGames, boolean switchDoor) {
        if (numberOfGames <= 0) { // Проверка числа игр
            throw new IllegalArgumentException("Число игр должно быть больше 0");
        }

        int wins = 0;

        for (int i = 0; i < numberOfGames; i++) {
            initializeGame();
            if (playRound(switchDoor)) {
                wins++;
            }
        }

        System.out.printf("Стратегия %s: выигрыш в %.2f%% случаев%n",
                switchDoor ? "смены двери" : "оставления выбора",
                (wins / (double) numberOfGames) * 100);
    }

}
