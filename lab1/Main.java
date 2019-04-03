import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
// import javax.swing.Timer


public class Main {
    public static void main(String[] args){
        Timer timer = new Timer();
        Random random = new Random();
        MainWindow frame = new MainWindow();
        frame.button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0){
                frame.setDestX(random.nextInt(frame.getWidth() - 100));
                frame.setDestY(random.nextInt(frame.getHeight() - 40));
                timer.schedule(frame.task, 10, 10);
            }
        });

        frame.init();
    }
}


class MainWindow extends JFrame {
    static final long SerialVersionUID = 420L;
    static int counter = 0;
    int dest_x, dest_y;
    int step_x, step_y;

    public JButton button = new JButton("Click me!");
    public TimerTask task;
    JPanel content = new JPanel();


    void init(){
        this.setSize(400,300);
        this.setTitle("Jumping Button");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setVisible(true);
        this.button.setBounds(70,70,90,30);
        this.content.setLayout(null);
        this.content.add(button);
        this.add(content);
        addTimerTask();
    }

    void addTimerTask(){
        this.task = new TimerTask(){
            @Override
            public void run(){
                if (counter == 100){
                    counter = 0;
                    addTimerTask();
                    this.cancel();
                }

                ++counter;
                button.setBounds(
                        button.getBounds().x + step_x,
                        button.getBounds().y + step_y,
                        90, 30);
            }

        };
    }

    public JButton getButton(){
        return this.button;
    }

    public JPanel getContent(){
        return this.content;
    }

    public int getDestX(){
        return this.dest_x;
    }

    public int getDestY(){
        return this.dest_y;
    }

    public void setDestX(int arg){
        this.dest_x = arg;
        this.step_x = (dest_x - this.button.getBounds().x)/100;
    }

    public void setDestY(int arg){
        this.dest_y = arg;
        this.step_y = (dest_y - this.button.getBounds().y)/100;
    }
}
