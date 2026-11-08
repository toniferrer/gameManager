/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.mycompany.practica1_entrega.gui;

import com.mycompany.practica1_entrega.data.User;
import com.mycompany.practica1_entrega.logic.RentalManager;
import javax.swing.JOptionPane;

/**
 *
 * @author toniferrer
 */
public class DeleteUserDialog extends javax.swing.JDialog {
    private final RentalManager manager;
    private final java.util.ArrayList<User> displayedUsers = new java.util.ArrayList<>();
    
    public DeleteUserDialog(java.awt.Frame parent, boolean modal, RentalManager manager) {
        super(parent, modal);
        this.manager = manager;
        initComponents();

        jComboBoxUsers.setEditable(false);
        jButtonDelete.setEnabled(false);

        jComboBoxUsers.addActionListener(e -> {
            jButtonDelete.setEnabled(
                jComboBoxUsers.getSelectedIndex() >= 0
            );
        });

        loadUsers();
    }
    
    //Cargar usuarios
    private void loadUsers() {
        jComboBoxUsers.removeAllItems();
        displayedUsers.clear();
        displayedUsers.addAll(manager.getUsers());

        for (User user : displayedUsers) {
            jComboBoxUsers.addItem(
                user.getName() + " "
                + user.getSurname() + " "
                + user.getSurname2() + " - "
                + user.getDni()
            );
        }

        jComboBoxUsers.setSelectedIndex(-1);
        jButtonDelete.setEnabled(false);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonDelete = new javax.swing.JButton();
        jComboBoxUsers = new javax.swing.JComboBox<>();
        jLabelDeleteUser = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jButtonDelete.setBackground(new java.awt.Color(204, 0, 0));
        jButtonDelete.setForeground(new java.awt.Color(255, 255, 255));
        jButtonDelete.setText("Eliminar");
        jButtonDelete.addActionListener(this::jButtonDeleteActionPerformed);

        jComboBoxUsers.setEditable(true);
        jComboBoxUsers.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabelDeleteUser.setText("Selecciona l'usari a eliminar:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(144, 144, 144)
                        .addComponent(jButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelDeleteUser)
                            .addComponent(jComboBoxUsers, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(107, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(93, Short.MAX_VALUE)
                .addComponent(jLabelDeleteUser)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxUsers, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83)
                .addComponent(jButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
        int selectedIndex = jComboBoxUsers.getSelectedIndex();

        if (selectedIndex < 0) {
            return;
        }

        User selectedUser = displayedUsers.get(selectedIndex);

        if (manager.hasRentals(selectedUser)) {
            JOptionPane.showMessageDialog(
                this,
                "No es pot eliminar aquest usuari perquè té lloguers associats",
                "No es pot eliminar",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (manager.deleteUser(selectedUser)) {
            loadUsers();

            JOptionPane.showMessageDialog(
                this,
                "Usuari eliminat correctament"
            );
        }
    }//GEN-LAST:event_jButtonDeleteActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JComboBox<String> jComboBoxUsers;
    private javax.swing.JLabel jLabelDeleteUser;
    // End of variables declaration//GEN-END:variables
}
