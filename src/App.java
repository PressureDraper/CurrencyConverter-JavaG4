import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.simple.JSONObject;
import java.io.IOException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.ArrayList;
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

    public static void main(String[] args) {
        
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
                    ArrayList<String> options = new ArrayList<String>();
                    double res;
                    double ratio;

                    if (val < 0) {
                        throw new NegativeNumbersException();
                    }

                    //make main frame not visible
                    f.setVisible(false);

                    //collect all currencies into dynamic arrayList
                    for (Currencies currency : Currencies.values()) {
                        options.add(currency.toString());
                    }

                    //parse dynamic arrayList into array & set icon to JOptionPane window
                    String[] arr = options.toArray(new String[0]);
                    ImageIcon icon = new ImageIcon("img\\coin.png");

                    String n = (String) JOptionPane.showInputDialog(null, "Select your value conversion",
                    "Conversion Values", JOptionPane.QUESTION_MESSAGE, icon, arr, arr[0]);

                    //split choosen value like this["MXN","to","USD"]
                    String[] splited = n.split("\\s+");

                    //send local value [0] and foreign one [2] to API searching, do operation, shows result and goes back to main window
                    ratio = FetchAPI(splited[0],splited[2]);
                    res = val * ratio;
                    JOptionPane.showMessageDialog(null,  String.format("%.2f %s equals to %.2f %s.", val, splited[0] , res, splited[2]));
                    f.setVisible(true);

                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null, "Please type numbers only!");
                } catch (NegativeNumbersException nne) {
                    JOptionPane.showMessageDialog(null, "Negative numbers are not allowed!");
                } catch (NullPointerException npe) {
                    f.setVisible(true);
                } catch (ParseException pe) {
                    pe.printStackTrace();
                    f.dispose();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                    f.dispose();
                }
            }
        }); 
    }
}
