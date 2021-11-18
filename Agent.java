import java.awt.*;
import java.util.Random;

public class Agent {
    public float x, y;
    public float angle;
    private Random r = new Random();

    public Agent(int x, int y, int angle){
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    private void placePheromone(Picture p){
        try {
            int color = Window.AGENT_STRENGTH;

            p.setColorAt((int) x, (int) y, new Color(color, color, color));
            /*p.setColorAt((int) x, (int) y+1, new Color(color, color, color));
            p.setColorAt((int) x+1, (int) y, new Color(color, color, color));
            p.setColorAt((int) x+1, (int) y+1, new Color(color, color, color));*/
        }catch (IndexOutOfBoundsException ie){}
    }

    private void move(float speed, Picture p){
        float xN = (float) Math.sin(2 * Math.PI * (angle / 360));
        float yN = (float) Math.cos(2 * Math.PI * (angle / 360));

        float newX = x + xN * speed;
        float newY = y + yN * speed;

        if (newX < 0 || newX >= Window.width || newY < 0 || newY >= Window.height){
            newX = (float) Math.min(Window.width - 0.01, Math.max(0, newX));
            newY = (float) Math.min(Window.height - 0.01, Math.max(0, newY));
            angle = new Random().nextInt(360);
        }
        x = newX;
        y = newY;
        if( Window.FOLLOW) {
            int fl = getSensoryData(45, speed, p);
            int f = getSensoryData(0, speed, p);
            int fr = getSensoryData(-45, speed, p);

            if (f > fl && f > fr) {
                return;
            } else if (f < fl && f < fr) {
                angle += (r.nextDouble() - 0.5) * Window.TURN_SPEED;
            } else if (fl < fr) {
                angle -= r.nextDouble() - Window.TURN_SPEED;
            } else if (fr < fl) {
                angle += r.nextDouble() - Window.TURN_SPEED;
            }
        }
    }

    private int getSensoryData(int turn, float speed, Picture p){
        float xN = (float) Math.sin(2 * Math.PI * ((angle-turn) / 360));
        float yN = (float) Math.cos(2 * Math.PI * ((angle-turn) / 360));
        float newX = x + xN * speed * Window.RANGE;
        float newY = y + yN * speed * Window.RANGE;
        try {
            return p.getColorAt((int) newX, (int) newY).getRed();
        }catch (IndexOutOfBoundsException ie){
            return 0;
        }
    }

    public void update(Picture p, float speed){
        move(speed, p);
        placePheromone(p);
    }
}
