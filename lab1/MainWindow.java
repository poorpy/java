import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

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
    }
}
