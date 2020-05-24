import java.util.Random;

public class Color {
    public int R, G, B;

    public Color(int r, int g, int b) {
        R = r;
        G = g;
        B = b;
    }

    public Color randomize(){
        int newR = (int) (R + (new Random().nextFloat()-0.5f)*10);
        int newG = (int) (G + (new Random().nextFloat()-0.5f)*10);
        int newB = (int) (B + (new Random().nextFloat()-0.5f)*10);
        return new Color(newR, newG, newB);
    }

    public Color randomizeByPerlin(float noise){
        int newR = (int) (R + (noise-0.5f)*300);
        int newG = (int) (G + (noise-0.5f)*300);
        int newB = (int) (B + (noise-0.5f)*300);
        return new Color(newR, newG, newB);
    }
}