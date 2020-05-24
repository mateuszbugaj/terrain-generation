import processing.core.PApplet;
import processing.core.PVector;
import processing.event.MouseEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Main extends PApplet {
    public static void main(String[] args) {
        PApplet.main("Main", args);
    }

    public void settings(){
        size(800,800, P3D);
    }


    float zoom = 1f;
    PVector cameraPosition = new PVector();
    PVector centerPosition = new PVector();
    PVector previousMousePosition = new PVector();

    List<Rectangle> rectangles = new ArrayList<>();
    HashSet<PVector> positions = new HashSet<>();

    int renderDistance = 1600;
    float offSetStep = 0.002f;

    public void setup(){
        Rectangle.p = this;
        rectMode(CENTER);
        cameraPosition.set(width/2f - 1000, height/2f - 1000);
        textSize(20);
    }

    public void draw(){
        background(255);
        centerPosition.set(-cameraPosition.x+width/2f, -cameraPosition.y+height/2f);

        pushMatrix();
        rotateX(PI/6);
        translate(0,-300, -300);

        translate(width/2f, height/2f);
        scale(zoom);
        translate(-width/2f, -height/2f);
        translate(cameraPosition.x, cameraPosition.y);

        generate(renderDistance, centerPosition);

//        rectangles.stream().filter(rect -> rect.position.dist(centerPosition)<renderDistance).forEach(Rectangle::show);
        rectangles.removeIf(rect -> rect.position.dist(centerPosition)>renderDistance);
        positions.removeIf(pos -> pos.dist(centerPosition)>renderDistance);
        rectangles.forEach(Rectangle::show);

        popMatrix();

        keyPressing();

//        fill(0);
//        text("Plates in memory: " + rectangles.size(), 0, 20);
//        text("FPS: " + (int)frameRate, 0, 40);
    }

    public float round(float value1, float value2){
        if(value1%value2 < value2/2){
            value1 = (int)(value1/value2)*value2;
        } else {
            value1 = (int)(value1/value2)*value2 + value2;
        }
        return value1;
    }

    public void generate(float area, PVector centerPosition){

        float rowsOfRectanglesToConsider = (area / Rectangle.SIZE)*2;
        for (int col = 0; col < rowsOfRectanglesToConsider; col++) {
            for (int row = 0; row < rowsOfRectanglesToConsider; row++) {

                PVector pos = new PVector();
                pos.x = round(centerPosition.x - (rowsOfRectanglesToConsider/2)*Rectangle.SIZE + col*Rectangle.SIZE, Rectangle.SIZE);
                pos.y = round(centerPosition.y - (rowsOfRectanglesToConsider/2)*Rectangle.SIZE + row*Rectangle.SIZE, Rectangle.SIZE);


                if(pos.dist(centerPosition)<area) {

                    if(!positions.contains(pos)){
                        float perlinX = pos.x * offSetStep;
                        float perlinY = pos.y * offSetStep;
                        float noise = noise(perlinX, perlinY);
                        Rectangle rectangle = new Rectangle(pos, noise);
                        rectangle.noiseValue = noise;

                        rectangles.add(rectangle);
                        positions.add(pos);
                    }
                }
            }
        }
    }

    public void keyPressing(){
        if(keyPressed){
            if(key == 'd'){
                cameraPosition.x-=20;
            }

            if(key == 'a'){
                cameraPosition.x+=20;
            }

            if(key == 'w'){
                cameraPosition.y+=20;
            }

            if(key == 's') {
                cameraPosition.y -= 20;
            }
        }
    }

    public void mousePressed(MouseEvent event){
        if(event.getButton()==LEFT){
            previousMousePosition.set(mouseX, mouseY);
        }
    }

    public void mouseDragged(MouseEvent event){
        if(event.getButton()==LEFT){
            PVector mouseDisplacement = new PVector(mouseX - previousMousePosition.x, mouseY - previousMousePosition.y);
            cameraPosition = cameraPosition.add(mouseDisplacement.div(zoom));
            previousMousePosition.set(mouseX, mouseY);
        }
    }

    public void mouseReleased(MouseEvent event){
        if(event.getButton()==LEFT){
            //generate();
        }
    }

    public void mouseWheel(MouseEvent event){
        zoom += event.getCount()/10f;
        if(zoom<0.3){
            zoom = 0.3f;
        }
        if(zoom>3){
            zoom=3;
        }

        //generate();
    }


}
