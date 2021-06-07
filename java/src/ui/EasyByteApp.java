package ui;

import javax.swing.*;

public class EasyByteApp extends javax.swing.JFrame {

        private JButton login;
        private JButton logout;
        private JTextArea username;
        private JTextArea password;
        private JButton deleteUser;
        private JLabel border;
        private JLabel lblCookie;
        private JLabel lblName;
        private JLabel lblTypes;
        private JComboBox<String> cboType;

        private final ImageIcon defaultAvatar = new ImageIcon(
                getClass().getResource("/ui/defaultAvatar.png"));



        public EasyByteApp() {

            initComponents();

//            btnChange.addActionListener(this::handleBtnChangeClick);

            setLocationRelativeTo(null);
            setVisible(true);
        }

        @SuppressWarnings("unchecked")
        private void initComponents() {

            username = new JTextArea();
            password = new JTextArea();

            login = new JButton();
            logout = new JButton();

            border = new javax.swing.JLabel();
            JLabel jLabel1 = new javax.swing.JLabel();
            JLabel jLabel2 = new javax.swing.JLabel();
            JLabel jLabel3 = new javax.swing.JLabel();
            lblName = new javax.swing.JLabel();
            lblTypes = new javax.swing.JLabel();
            JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
            lblCookie = new javax.swing.JLabel();
            cboType = new javax.swing.JComboBox<>();
            JLabel jLabel4 = new javax.swing.JLabel();

            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

            border.setIcon(this.defaultAvatar);
            border.setBorder(javax.swing.BorderFactory.createLineBorder(null, 1));

            jLabel1.setText("Username:");

            jLabel2.setText("Password:");

            jLabel3.setText("User data:");

            lblName.setText("Username goes here");

            lblTypes.setText("Password go here");

            username.setEditable(false);
            username.setColumns(20);
            username.setLineWrap(true);
            username.setRows(5);
            jScrollPane1.setViewportView(username);

            login.setText("View");

            lblCookie.setText("You've seen 0 Users so far!");

//            cboType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Any", "bug", "dark", "dragon",
//                    "electric", "fairy", "fighting", "fire", "flying", "ghost", "grass", "ground", "ice", "normal", "poison", "psychic", "rock", "steel", "water"}));

            jLabel4.setText("Filter type:");

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                            .addComponent(border, GroupLayout.PREFERRED_SIZE,
                                                                    GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                                                            .addComponent(cboType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(login, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                                                            .addGroup(layout.createSequentialGroup()
                                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                            .addGroup(layout.createSequentialGroup()
                                                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                            .addComponent(lblName)
                                                                                            .addComponent(lblTypes)))
                                                                            .addComponent(jLabel3))
                                                                    .addGap(0, 0, Short.MAX_VALUE))))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                    .addGap(0, 0, Short.MAX_VALUE)
                                                    .addComponent(lblCookie)))
                                    .addContainerGap())
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                            .addComponent(jLabel1)
                                                            .addComponent(lblName))
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                            .addComponent(jLabel2)
                                                            .addComponent(lblTypes))
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(jLabel3)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(jScrollPane1))
                                            .addGroup(layout.createSequentialGroup()
                                                    .addComponent(border)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(jLabel4)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(cboType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(login)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lblCookie)
                                    .addContainerGap())
            );

            pack();
        }
//
//        private void handleBtnChangeClick(ActionEvent e) {
//            new RetrieveUserSwingWorker().execute();
//        }

//        private void setNumPokemonSeen(int num) {
//            lblCookie.setText("You've seen " + num + " Pokémon so far!");
//        }

//        private void setPokemon(Pokemon pkm) {
//            lblName.setText(pkm.getName());
//            lblTypes.setText(pkm.getTypes());
//            txtAbout.setText(pkm.getDexEntry());
//
//            String url = "http://localhost:3000/images/" + pkm.getSprite();
//            try {
//                imgSprite.setIcon(new ImageIcon(new URL(url)));
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//        }

//        private class RetrieveUserSwingWorker extends SwingWorker<Pokemon, Void> {
//
//            private final String type;
//
//            public RetrieveUserSwingWorker() {
//                btnChange.setEnabled(false);
//                cboType.setEnabled(false);
//                imgSprite.setIcon(defaultImage);
//
//                this.type = cboType.getSelectedItem().toString();
//            }
////
//            @Override
//            protected User doInBackground() throws Exception {
//                if (type.equals("Any")) {
//                    return API.getInstance().getRandomPokemon();
//                } else {
//                    return API.getInstance().getRandomPokemonOfType(new TypeQuery(type));
//                }
//            }
//
//            @Override
//            protected void done() {
//                try {
//                    Pokemon pkm = get();
//                    int callCount = API.getInstance().getCallCount();
//                    setPokemon(pkm);
//                    setNumPokemonSeen(callCount);
//
//                } catch (InterruptedException | ExecutionException e) {
//                    e.printStackTrace();
//                }
//
//                btnChange.setEnabled(true);
//                cboType.setEnabled(true);
//            }
//        }
    }


