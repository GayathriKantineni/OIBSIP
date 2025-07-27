import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int rounds = 3;
        int maxAttempts = 5;
        int score = 0;

        System.out.println("===============================");
        System.out.println("  Welcome to the Number Guessing Game!");
        System.out.println("===============================");

        for (int round = 1; round <= rounds; round++) {
            int numberToGuess = random.nextInt(100) + 1;
            int attempts = 0;
            boolean guessed = false;

            System.out.println("\nRound " + round + ": Guess a number between 1 and 100!");

            while (attempts < maxAttempts) {
                System.out.print("Attempt " + (attempts + 1) + ": Enter your guess: ");
                int userGuess = scanner.nextInt();
                attempts++;

                if (userGuess == numberToGuess) {
                    System.out.println("Congratulations! You guessed the number in " + attempts + " attempts.");
                    score += (maxAttempts - attempts + 1) * 10; // More points for fewer attempts
                    guessed = true;
                    break;
                } else if (userGuess < numberToGuess) {
                    System.out.println("Too low!");
                } else {
                    System.out.println("Too high!");
                }
            }

            if (!guessed) {
                System.out.println("Sorry! The correct number was: " + numberToGuess);
            }
        }

        System.out.println("\nGame Over! Your total score: " + score);
        scanner.close();
    }
}