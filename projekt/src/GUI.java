import com.toedter.calendar.JCalendar;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.*;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class GUI {
    static JButton b, b1, b2;
    static JLabel l;
    Date selectedDate;
    String selectedCity;
    String cities[] = {"Wroclaw", "Warsaw", "London","Mexico"};


    public GUI() {
        JLabel city = new JLabel("Wybrane miasto: " + selectedCity, SwingConstants.CENTER);

        JFrame f = new JFrame();
        l = new JLabel("Wybierz miasto:");


        JPanel p = new JPanel();
        p.add(l);
        JComboBox cb = createCityList(cities);
        p.add(cb);
        b = new JButton("potwierdź");
        p.add(b);
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                selectedCity = cb.getItemAt(cb.getSelectedIndex()).toString();
                city.setText("Wybrane miasto: "+ selectedCity);
                XYDataset ds = createDataset();
                ChartPanel cp = new ChartPanel(createChart(ds));
                f.getContentPane().add(cp, BorderLayout.PAGE_END);
            }
        });

        JCalendar cal = new JCalendar();
        JLabel label = new JLabel("label", SwingConstants.CENTER);
        label.setText(cal.getDate().toString());
        cal.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                selectedDate = cal.getDate();
                label.setText("Wybrany dzień: " + dateFormat.format(selectedDate).toString());
            }
        });

        JPanel pp = new JPanel();
        JButton addCity = new JButton("dodaj miasto");
        addCity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                f.repaint();
            }
        });

        pp.add(addCity);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(p);
        panel.add(cal);
        panel.add(pp);


        f.setSize(400, 700);
        f.setVisible(true);
        XYDataset ds = createDataset();

        ChartPanel cp = new ChartPanel(createChart(ds));
        f.getContentPane().add(cp, BorderLayout.PAGE_END);
//        f.add(p, BorderLayout.NORTH);
        panel.add(city);

        panel.add(label);
        f.add(panel);

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
             @Override
             public void run() {
                 XYDataset ds = createDataset();
                 ChartPanel cp = new ChartPanel(createChart(ds));
                 f.getContentPane().add(cp, BorderLayout.PAGE_END);
             }
         };

         timer.schedule(task, 36000,36000);

    }

    private static JComboBox createCityList(String cities[]) {
        JComboBox cb = new JComboBox(cities);
        return cb;
    }

    static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }


    private XYDataset createDataset() {
    try {
        if (selectedCity != null) {
            String url = "http://api.openweathermap.org/data/2.5/weather?q=" + selectedCity + "&APPID=" + readFile("/home/arch/java/secret.txt", Charset.defaultCharset());
            com.company.ParseJSON.parse(com.company.GetJSON.downloadJSON(url));
            //System.out.println(data);
        }
    }
    catch (IOException e) {
        e.printStackTrace();
    }

        final TimeSeries series = new TimeSeries("Data", Hour.class);
        final Day today = new Day();
        try(Connection conn = DriverManager.getConnection("jdbc:sqlite:temp.db"))
        {
            Statement statement = conn.createStatement();
            String initDB = "CREATE TABLE IF NOT EXISTS temps(\n"
                    + " id INTEGER PRIMARY KEY    AUTOINCREMENT,\n"
                    + " city           TEXT    NOT NULL,\n"
                    + " temp           FLOAT    NOT NULL,\n"
                    + " temp_time      TEXT    NOT NULL);";
            statement.execute(initDB);
            ResultSet rs = statement.executeQuery("select * from temps where city='"+selectedCity+"'");
            while(rs.next())
            {
                //System.out.println(rs.getInt("id") + " " + rs.getString("city") + " " +rs.getFloat("temp")+" " + rs.getString("temp_time"));
                float temp = rs.getFloat("temp");
                String string = rs.getString("temp_time");

                String[] datetime = string.split(" ");
                String[] dateparts = datetime[0].split("-");
                int year = Integer.valueOf(dateparts[0]); // 004
                int month = Integer.valueOf(dateparts[1]);
                int day = Integer.valueOf(dateparts[2]);
                String[] time = datetime[1].split(":");
                int hour = Integer.valueOf(time[0]);
                System.out.println(hour+" "+day+"/"+month+"/"+year+" "+ temp);
                series.addOrUpdate(new Hour(hour,new Day(day,month,year)),temp);
            }

            // Dla jednej godziny może być przypisana tylko jedna wartość,
            // więc na początku dodaję losową liczbę, by od razu było widać działający gotowy wykres,
            // bez czekania aż uzupełni się baza danych.
            series.addOrUpdate(new Hour(2,today),33);


        }
        catch(SQLException e) {
            e.printStackTrace();
        }

        final TimeSeriesCollection dataset = new TimeSeriesCollection(series);
        return dataset;
    }

    private JFreeChart createChart(final XYDataset dataset) {
        final JFreeChart chart = ChartFactory.createTimeSeriesChart(
                null, // chart title
                "godz", // x axis label
                "temp", // y axis label
                dataset, // data
                false, // include legend
                true, // tooltips
                false // urls
        );
        return chart;
    }


}
