package com.mycompany.practica1_entrega.gui;

import java.awt.Color;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import com.mycompany.practica1_entrega.logic.RentalManager;
import javax.swing.JOptionPane;

public class CreateUserDialog extends javax.swing.JDialog {
    private final RentalManager manager;
    
    public CreateUserDialog(java.awt.Frame parent, boolean modal, RentalManager manager) {
        super(parent, modal);
        this.manager = manager;
        initComponents();
        
        // Fecha de hoy
        jTextFieldDate.setText(
            LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        );
        jTextFieldDate.setEditable(false);

        // Permite mostrar el color
        jButtonConfirmar.setContentAreaFilled(true);
        jButtonConfirmar.setOpaque(true);

        // Cerrar el diálogo al cancelar
        jButtonCancel.addActionListener(e -> dispose());

        // Revisar los campos cada vez que cambia su contenido
        DocumentListener listener = new DocumentListener() {
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
        };

        JTextField[] requiredFields = {
            jTextFieldName,
            jTextFieldSurname,
            jTextFieldSurname2,
            jTextFieldDNI,
            jTextFieldMobile
        };

        for (JTextField field : requiredFields) {
            field.getDocument().addDocumentListener(listener);
        }

        updateConfirmButton();
        
        jButtonConfirmar.addActionListener(e -> saveUser());
    }
    
    
    //Habilitar el botón de confirmar al estar todo completado
    private void updateConfirmButton() {
        boolean complete = !jTextFieldName.getText().trim().isEmpty() && !jTextFieldSurname.getText().trim().isEmpty() && !jTextFieldSurname2.getText().trim().isEmpty() && !jTextFieldDNI.getText().trim().isEmpty() && !jTextFieldMobile.getText().trim().isEmpty();

        jButtonConfirmar.setEnabled(complete);

        if (complete) {
            jButtonConfirmar.setBackground(new Color(46, 160, 67));
            jButtonConfirmar.setForeground(Color.WHITE);
        } else {
            jButtonConfirmar.setBackground(Color.LIGHT_GRAY);
            jButtonConfirmar.setForeground(Color.DARK_GRAY);
        }
    }
    
    //Guardem l'usuari nou
    private void saveUser() {
        updateConfirmButton();

        if (!jButtonConfirmar.isEnabled()) {
            return;
        }

        String mobile = jTextFieldMobile.getText().trim();

        if (!mobile.matches("[0-9]{9}")) {
            JOptionPane.showMessageDialog(this, "El telèfon ha de tenir 9 dígits.", "Telèfon incorrecte", JOptionPane.ERROR_MESSAGE);
            return;
        }

        manager.addUser(jTextFieldName.getText().trim(), jTextFieldSurname.getText().trim(), jTextFieldSurname2.getText().trim(), jTextFieldDNI.getText().trim(), Integer.parseInt(mobile));

        JOptionPane.showMessageDialog(this, "Usuari creat correctament.");

        dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextFieldName = new javax.swing.JTextField();
        jLabelName = new javax.swing.JLabel();
        jLabelSurname = new javax.swing.JLabel();
        jTextFieldSurname = new javax.swing.JTextField();
        jLabelSurname2 = new javax.swing.JLabel();
        jTextFieldSurname2 = new javax.swing.JTextField();
        jLabelDNI = new javax.swing.JLabel();
        jTextFieldDNI = new javax.swing.JTextField();
        jLabelMobile = new javax.swing.JLabel();
        jTextFieldMobile = new javax.swing.JTextField();
        jLabelDate = new javax.swing.JLabel();
        jTextFieldDate = new javax.swing.JTextField();
        jButtonCancel = new javax.swing.JButton();
        jButtonConfirmar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTextFieldName.setToolTipText("");
        jTextFieldName.addActionListener(this::jTextFieldNameActionPerformed);

        jLabelName.setText("Nom* :");

        jLabelSurname.setText("Llinatge 1* :");

        jTextFieldSurname.setToolTipText("");
        jTextFieldSurname.addActionListener(this::jTextFieldSurnameActionPerformed);

        jLabelSurname2.setText("Llinatge 2* :");

        jTextFieldSurname2.setToolTipText("");
        jTextFieldSurname2.addActionListener(this::jTextFieldSurname2ActionPerformed);

        jLabelDNI.setText("DNI * :");

        jTextFieldDNI.setToolTipText("");
        jTextFieldDNI.addActionListener(this::jTextFieldDNIActionPerformed);

        jLabelMobile.setText("Telèfon * :");

        jTextFieldMobile.setToolTipText("");
        jTextFieldMobile.addActionListener(this::jTextFieldMobileActionPerformed);

        jLabelDate.setText("Data de registre:");

        jTextFieldDate.setEditable(false);
        jTextFieldDate.setToolTipText("");
        jTextFieldDate.addActionListener(this::jTextFieldDateActionPerformed);

        jButtonCancel.setText("Cancel.lar");

        jButtonConfirmar.setText("Confirmar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelSurname)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldSurname, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelName)
                        .addGap(40, 40, 40)
                        .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jButtonConfirmar)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jButtonCancel))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabelDate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextFieldDate, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelSurname2)
                                    .addComponent(jLabelDNI)
                                    .addComponent(jLabelMobile))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldMobile, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldSurname2, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelName))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelSurname)
                    .addComponent(jTextFieldSurname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelSurname2)
                    .addComponent(jTextFieldSurname2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelDNI)
                    .addComponent(jTextFieldDNI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelMobile)
                    .addComponent(jTextFieldMobile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelDate)
                    .addComponent(jTextFieldDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonCancel)
                    .addComponent(jButtonConfirmar))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNameActionPerformed

    private void jTextFieldSurnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldSurnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldSurnameActionPerformed

    private void jTextFieldSurname2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldSurname2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldSurname2ActionPerformed

    private void jTextFieldDNIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldDNIActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldDNIActionPerformed

    private void jTextFieldMobileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldMobileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldMobileActionPerformed

    private void jTextFieldDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldDateActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonConfirmar;
    private javax.swing.JLabel jLabelDNI;
    private javax.swing.JLabel jLabelDate;
    private javax.swing.JLabel jLabelMobile;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JLabel jLabelSurname;
    private javax.swing.JLabel jLabelSurname2;
    private javax.swing.JTextField jTextFieldDNI;
    private javax.swing.JTextField jTextFieldDate;
    private javax.swing.JTextField jTextFieldMobile;
    private javax.swing.JTextField jTextFieldName;
    private javax.swing.JTextField jTextFieldSurname;
    private javax.swing.JTextField jTextFieldSurname2;
    // End of variables declaration//GEN-END:variables
}
