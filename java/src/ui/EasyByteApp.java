package ui;

import pojos.User;
import pojos.UserLogin;
import web.API;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;


public class EasyByteApp extends JPanel implements ActionListener {

    // ----- Variables ----

    // JPanel
    private static JPanel panel = new JPanel();

    // Swing components
    private static JLabel successLogin;
    private static JLabel passwordLabel;
    private static JLabel usernameLabel;
    private static JButton login;
    private static JButton logout;
    private static JButton deleteAccount;
    private static JPasswordField passwordText;
    private static JTextField usernameText;

    // Table components
    private static JTable userData;
    private static String[] columnNames;
    private static String[][] data;


    // Images
    private ImageIcon logo;
    private static JLabel imageLabel;
    private ImageIcon defaultAvatar;

    // Colours
    private static final Color backgroundColour = new Color(87, 163, 163);
    private static final Color buttonColour = new Color(236, 198, 164);

    // User object
    private User user;


    // ----- Constructor ----
    public EasyByteApp() {

        // JFrame
        JFrame frame = new JFrame();
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Easy Byte Recipe Login");
        frame.add(panel);

        // Background / layout features
        panel.setLayout(null);
        panel.setBackground(backgroundColour);


        // -------- Logo ----------
        this.logo = new ImageIcon(getClass().getResource("/ui/logo.png"));

        imageLabel = new JLabel(logo);
        imageLabel.setIcon(this.logo);
        add(imageLabel);



        // ----- Default avatar -----
        this.defaultAvatar = new ImageIcon(getClass().getResource("/ui/defaultAvatar.png"));
        imageLabel.setIcon(this.defaultAvatar);
        add(imageLabel);




        // ----- Username -----
        usernameLabel = new JLabel("Username:");
        // x, y, width, height
        usernameLabel.setBounds(130, 50, 80, 25);
        panel.add(usernameLabel);
        usernameText = new JTextField(20);
        usernameText.setBounds(200, 50, 165, 25);
        panel.add(usernameText);


        // ------ Password ------
        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(130, 80, 80, 25);
        panel.add(passwordLabel);
        passwordText = new JPasswordField(20);
        passwordText.setBounds(200, 80, 165, 25);
        panel.add(passwordText);


        // ------ Login button -----
        login = new JButton("Login");
        login.setBounds(150, 130, 80, 25);
        login.setBackground(buttonColour);
        // Add action to login button
        login.addActionListener(this::loginClick);
        panel.add(login);


        // ------ Logout button -----
        logout = new JButton("Logout");
        logout.setBounds(250, 130, 80, 25);
        logout.setBackground(buttonColour);
        logout.addActionListener(this::logoutClick);
        panel.add(logout);


        // ------ Delete Account button -----
        deleteAccount = new JButton("Delete Account");
        deleteAccount.setBounds(180, 350, 150, 25);
        deleteAccount.setBackground(Color.RED);
        panel.add(deleteAccount);



		/* Add success message to login button - Only show if user enters a correct
		 * username and password
		 */
        successLogin = new JLabel("");
        successLogin.setBounds(210, 160, 300, 25);
        panel.add(successLogin);


        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    // End of constructor



    // Handle login button
    private void loginClick(ActionEvent a) {
        new getUserSwingWorker().execute();
    }


    // Handle logout button
    private void logoutClick(ActionEvent a) {
        try{
            API.getInstance().userLogout();
        } catch (IOException | java.lang.InterruptedException e) {
            e.printStackTrace();
        }
    }



    // ---- Get user details and send to JTable to be displayed
    private static void getUser(User user) {

        // ---- JTable containing user data ----
        columnNames = new String[]{"Username", "First name", "Last name", "UserID"};
        data = new String[][]{{user.getUsername()}, {user.getFirstName()}, {user.getLastName()},
                {user.getUserId()}};


        userData = new JTable(data, columnNames);
        userData.setFillsViewportHeight(true);
        panel.add(userData);

        // Add a scroll pane
        JScrollPane scrollPane = new JScrollPane(userData);
        scrollPane.setBounds(80, 200, 350, 40);
        panel.add(scrollPane);


        String url = "http://localhost:3000/userhome/" + user.getUsername();
        try {
            imageLabel.setIcon(new ImageIcon(new URL(url)));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    // Login and logout button action event handlers
    @Override
    public void actionPerformed(ActionEvent arg0) {

        String username = usernameText.getText();
        String password = passwordText.getText();


        // If logging in or out, print appropriate message successful message
        if (arg0.getSource().equals("login")) {
            if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
                successLogin.setText("Login successful!");
                successLogin.setForeground(Color.BLUE);
            } else if (arg0.getSource().equals("logout")) {
                successLogin.setText("Logout successful!");
                successLogin.setForeground(Color.ORANGE);
            }
            else {
                JOptionPane.showMessageDialog(login, "Login Failed");
            }

        }
    }



    // Beginning of SwingWorker class
    private static class getUserSwingWorker extends SwingWorker<User, Void> {

        private String user = null;

        // Constructor
        public getUserSwingWorker() {
            this.user = user;
            login.setEnabled(false);
            logout.setEnabled(false);
        }



        // doInBackground Method
        @Override
        protected User doInBackground() throws Exception {

            // TODO remove dummy data once finished
            return API.getInstance().getUserLogin(new UserLogin("coltonrandall", "pa55word"));

        }

        // Done method
        @Override
        protected void done() {
            try {
                User currentUser = get();
                getUser(currentUser);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            login.setEnabled(true);
            logout.setEnabled(true);
        }
    }



    // Draw images
    public void paint(Graphics g){
        g.drawImage(defaultAvatar.getImage(), 100, 5, null);
    }

}






