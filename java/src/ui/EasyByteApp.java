package ui;

import pojos.User;
import pojos.UserLogin;
import web.API;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;


public class EasyByteApp extends JPanel implements ActionListener {

    // ----- Variables ----

    // JPanel
    private static JPanel panel = new JPanel();

    // Swing components
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
    private static DefaultTableModel model;


    // Images
    private ImageIcon logo;
    private static JLabel imageLabel;
    private ImageIcon defaultAvatar;

    // Colours
    private static final Color backgroundColour = new Color(87, 163, 163);
    private static final Color buttonColour = new Color(236, 198, 164);

    // Border
    Border border = new LineBorder(Color.black, 2, false);

    // User object
    private User user;


    // ----- Constructor ----
    public EasyByteApp() {

        // JFrame
        JFrame frame = new JFrame();
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Easy Byte Recipe Login");
        frame.add(panel);

        // Background / layout features
        panel.setLayout(null);
        panel.setBackground(backgroundColour);
        panel.setBorder(border);


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
        usernameLabel.setBounds(280, 50, 80, 25);
        panel.add(usernameLabel);
        usernameText = new JTextField(20);
        usernameText.setBounds(370, 50, 165, 25);
        usernameText.setBorder(border);
        panel.add(usernameText);


        // ------ Password ------
        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(280, 90, 80, 25);
        panel.add(passwordLabel);
        passwordText = new JPasswordField(20);
        passwordText.setBounds(370, 90, 165, 25);
        passwordText.setBorder(border);
        panel.add(passwordText);


        // ------ Login button -----
        login = new JButton("Login");
        login.setBounds(290, 145, 80, 25);
        login.setBackground(buttonColour);
        login.addActionListener(this);
        login.setBorder(border);
        panel.add(login);

        // ------ Logout button -----
        logout = new JButton("Logout");
        logout.setBounds(440, 145, 80, 25);
        logout.setBackground(buttonColour);
        logout.addActionListener(this);
        logout.setBorder(border);
        panel.add(logout);

        // ----- JTable ----
        columnNames = new String[]{"Username", "First name", "Last name", "UserID", "Avatar", "Birthday",
                "Description", "AuthToken"};
//        data = new String[][]{{User.getUsername()}, {User.getFirstName()}, {User.getLastName()},
//                {User.getUserId()}};

        data = new String[][]{};


        userData = new JTable(new DefaultTableModel(columnNames, 0));
        userData.setFillsViewportHeight(true);
        panel.add(userData);


        // TableModel
        model = (DefaultTableModel) userData.getModel();


        // Add a scroll pane
        JScrollPane scrollPane = new JScrollPane(userData);
        scrollPane.setBounds(80, 200, 650, 200);
        scrollPane.setBorder(border);
        panel.add(scrollPane);


        // ------ Delete Account button -----
        deleteAccount = new JButton("Delete Account");
        deleteAccount.setBounds(330, 450, 150, 25);
        deleteAccount.setBackground(Color.RED);
        deleteAccount.addActionListener(this);
        deleteAccount.setBorder(border);
        panel.add(deleteAccount);


        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    // ------- End of constructor ------


    // Handle login and logout buttons
    @Override
    public void actionPerformed(ActionEvent a) {
        if (a.getSource() == login) {
            System.out.println("Login clicked");

            // Create a new getUserSwingWorker if the login button is clicked
            new getUserSwingWorker(usernameText.getText(), String.valueOf(passwordText.getPassword())).execute();


        } else if (a.getSource() == logout) {
            System.out.println("Logout clicked");
            try {
                API.getInstance().userLogout();
                JOptionPane.showMessageDialog(null, "User successfully logged out");
            } catch (IOException | java.lang.InterruptedException e) {
                e.printStackTrace();
            }
        } else if (a.getSource() == deleteAccount) {
            if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete your account?") == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, "User deleted");
                // TODO remove row
            }
        }
    }



    // Beginning of SwingWorker class
    static class getUserSwingWorker extends SwingWorker<Boolean, Void> {

        private String username;
        private String password;

        // Constructor
        public getUserSwingWorker(String username, String password) {
            this.username = username;
            this.password = password;
        }

        // doInBackground Method
        @Override
        protected Boolean doInBackground() throws Exception {

            System.out.println(username + password);

            // Disable login button while SwingWorker thread is running
            login.setEnabled(false);

            return API.getInstance().getUserLogin(new UserLogin(username, password));
        }

        // Done method
        @Override
        protected void done() {
            try {
                // get info from doInBackground() if successful (204)
                Boolean isAdmin = get();
                if (isAdmin) {
                    JOptionPane.showMessageDialog(null, "Login Successful!");

                    // If user is admin, retrieve the list of all users in database by calling the next SwingWorker
                    new retrieveUserListSwingWorker().execute();

                } else {
                    JOptionPane.showMessageDialog(null, "Login Failed, please try again!");
                    login.setEnabled(true);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            login.setEnabled(true);
        }
    }


    //    -- Obtain list of users in JTable --
    static class retrieveUserListSwingWorker extends SwingWorker<ArrayList<User>, Void> {


        @Override
        protected ArrayList <User> doInBackground() throws Exception {

            return API.getInstance().retrieveUserList();

        }


        @Override
        protected void done() {

            try {
                ArrayList <User> userList = get();

                // loop through array of users and populate JTable
                    for (User result : userList){
                        model.addRow(result.toJTableRow());
                    }

            } catch (InterruptedException | ExecutionException interruptedException) {
                interruptedException.printStackTrace();

            }
        }
    }
}










