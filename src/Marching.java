import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class Marching {

    public static final int rays = 4;

    static double dist(double x, double y, double z) {
        x = (x - Math.floor(x)) * 2 - 1;
        y = (y - Math.floor(y)) * 2 - 1;
        z = (z - Math.floor(z)) * 2 - 1;
        return Math.sqrt(x * x + y * y + z * z) - 0.35;
    }

    static BufferedImage renderer(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, TYPE_INT_RGB);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double depth = march(i - width / 2, j - height / 2);
                int col = (int) ((Math.exp(-depth / 125.0)) * 255);
                int rgb = (col << 16) | (col << 8) | col;
                image.setRGB(i, j, rgb);
            }
        }
        return image;
    }

    static double march(int x, int y) {
        int summary = 0;
        for (int i = 0; i < rays; i++) {

            double ray_x = (x + Math.random() - 0.5) / 512.;
            double ray_y = (y + Math.random() - 0.5) / 512.;
            double ray_z = 1;

            double length = Math.sqrt(ray_x * ray_x + ray_y * ray_y + ray_z * ray_z);

            ray_x /= length;
            ray_y /= length;
            ray_z /= length;

            double cx = 0.7;
            double cy = 0.2;
            double cz = -2.8;

            for (int j = 0; j < 512; j++) {
                double d = dist(cx, cy, cz) * 0.1;
                cx += ray_x * d;
                cy += ray_y * d;
                cz += ray_z * d;
                if (d < 1e-4) {
                    break;
                }
                summary++;
            }
        }
        return summary * 1.0 / rays;
    }
}
