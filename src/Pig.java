import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import static java.lang.System.*;

/*
 * The Pig game
 * See http://en.wikipedia.org/wiki/Pig_%28dice_game%29
 *
 */
public class Pig {

    public static void main(String[] args) {
        new Pig().program();
    }

    // The only allowed instance variables (i.e. declared outside any method)
    // Accessible from any method
    final Scanner sc = new Scanner(in);
    final Random rand = new Random();

    void program() {
        final int winPts = 20;    // Points to win
        Player[] players;         // The players (array of Player objects)
        Player actual;            // Actual player for round (must use)
        boolean aborted = false;  // Game aborted by player?

        int nPlayers = numberOfPlayers(); // Asks for number of players in the game.
        players = new Player[nPlayers]; //Creates an empty array of type Player.
        players = getPlayers(players, nPlayers);  // Method to read in all players

        welcomeMsg(winPts);
        statusMsg(players);

        for (int i = 0; i < players.length; i++) { //Go through each players turn
            String action = "r"; //Is this needed?
            actual = players[i];
            action = getPlayerChoice(actual);
            actual.roundPts = 0;
            int pts = 0; //Is this needed?

            while (action.equals("r")) { //When the player chooses to roll die.
                // Can use switch instead.
                pts = RollDie();

                if (pts != 1) { //Adds points to round points
                    actual.roundPts = pts + actual.roundPts;
                    roundMsg(pts, actual);

                    if (actual.roundPts + actual.totalPts > winPts - 1) { //Checks if player has won.
                        gameOverMsg(actual, aborted);
                        i = players.length;
                        action = "n";

                    } else {
                        action = getPlayerChoice(actual); // Asks for next action
                    }

                } else { // Lose all points if the die shows 1 and moves on to next player.
                    actual.roundPts = 0;
                    action = "n";
                    roundMsg(pts, actual); //TODO Lose all points when pts = 1.
                }

            }

            actual.totalPts = actual.totalPts + actual.roundPts; //Adds round points to the total score.

            if (i == players.length - 1) { // When last player has played, return to first player.
                i = -1;
            }
            //i = (i + 1) % players.length; Replace if with modulus?

            statusMsg(players);

            if (action.equals("q")) { //Stops program if player choose to quit.
                i = players.length; //Not really happy with this. Write break instead, set action = q.
                aborted = true;
                gameOverMsg(actual, aborted);

            }
        }
        // TODO Game logic, using small step, functional decomposition

    }

    // ---- Game logic methods --------------
    int RollDie() { //Rolls the die
        return rand.nextInt(6) + 1;
    }

    // ---- IO methods ------------------

    void welcomeMsg(int winPoints) { //Shows welcome message.
        out.println("Welcome to PIG!");
        out.println("First player to get " + winPoints + " points will win!");
        out.println("Commands are: r = roll , n = next, q = quit");
        out.println();
    }

    void statusMsg(Player[] players) { // Shows the points for all players.
        out.print("Points: ");
        for (int i = 0; i < players.length; i++) {
            out.print(players[i].name + " = " + players[i].totalPts + " ");
        }
        out.println();
    }

    void roundMsg(int result, Player actual) { // Shows current round points.
        if (result > 1) {
            out.println("Got " + result + " running total are " + actual.roundPts);
        } else {
            out.println("Got 1 lost it all!");
        }
    }

    void gameOverMsg(Player player, boolean aborted) { // Messages when game is over.
        if (aborted) {
            out.println("Aborted");
        } else {
            out.println("Game over! Winner is player " + player.name + " with "
                    + (player.totalPts + player.roundPts) + " points");
        }
    }

    String getPlayerChoice(Player player) { // Chooses player and asks player to choose an action.
        out.print("Player is " + player.name + " > ");
        String action = sc.next(); // TODO Why do we get a problem when we have nextLine here instead of just next?
        return action;
    }

    Player[] getPlayers(Player[] players, int nPlayers) { //Creates the players
        // TODO

        int i = 0;
        for (i = 0; i < nPlayers; i++) {
            out.println("Enter name of player: ");
            String namn = sc.next();
            players[i] = new Player(namn); //TODO Question: What exactly happens here?

        }
        return players;
    }

    int numberOfPlayers() { // Asks for number of players
        out.println("Enter number of players:");
        int nPlayers = sc.nextInt();
        return nPlayers;
    }


    // ---------- Class -------------
// Class representing the concept of a player
    class Player {
        String name;     // Default null
        int totalPts;    // Total points for all rounds, default 0
        int roundPts;    // Points for a single round, default 0

        Player(String name) {
            this.name = name;
        }

    }

    // ----- Testing -----------------
    // Here you run your tests i.e. call your game logic methods
    // to see that they really work (IO methods not tested here)
    void test() {
        // This is hard coded test data, an array of Players 
        Player[] players = {new Player("a"), new Player("b"), new Player("c")};


        exit(0);   // End program
    }
}



