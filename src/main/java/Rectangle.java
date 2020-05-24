import processing.core.PApplet;
import processing.core.PVector;

public class Rectangle {
    static PApplet p;
    PVector position;
    static float SIZE = 35;
    float noiseValue;
    Color color1, color2, color3;

    public Rectangle(PVector position, float noiseValue) {
        this.position = position;
        this.noiseValue = noiseValue;

        color1 = new Color(237, 159, 64).randomizeByPerlin(noiseValue);
        color2 = new Color(82, 40, 17).randomizeByPerlin(noiseValue);
        color3 = new Color(42, 184, 232).randomizeByPerlin(noiseValue);
    }

    public void show(){
        p.pushMatrix();
        p.translate(position.x, position.y);

        p.noStroke();

        if(noiseValue > 0.5){
            p.fill(color2.R, color2.G, color2.B);
            p.box(SIZE, SIZE, SIZE*5);
        }

        if(noiseValue <= 0.5 && noiseValue > 0.3){
            p.fill(color1.R, color1.G, color1.B);
            p.box(SIZE, SIZE, SIZE*2);
        }

        if(noiseValue <= 0.3){
            p.fill(color3.R, color3.G, color3.B);
            p.box(SIZE, SIZE, SIZE);
        }


        p.popMatrix();
    }
}
