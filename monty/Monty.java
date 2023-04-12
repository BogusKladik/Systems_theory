package monty;

import java.util.Random;
import java.util.Scanner;

public class Monty {
    public static void main(String[] args) {
        int count_games = -1;
        int count_doors = -1;

        Scanner console = new Scanner(System.in);

        do {
            System.out.print("Введите колличество игр: ");
            count_games = console.nextInt();

            if (count_games > 0) {
                break;
            }

            System.out.println("Неверное число, введите положительное число");
        } while (true);

        do {
            System.out.print("Введите колличество дверей: ");
            count_doors = console.nextInt();

            if (count_doors > 0) {
                break;
            }

            System.out.println("Неверное число, введите положительное число");
        } while (true);

        console.close();

        Game game_with_change = new Game(count_games, count_doors, true);
        System.out.println("Число побед при изменении выбора: " + game_with_change.run());

        Game game_without_change = new Game(count_games, count_doors, false);
        System.out.println("Число побед без изменении выбора: " + game_without_change.run());
    }
}

class Game {
    private final Player player;
    private final int count_doors;
    private final int count_games;
    private final Random rand;

    public Game() {
        this.player = new Player();
        this.count_doors = 3;
        this.count_games = 100;
        this.rand = new Random();
    }

    public Game(int count_games) {
        this.player = new Player();
        this.count_doors = 3;
        this.count_games = count_games;
        this.rand = new Random();
    }

    public Game(int count_games, boolean player_is_smart) {
        this.player = new Player(player_is_smart);
        this.count_doors = 3;
        this.count_games = count_games;
        this.rand = new Random();
    }

    public Game(int count_games, int count_doors) {
        this.player = new Player();
        this.count_doors = 3;
        this.count_games = count_games;
        this.rand = new Random();
    }

    public Game(int count_games, int count_doors, boolean player_is_smart) {
        this.player = new Player(player_is_smart);
        this.count_doors = count_doors;
        this.count_games = count_games;
        this.rand = new Random();
    }

    public int run() {
        int wins = 0;

        for (int i = 0; i < count_games; i++) {
            int car_door = rand.nextInt(count_doors);
            player.selected_element = rand.nextInt(count_doors);

            int open_door = -1;
            do {
                open_door = rand.nextInt(count_doors);
            } while (open_door == car_door || open_door == player.selected_element);

            if (player.smart == false) {
                if (player.selected_element == car_door) {
                    wins++;
                }
                continue;
            }

            int switch_guess = -1;
            do {
                switch_guess = rand.nextInt(count_doors);
            } while (switch_guess == open_door || switch_guess == player.selected_element);
            player.selected_element = switch_guess;

            if (player.selected_element == car_door) {
                wins++;
            }
        }

        return wins;
    }
}

class Player {
    int selected_element;
    boolean smart;

    public Player(int selected_element, boolean smart) {
        this.selected_element = selected_element;
        this.smart = smart;
    }

    public Player(boolean smart) {
        this(0, smart);
    }

    public Player() {
        this(0, false);
    }
}