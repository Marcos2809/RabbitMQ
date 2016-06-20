 /* **************************************************************************************
 * This class monitors the environmental control systems that control museum
 * temperature and humidity. In addition to monitoring the temperature and
 * humidity, the ECSMonitor also allows a user to set the humidity and
 * temperature ranges to be maintained. If temperatures exceed those limits
 * over/under alarm indicators are triggered.
 * **************************************************************************************
 */
package views;

import common.Component;
import instrumentation.*;
import java.awt.Color;


/**
 *
 * @author JMMM, YMGM, MGL
 */

public class MumaMonitor2 extends javax.swing.JFrame {

    /**
     * Creates new form MumaMonitor
     */
    

    private MumaMonitor2() {
        initComponents();
    }
    
    private static MumaMonitor2 INSTANCE;

    public static MumaMonitor2 getINSTANCE() 
    {
        if (INSTANCE == null){
            INSTANCE = new MumaMonitor2();
        }
        
        return INSTANCE;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel9 = new javax.swing.JLabel();
        txtDoor = new javax.swing.JTextField();
        btnADDoor = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtWindow = new javax.swing.JTextField();
        btnADWindow = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        txtMovement = new javax.swing.JTextField();
        btnADMovement = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtFire = new javax.swing.JTextField();
        txtSprinkler = new javax.swing.JTextField();
        btnADFire = new javax.swing.JButton();
        btnADSprinkler = new javax.swing.JButton();
        btnADAll = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MUMA Monitor");

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Door");

        txtDoor.setBackground(java.awt.Color.green);
        txtDoor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDoor.setText("OK");

        btnADDoor.setText("Deactivate");
        btnADDoor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnADDoorActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Ubuntu", 3, 18)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("ALARMS");

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Window");

        txtWindow.setBackground(java.awt.Color.green);
        txtWindow.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtWindow.setText("OK");

        btnADWindow.setText("Deactivate");
        btnADWindow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnADWindowActionPerformed(evt);
            }
        });

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Movement");

        txtMovement.setBackground(java.awt.Color.green);
        txtMovement.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtMovement.setText("NONE");

        btnADMovement.setText("Deactivate");
        btnADMovement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnADMovementActionPerformed(evt);
            }
        });

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Fire");

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Sprinkler");

        txtFire.setBackground(java.awt.Color.green);
        txtFire.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFire.setText("OK");

        txtSprinkler.setBackground(java.awt.Color.black);
        txtSprinkler.setForeground(java.awt.Color.white);
        txtSprinkler.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSprinkler.setText("OFF");

        btnADFire.setText("Deactivate");
        btnADFire.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnADFireActionPerformed(evt);
            }
        });

        btnADSprinkler.setText("Deactivate");
        btnADSprinkler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnADSprinklerActionPerformed(evt);
            }
        });

        btnADAll.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        btnADAll.setText("Deactivate All Alarms");
        btnADAll.setName("btnADAll"); // NOI18N
        btnADAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnADAllActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtDoor)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(31, 31, 31)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtWindow, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(28, 28, 28)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtMovement)
                                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(115, 115, 115)
                                        .addComponent(btnADFire)
                                        .addGap(162, 162, 162)
                                        .addComponent(btnADSprinkler)))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtFire)
                                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(69, 69, 69)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtSprinkler, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(94, 94, 94)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(btnADDoor)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnADWindow)
                                .addGap(114, 114, 114)
                                .addComponent(btnADMovement)
                                .addGap(52, 52, 52))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(195, 195, 195)
                        .addComponent(btnADAll, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDoor, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtWindow, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMovement, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnADDoor)
                    .addComponent(btnADWindow)
                    .addComponent(btnADMovement))
                .addGap(83, 83, 83)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFire, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSprinkler, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnADSprinkler)
                    .addComponent(btnADFire))
                .addGap(64, 64, 64)
                .addComponent(btnADAll, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(117, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnADWindowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnADWindowActionPerformed
        // TODO add your handling code here:
        if (btnADWindow.getText().equals("Deactivate")){
            btnADWindow.setText("Activate");
            txtWindow.setForeground(Color.white);
            txtWindow.setBackground(Color.black);
            txtWindow.setText("OFF");
        }
        else{
            btnADWindow.setText("Deactivate");
            txtWindow.setForeground(Color.black);
            txtWindow.setBackground(Color.green);
            txtWindow.setText("OK");
        }
    }//GEN-LAST:event_btnADWindowActionPerformed

    private void btnADDoorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnADDoorActionPerformed
        // TODO add your handling code here:
        if (btnADDoor.getText().equals("Deactivate")){
            btnADDoor.setText("Activate");
            txtDoor.setForeground(Color.white);
            txtDoor.setBackground(Color.black);
            txtDoor.setText("OFF");
        }            
        else{
            btnADDoor.setText("Deactivate");
            txtDoor.setForeground(Color.black);
            txtDoor.setBackground(Color.green);
            txtDoor.setText("OK");
        }
    }//GEN-LAST:event_btnADDoorActionPerformed

    private void btnADMovementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnADMovementActionPerformed
        // TODO add your handling code here:
        if (btnADMovement.getText().equals("Deactivate")){
            btnADMovement.setText("Activate");
            txtMovement.setForeground(Color.white);
            txtMovement.setBackground(Color.black);
            txtMovement.setText("OFF");
        }            
        else{
            btnADMovement.setText("Deactivate");
            txtMovement.setForeground(Color.black);
            txtMovement.setBackground(Color.green);
            txtMovement.setText("NONE");
        }
    }//GEN-LAST:event_btnADMovementActionPerformed

    private void btnADFireActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnADFireActionPerformed
        // TODO add your handling code here:
        if (btnADFire.getText().equals("Deactivate")){
            btnADFire.setText("Activate");
            txtFire.setForeground(Color.white);
            txtFire.setBackground(Color.black);
            txtFire.setText("OFF");
            txtSprinkler.setForeground(Color.white);
            txtSprinkler.setBackground(Color.black);
            txtSprinkler.setText("DEACTIVATED");
            btnADSprinkler.setText("Activate");
            btnADSprinkler.setEnabled(false);
        }            
        else{
            btnADFire.setText("Deactivate");
            txtFire.setForeground(Color.black);
            txtFire.setBackground(Color.green);
            txtFire.setText("OK");
            btnADSprinkler.setEnabled(true);
        }
    }//GEN-LAST:event_btnADFireActionPerformed

    private void btnADSprinklerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnADSprinklerActionPerformed
        // TODO add your handling code here:
        if (btnADSprinkler.getText().equals("Deactivate")){
            btnADSprinkler.setText("Activate");
            txtSprinkler.setForeground(Color.white);
            txtSprinkler.setBackground(Color.black);
            txtSprinkler.setText("DEACTIVATED");
        }            
        else{
            btnADSprinkler.setText("Deactivate");
            txtSprinkler.setForeground(Color.white);
            txtSprinkler.setBackground(Color.black);
            txtSprinkler.setText("OFF");
        }
    }//GEN-LAST:event_btnADSprinklerActionPerformed

    private void btnADAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnADAllActionPerformed
        // TODO add your handling code here:
        if (btnADAll.getText().equals("Deactivate All Alarms")){
            btnADAll.setText("Activate All Alarms");
            txtDoor.setForeground(Color.white);
            txtDoor.setBackground(Color.black);
            txtDoor.setText("OFF");
            btnADDoor.setText("Activate");
            btnADDoor.setEnabled(false);
            txtWindow.setForeground(Color.white);
            txtWindow.setBackground(Color.black);
            txtWindow.setText("OFF");
            btnADWindow.setText("Activate");
            btnADWindow.setEnabled(false);
            txtMovement.setForeground(Color.white);
            txtMovement.setBackground(Color.black);
            txtMovement.setText("OFF");
            btnADMovement.setText("Activate");
            btnADMovement.setEnabled(false);
            txtFire.setForeground(Color.white);
            txtFire.setBackground(Color.black);
            txtFire.setText("OFF");
            btnADFire.setText("Activate");
            btnADFire.setEnabled(false);
            txtSprinkler.setForeground(Color.white);
            txtSprinkler.setBackground(Color.black);
            txtSprinkler.setText("DEACTIVATED");
            btnADSprinkler.setText("Activate");
            btnADSprinkler.setEnabled(false);
            
        }            
        else{
            btnADAll.setText("Deactivate All Alarms");
            txtDoor.setForeground(Color.black);
            txtDoor.setBackground(Color.green);
            txtDoor.setText("OK");
            btnADDoor.setText("Deactivate");
            btnADDoor.setEnabled(true);
            txtWindow.setForeground(Color.black);
            txtWindow.setBackground(Color.green);
            txtWindow.setText("OK");
            btnADWindow.setText("Deactivate");
            btnADWindow.setEnabled(true);
            txtMovement.setForeground(Color.black);
            txtMovement.setBackground(Color.green);
            txtMovement.setText("NONE");
            btnADMovement.setText("Deactivate");
            btnADMovement.setEnabled(true);
            txtFire.setForeground(Color.black);
            txtFire.setBackground(Color.green);
            txtFire.setText("OK");
            btnADFire.setText("Deactivate");
            btnADFire.setEnabled(true);
            txtSprinkler.setForeground(Color.white);
            txtSprinkler.setBackground(Color.black);
            txtSprinkler.setText("OFF");
            btnADSprinkler.setText("Deactivate");
            btnADSprinkler.setEnabled(true);
        }
    }//GEN-LAST:event_btnADAllActionPerformed

    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MumaMonitor2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MumaMonitor2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MumaMonitor2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MumaMonitor2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MumaMonitor2().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnADAll;
    public javax.swing.JButton btnADDoor;
    public javax.swing.JButton btnADFire;
    public javax.swing.JButton btnADMovement;
    public javax.swing.JButton btnADSprinkler;
    public javax.swing.JButton btnADWindow;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel9;
    public javax.swing.JTextField txtDoor;
    public javax.swing.JTextField txtFire;
    public javax.swing.JTextField txtMovement;
    public javax.swing.JTextField txtSprinkler;
    public javax.swing.JTextField txtWindow;
    // End of variables declaration//GEN-END:variables
}
