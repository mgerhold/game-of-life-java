package com.iu.display;

import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.Color;

public class Display {
    private BufferedImage image;
    private int width;
    private int height;
    private int scale;
    private JFrame frame;

    public Display(final String windowTitle, final int width, final int height, final int scale) {
        if (width < 100 || height < 100) {
            throw new IllegalArgumentException();
        }
        image = new BufferedImage(width * scale, height * scale, BufferedImage.TYPE_INT_RGB);
        this.width = width;
        this.height = height;
        this.scale = scale;
        frame = new JFrame(windowTitle);

        javax.swing.SwingUtilities.invokeLater(
            new Runnable() {
                public void run() {
                    showWindow("Game of Life");
                }
            }
        );
    }

    private void showWindow(final String windowTitle) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final var imageLabel = new JLabel(new ImageIcon(image));

        frame.getContentPane().add(imageLabel);
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void update(final boolean[][] pixels) {
        if (pixels == null || pixels.length != height || pixels[0] == null || pixels[0].length != width) {
            throw new IllegalArgumentException();
        }
        final int black = Color.BLACK.getRGB();
        final int white = Color.WHITE.getRGB();

        for (int y = 0; y < pixels.length; ++y) {
            for (int x = 0; x < pixels[0].length; ++x) {
                final int color = pixels[y][x] ? black : white;
                for (int scaleX = 0; scaleX < scale; ++scaleX) {
                    for (int scaleY = 0; scaleY < scale; ++scaleY) {
                        image.setRGB(x * scale + scaleX, y * scale + scaleY, color);
                    }
                }
            }
        }
        frame.repaint();
    }
}
