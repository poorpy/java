import javax.swing.*;

import com.toedter.calendar.JCalendar;
import org.jfree.data.xy.XYDataset;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class GUI {
    static JButton b, b1, b2;
    static JLabel l;
    Date selectedDate;
    String selectedCity;
    String cities[] = {"Wroclaw", "Kraków", "Gdańsk"};


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

    }

    private static JComboBox createCityList(String cities[]) {
        JComboBox cb = new JComboBox(cities);
        return cb;
    }

    private static XYDataset createDataset() {
        final TimeSeries series = new TimeSeries("Data", Hour.class);
        final Day today = new Day();
        series.add(new Hour(1, today), 5.2);
        series.add(new Hour(2, today), 6.1);
        series.add(new Hour(3, today), 7.4);
        series.add(new Hour(4, today), 3.2);
        series.add(new Hour(7, today), 5.2);
        series.add(new Hour(8, today), 10.2);
        series.add(new Hour(12, today), 15.4);
        series.add(new Hour(16, today), 11.2);
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
