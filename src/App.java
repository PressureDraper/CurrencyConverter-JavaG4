import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.simple.JSONObject;
import java.io.IOException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class App {

    static double FetchAPI(String localCurrency,String foreignCurrency) throws ParseException, IOException {
        //Setting link
        String link = String.format("https://v6.exchangerate-api.com/v6/f6495a38298023f4e0d50b89/latest/%s", localCurrency);
        URL url = new URL(link);

        //Making request
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.setRequestMethod("GET");
        request.connect();

        int responseCode = request.getResponseCode();

        // 200 success
        if (responseCode != 200) {
            throw new RuntimeException("API Connection failed: " + responseCode);
        }

        // Fetch info from API
        StringBuilder info = new StringBuilder();
        Scanner scanner = new Scanner(url.openStream());

        while (scanner.hasNext()) {
            info.append(scanner.nextLine());
        }

        //close scanner
        scanner.close();

        //convert to JSON
        JSONParser parse = new JSONParser();
        String singleString = info.toString();
        JSONObject json = (JSONObject) parse.parse(singleString);

        // Accessing object
        JSONObject json2 = (JSONObject) json.get("conversion_rates");

        double val = (double) json2.get(foreignCurrency);
        return val;
    }
    public static void main(String[] args) throws Exception {
        
        /*-----------------------------GRAPHIC INTERFACE-------------------------------------- */
        JFrame f = new JFrame(); //creating instance of JFrame 
        JButton b = new JButton("click"); //creating instance of JButton
        Image icon = Toolkit.getDefaultToolkit().getImage("img\\coin.png");
        JLabel l = new JLabel();
        JTextField tf = new JTextField();

        l.setBounds(110,10,200,20);
        l.setText("Introduce a value to convert:");
        l.setForeground(Color.black);
        f.add(l);

        tf.setBounds(115,40, 150,20);
        tf.setHorizontalAlignment(JTextField.CENTER);
        f.add(tf);

        b.setBounds(140,70,100, 25); //x axis, y axis, width, height 
        b.setText("Ok");
        f.add(b); //adding button in JFrame

        f.setTitle("Currency Converter");
        f.setIconImage(icon);
        f.setSize(400,150);
        f.setLocationRelativeTo(null);
        f.setResizable(false);
        f.setBackground(Color.black);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null); //using no layout managers

        f.setVisible(true);

        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String value = tf.getText();
                    double val = Double.parseDouble(value);
                    double res;
                    double ratio;
                    f.setVisible(false);
                    
                    String[] options = {"MXN to USD", "USD to MXN", "MXN to EUR", "EUR to MXN", "MXN to GBP", "GBP to MXN", "MXN to JPY", "JPY to MXN", "MXN to KRW", "KRW to MXN"};

                    ImageIcon icon = new ImageIcon("img\\coin.png");

                    String n = (String) JOptionPane.showInputDialog(null, "Select your value conversion",
                    "Conversion Values", JOptionPane.QUESTION_MESSAGE, icon, options, options[2]);

                    switch (n) {
                        case "MXN to USD":
                            ratio = FetchAPI("MXN","USD");
                            res = val * ratio;
                            JOptionPane.showMessageDialog(null,  String.format("%.2f MXN equals to %.2f USD.", val, res));

                            f.setVisible(true);
                            break;
                        case "USD to MXN":
                            ratio = FetchAPI("USD","MXN");
                            res = val * ratio;
                            JOptionPane.showMessageDialog(null,  String.format("%.2f USD equals to %.2f MXN.", val, res));

                            f.setVisible(true);
                            break;
                        case "MXN to EUR":
                            ratio = FetchAPI("MXN","EUR");
                            res = val * ratio;
                            JOptionPane.showMessageDialog(null,  String.format("%.2f MXN equals to %.2f EUR.", val, res));

                            f.setVisible(true);
                            break;
                        case "EUR to MXN":
                            ratio = FetchAPI("EUR","MXN");
                            res = val * ratio;
                            JOptionPane.showMessageDialog(null,  String.format("%.2f EUR equals to %.2f MXN.", val, res));

                            f.setVisible(true);
                            break;
                        case "MXN to GBP":
                            ratio = FetchAPI("MXN","GBP");
                            res = val * ratio;
                            JOptionPane.showMessageDialog(null,  String.format("%.2f MXN equals to %.2f GBP.", val, res));

                            f.setVisible(true);
                            break;
                        case "GBP to MXN":
                            ratio = FetchAPI("GBP","MXN");
                            res = val * ratio;
                            JOptionPane.showMessageDialog(null,  String.format("%.2f GBP equals to %.2f MXN.", val, res));

                            f.setVisible(true);
                            break;
                        case "MXN to JPY":
                            ratio = FetchAPI("MXN","JPY");
                            res = val * ratio;
                            JOptionPane.showMessageDialog(null,  String.format("%.2f MXN equals to %.2f JPY.", val, res));

                            f.setVisible(true);
                            break;
                        case "JPY to MXN":
                            ratio = FetchAPI("JPY","MXN");
                            res = val * ratio;
                            JOptionPane.showMessageDialog(null,  String.format("%.2f JPY equals to %.2f MXN.", val, res));

                            f.setVisible(true);
                            break;
                        case "MXN to KRW":
                            ratio = FetchAPI("MXN","KRW");
                            res = val * ratio;
                            JOptionPane.showMessageDialog(null,  String.format("%.2f MXN equals to %.2f KRW.", val, res));

                            f.setVisible(true);
                            break;
                        case "KRW to MXN":
                            ratio = FetchAPI("KRW","MXN");
                            res = val * ratio;
                            JOptionPane.showMessageDialog(null,  String.format("%.2f KRW equals to %.2f MXN.", val, res));

                            f.setVisible(true);
                            break;
                        default:
                            f.dispose();
                            break;
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Please type numbers only!");
                }
            }
        }); 
    }
}
