import parcs.AM;
import parcs.AMInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MarchingParallel implements AM {
    @Override
    public void run(AMInfo info) {
        int width = info.parent.readInt();
        int height = info.parent.readInt();
        List<Integer> xs = (List<Integer>) info.parent.readObject();
        List<Integer> ys = (List<Integer>) info.parent.readObject();
        int n = xs.size();
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            double depth = Marching.march(xs.get(i) - width / 2, ys.get(i) - height / 2);
            int color = (int) ((Math.exp(-depth / 125.0)) * 255);
            res.add(color);
        }
        info.parent.write((Serializable) res);
    }
}
