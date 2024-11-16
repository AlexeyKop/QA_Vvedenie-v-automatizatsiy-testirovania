package com.home.monty_hall;

public class Main {
    public static void main(String[] args) {
        MontyHallSimulator simulator = new MontyHallSimulator();

        System.out.println("Играем 1000 раундов...");
        simulator.simulateGames(1000, true);  // Стратегия смены двери
        simulator.simulateGames(1000, false); // Стратегия оставления выбора
    }
}
