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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class EasyByteApp extends JPanel implements ActionListener, MouseListener {

    // ----- Variables ----

    // JPanel
    private static JPanel panel = new JPanel();

    // Swing components
    private static JLabel passwordLabel;
    private static JLabel usernameLabel;
    private static JButton login;
    private static JButton logout;
    private static JButton deleteUser;
    private static JPasswordField passwordText;
    private static JTextField usernameText;

    // Num users count
    private static JLabel numUsersLabel;
    private static int userCount;

    // Table components
    private static JTable userData;
    private static String[] columnNames;
    private static DefaultTableModel model;


    // Images
    private ImageIcon logo;
    private static JLabel imageLabel;
    private ImageIcon defaultAvatar;

    // Colours
    private static final Color backgroundColour = new Color(87, 163, 163);
    private static final Color buttonColour = new Color(236, 198, 164);
    private static final Color tableColour = new Color(173, 216, 230);
    private static final Color deleteButton = new Color(255, 105, 97);


    // Border
    Border border = new LineBorder(Color.black, 2, false);

    // User delete
    private static int selectedRowUserId;


    // ----- Constructor ----
    public EasyByteApp() {

        // JFrame
        JFrame frame = new JFrame();
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Easy Byte Recipe Login");
        frame.setResizable(false);
        frame.add(panel);

        // Background / layout features
        panel.setLayout(null);
        panel.setBackground(backgroundColour);
        panel.setBorder(border);


        // ----- Username -----
        usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(260, 58, 150, 25);
        usernameLabel.setFont(new Font("Calibri", Font.BOLD, 18));
        panel.add(usernameLabel);
        usernameText = new JTextField(20);
        usernameText.setBounds(370, 55, 165, 25);
        usernameText.setBorder(border);
        panel.add(usernameText);


        // ------ Password ------
        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(265, 93, 150, 25);
        passwordLabel.setFont(new Font("Calibri", Font.BOLD, 18));
        panel.add(passwordLabel);
        passwordText = new JPasswordField(20);
        passwordText.setBounds(370, 90, 165, 25);
        passwordText.setBorder(border);
        panel.add(passwordText);


        // ------ Login button -----
        login = new JButton("Login");
        login.setBounds(295, 145, 90, 30);
        login.setBackground(buttonColour);
        login.addActionListener(this);
        login.setBorder(border);
        panel.add(login);

        // ------ Logout button -----
        logout = new JButton("Logout");
        logout.setBounds(440, 145, 90, 30);
        logout.setBackground(buttonColour);
        logout.addActionListener(this);
        logout.setBorder(border);
        panel.add(logout);
        logout.setEnabled(false);

        // ------ Delete User button -----
        deleteUser = new JButton("Delete Account");
        deleteUser.setBounds(320, 500, 150, 35);
        deleteUser.setBackground(deleteButton);
        deleteUser.addActionListener(this);
        deleteUser.setBorder(border);
        panel.add(deleteUser);
        deleteUser.setEnabled(false);


        // ----- JTable ----
        columnNames = new String[]{"First name", "Last name", "Username", "Avatar", "UserID", "Birthday",
                "Description", "AuthToken", "Password"};
        userData = new JTable(new DefaultTableModel(columnNames, 0));
        userData.setFillsViewportHeight(true);
        userData.setBackground(tableColour);
        panel.add(userData);

        // TableModel
        model = (DefaultTableModel) userData.getModel();

        // Add a scroll pane
        JScrollPane scrollPane = new JScrollPane(userData);
        scrollPane.setBounds(5, 200, 780, 200);
        scrollPane.setBorder(border);
        panel.add(scrollPane);

        // Num users label
        numUsersLabel = new JLabel("Total registered accounts: ");
        numUsersLabel.setBounds(20, 400, 200, 35);
        panel.add(numUsersLabel);
        numUsersLabel.setVisible(false);


        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    // ------- End of constructor ------



    // Handle login, logout and deleteUser buttons
    @Override
    public void actionPerformed(ActionEvent a) {
        // Login button click
        if (a.getSource() == login) {
            // Create a new getUserSwingWorker if the login button is clicked
            new getUserSwingWorker(usernameText.getText(), String.valueOf(passwordText.getPassword())).execute();

        // Logout button click
        } else if (a.getSource() == logout) {
            try {
                // Ask the user to confirm logout
                int logoutResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to Logout?");

                if (logoutResult == JOptionPane.YES_OPTION) {
                    API.getInstance().userLogout();
                    JOptionPane.showMessageDialog(null, "User successfully logged out");

                    // Reset the GUI, remove the number of registered users and reset the login button
                    model.setRowCount(0);
                    usernameText.setText("");
                    passwordText.setText("");
                    login.setEnabled(true);
                    numUsersLabel.setVisible(false);
                }
            } catch (IOException | java.lang.InterruptedException e) {
                e.printStackTrace();
            }

        // Delete button click
        } else if (a.getSource() == deleteUser) {
            int deleteResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this account?");

            if (deleteResult == JOptionPane.YES_OPTION) {
                //Delete Selected Row
                int getSelectedRowForDeletion = userData.getSelectedRow();
                //Check if their is a row selected
                if (getSelectedRowForDeletion != -1) {
                    model.removeRow(getSelectedRowForDeletion);
                    JOptionPane.showMessageDialog(null, "User successfully deleted");
                    new deleteUserSwingWorker().execute();
                } else {
                    JOptionPane.showMessageDialog(null, "Unable To Delete");
                }
            }
        }
    }



    // Set number of registered accounts in JTable
    private static void setNumUsers(int userCount) {
        numUsersLabel.setText("Total registered users: " + userCount);
    }


    // SwingWorker 1: retrieve the user when logging in
    static class getUserSwingWorker extends SwingWorker<Boolean, Void> {

        private String username;
        private String password;

        // Constructor
        public getUserSwingWorker(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        protected Boolean doInBackground() throws Exception {

            // Disable login button while SwingWorker thread is running
            login.setEnabled(false);

            return API.getInstance().getUserLogin(new UserLogin(username, password));
        }

        @Override
        protected void done() {
            try {
                // get info from doInBackground() if successful (204)
                Boolean isAdmin = get();
                if (isAdmin) {
                    JOptionPane.showMessageDialog(null, "Login Successful!");

                    // If user is admin, retrieve the list of all users in database by calling the next SwingWorker
                    new retrieveUserListSwingWorker().execute();

                    // enable delete account button once logged in
                    deleteUser.setEnabled(true);
                    logout.setEnabled(true);

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


    //  SwingWorker 2: Obtain list of users in JTable
    static class retrieveUserListSwingWorker extends SwingWorker<ArrayList<User>, Void> {

        @Override
        protected ArrayList<User> doInBackground() throws Exception {

            return API.getInstance().retrieveUserList();
        }

        @Override
        protected void done() {

            try {
                ArrayList<User> userList = get();
                // loop through array of users and populate JTable
                for (User result : userList) {
                    model.addRow(result.toJTableRow());
                }

                // When logged in:
                // Display number of registered users
                userCount = userData.getRowCount();
                setNumUsers(userCount);
                numUsersLabel.setVisible(true);
                // Disable login button
                login.setEnabled(false);

            } catch (InterruptedException | ExecutionException interruptedException) {
                interruptedException.printStackTrace();

            }
        }
    }


    // SwingWorker 3: Delete a user account
    static class deleteUserSwingWorker extends SwingWorker<Boolean, Void> {


        @Override
        protected Boolean doInBackground() throws Exception {

            return API.getInstance().deleteUser(selectedRowUserId);
        }


        @Override
        protected void done() {
            try {
                // update registered user count
                userCount = model.getRowCount();
                setNumUsers(userCount);
                numUsersLabel.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }


    // User a mouse event to get the user's id
    @Override
    public void mouseClicked(MouseEvent e) {
        selectedRowUserId = (int) e.getSource();
    }


    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}










