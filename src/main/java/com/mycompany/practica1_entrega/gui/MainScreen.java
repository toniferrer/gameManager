package com.mycompany.practica1_entrega.gui;

import com.mycompany.practica1_entrega.data.Rental;
import com.mycompany.practica1_entrega.logic.RentalManager;
import javax.swing.JMenuItem;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;

public class MainScreen extends javax.swing.JFrame {
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MainScreen.class.getName());
    private RentalManager manager;

    public MainScreen() {
        initComponents();
        manager = new RentalManager();
        
        manager.addGames();
        manager.addUsers();
        manager.addStock();
        manager.addRentals();
        
        //Definimos las columnas de la tabla
        String [] columns = {"Núm. registre", "Titol del joc", "Usuari que l'ha llogat", "Plataforma", "Data d'inici", "Data de tornada", "Gènere"};
        
        DefaultTableModel model = new DefaultTableModel( new Object[][] {}, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        jTable1.setModel(model);
        
        //Aplicar colores a la tabla en función al estado del alquiler
        jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean hasFocus, int row, int column) {

                Component cell = super.getTableCellRendererComponent(table, value, selected, hasFocus, row, column);

                int modelRow = table.convertRowIndexToModel(row);
                Rental rental = manager.getRentals().get(modelRow);

                Color background;

                if (rental.isReturned()) {
                    background = new Color(206, 240, 211);
                } else {
                    background = new Color(250, 213, 213);
                }

                if (selected) {
                    background = background.darker();
                }

                cell.setBackground(background);
                cell.setForeground(Color.BLACK);
                return cell;
            }
        });

        //Abrir el dialog de devolver alquiler al pulsar encima
        jTable1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() != 2 || !SwingUtilities.isLeftMouseButton(e)) {
                    return;
                }

                int viewRow = jTable1.rowAtPoint(e.getPoint());

                if (viewRow < 0) {
                    return;
                }

                int modelRow = jTable1.convertRowIndexToModel(viewRow);
                openReturnDialog(manager.getRentals().get(modelRow));
            }
        });
        
        //Cargar los alquileres inciales
        loadRentals();
        
        //Añadimos los menus a la JBar
        JMenuItem menuItemRegister = new JMenuItem("Registrar...");
        JMenuItem menuItemReturn = new JMenuItem("Tornar...");
        JMenuItem menuItemCreate = new JMenuItem("Crear...");
        JMenuItem menuItemDelete = new JMenuItem("Borrar...");
        JMenuItem menuItemCreateGame = new JMenuItem("Crear...");
        JMenuItem menuItemDeleteGame = new JMenuItem("Borrar...");
        
        //Añadimos el menu item de Crear... alquiler y su listener
        jMenu1.add(menuItemRegister);
        menuItemRegister.addActionListener(e -> {
            CreateRentalDialog dialog = new CreateRentalDialog(this, true, manager);
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);

            // Actualizar la tabla al cerrar el diálogo
            loadRentals();
        });
        
        //Añadimos el menu item de tornar... alquiler y su listener
        jMenu1.add(menuItemReturn);
        menuItemReturn.addActionListener(e -> {
            openReturnDialog(null);
        });
        
        //Añadimos el menu item de Crear... usuari y su listener
        jMenu2.add(menuItemCreate);
        menuItemCreate.addActionListener(e -> {
            CreateUserDialog dialog = new CreateUserDialog(this, true, manager);
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        });
        
        //Añadimos el menu item de Eliminar... usuari y su listener
        jMenu2.add(menuItemDelete);
        menuItemDelete.addActionListener(e -> {
            DeleteUserDialog dialog = new DeleteUserDialog(this, true, manager);
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        });
        
        //Añadimos el menu item Crear... juego y su listener
        jMenu3.add(menuItemCreateGame);
        menuItemCreateGame.addActionListener(e -> {
            CreateGameDialog dialog = new CreateGameDialog(this, true, manager);
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        });
        
        jMenu3.add(menuItemDeleteGame);
        menuItemDeleteGame.addActionListener(e -> {
            DeleteGameDialog dialog = new DeleteGameDialog(this, true, manager);
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        });
        
        //Configuración básica de la ventana
        setResizable(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jFrame2 = new javax.swing.JFrame();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame2Layout = new javax.swing.GroupLayout(jFrame2.getContentPane());
        jFrame2.getContentPane().setLayout(jFrame2Layout);
        jFrame2Layout.setHorizontalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame2Layout.setVerticalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setText("Alquilers");

        jMenu1.setText("Lloguer");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Usuari");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("Joc");
        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> new MainScreen().setVisible(true));
    }
    
    //Cargar alquileres
    private void loadRentals() {

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

        // Limpiamos las filas existentes
        model.setRowCount(0);
        int numeroRegistro = 1;

        // Recorremos los alquileres
        for (Rental rental : manager.getRentals()) {
            model.addRow(new Object[] { numeroRegistro++, rental.getGame().getName(), rental.getUser().getName(), rental.getPlatform(), rental.getStartDate(), rental.getFinalDate(), rental.getGame().getGenre()});
        }

        // Actualizamos el contador
        jLabel1.setText(
            "Total de lloguers: " + manager.getRentals().size()
        );
    }
    
    //Abrir el dialog de devolver alquiler
    private void openReturnDialog(Rental rental) {
        ReturnRentalDialog dialog = new ReturnRentalDialog(this, true, manager, rental);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        // Repinta la fila en verde si se ha devuelto el alquiler
        loadRentals();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
