package com.mycompany.practica1_entrega.gui;

import com.mycompany.practica1_entrega.data.Game;
import com.mycompany.practica1_entrega.data.Platform;
import com.mycompany.practica1_entrega.data.User;
import com.mycompany.practica1_entrega.logic.RentalManager;
import java.awt.Color;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class CreateRentalDialog extends javax.swing.JDialog {
    
    private final RentalManager manager;
    private final ArrayList<User> displayedUsers;
    private final ArrayList<Game> displayedGames;
    private final LocalDate startDate = LocalDate.now();
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
    
    public CreateRentalDialog(java.awt.Frame parent, boolean modal, RentalManager manager) {
        super(parent, modal);
        this.manager = manager;
        displayedUsers = manager.getUsers();
        displayedGames = manager.getGames();

        initComponents();

        buttonGroup1.add(jRadioButtonPC);
        buttonGroup1.add(jRadioButtonXBOX);
        buttonGroup1.add(jRadioButtonPS5);
        buttonGroup1.add(jRadioButtonSwitch2);

        jTextFieldDataStart.setText(startDate.format(dateFormat));
        jTextFieldDataStart.setEditable(false);
        jTextFieldDataEnd.setToolTipText("dd/MM/yyyy");

        // Coincide con la numeración actual de las filas de tu tabla
        jTextFieldNumber.setText(String.valueOf(manager.getRentals().size() + 1));
        jTextFieldNumber.setEditable(false);
        jComboBoxUser.setEditable(false);
        jComboBoxUser.removeAllItems();

        for (User user : displayedUsers) {
            jComboBoxUser.addItem(
                user.getName() + " "
                + user.getSurname() + " "
                + user.getSurname2() + " - "
                + user.getDni()
            );
        }

        jComboBoxUser.setSelectedIndex(-1);

        jComboBoxGame.setEditable(false);
        jComboBoxGame.removeAllItems();

        for (Game game : displayedGames) {
            jComboBoxGame.addItem(game.getId() + " - " + game.getName());
        }

        jComboBoxGame.setSelectedIndex(-1);

        jComboBoxUser.addActionListener(e -> updateConfirmButton());
        jComboBoxGame.addActionListener(e -> updateGameInfo());

        jRadioButtonPC.addActionListener(e -> updateConfirmButton());
        jRadioButtonXBOX.addActionListener(e -> updateConfirmButton());
        jRadioButtonPS5.addActionListener(e -> updateConfirmButton());
        jRadioButtonSwitch2.addActionListener(e -> updateConfirmButton());

        jTextFieldDataEnd.getDocument().addDocumentListener(
            new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    updateConfirmButton();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    updateConfirmButton();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    updateConfirmButton();
                }
            }
        );

        jButtonConfirmar.setContentAreaFilled(true);
        jButtonConfirmar.setOpaque(true);
        jButtonConfirmar.addActionListener(e -> saveRental());

        jButtonCancel.addActionListener(e -> dispose());

        updateGameInfo();
    }
    
    //Obtener el juego seleccionado
    private Game getSelectedGame() {
        int index = jComboBoxGame.getSelectedIndex();

        if (index < 0) {
            return null;
        }

        return displayedGames.get(index);
    }

    //Actualizar la info de un juego
    private void updateGameInfo() {
        Game game = getSelectedGame();

        // Al cambiar de juego hay que elegir de nuevo la plataforma
        buttonGroup1.clearSelection();

        updatePlatform(jRadioButtonPC, "PC", Platform.PC, game);
        updatePlatform(jRadioButtonXBOX, "Xbox", Platform.XBOX, game);
        updatePlatform(jRadioButtonPS5, "PS5", Platform.PS5, game);
        updatePlatform(jRadioButtonSwitch2, "Switch 2", Platform.SWITCH_2, game);

        if (game == null) {
            jLabelGameInfoGenre.setText("Gènere:");
        } else {
            jLabelGameInfoGenre.setText("Gènere: " + game.getGenre());
        }

        updateConfirmButton();
    }

    //Actualizar la plataforma
    private void updatePlatform(JRadioButton button, String name, Platform platform, Game game) {
        int available = 0;

        if (game != null) {
            available = game.getStock(platform);
        }

        button.setText(name + " (" + available + " unitats)");
        button.setEnabled(available > 0);
    }

    //Obtener la plataforma seleccionada
    private Platform getSelectedPlatform() {
        if (jRadioButtonPC.isSelected() && jRadioButtonPC.isEnabled()) {
            return Platform.PC;
        }

        if (jRadioButtonXBOX.isSelected() && jRadioButtonXBOX.isEnabled()) {
            return Platform.XBOX;
        }

        if (jRadioButtonPS5.isSelected() && jRadioButtonPS5.isEnabled()) {
            return Platform.PS5;
        }

        if (jRadioButtonSwitch2.isSelected() && jRadioButtonSwitch2.isEnabled()) {
            return Platform.SWITCH_2;
        }

        return null;
    }
    
    //Obtener la fecha final del alquiler
    private LocalDate getFinalDate() {
        try {
            return LocalDate.parse(jTextFieldDataEnd.getText().trim(), dateFormat);
        } catch (DateTimeParseException ex) {
            return null;
        }
    }

    //Habilitar botón de confirmar una vez que es posible
    private void updateConfirmButton() {
        Game game = getSelectedGame();
        Platform platform = getSelectedPlatform();
        LocalDate finalDate = getFinalDate();

        boolean complete = jComboBoxUser.getSelectedIndex() >= 0 && game != null && platform != null && finalDate != null;

        if (complete) {
            complete = !finalDate.isBefore(startDate) && game.getStock(platform) > 0;
        }

        jButtonConfirmar.setEnabled(complete);

        if (complete) {
            jButtonConfirmar.setBackground(new Color(46, 160, 67));
            jButtonConfirmar.setForeground(Color.WHITE);
        } else {
            jButtonConfirmar.setBackground(Color.LIGHT_GRAY);
            jButtonConfirmar.setForeground(Color.DARK_GRAY);
        }
    }
    
    //Añadir un nuevo alquiler
    private void saveRental() {
        updateConfirmButton();

        if (!jButtonConfirmar.isEnabled()) {
            return;
        }

        User user = displayedUsers.get(jComboBoxUser.getSelectedIndex());

        boolean saved = manager.addRental(user, getSelectedGame(), getSelectedPlatform(), startDate, getFinalDate());

        if (!saved) {
            JOptionPane.showMessageDialog(
                this,
                "No s'ha pogut registrar el lloguer. "
                    + "Comprova les dades i l'estoc disponible.",
                "No es pot registrar",
                JOptionPane.WARNING_MESSAGE
            );

            updateGameInfo();
            return;
        }

        JOptionPane.showMessageDialog(this, "Lloguer registrat correctament.");

        dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabelDataRental = new javax.swing.JLabel();
        jLabelNumber = new javax.swing.JLabel();
        jTextFieldNumber = new javax.swing.JTextField();
        jLabelUser = new javax.swing.JLabel();
        jComboBoxUser = new javax.swing.JComboBox<>();
        jLabelGame = new javax.swing.JLabel();
        jComboBoxGame = new javax.swing.JComboBox<>();
        jLabelStartDate = new javax.swing.JLabel();
        jTextFieldDataStart = new javax.swing.JTextField();
        jTextFieldDataEnd = new javax.swing.JTextField();
        jLabelEndDate = new javax.swing.JLabel();
        jRadioButtonPC = new javax.swing.JRadioButton();
        jLabelPlatform = new javax.swing.JLabel();
        jRadioButtonXBOX = new javax.swing.JRadioButton();
        jRadioButtonPS5 = new javax.swing.JRadioButton();
        jRadioButtonSwitch2 = new javax.swing.JRadioButton();
        jLabelGameInfo = new javax.swing.JLabel();
        jLabelGameInfoGenre = new javax.swing.JLabel();
        jButtonCancel = new javax.swing.JButton();
        jButtonConfirmar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabelDataRental.setText("Dades del lloguer");

        jLabelNumber.setText("Núm. de registre:");

        jLabelUser.setText("Usuari:");

        jComboBoxUser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabelGame.setText("Joc elegit:");

        jComboBoxGame.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabelStartDate.setText("Data d'inici:");

        jLabelEndDate.setText("Data de tornada:");

        jRadioButtonPC.setText("PC");

        jLabelPlatform.setText("Plataforma");

        jRadioButtonXBOX.setText("Xbox");
        jRadioButtonXBOX.addActionListener(this::jRadioButtonXBOXActionPerformed);

        jRadioButtonPS5.setText("PS5");
        jRadioButtonPS5.addActionListener(this::jRadioButtonPS5ActionPerformed);

        jRadioButtonSwitch2.setText("Switch 2");
        jRadioButtonSwitch2.addActionListener(this::jRadioButtonSwitch2ActionPerformed);

        jLabelGameInfo.setText("Informació del joc");

        jLabelGameInfoGenre.setText("Gènere: ");

        jButtonCancel.setText("Cancel.lar");

        jButtonConfirmar.setText("Confirmar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelEndDate)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabelDataRental, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabelNumber)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextFieldNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabelUser, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jComboBoxUser, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabelGame, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jComboBoxGame, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jTextFieldDataEnd)
                                .addComponent(jLabelStartDate, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextFieldDataStart, javax.swing.GroupLayout.Alignment.LEADING)))
                        .addGap(96, 96, 96)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelGameInfoGenre)
                            .addComponent(jRadioButtonPC)
                            .addComponent(jLabelPlatform)
                            .addComponent(jRadioButtonXBOX)
                            .addComponent(jRadioButtonPS5)
                            .addComponent(jRadioButtonSwitch2)
                            .addComponent(jLabelGameInfo)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonConfirmar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButtonCancel)))))
                .addContainerGap(205, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelDataRental)
                            .addComponent(jLabelPlatform))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelNumber)
                            .addComponent(jTextFieldNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jRadioButtonPC))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelUser)
                            .addComponent(jRadioButtonXBOX))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBoxUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jRadioButtonPS5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelGame))
                    .addComponent(jRadioButtonSwitch2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelGameInfo)
                    .addComponent(jComboBoxGame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelStartDate)
                    .addComponent(jLabelGameInfoGenre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldDataStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelEndDate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextFieldDataEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonCancel)
                            .addComponent(jButtonConfirmar))
                        .addGap(31, 31, 31))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jRadioButtonXBOXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonXBOXActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButtonXBOXActionPerformed

    private void jRadioButtonPS5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonPS5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButtonPS5ActionPerformed

    private void jRadioButtonSwitch2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonSwitch2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButtonSwitch2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonConfirmar;
    private javax.swing.JComboBox<String> jComboBoxGame;
    private javax.swing.JComboBox<String> jComboBoxUser;
    private javax.swing.JLabel jLabelDataRental;
    private javax.swing.JLabel jLabelEndDate;
    private javax.swing.JLabel jLabelGame;
    private javax.swing.JLabel jLabelGameInfo;
    private javax.swing.JLabel jLabelGameInfoGenre;
    private javax.swing.JLabel jLabelNumber;
    private javax.swing.JLabel jLabelPlatform;
    private javax.swing.JLabel jLabelStartDate;
    private javax.swing.JLabel jLabelUser;
    private javax.swing.JRadioButton jRadioButtonPC;
    private javax.swing.JRadioButton jRadioButtonPS5;
    private javax.swing.JRadioButton jRadioButtonSwitch2;
    private javax.swing.JRadioButton jRadioButtonXBOX;
    private javax.swing.JTextField jTextFieldDataEnd;
    private javax.swing.JTextField jTextFieldDataStart;
    private javax.swing.JTextField jTextFieldNumber;
    // End of variables declaration//GEN-END:variables
}
