import javax.swing.*;
import java.util.Vector;

public class FileMenu extends JMenu {

    FileMenu(Vector<String> cities, JComboBox comboBox) {
        this.setText("Plik");
        this.add(new AddCityItem("Dodaj Miasto", cities, comboBox));
    }


    private class AddCityItem extends JMenuItem {
        Vector<String> cities;
        JComboBox comboBox;

        AddCityItem(String text, Vector<String> cities, JComboBox comboBox) {
            this.setText(text);
            this.cities = cities;
            this.comboBox = comboBox;
            this.addActionListener(event -> {
                String result = JOptionPane.showInputDialog(null, "Wpisz miasto");
                this.cities.add(result);
                this.comboBox = new JComboBox(this.cities);
            });
        }
    }
}
