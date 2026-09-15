package guessinggame;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class GuessingGame {

    public static void main(String[] args) {

        boolean playAgain = true;

        while (playAgain) {

            // Welcome screen
            JOptionPane.showMessageDialog(
                    null,
                    "====================================\n"
                    + "       NUMBER GUESSING GAME\n"
                    + "====================================\n\n"
                    + "Welcome to the upgraded version!\n"
                    + "Guess the secret number and earn points.",
                    "Number Guessing Game",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // Difficulty selection
            String[] difficulties = {
                "Easy",
                "Medium",
                "Hard"
            };

            int difficulty = JOptionPane.showOptionDialog(
                    null,
                    "SELECT DIFFICULTY LEVEL\n\n"
                    + "Easy   : Number 1 - 50, 10 attempts\n"
                    + "Medium : Number 1 - 100, 7 attempts\n"
                    + "Hard   : Number 1 - 500, 8 attempts",
                    "Difficulty Selection",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    difficulties,
                    difficulties[1]
            );

            // Exit if window is closed
            if (difficulty == -1) {
                return;
            }

            // Game settings
            int maxNumber;
            int maxAttempts;
            String difficultyName;

            if (difficulty == 0) {
                maxNumber = 50;
                maxAttempts = 10;
                difficultyName = "Easy";
            } 
            else if (difficulty == 1) {
                maxNumber = 100;
                maxAttempts = 7;
                difficultyName = "Medium";
            } 
            else {
                maxNumber = 500;
                maxAttempts = 8;
                difficultyName = "Hard";
            }

            // Generate random number
            int secretNumber = (int) (Math.random() * maxNumber) + 1;

            // Game variables
            int attempts = 0;
            int score = 1000;
            int hintsUsed = 0;
            int maxHints = 2;

            boolean correct = false;

            ArrayList<Integer> guessHistory = new ArrayList<>();

            // Start timer
            long startTime = System.currentTimeMillis();

            // Main game loop
            while (attempts < maxAttempts) {

                String input = JOptionPane.showInputDialog(
                        null,
                        "====================================\n"
                        + "          GAME IN PROGRESS\n"
                        + "====================================\n\n"
                        + "Difficulty       : " + difficultyName + "\n"
                        + "Number Range     : 1 - " + maxNumber + "\n"
                        + "Attempts Left    : " + (maxAttempts - attempts) + "\n"
                        + "Current Score    : " + score + "\n"
                        + "Hints Available  : " + (maxHints - hintsUsed)
                        + "\n\n"
                        + "Enter your guess:",
                        "Enter Guess",
                        JOptionPane.QUESTION_MESSAGE
                );

                // Cancel button
                if (input == null) {

                    JOptionPane.showMessageDialog(
                            null,
                            "The game has been cancelled.",
                            "Game Cancelled",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    return;
                }

                int guess;

                // Check whether input is a number
                try {
                    guess = Integer.parseInt(input);
                } 
                catch (NumberFormatException e) {

                    JOptionPane.showMessageDialog(
                            null,
                            "ERROR: Please enter a valid number.",
                            "Invalid Input",
                            JOptionPane.ERROR_MESSAGE
                    );

                    continue;
                }

                // Check range
                if (guess < 1 || guess > maxNumber) {

                    JOptionPane.showMessageDialog(
                            null,
                            "ERROR: Invalid guess.\n\n"
                            + "Please enter a number between 1 and "
                            + maxNumber + ".",
                            "Invalid Guess",
                            JOptionPane.WARNING_MESSAGE
                    );

                    continue;
                }

                // Record attempt
                attempts++;
                guessHistory.add(guess);

                // Correct answer
                if (guess == secretNumber) {

                    correct = true;

                    long endTime = System.currentTimeMillis();

                    long timeTaken = (endTime - startTime) / 1000;

                    // Bonus score for fewer attempts
                    int bonus = (maxAttempts - attempts) * 50;

                    score += bonus;

                    // Difficulty bonus
                    if (difficulty == 1) {
                        score += 100;
                    } 
                    else if (difficulty == 2) {
                        score += 250;
                    }

                    JOptionPane.showMessageDialog(
                            null,
                            "====================================\n"
                            + "          CONGRATULATIONS!\n"
                            + "====================================\n\n"
                            + "You guessed the correct number!\n\n"
                            + "Secret Number : " + secretNumber + "\n"
                            + "Difficulty    : " + difficultyName + "\n"
                            + "Attempts      : " + attempts + "\n"
                            + "Time Taken    : " + timeTaken + " seconds\n"
                            + "Final Score   : " + score + "\n\n"
                            + "Guess History:\n"
                            + guessHistory,
                            "YOU WON!",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    break;
                }

                // Deduct score for wrong guess
                score -= 50;

                if (score < 0) {
                    score = 0;
                }

                // Give high / low feedback
                String message;

                if (guess > secretNumber) {
                    message = "Your guess is TOO HIGH.";
                } 
                else {
                    message = "Your guess is TOO LOW.";
                }

                // Give hint after third attempt
                if (attempts == 3 && hintsUsed < maxHints) {

                    String hint;

                    if (secretNumber % 2 == 0) {
                        hint = "Hint: The secret number is EVEN.";
                    } 
                    else {
                        hint = "Hint: The secret number is ODD.";
                    }

                    message += "\n\n" + hint;

                    hintsUsed++;
                }

                // Second hint when few attempts remain
                if (attempts == maxAttempts - 2
                        && hintsUsed < maxHints) {

                    int lowerRange = Math.max(1, secretNumber - 10);
                    int upperRange = Math.min(maxNumber, secretNumber + 10);

                    message += "\n\n"
                            + "Hint: The number is between "
                            + lowerRange + " and " + upperRange + ".";

                    hintsUsed++;
                }

                // Show result of guess
                JOptionPane.showMessageDialog(
                        null,
                        message
                        + "\n\n"
                        + "Attempts Left : "
                        + (maxAttempts - attempts)
                        + "\n"
                        + "Current Score : "
                        + score,
                        "Guess Result",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

            // Game Over
            if (!correct) {

                long endTime = System.currentTimeMillis();

                long timeTaken = (endTime - startTime) / 1000;

                JOptionPane.showMessageDialog(
                        null,
                        "====================================\n"
                        + "              GAME OVER\n"
                        + "====================================\n\n"
                        + "You have used all your attempts.\n\n"
                        + "The correct number was: "
                        + secretNumber + "\n\n"
                        + "Difficulty  : " + difficultyName + "\n"
                        + "Attempts    : " + attempts + "\n"
                        + "Time Taken  : " + timeTaken + " seconds\n"
                        + "Final Score : " + score + "\n\n"
                        + "Guess History:\n"
                        + guessHistory,
                        "Game Over",
                        JOptionPane.ERROR_MESSAGE
                );
            }

            // Play again
            int choice = JOptionPane.showConfirmDialog(
                    null,
                    "Would you like to play another game?",
                    "Play Again",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice != JOptionPane.YES_OPTION) {

                playAgain = false;

                JOptionPane.showMessageDialog(
                        null,
                        "====================================\n"
                        + "        THANK YOU FOR PLAYING!\n"
                        + "====================================",
                        "Goodbye",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        }
    }
}