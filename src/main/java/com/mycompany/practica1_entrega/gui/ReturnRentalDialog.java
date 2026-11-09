package com.mycompany.practica1_entrega.gui;

import com.mycompany.practica1_entrega.data.Rental;
import com.mycompany.practica1_entrega.logic.RentalManager;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class ReturnRentalDialog extends javax.swing.JDialog {
    
    private final RentalManager manager;
    private final ArrayList<Rental> displayedRentals = new ArrayList<>();
    private final DefaultTableModel tableModel;
    private final Rental returnedPreview;

    public ReturnRentalDialog(java.awt.Frame parent, boolean modal, RentalManager manager, Rental selectedRental) {
        super(parent, modal);
        this.manager = manager;
        if (selectedRental != null && selectedRental.isReturned()) {
            returnedPreview = selectedRental;
        } else {
            returnedPreview = null;
        }
        initComponents();
        
        String[] columns = {
        "Núm. registre", "Titol del joc", "Usuari que l'ha llogat",
        "Plataforma", "Data d'inici", "Data de tornada", "Gènere"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        jTableRentals.setModel(tableModel);
        jTableRentals.setRowSelectionAllowed(true);
        jTableRentals.setColumnSelectionAllowed(false);
        jTableRentals.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        jTableRentals.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateSelection();
            }
        });

        jButtonReturn.setContentAreaFilled(true);
        jButtonReturn.setOpaque(true);
        jButtonReturn.addActionListener(e -> returnSelectedRental());
        jButtonClose.addActionListener(e -> dispose());

        loadActiveRentals();
        
        if (selectedRental != null) {
            int modelRow = displayedRentals.indexOf(selectedRental);

            if (modelRow >= 0) {
                int viewRow = jTableRentals.convertRowIndexToView(modelRow);

                if (viewRow >= 0) {
                    jTableRentals.setRowSelectionInterval(viewRow, viewRow);
                    jTableRentals.scrollRectToVisible(
                        jTableRentals.getCellRect(viewRow, 0, true)
                    );
                }
            }
        }
    }
    
    //Cargar alquileres activos
    private void loadActiveRentals() {
        jTableRentals.clearSelection();
        tableModel.setRowCount(0);
        displayedRentals.clear();

        int registrationNumber = 0;

        for (Rental rental : manager.getRentals()) {
            registrationNumber++;

            if (returnedPreview != null) {
                if (rental != returnedPreview) {
                    continue;
                }
            } else if (rental.isReturned()) {
                continue;
            }

            displayedRentals.add(rental);
            tableModel.addRow(new Object[] {
                registrationNumber,
                rental.getGame().getName(),
                rental.getUser().getName(),
                rental.getPlatform(),
                rental.getStartDate(),
                rental.getFinalDate(),
                rental.getGame().getGenre()
            });
        }

        updateSelection();
    }

    //Actualizar el seleccionado
    private void updateSelection() {
        int viewRow = jTableRentals.getSelectedRow();
        String[] values = {"—", "—", "—", "—", "—", "—", "—"};
        boolean canReturn = false;

        if (viewRow >= 0) {
            int modelRow = jTableRentals.convertRowIndexToModel(viewRow);
            Rental rental = displayedRentals.get(modelRow);
            canReturn = !rental.isReturned();

            for (int column = 0; column < values.length; column++) {
                values[column] = String.valueOf(
                    tableModel.getValueAt(modelRow, column)
                );
            }
        }

        jButtonReturn.setEnabled(canReturn);

        if (canReturn) {
            jButtonReturn.setBackground(new Color(46, 160, 67));
            jButtonReturn.setForeground(Color.WHITE);
        } else {
            jButtonReturn.setBackground(Color.LIGHT_GRAY);
            jButtonReturn.setForeground(Color.DARK_GRAY);
        }

        jLabelGameName.setText("Joc: " + values[1]);
        jLabelUserName.setText("Usuari: " + values[2]);
        jLabelPlatform.setText("Plataforma: " + values[3]);
        jLabelStartDate.setText("Data d'inici: " + values[4]);
    }

    private void returnSelectedRental() {
        int selectedRow = jTableRentals.getSelectedRow();

        if (selectedRow < 0) {
            return;
        }

        int modelRow = jTableRentals.convertRowIndexToModel(selectedRow);
        Rental rental = displayedRentals.get(modelRow);

        if (manager.returnRental(rental)) {
            loadActiveRentals();
            return;
        }

        loadActiveRentals();
        JOptionPane.showMessageDialog(this, "No s'ha pogut marcar el lloguer com a retornat.", "No es pot retornar", JOptionPane.WARNING_MESSAGE);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTableRentals = new javax.swing.JTable();
        jButtonReturn = new javax.swing.JButton();
        jButtonClose = new javax.swing.JButton();
        jLabelGameName = new javax.swing.JLabel();
        jLabelUserName = new javax.swing.JLabel();
        jLabelPlatform = new javax.swing.JLabel();
        jLabelStartDate = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTableRentals.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTableRentals);

        jButtonReturn.setText("Marcar com a retornat");
        jButtonReturn.setToolTipText("");
        jButtonReturn.addActionListener(this::jButtonReturnActionPerformed);

        jButtonClose.setText("Tancar");
        jButtonClose.setToolTipText("");
        jButtonClose.addActionListener(this::jButtonCloseActionPerformed);

        jLabelGameName.setText("Joc: ");
        jLabelGameName.setToolTipText("");

        jLabelUserName.setText("Usuari: ");
        jLabelUserName.setToolTipText("");

        jLabelPlatform.setText("Plataforma: ");
        jLabelPlatform.setToolTipText("");

        jLabelStartDate.setText("Data d'inici: ");
        jLabelStartDate.setToolTipText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelGameName)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabelStartDate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonClose))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelUserName)
                                    .addComponent(jLabelPlatform))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 390, Short.MAX_VALUE)
                                .addComponent(jButtonReturn)))
                        .addGap(60, 60, 60))))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 685, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(363, Short.MAX_VALUE)
                .addComponent(jLabelGameName)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelUserName)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelPlatform)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonClose, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelStartDate))
                .addGap(35, 35, 35))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(222, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReturnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonReturnActionPerformed

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonCloseActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonReturn;
    private javax.swing.JLabel jLabelGameName;
    private javax.swing.JLabel jLabelPlatform;
    private javax.swing.JLabel jLabelStartDate;
    private javax.swing.JLabel jLabelUserName;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableRentals;
    // End of variables declaration//GEN-END:variables
}
