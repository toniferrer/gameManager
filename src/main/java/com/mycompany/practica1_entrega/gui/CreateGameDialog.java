package com.mycompany.practica1_entrega.gui;

import com.mycompany.practica1_entrega.logic.RentalManager;
import com.mycompany.practica1_entrega.data.Platform;
import java.awt.Color;
import java.text.ParseException;
import java.util.HashMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class CreateGameDialog extends javax.swing.JDialog {
    private final RentalManager manager;

    public CreateGameDialog(java.awt.Frame parent, boolean modal, RentalManager manager) {
        super(parent, modal);
        this.manager = manager;
        initComponents();
        
        jComboBoxGenre.setModel(new DefaultComboBoxModel<>(
        new String[] {
            "Acción", "Aventura", "RPG", "Action RPG",
            "Sandbox", "Deportes", "Shooter", "Plataformas",
            "Metroidvania", "JRPG", "Simulación", "MOBA",
            "Battle Royale", "Party", "Cooperativo",
            "Puzzle", "Lucha", "Terror"
        }
        ));
        
        jComboBoxGenre.setSelectedIndex(-1);

        configurePlatform(jCheckBoxPS5, jSpinnerPS5);
        configurePlatform(jCheckBoxXBOX, jSpinnerXbox);
        configurePlatform(jCheckBoxPC, jSpinnerPC);
        configurePlatform(jCheckBoxSwitch2, jSpinnerSwitch2);

        jTextFieldName.getDocument().addDocumentListener(
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

        jComboBoxGenre.addActionListener(e -> updateConfirmButton());

        jButtonConfirmar.setContentAreaFilled(true);
        jButtonConfirmar.setOpaque(true);
        jButtonConfirmar.addActionListener(e -> saveGame());

        jButtonCancel.addActionListener(e -> dispose());

        updateConfirmButton();
    }
    
    
    //Configurar por plataformas
    private void configurePlatform(JCheckBox checkBox, JSpinner spinner) {
        spinner.setModel(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));

        checkBox.setSelected(false);
        spinner.setEnabled(false);

        checkBox.addItemListener(e -> {
            spinner.setEnabled(checkBox.isSelected());

            if (!checkBox.isSelected()) {
                spinner.setValue(0);
            }

            updateConfirmButton();
        });
    }

    //Habilitar el botón de confirmar 
    private void updateConfirmButton() {
        boolean hasPlatform = jCheckBoxPS5.isSelected() || jCheckBoxXBOX.isSelected() || jCheckBoxPC.isSelected() || jCheckBoxSwitch2.isSelected();

        boolean complete = !jTextFieldName.getText().trim().isEmpty() && jComboBoxGenre.getSelectedIndex() >= 0 && hasPlatform;

        jButtonConfirmar.setEnabled(complete);

        if (complete) {
            jButtonConfirmar.setBackground(new Color(46, 160, 67));
            jButtonConfirmar.setForeground(Color.WHITE);
        } else {
            jButtonConfirmar.setBackground(Color.LIGHT_GRAY);
            jButtonConfirmar.setForeground(Color.DARK_GRAY);
        }
    }
    
    //Guardar un juego nuevo
    private void saveGame() {
        updateConfirmButton();

        if (!jButtonConfirmar.isEnabled()) {
            return;
        }

        HashMap<Platform, Integer> stock = new HashMap<>();

        try {
            addPlatformStock(stock, Platform.PS5, jCheckBoxPS5, jSpinnerPS5);

            addPlatformStock(stock, Platform.XBOX, jCheckBoxXBOX, jSpinnerXbox);

            addPlatformStock(stock, Platform.PC, jCheckBoxPC, jSpinnerPC);

            addPlatformStock(stock, Platform.SWITCH_2, jCheckBoxSwitch2, jSpinnerSwitch2);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Introdueix una quantitat vàlida per a cada plataforma", "Stock incorrecte", JOptionPane.ERROR_MESSAGE);
            return;
        }

        manager.addGame(jTextFieldName.getText().trim(), jComboBoxGenre.getSelectedItem().toString(), stock);

        JOptionPane.showMessageDialog(this, "Joc creat correctament");

        dispose();
    }

    //Añadir el stock a cada plataforma
    private void addPlatformStock(HashMap<Platform, Integer> stock, Platform platform, JCheckBox checkBox, JSpinner spinner) throws ParseException {

        if (checkBox.isSelected()) {
            // Recoge también las cantidades escritas con el teclado
            spinner.commitEdit();

            int quantity = ((Number) spinner.getValue()).intValue();

            if (quantity < 0) {
                throw new ParseException("Stock negatiu", 0);
            }

            stock.put(platform, quantity);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        jLabelName = new javax.swing.JLabel();
        jTextFieldName = new javax.swing.JTextField();
        jLabelGenre = new javax.swing.JLabel();
        jComboBoxGenre = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jCheckBoxXBOX = new javax.swing.JCheckBox();
        jCheckBoxPC = new javax.swing.JCheckBox();
        jCheckBoxSwitch2 = new javax.swing.JCheckBox();
        jCheckBoxPS5 = new javax.swing.JCheckBox();
        jLabelPlatforms = new javax.swing.JLabel();
        jLabelUnitsPS5 = new javax.swing.JLabel();
        jLabelUnitsPC = new javax.swing.JLabel();
        jLabelUnitsXbox = new javax.swing.JLabel();
        jLabelUnitsSwitch2 = new javax.swing.JLabel();
        jSpinnerPS5 = new javax.swing.JSpinner();
        jSpinnerXbox = new javax.swing.JSpinner();
        jSpinnerPC = new javax.swing.JSpinner();
        jSpinnerSwitch2 = new javax.swing.JSpinner();
        jButtonCancel = new javax.swing.JButton();
        jButtonConfirmar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabelName.setText("Nom *:");

        jLabelGenre.setText("Gènere *:");

        jComboBoxGenre.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel1.setName(""); // NOI18N

        jCheckBoxXBOX.setText("Xbox");
        jCheckBoxXBOX.setToolTipText("");

        jCheckBoxPC.setText("PC");
        jCheckBoxPC.setToolTipText("");

        jCheckBoxSwitch2.setText("Switch 2");
        jCheckBoxSwitch2.setToolTipText("");

        jCheckBoxPS5.setText("PS5");
        jCheckBoxPS5.setToolTipText("");

        jLabelPlatforms.setText("Plataformes disponibles");

        jLabelUnitsPS5.setText("Unitats:");

        jLabelUnitsPC.setText("Unitats:");

        jLabelUnitsXbox.setText("Unitats:");

        jLabelUnitsSwitch2.setText("Unitats:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBoxPS5)
                    .addComponent(jCheckBoxXBOX)
                    .addComponent(jCheckBoxPC)
                    .addComponent(jCheckBoxSwitch2)
                    .addComponent(jLabelPlatforms))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabelUnitsSwitch2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSpinnerSwitch2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabelUnitsPS5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSpinnerPS5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabelUnitsXbox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSpinnerXbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabelUnitsPC)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSpinnerPC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelPlatforms)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBoxPS5)
                    .addComponent(jLabelUnitsPS5)
                    .addComponent(jSpinnerPS5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBoxXBOX)
                    .addComponent(jLabelUnitsXbox)
                    .addComponent(jSpinnerXbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBoxPC)
                    .addComponent(jLabelUnitsPC)
                    .addComponent(jSpinnerPC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBoxSwitch2)
                    .addComponent(jLabelUnitsSwitch2)
                    .addComponent(jSpinnerSwitch2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );

        jButtonCancel.setText("Cancel.lar");

        jButtonConfirmar.setText("Confirmar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonConfirmar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonCancel))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabelName)
                                .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(28, 28, 28)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabelGenre)
                                .addComponent(jComboBoxGenre, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelName)
                    .addComponent(jLabelGenre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxGenre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonCancel)
                    .addComponent(jButtonConfirmar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonConfirmar;
    private javax.swing.JCheckBox jCheckBoxPC;
    private javax.swing.JCheckBox jCheckBoxPS5;
    private javax.swing.JCheckBox jCheckBoxSwitch2;
    private javax.swing.JCheckBox jCheckBoxXBOX;
    private javax.swing.JComboBox<String> jComboBoxGenre;
    private javax.swing.JLabel jLabelGenre;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JLabel jLabelPlatforms;
    private javax.swing.JLabel jLabelUnitsPC;
    private javax.swing.JLabel jLabelUnitsPS5;
    private javax.swing.JLabel jLabelUnitsSwitch2;
    private javax.swing.JLabel jLabelUnitsXbox;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSpinner jSpinnerPC;
    private javax.swing.JSpinner jSpinnerPS5;
    private javax.swing.JSpinner jSpinnerSwitch2;
    private javax.swing.JSpinner jSpinnerXbox;
    private javax.swing.JTextField jTextFieldName;
    // End of variables declaration//GEN-END:variables
}
