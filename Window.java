import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class Window {
    public static int width, height;
    public static int TURN_SPEED = 30;
    public static int RANGE = 20;
    private final int AGENTS = 250;
    public static float BLUR = 0.5f;
    public static float EVAP = 0.5f;
    public static int AGENT_STRENGTH = 255;
    public static boolean FOLLOW = true;


    private final Agent[] agents;
    private final Picture map;
    Random r = new Random();

    public Window(int width, int height){
        Window.width = width;
        Window.height = height;
        agents = new Agent[AGENTS];
        fillAgents();
        map = new Picture(width,height);
    }

    private void fillAgents(){
        for (int i = 0; i < agents.length; i++) {
            agents[i] = new Agent(width/2, height/2,  r.nextInt(360));

            //agents[i] = new Agent(r.nextInt(width), r.nextInt(height), r.nextInt(360));
        }
    }

    private void diffuse(){
        map.diffuse();
    }

    public void loop(){
        while (true){
            //long lastTime = System.nanoTime();
            CompletableFuture.runAsync(new Runnable() {
                @Override
                public void run() {
                    for (Agent agent : agents){
                        agent.update(map, 1f);
                    }
                }
            });

            //System.out.println((double)(System.nanoTime()-lastTime)/1_000_000.0);
            diffuse();
            Canvas.getInstance().nextImage(map);
        }
    }


    public static void main(String[] args) {
        Gui g = new Gui();

        Window w = new Window(320,180);
        w.loop();
    }
}
