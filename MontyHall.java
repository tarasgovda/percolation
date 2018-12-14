/* *****************************************************************************
 *  Name: Monty Hall test
 *  Date: 10-12-2018
 *  Description: Monty Hall test
 **************************************************************************** */


import java.util.Random;

public class MontyHall {

    private final boolean[] doors;

    public MontyHall() {
        this.doors = new boolean[3];
        int luckyIndex = uniform(3);
        this.doors[luckyIndex] = true;
    }

    public int openTheWrongDoor(final int choosenDoor) {
        if (doors[choosenDoor]) {
            int[] wrongDoors = new int[doors.length - 1];
            int wrongDoorsIndex = 0;
            for (int i = 0; i < doors.length; i++) {
                if (!doors[i]) {
                    wrongDoors[wrongDoorsIndex++] = i;
                }
            }

            return wrongDoors[uniform(2)];
        } else {
            for (int i = 0; i < doors.length; i++) {
                if (i != choosenDoor && !doors[i]) {
                    return i;
                }
            }
        }

        return -1;
    }

    public boolean isCorrect(int choosenDoor) {
        return doors[choosenDoor];
    }

    public static void main(String[] args) {

        MontyHall montyHall = new MontyHall();

        int changeMindCorrectGuesses = 0;
        int dontChangeMindCorrectGuesses = 0;

        // change mind
        int choosenDoor;
        int wrongDoor;
        for (int i = 0; i < 50_000; i++) {

            choosenDoor = montyHall.uniform(3);
            wrongDoor = montyHall.openTheWrongDoor(choosenDoor);

            for (int j = 0; j < 3; j++) {
                if (j != choosenDoor && j != wrongDoor) {
                    choosenDoor = j;
                    break;
                }
            }
            if (montyHall.isCorrect(choosenDoor)) {
                changeMindCorrectGuesses++;
            }

        }

        // don't change mind
        for (int i = 0; i < 50_000; i++) {
            choosenDoor = montyHall.uniform(3);

            if (montyHall.isCorrect(choosenDoor)) {
                dontChangeMindCorrectGuesses++;
            }
        }

        System.out.println("Correct guesses after changing the final decision: " + changeMindCorrectGuesses);
        System.out.println("Correct guesses after staying on the same door: " + dontChangeMindCorrectGuesses);
    }


    public int uniform(int n) {
        if (n <= 0) throw new IllegalArgumentException("argument must be positive: " + n);
        Random random = new Random(System.currentTimeMillis());
        return random.nextInt(n);
    }
}
