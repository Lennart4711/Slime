import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Gui extends JFrame implements ChangeListener {

    JSlider range;
    JSlider turn;
    JSlider evap;
    JSlider blur;
    JSlider strength;
    JSlider follow;



    public Gui() {

        setTitle("Control Panel");
        setSize(400, 400);
        JPanel panel = new JPanel();


        range = new JSlider(JSlider.HORIZONTAL, 0,20,15);
        range.setMajorTickSpacing(10);
        range.setMinorTickSpacing(1);
        range.setPaintTicks(true);
        range.addChangeListener(this);

        turn = new JSlider(JSlider.HORIZONTAL, 0,100,15);
        turn.setMajorTickSpacing(25);
        turn.setMinorTickSpacing(10);
        turn.setPaintTicks(true);
        turn.addChangeListener(this);

        evap = new JSlider(JSlider.HORIZONTAL, 0,100,1);
        evap.setMajorTickSpacing(10);
        evap.setMinorTickSpacing(1);
        evap.setPaintTicks(true);
        evap.addChangeListener(this);

        blur = new JSlider(JSlider.HORIZONTAL, 0,100,1);
        blur.setMajorTickSpacing(10);
        blur.setMinorTickSpacing(1);
        blur.setPaintTicks(true);
        blur.addChangeListener(this);

        strength = new JSlider(JSlider.HORIZONTAL, 0,255,200);
        strength.setMajorTickSpacing(10);
        strength.setMinorTickSpacing(1);
        strength.setPaintTicks(true);
        strength.addChangeListener(this);

        follow = new JSlider(JSlider.HORIZONTAL, 0,1,1);
        follow.setMajorTickSpacing(1);
        follow.setMinorTickSpacing(1);
        follow.setPaintTicks(true);
        follow.addChangeListener(this);

        // Add button to JPanel
        panel.add(range);
        panel.add(turn);
        panel.add(evap);
        panel.add(blur);
        panel.add(strength);
        panel.add(follow);
        // And JPanel needs to be added to the JFrame itself!
        this.getContentPane().add(panel);

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (range.equals(e.getSource())) {
            Window.RANGE = range.getValue();
        }else if (turn.equals(e.getSource())){
            Window.TURN_SPEED = turn.getValue();
        }else if (evap.equals(e.getSource())){
            Window.EVAP = evap.getValue()/10;
        }else if (blur.equals(e.getSource())){
            Window.BLUR = ((float) blur.getValue())/100;
        }else if (strength.equals(e.getSource())){
            Window.AGENT_STRENGTH = strength.getValue();
        }else if (follow.equals(e.getSource())){
            if (follow.getValue() == 1){
                Window.FOLLOW = true;
            }else Window.FOLLOW = false;
        }
    }
}