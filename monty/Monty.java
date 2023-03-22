package monty;

import java.util.Random;
import java.util.Scanner;

public class Monty {
    public static void main(String[] args) {
        int count_games;

        Scanner console = new Scanner(System.in);
        do {
            System.out.print("Введите колличество игр: ");
            count_games = console.nextInt();

            if (count_games > 0) {
                break;
            }

            System.out.println("Неверное число, введите положительное число");
        } while (true);
        console.close();

        Game game_with_change = new Game(count_games, true);
        System.out.println("Число побед при изменении выбора: " + game_with_change.start_games());

        Game game_without_change = new Game(count_games, false);
        System.out.println("Число побед без изменении выбора: " + game_without_change.start_games());
    }
}

class Game {
    Player player = new Player();

    Random rand = new Random();

    int count_games = 0;

    public Game(int count_games) {
        this.count_games = count_games;
    }

    public Game(int count_games, boolean player_is_smart) {
        this(count_games);
        this.player.smart = player_is_smart;
    }

    public int start_games() {
        int wins = 0;
        for (int game = 0; game < count_games; game++) {
            wins += start_game();
        }
        return wins;
    }

    private int start_game() {
        int car_index = rand.nextInt(0, 3);
        Door[] doors = new Door[3];
        for (int door_index = 0; door_index < doors.length; door_index++) {
            doors[door_index] = door_index == car_index ? Door.Car : Door.Goat;
        }

        player.selected_element = rand.nextInt(0, 3);

        int[] possible_doors = init_possible_doors(doors.length, car_index, player.selected_element);

        int index_door_with_goat = rand.nextInt(0, possible_doors.length);

        if (player.smart) {
            for (int door_index = 0; door_index < doors.length; door_index++) {
                if (door_index != possible_doors[index_door_with_goat] && door_index != player.selected_element) {
                    player.selected_element = door_index;
                    break;
                }
            }
        }

        return doors[player.selected_element] == Door.Car ? 1 : 0;
    }

    int[] init_possible_doors(int length_doors, int car_index, int door_index) {
        int size = car_index == door_index ? length_doors - 1 : length_doors - 2;

        int[] possible_doors = new int[size];
        int j = 0;
        for (int possible_door = 0; possible_door < length_doors; possible_door++) {
            if (possible_door == car_index || possible_door == door_index) {
                continue;
            }
            possible_doors[j++] = possible_door;
        }

        return possible_doors;
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

enum Door {
    Goat,
    Car
}