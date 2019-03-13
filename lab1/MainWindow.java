import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainWindow extends JFrame {

    public static void main(String[] args) {

        MainWindow frame = new MainWindow();
        frame.setSize(400,300);
        frame.setTitle("Jumping Button");

        Random r = new Random();

        JButton c = new JButton("Click me!");
        c.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                c.setBounds(r.nextInt(frame.getWidth()-100),
                            r.nextInt(frame.getHeight()-40), 90, 30);
                c.setText("Click me!");
            }
        });

        JPanel content = new JPanel();
        content.setLayout(null);

        c.setBounds(70,70,90,30);
        content.add(c);
        frame.add(content);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(true);

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                c.setText("DO IT!");
                c.setBounds(c.getBounds().x+50,
                            c.getBounds().y+50, 90, 30);
            }
        };

        timer.schedule(task, 1000,2000);

    }
}
