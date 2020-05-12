import parcs.AMInfo;
import parcs.channel;
import parcs.point;
import parcs.task;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        task curtask = new task();
        curtask.addJarFile("MarchingParallel.jar");

        AMInfo info = new AMInfo(curtask, null);
        Scanner sc = new Scanner(new File(curtask.findFile("input.txt")));

        int w = 512;
        int h = 512;

        List<Integer> xs = new ArrayList<>();
        List<Integer> ys = new ArrayList<>();

        List<List<Integer>> data_x = new ArrayList<>();
        List<List<Integer>> data_y = new ArrayList<>();

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                xs.add(i);
                ys.add(j);
                if (xs.size() > 4096) {
                    data_x.add(xs);
                    data_y.add(ys);
                    xs = new ArrayList<>();
                    ys = new ArrayList<>();
                }
            }
        }

        List<channel> channels = new ArrayList<>();

        for (int i = 0; i < data_x.size(); i++) {
            point p = info.createPoint();
            channel c = p.createChannel();
            p.execute("MarchingParallel");
            c.write(w);
            c.write(h);
            c.write((Serializable) data_x.get(i));
            c.write((Serializable) data_y.get(i));
            channels.add(c);
        }

        BufferedImage image = Marching.renderer(w, h);

        for (int i = 0; i < channels.size(); i++) {
            List<Integer> color = (List<Integer>) channels.get(i).readObject();
            for (int j = 0; j < data_x.size(); j++) {
                int rgb = (color.get(j) << 16) | (color.get(j) << 8) | color.get(j);
                image.setRGB(data_x.get(i).get(j), data_y.get(i).get(j), rgb);
            }
        }

        curtask.end();

        File outputfile = new File("image.jpg");
        ImageIO.write(image, "jpg", outputfile);
    }

}
