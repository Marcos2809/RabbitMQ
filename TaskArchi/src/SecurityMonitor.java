/**
 * **************************************************************************************
 * File:ECSMonitor.java 
 * Course: Software Architecture 
 * Project: Event Architectures  
 * Institution: Autonomous University of Zacatecas 
 * Date: November 2015
 * Developer: Ferman Ivan Tovar 
 * Reviewer: Perla Velasco Elizondo
 * Update: Equipo MEETMECORP
 * Institution: CIMAT
 * Date: 29/04/2016
 * **************************************************************************************
 * This class monitors the environmental control systems that control museum
 * temperature and humidity. In addition to monitoring the temperature and
 * humidity, the ECSMonitor also allows a user to set the humidity and
 * temperature ranges to be maintained. If temperatures exceed those limits
 * over/under alarm indicators are triggered.
 * **************************************************************************************
 */
import common.Component;
import views.MumaMonitor2;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.Logger;
import com.rabbitmq.client.*;
import common.IOManager;
import java.awt.Color;
import java.io.IOException;
import java.util.concurrent.TimeoutException;



public class SecurityMonitor extends Thread {

     
    String DoorStatus="OK";
    IOManager userInput = new IOManager();	// IOManager IO Object
    boolean registered = true;				// Signifies that this class is registered with an event manager.
    MumaMonitor2 mMonitor = MumaMonitor2.getINSTANCE();
    private Connection connection;
    private Channel channel, canalDoor, canalWindow, canalMotionSensor, canalDoorControlador;
       
    boolean alarmsStatus= true;  
    boolean sprinklerStatus=false;
    boolean doorActive = false, windowActive = false, movActive = false, fireActive = false, sprinklerActive = false;
    ConfirmSprinkler timernew;
    
            
    
    public SecurityMonitor() {
        // event manager is on the local system
        try{
            canalDoor = conectorrabbit("DoorSensor");
            canalDoorControlador = conectorrabbit("DoorControlador");
            timernew = new ConfirmSprinkler();
                      
        }catch(Exception e){
            System.out.println("ECSMonitor::Error instantiating event manager interface: " + e);
        }
    } //Constructor
   
    
    
    
    protected Channel conectorrabbit(String connector) throws IOException, TimeoutException{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        // Declaration Publish 
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(connector, false, false, false, null);

        return channel; 
                
    }
   
    @Override
    public void run() {
        int delay = 1000;			// The loop delay (1 second)
        boolean isDone = false;			// Loop termination flag
        boolean on = true;			// Used to turn on heaters, chillers, humidifiers, and dehumidifiers
        boolean off = false;			// Used to turn off heaters, chillers, humidifiers, and dehumidifiers

        mMonitor.setVisible(true);
        mMonitor.setLocation(680, 80);
        

        /**
         * ******************************************************************
         ** Here we start the main simulation loop
         * *******************************************************************
         */
        
        while (!isDone) {
        //String DoorStatus="OK";
         
            // Here we get the messages from rabbit
            final Consumer consumer = new DefaultConsumer(canalDoor) {
                public void handleDelivery(String consumerTag, Envelope envelope, 
                    AMQP.BasicProperties properties, byte[] body) throws java.io.IOException {
                    String message = new String(body, "UTF-8");
                   // setdoor(Float.valueOf(message));
                    if (alarmsStatus){
                        
                        if(message.equalsIgnoreCase("7")){
                            try {
                                
                                Activatefire(on);
                                Activatesprinkler(on);
                            } catch (Exception ex) {
                                Logger.getLogger(SecurityMonitor.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                            
                            
                            }
                         
                        if(message.equalsIgnoreCase("6")){
                            try {
                                Activatefire(off);
                                Activatesprinkler(off);
                            } catch (Exception ex) {
                                Logger.getLogger(SecurityMonitor.class.getName()).log(Level.SEVERE, null, ex);
                            }                           
                        }
                        if(message.equalsIgnoreCase("5")){
                            try {
                                Activatemov(on);
                            } catch (Exception ex) {
                                Logger.getLogger(SecurityMonitor.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } 
                        if(message.equalsIgnoreCase("4")){
                            try {
                                Activatemov(off);
                            } catch (Exception ex) {
                                Logger.getLogger(SecurityMonitor.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } 
                        if(message.equalsIgnoreCase("3")){
                            try {
                                Activatewindow(on);
                            } catch (Exception ex) {
                                Logger.getLogger(SecurityMonitor.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } 
                        if(message.equalsIgnoreCase("2")){
                            try {
                                Activatewindow(off);
                            } catch (Exception ex) {
                                Logger.getLogger(SecurityMonitor.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } 
                        if(message.equalsIgnoreCase("1")){
                            try {
                                Activatedoor(on);
                            } catch (Exception ex) {
                                Logger.getLogger(SecurityMonitor.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } 
                        if(message.equalsIgnoreCase("0")) {
                            try {
                                Activatedoor(off);
                            } catch (Exception ex) {
                                Logger.getLogger(SecurityMonitor.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    } 
                }
            };

            try {
                canalDoor.basicConsume("DoorSensor", true, consumer);
            } catch (IOException ex) { 
                Logger.getLogger(ECSMonitor.class.getName()).log(Level.SEVERE, null, ex);
            }

            // This delay slows down the sample rate to Delay milliseconds
            try {
                Thread.sleep(delay);
            } // try
            catch (Exception e) {
                System.out.println("Sleep error:: " + e);
            } // catch
            
            if(mMonitor.btnADAll.getText().equalsIgnoreCase("Deactivate All Alarms")){
                alarmsStatus = true;
            }else{
                alarmsStatus = false;
            }
            
            if(mMonitor.btnADDoor.getText().equalsIgnoreCase("Deactivate")){
                doorActive = true;
            }else{
                doorActive = false;
            }
            
            if(mMonitor.btnADWindow.getText().equalsIgnoreCase("Deactivate")){
                windowActive = true;
            }else{
                windowActive = false;
            }
            
            if(mMonitor.btnADMovement.getText().equalsIgnoreCase("Deactivate")){
                movActive = true;
            }else{
                movActive = false;
            }
            
            if(mMonitor.btnADFire.getText().equalsIgnoreCase("Deactivate")){
                fireActive = true;
            }else{
                fireActive = false;
            }
            
            if(mMonitor.btnADSprinkler.getText().equalsIgnoreCase("Deactivate")){
                sprinklerActive = true;
            }else{
                sprinklerActive = false;
            }
            
        } // while
        
       
    } // main
    
    public boolean isRegistered() {
        return (registered);
    } // setTemperatureRange
    
    /**
     * This method sets the alarms status
     *
     * @param status
     */
    public void setAlarmsStatus(boolean status) {
        alarmsStatus = status;
    } 

    public void setSprinklerStatus(boolean status) {
        sprinklerStatus = status;
        //System.out.println("DATO DE CONSOLA::" + sprinklerStatus);
    }
    
    
    
    
  private void Activatedoor(boolean ON)throws Exception {
        // Here we create the event.
        String message;
        if ((alarmsStatus) && (windowActive)){            
            if (ON) {
                message = Component.DOOR_ON;
                mMonitor.txtDoor.setText("OPEN");
                mMonitor.txtDoor.setBackground(Color.red);
                mMonitor.txtDoor.setForeground(Color.white);
            }
            else {
                message = Component.DOOR_OFF;
                mMonitor.txtDoor.setText("OK");
                mMonitor.txtDoor.setBackground(Color.green);
                mMonitor.txtDoor.setForeground(Color.black);
           } // if
            try {
            canalDoor.basicPublish("", "DoorControlador" , null, message.getBytes());
            } // heater
            catch (Exception e) {
             }
        }
    }
    private void Activatewindow(boolean ON)throws Exception {
        // Here we create the event.
        String message;
        if ((alarmsStatus) && (windowActive)){            
            if (ON) {
                message = Component.WINDOW_ON;
                mMonitor.txtWindow.setText("BROKEN");
                mMonitor.txtWindow.setBackground(Color.red);
                mMonitor.txtWindow.setForeground(Color.white);
            }
            else {
                message = Component.WINDOW_OFF;
                mMonitor.txtWindow.setText("OK");
                mMonitor.txtWindow.setBackground(Color.green);
                mMonitor.txtWindow.setForeground(Color.black);
           } // if
            try {
            canalDoor.basicPublish("", "DoorControlador" , null, message.getBytes());
            } // heater
            catch (Exception e) {
             }
        }
    }
    private void Activatemov(boolean ON)throws Exception {
        // Here we create the event.
        String message;
        if ((alarmsStatus) && (movActive)){            
            if (ON) {
                message = Component.MOVEMENT_ON;
                mMonitor.txtMovement.setText("DETECTED");
                mMonitor.txtMovement.setBackground(Color.red);
                mMonitor.txtMovement.setForeground(Color.white);
                
            }
            else {
                message = Component.MOVEMENT_OFF;
                mMonitor.txtMovement.setText("NONE");
                mMonitor.txtMovement.setBackground(Color.green);
                mMonitor.txtMovement.setForeground(Color.black);
                
           } // if
            try {
            canalDoor.basicPublish("", "DoorControlador" , null, message.getBytes());
            } // heater
            catch (Exception e) {
             }
        }
    }
    
    private void Activatefire(boolean ON)throws Exception {
        // Here we create the event.
        String message;
        if ((alarmsStatus) && (fireActive)){            
            
            if (ON) {
                message = Component.FIRE_ON;
                mMonitor.txtFire.setText("DETECTED");
                mMonitor.txtFire.setBackground(Color.red);
                mMonitor.txtFire.setForeground(Color.white);
                
            } else{
                message = Component.FIRE_OFF;
                mMonitor.txtFire.setText("OK");
                mMonitor.txtFire.setBackground(Color.green);
                mMonitor.txtFire.setForeground(Color.black);
           } // if
            try {
            canalDoor.basicPublish("", "DoorControlador" , null, message.getBytes());
            } // heater
            catch (Exception e) {
             }
        }
    }
    
    private void Activatesprinkler(boolean ON)throws Exception {
        // Here we create the event.
        String message;
        if ((alarmsStatus) && (sprinklerActive)){            
            if (ON) {
                message = Component.SPRINKLER_ON;
                mMonitor.txtSprinkler.setText("ON");
                mMonitor.txtSprinkler.setBackground(Color.green);
                mMonitor.txtSprinkler.setForeground(Color.black);
            } else{
                message = Component.SPRINKLER_OFF;
                mMonitor.txtSprinkler.setText("OFF");
                mMonitor.txtSprinkler.setBackground(Color.black);
                mMonitor.txtSprinkler.setForeground(Color.white);
            } // if
            try {
                canalDoor.basicPublish("", "DoorControlador" , null, message.getBytes());
            } // heater
                catch (Exception e) {
            }
        }
    }
                    
    
        
          // } // Dehumidifier
} // ECSMonitor
