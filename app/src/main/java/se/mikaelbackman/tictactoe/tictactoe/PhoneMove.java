package se.mikaelbackman.tictactoe.tictactoe;


import java.util.Random;

public class PhoneMove {
    public PhoneMove() {
    }

    // Just makes an random move. The code itself works for any N*N playing field.
    public int[] phoneMoveRandom(PlayActivity.GameState[][] game, int size) {
        Random random = new Random();
        int x = random.nextInt(size);
        int y = random.nextInt(size);
        // If the square is already taken, then a new will be randomized.
        while ((game[x][y] == PlayActivity.GameState.X) || (game[x][y] == PlayActivity.GameState.O)) {
            x = random.nextInt(size);
            y = random.nextInt(size);
        }
        int[] vec = new int[3];
        vec[1] = x;
        vec[2] = y;

        // Determine squarenumber to be changed and is reported back in the format:
        // vec[0] = squarenumber
        // vec[1] = x
        // vec[2] = y
        vec[0] = ((y * size) + x);
        return vec;
    }
}
