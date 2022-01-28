package win_lab;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class WinLab extends Frame implements ActionListener {
    private final Button exitBtn = new Button("Exit");
    private final Button searchBtn = new Button("Search");
    private final TextArea textArea = new TextArea();

    public WinLab() {
        super("my window");
        setLayout(null);
        setBackground(new Color(157, 204, 255, 255));
        setSize(450, 250);
        add(exitBtn);
        add(searchBtn);
        add(textArea);
        exitBtn.setBounds(300, 190, 100, 20);
        exitBtn.addActionListener(this);
        searchBtn.setBounds(110, 165, 100, 20);
        searchBtn.addActionListener(this);

        textArea.setBounds(20, 50, 300, 100);
        this.show();
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == exitBtn) {
            System.exit(0);
        } else if (actionEvent.getSource() == searchBtn) {
            String[] keywords = textArea.getText().split(",");
            for (String keyword : keywords) {
                System.out.println(keyword);
            }
            File f = new File("//Users//ilarodkin//Desktop//Учеба//AiPRP//labs//src//source_html"); //папка с файлами
            ArrayList<File> files = new ArrayList<>(Arrays.asList(Objects.requireNonNull(f.listFiles())));

            textArea.setText("");
            int maxCountOfKeys = 0;
            File openedFile = null;
            for (File elem : files) {

                int countOfKeys = searchCount(elem, keywords);
                if (maxCountOfKeys < countOfKeys) {
                    maxCountOfKeys = countOfKeys;
                    openedFile = elem;
                }
                textArea.append("\n" + elem + "  :" + countOfKeys);
            }
            try {
                if (openedFile != null) {
                    Desktop.getDesktop().browse(openedFile.toURI());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static int searchCount(File curFile, String[] keywords) {
        int count = 0;
        int i;
        try {
            URL url = new URL("file:/" + curFile);

            URLConnection connection = url.openConnection();
            BufferedInputStream inputStream = new BufferedInputStream(connection.getInputStream());
            StringBuilder builder = new StringBuilder(); //file content in byte array
            while ((i = inputStream.read()) != -1) {
                builder.append((char) i);
            }
            inputStream.close();
            String htmlContent = (builder.toString()).toLowerCase(); //file content in string
            System.out.println("New url content is: " + htmlContent);
            for (String keyword : keywords) {
                if (htmlContent.contains(keyword.trim().toLowerCase()))
                    count++;
            }
        } catch (Exception malformedInputException) {
            System.out.println("error " + malformedInputException.getMessage());
            return -1;
        }
        return count;
    }


    public static void main(String[] args) {

        new WinLab();


    }

}