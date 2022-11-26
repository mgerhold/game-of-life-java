package com.iu.gameoflife;

import java.util.Random;

import com.iu.display.Display;

public class Main {
    private static boolean[][] createRandomPixels(final int width, final int height) {
        final var pixels = new boolean[height][width];
        final var random = new Random();
        for (int y = 0; y < pixels.length; ++y) {
            for (int x = 0; x < pixels[0].length; ++x) {
                pixels[y][x] = random.nextBoolean();
            }
        }
        return pixels;
    }

    private static int countLivingNeighbors(final boolean[][] pixels, final int centerX, final int centerY) {
        final int height = pixels.length;
        final int width = pixels[0].length;
        int result = 0;
        for (int y = centerY - 1; y <= centerY + 1; ++y) {
            for (int x = centerX - 1; x <= centerX + 1; ++x) {
                if (x == centerX && y == centerY) {
                    continue;
                }
                final int currentX = Math.floorMod(x, width);
                final int currentY = Math.floorMod(y, height);
                if (pixels[currentY][currentX]) {
                    ++result;
                }
            }
        }
        return result;
    }

    private static boolean[][] makeStep(final boolean[][] pixels) {
        final int height = pixels.length;
        final int width = pixels[0].length;

        final var newPixels = new boolean[height][width];
        for (int y = 0; y < pixels.length; ++y) {
            for (int x = 0; x < pixels[0].length; ++x) {
                final int livingNeighbors = countLivingNeighbors(pixels, x, y);
                if (livingNeighbors == 3 || (pixels[y][x] && livingNeighbors == 2)) {
                    newPixels[y][x] = true;
                }
            }
        }
        return newPixels;
    }

    public static void main(String[] args) {
        final int WIDTH = 400;
        final int HEIGHT = 400;
        final int FRAMES_PER_SECOND = 30;
        final var display = new Display("Game of Life", WIDTH, HEIGHT, 2);
        var pixels = createRandomPixels(WIDTH, HEIGHT);

        while (true) {
            display.update(pixels);
            pixels = makeStep(pixels);
            try {
                Thread.sleep(1000 / FRAMES_PER_SECOND);
            } catch (Exception e) {
                break;
            }
        }
    }
}
