package com.company;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main extends JFrame{
    public static void main(String[] args) {


        Main frame = new Main();
        frame.setSize(300,200);
        frame.setTitle("Pogoda");
        JButton btnPick = new JButton("Wybierz");
        JTextArea txtCity = new JTextArea("<wpisz miasto>");
        JTextArea labelData = new JTextArea("");
        JLabel fillCity = new JLabel("Wpisz miasto:");


        btnPick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

        try {
            String getCity = txtCity.getText();
            String url = "http://api.openweathermap.org/data/2.5/weather?q="+getCity+"&APPID=" + readFile("src/secret.txt", Charset.defaultCharset());
            Data data = ParseJSON.parse(GetJSON.downloadJSON(url));
            System.out.println(data);
            labelData.setText(data.toString());

        } catch (IOException e) {
            labelData.setText("Źle podana nazwa miasta");
            e.printStackTrace();
        }

            }
        });

        JPanel content = new JPanel();
        content.setLayout(new MigLayout("","[grow]","[grow][fill][][]"));

        frame.getSize();
        content.add(labelData,"cell 0 0, growx");
        content.add(fillCity,"flowx, cell 0 1, align left, aligny center");
        content.add(txtCity,"flowx, cell 0 1, alignx left, aligny center");
        content.add(btnPick,"cell 0 2, growx");
        frame.add(content);
        frame.setVisible(true);
    }

    static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

}


