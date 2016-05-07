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
import event.*;


/**
 *
 * @author juan
 */

public class MumaMonitor extends javax.swing.JFrame {

    /**
     * Creates new form MumaMonitor
     */
    public MumaMonitor() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jLabel1 = new javax.swing.JLabel();
        txtTemperature = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        sldTempMin = new javax.swing.JSlider();
        sldTempMax = new javax.swing.JSlider();
        lblTempMinVal = new javax.swing.JLabel();
        lblTempMinMin = new javax.swing.JLabel();
        lblTempMinMax = new javax.swing.JLabel();
        lblTempMaxVal = new javax.swing.JLabel();
        lblTempMaxMin = new javax.swing.JLabel();
        lblTempMaxMax = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtHumidity = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        sldHumMin = new javax.swing.JSlider();
        jLabel7 = new javax.swing.JLabel();
        lblHumMinVal = new javax.swing.JLabel();
        lblHumMinMin = new javax.swing.JLabel();
        lblHumMinMax = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        sldHumMax = new javax.swing.JSlider();
        lblHumMaxVal = new javax.swing.JLabel();
        lblHumMaxMin = new javax.swing.JLabel();
        lblHumMaxMax = new javax.swing.JLabel();
        txtHeater = new javax.swing.JTextField();
        txtChiller = new javax.swing.JTextField();
        lblHumidifier = new javax.swing.JLabel();
        lblDehumidifier = new javax.swing.JLabel();
        txtHumidifier = new javax.swing.JTextField();
        txtDehumidifier = new javax.swing.JTextField();
        lblHeater = new javax.swing.JLabel();
        lblChiller = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtDoor = new javax.swing.JTextField();
        btnADDoor = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
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
        MnuBar = new javax.swing.JMenuBar();
        MnuSystem = new javax.swing.JMenu();
        MnuExit = new javax.swing.JMenuItem();
        MnuShow = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MUMA Monitor");

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Temperature");

        txtTemperature.setBackground(java.awt.Color.green);
        txtTemperature.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTemperature.setText("10");

        jLabel2.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel2.setText("Set Ranges:");

        jLabel3.setText("Min");

        jLabel4.setText("Max");

        sldTempMin.setPaintLabels(true);
        sldTempMin.setValue(0);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, sldTempMax, org.jdesktop.beansbinding.ELProperty.create("${value}"), sldTempMin, org.jdesktop.beansbinding.BeanProperty.create("maximum"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, sldTempMin, org.jdesktop.beansbinding.ELProperty.create("${value}"), sldTempMin, org.jdesktop.beansbinding.BeanProperty.create("toolTipText"));
        bindingGroup.addBinding(binding);

        sldTempMax.setPaintLabels(true);
        sldTempMax.setValue(100);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, sldTempMin, org.jdesktop.beansbinding.ELProperty.create("${value}"), sldTempMax, org.jdesktop.beansbinding.BeanProperty.create("minimum"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, sldTempMin, org.jdesktop.beansbinding.ELProperty.create("${value}"), lblTempMinVal, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        lblTempMinMin.setText("0");

        lblTempMinMax.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, sldTempMin, org.jdesktop.beansbinding.ELProperty.create("${maximum}"), lblTempMinMax, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, sldTempMax, org.jdesktop.beansbinding.ELProperty.create("${value}"), lblTempMaxVal, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, sldTempMax, org.jdesktop.beansbinding.ELProperty.create("${minimum}"), lblTempMaxMin, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        lblTempMaxMax.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, sldTempMax, org.jdesktop.beansbinding.ELProperty.create("${maximum}"), lblTempMaxMax, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Humidity");

        txtHumidity.setBackground(java.awt.Color.green);
        txtHumidity.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtHumidity.setText("10");

        jLabel6.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel6.setText("Set Ranges:");

        sldHumMin.setPaintLabels(true);
        sldHumMin.setValue(0);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, sldHumMax, org.jdesktop.beansbinding.ELProperty.create("${value}"), sldHumMin, org.jdesktop.beansbinding.BeanProperty.create("maximum"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, sldHumMin, org.jdesktop.beansbinding.ELProperty.create("${value}"), sldHumMin, org.jdesktop.beansbinding.BeanProperty.create("toolTipText"));
        bindingGroup.addBinding(binding);

        jLabel7.setText("Min");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, sldHumMin, org.jdesktop.beansbinding.ELProperty.create("${value}"), lblHumMinVal, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        lblHumMinMin.setText("0");

        lblHumMinMax.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, sldHumMax, org.jdesktop.beansbinding.ELProperty.create("${value}"), lblHumMinMax, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jLabel8.setText("Max");

        sldHumMax.setPaintLabels(true);
        sldHumMax.setValue(100);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, sldHumMin, org.jdesktop.beansbinding.ELProperty.create("${value}"), sldHumMax, org.jdesktop.beansbinding.BeanProperty.create("minimum"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, sldHumMax, org.jdesktop.beansbinding.ELProperty.create("${value}"), sldHumMax, org.jdesktop.beansbinding.BeanProperty.create("toolTipText"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, sldHumMax, org.jdesktop.beansbinding.ELProperty.create("${value}"), lblHumMaxVal, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, sldHumMin, org.jdesktop.beansbinding.ELProperty.create("${value}"), lblHumMaxMin, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        lblHumMaxMax.setText("100");

        txtHeater.setBackground(java.awt.Color.black);

        txtChiller.setBackground(java.awt.Color.black);

        lblHumidifier.setText("HUMIDIFIER OFF");

        lblDehumidifier.setText("DEHUMIDIFIER OFF");

        txtHumidifier.setBackground(java.awt.Color.black);

        txtDehumidifier.setBackground(java.awt.Color.black);

        lblHeater.setText("HEATER OFF");

        lblChiller.setText("CHILLER OFF");

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

        jLabel10.setFont(new java.awt.Font("Ubuntu", 3, 18)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("SENSORS");

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

        btnADSprinkler.setText("Deactivate");

        MnuSystem.setText("System");

        MnuExit.setText("Exit");
        MnuSystem.add(MnuExit);

        MnuBar.add(MnuSystem);

        MnuShow.setText("Show");

        jMenuItem1.setText("Sensors");
        MnuShow.add(jMenuItem1);

        jMenuItem2.setText("Alarms");
        MnuShow.add(jMenuItem2);

        jMenuItem3.setText("All");
        MnuShow.add(jMenuItem3);

        MnuBar.add(MnuShow);

        setJMenuBar(MnuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtChiller)
                                .addComponent(txtHeater)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(lblTempMaxMin)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblTempMaxMax))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(lblTempMinMin, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblTempMinMax))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addGap(18, 18, 18)
                                    .addComponent(lblTempMaxVal))
                                .addComponent(sldTempMin, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(txtTemperature, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                                .addComponent(sldTempMax, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblTempMinVal, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel2))
                            .addComponent(lblChiller)
                            .addComponent(lblHeater))
                        .addGap(64, 64, 64)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDehumidifier)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtHumidity, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel8)
                                            .addGap(18, 18, 18)
                                            .addComponent(lblHumMaxVal, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(lblHumMaxMin)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lblHumMaxMax))
                                        .addComponent(sldHumMax, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(lblHumMinMin, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lblHumMinMax, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(sldHumMin, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addComponent(txtHumidifier)
                                        .addComponent(txtDehumidifier)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel7)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lblHumMinVal, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jLabel6))
                                    .addComponent(lblHumidifier))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(87, 87, 87)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(txtDoor, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                                                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                    .addGap(31, 31, 31)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtWindow, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGap(28, 28, 28)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(txtMovement)
                                                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)))
                                                .addGroup(layout.createSequentialGroup()
                                                    .addGap(115, 115, 115)
                                                    .addComponent(btnADFire)
                                                    .addGap(162, 162, 162)
                                                    .addComponent(btnADSprinkler))))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(txtFire, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                                                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGap(69, 69, 69)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                                                .addComponent(txtSprinkler))
                                            .addGap(94, 94, 94)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(132, 132, 132)
                                        .addComponent(btnADDoor)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 118, Short.MAX_VALUE)
                                        .addComponent(btnADWindow)
                                        .addGap(114, 114, 114)
                                        .addComponent(btnADMovement)
                                        .addGap(52, 52, 52)))))))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5)
                    .addComponent(jLabel9)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTemperature, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHumidity, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDoor, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtWindow, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMovement, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel6)
                    .addComponent(btnADDoor)
                    .addComponent(btnADWindow)
                    .addComponent(btnADMovement))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblTempMinVal)
                    .addComponent(jLabel7)
                    .addComponent(lblHumMinVal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sldTempMin, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sldHumMin, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblHumMinMax, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14)
                        .addComponent(jLabel15))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblTempMinMin)
                        .addComponent(lblTempMinMax)
                        .addComponent(lblHumMinMin)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtFire, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSprinkler, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTempMaxVal)
                            .addComponent(jLabel8)
                            .addComponent(lblHumMaxVal)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(sldHumMax, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sldTempMax, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblHumMaxMin)
                            .addComponent(lblHumMaxMax)
                            .addComponent(lblTempMaxMax)
                            .addComponent(lblTempMaxMin))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblHeater)
                            .addComponent(lblHumidifier))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnADSprinkler)
                            .addComponent(btnADFire))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHeater, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHumidifier, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblChiller)
                    .addComponent(lblDehumidifier))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtChiller, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDehumidifier, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(84, 84, 84))
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnADWindowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnADWindowActionPerformed
        // TODO add your handling code here:
        if (btnADWindow.getText().equals("Deactivate"))
            btnADWindow.setText("Activate");
        else
            btnADWindow.setText("Deactivate");
    }//GEN-LAST:event_btnADWindowActionPerformed

    private void btnADDoorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnADDoorActionPerformed
        // TODO add your handling code here:
        if (btnADDoor.getText().equals("Deactivate"))
            btnADDoor.setText("Activate");
        else
            btnADDoor.setText("Deactivate");
    }//GEN-LAST:event_btnADDoorActionPerformed

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
            java.util.logging.Logger.getLogger(MumaMonitor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MumaMonitor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MumaMonitor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MumaMonitor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MumaMonitor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar MnuBar;
    private javax.swing.JMenuItem MnuExit;
    private javax.swing.JMenu MnuShow;
    private javax.swing.JMenu MnuSystem;
    public javax.swing.JButton btnADDoor;
    public javax.swing.JButton btnADFire;
    public javax.swing.JButton btnADMovement;
    public javax.swing.JButton btnADSprinkler;
    public javax.swing.JButton btnADWindow;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    public javax.swing.JLabel lblChiller;
    public javax.swing.JLabel lblDehumidifier;
    public javax.swing.JLabel lblHeater;
    private javax.swing.JLabel lblHumMaxMax;
    private javax.swing.JLabel lblHumMaxMin;
    private javax.swing.JLabel lblHumMaxVal;
    private javax.swing.JLabel lblHumMinMax;
    private javax.swing.JLabel lblHumMinMin;
    private javax.swing.JLabel lblHumMinVal;
    public javax.swing.JLabel lblHumidifier;
    private javax.swing.JLabel lblTempMaxMax;
    private javax.swing.JLabel lblTempMaxMin;
    private javax.swing.JLabel lblTempMaxVal;
    private javax.swing.JLabel lblTempMinMax;
    private javax.swing.JLabel lblTempMinMin;
    private javax.swing.JLabel lblTempMinVal;
    public javax.swing.JSlider sldHumMax;
    public javax.swing.JSlider sldHumMin;
    public javax.swing.JSlider sldTempMax;
    public javax.swing.JSlider sldTempMin;
    public javax.swing.JTextField txtChiller;
    public javax.swing.JTextField txtDehumidifier;
    public javax.swing.JTextField txtDoor;
    public javax.swing.JTextField txtFire;
    public javax.swing.JTextField txtHeater;
    public javax.swing.JTextField txtHumidifier;
    public javax.swing.JTextField txtHumidity;
    public javax.swing.JTextField txtMovement;
    public javax.swing.JTextField txtSprinkler;
    public javax.swing.JTextField txtTemperature;
    public javax.swing.JTextField txtWindow;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
