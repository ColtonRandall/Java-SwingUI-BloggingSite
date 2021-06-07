package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class EasyByteApp extends JPanel implements ActionListener {

    // Swing components
    private static JLabel successLogin;
    private static JLabel passwordLabel;
    private static JLabel usernameLabel;
    private static JButton login;
    private static JButton logout;
    private static JButton deleteAccount;
    private static JPasswordField passwordText;
    private static JTextField usernameText;
    private static JTable userData;

    // Images
    private final ImageIcon logo;
    private static JLabel imageLabel;

    // Colours
    private static final Color backgroundColour = new Color(87, 163, 163);
    private static final Color buttonColour = new Color(236, 198, 164);



    // Constructor
    public EasyByteApp() {

        // JPanel
        JPanel panel = new JPanel();

        // JFrame
        JFrame frame = new JFrame();
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Easy Byte Recipe Login");
        frame.add(panel);

        // Background / layout features
        panel.setLayout(null);
        panel.setBackground(backgroundColour);


        // TODO  -------- Logo ----------
        this.logo = new ImageIcon(getClass().getResource("logo.png"));

        imageLabel = new JLabel(logo);
        imageLabel.setIcon(this.logo);
        add(imageLabel);



        // ----- Username -----
        usernameLabel = new JLabel("Username");
        // x, y, width, height
        usernameLabel.setBounds(10, 20, 80, 25);
        panel.add(usernameLabel);
        usernameText = new JTextField(20);
        usernameText.setBounds(100, 20, 165, 25);
        panel.add(usernameText);

        // ------ Password ------
        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);
        passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);

        // ------ Login button -----
        login = new JButton("Login");
        login.setBounds(10, 80, 80, 25);
        login.setBackground(buttonColour);
        // Add action to login button
        login.addActionListener(this);
        panel.add(login);


        // ------ Logout button -----
        logout = new JButton("Logout");
        logout.setBounds(100, 80, 80, 25);
        logout.setBackground(buttonColour);
        panel.add(logout);


        // ---- JTable user data ----

        // TODO ------ remove dummy data once finished
        String[] columnNames = {"First name", "Last name", "UserID"};
        Object[][] data = {
                {"Colton", "Randall", "01"},
                {"Bob", "Martin", "02"},
                {"Sarah", "Greenwood", "03"},
                {"Jess", "McDonald", "04"},
        };

        // TODO ----- end of dummy data

        userData = new JTable(data, columnNames);
        userData.setFillsViewportHeight(true);

        // Add a scroll pane
        JScrollPane scrollPane = new JScrollPane(userData);
        scrollPane.setBounds(80, 150, 500, 100);
        panel.add(scrollPane);



        // ------ Delete Account button -----
        deleteAccount = new JButton("Delete Account");
        deleteAccount.setBounds(200, 300, 150, 25);
        deleteAccount.setBackground(Color.RED);
        panel.add(deleteAccount);







		/* Add success message to login button - Only show if user enters a correct
		 * username and password
		 */
        successLogin = new JLabel("");
        successLogin.setBounds(10, 110, 300, 25);
        panel.add(successLogin);


        // TODO Leave at end of constructor
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }


    // Runs whenever login button is run
    // TODO Implement this method.
    /*  Hint #1: event.getSource() will return the button which was pressed.
        Hint #2: JTextField's getText() method will get the value in the text box, as a String.
        Hint #3: JTextField's setText() method will allow you to pass it a String, which will be displayed in
        the text box.
     */

    @Override
    public void actionPerformed(ActionEvent arg0) {

        String username = usernameText.getText();
        String password = passwordText.getText();

        /*
         *  Conditional for if username and password
         *  match - then print success message
         */
        if (username.equals("username") && password.equals("pa55word")) {
            successLogin.setText("Login successful!");
        } else {
            successLogin.setText("Login failed!");
        }

    }

    // Draw images
//    public void paint(Graphics g){
//
//        g.drawImage(logo.getImage(), 100, 5, null);
//    }



}




