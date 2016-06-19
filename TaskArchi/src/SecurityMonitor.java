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
import instrumentation.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.Logger;
import com.rabbitmq.client.*;
import common.IOManager;
import java.io.IOException;
import java.util.concurrent.TimeoutException;



public class SecurityMonitor extends Thread {

     private Indicator wi;               
     private Indicator di;               
     private Indicator mi;               
     private Indicator fi;               
     private Indicator si;
     String DoorStatus="OK";
     IOManager userInput = new IOManager();	// IOManager IO Object
     boolean registered = true;				// Signifies that this class is registered with an event manager.     
    private Connection connection;
    private Channel channel, canalDoor, canalWindow, canalMotionSensor, canalDoorControlador;
    MessageWindow messageWin = null;   
    boolean alarmsStatus= true;  
    boolean sprinklerStatus=false;
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

      
        
        // Now we create the ECS status and message panel
        // Note that we set up two indicators that are initially yellow. This is
        // because we do not know if the temperature/humidity is high/low.
        // This panel is placed in the upper left hand corner and the status 
        // indicators are placed directly to the right, one on top of the other
        messageWin = new MessageWindow("Security Monitoring Console", 0, 0);
        di= new Indicator("DOOR OK", messageWin.getX() + messageWin.width(), 1);
        di.setLampColorAndMessage("DOOR OK", 1);
        wi= new Indicator("WINDOW OK", messageWin.getX() + messageWin.width(), 1);
        wi.setLampColorAndMessage("WINDOW OK", 1);
        mi= new Indicator("MOVEMENT DETECTION", messageWin.getX() + messageWin.width(), di.height()*2);
        mi.setLampColorAndMessage("NOT MOVEMENT DETECTION", 1);
        fi= new Indicator("FIRE DETECTION", messageWin.getX() + messageWin.width(), mi.height()*2);
        fi.setLampColorAndMessage("NOT FIRE DETECTED", 1);
        si= new Indicator("SPRINKLER ACTIVATION", messageWin.getX() + messageWin.width(), fi.height()*2);
        si.setLampColorAndMessage("SPRINKLER OFF", 0);

        messageWin.writeMessage("Registered with the event manager.");



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
                                //Activatesprinkler(on);
                            } catch (Exception ex) {
                                Logger.getLogger(SecurityMonitor.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            messageWin.writeMessage("Security:: ALERT! Fire detection");
                            fi.setLampColorAndMessage("Fire Detected", 3); // Fire Detected
                            //timernew.show();
                            //timernew.setVisible(true);
                            timernew.setVisible(true);
                            if (timernew.sprinkleractive){
                            try{
                                Activatesprinkler(on);
                            } catch (Exception ex) {
                                Logger.getLogger(SecurityMonitor.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            si.setLampColorAndMessage("Sprinkler ON", 1); // Sprinkler ON
                            }
                            else{
                                try {
                                    Activatesprinkler(off);
                                } catch (Exception ex) {
                                    Logger.getLogger(SecurityMonitor.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            si.setLampColorAndMessage("Sprinkler OFF", 0); // Sprinkler OFF                            
                            }
                        } 
                        if(message.equalsIgnoreCase("6")){
                            try {
                                Activatefire(off);
                                Activatesprinkler(off);
                            } catch (Exception ex) {
                                Logger.getLogger(SecurityMonitor.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            messageWin.writeMessage("Security:: Fire detection: False");
                            fi.setLampColorAndMessage("Not Fire Detected", 1); // Not Fire Detected
                            si.setLampColorAndMessage("Sprinkler OFF", 0); // Sprinkler OFF                            
                        }
                        if(message.equalsIgnoreCase("5")){
                            try {
                                Activatemov(on);
                            } catch (Exception ex) {
                                Logger.getLogger(SecurityMonitor.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            messageWin.writeMessage("Security:: ALERT! Movement detection");
                            mi.setLampColorAndMessage("Movement Detected", 3); // Door is broken
                        } 
                        if(message.equalsIgnoreCase("4")){
                            try {
                                Activatemov(off);
                            } catch (Exception ex) {
                                Logger.getLogger(SecurityMonitor.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            messageWin.writeMessage("Security:: Movement detection: False");
                            mi.setLampColorAndMessage("NOT Movement Detected", 1); // Door is broken
                        } 
                        if(message.equalsIgnoreCase("3")){
                            try {
                                Activatewindow(on);
                            } catch (Exception ex) {
                                Logger.getLogger(SecurityMonitor.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            messageWin.writeMessage("Security:: ALERT! WINDOWS broken");
                            wi.setLampColorAndMessage("WINDOW BROKEN", 3); // Door is broken
                        } 
                        if(message.equalsIgnoreCase("2")){
                            try {
                                Activatewindow(off);
                            } catch (Exception ex) {
                                Logger.getLogger(SecurityMonitor.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            messageWin.writeMessage("Security:: ALERT! WINDOWS OK");
                            wi.setLampColorAndMessage("WINDOW OK", 1); // Door is broken
                        } 
                        if(message.equalsIgnoreCase("1")){
                            try {
                                Activatedoor(on);
                            } catch (Exception ex) {
                                Logger.getLogger(SecurityMonitor.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            messageWin.writeMessage("Security:: ALERT! Door broken");
                            di.setLampColorAndMessage("DOOR BROKEN", 3); // Door is broken
                        } 
                        if(message.equalsIgnoreCase("0")) {
                            try {
                                Activatedoor(off);
                            } catch (Exception ex) {
                                Logger.getLogger(SecurityMonitor.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            messageWin.writeMessage("DOOR OK");
                            di.setLampColorAndMessage("DOOR OK", 1); // Door is ok
                        }
                    } else {
                        messageWin.writeMessage("Security:: ALERT! Alarms deactivate");
                        di.setLampColorAndMessage("DOOR OFF", 0); // Alarms deactivate                
                        wi.setLampColorAndMessage("WINDOW OFF", 0); // Alarms deactivate                
                        mi.setLampColorAndMessage("MOVEMENT DETECTION OFF", 0); // Alarms deactivate
                        fi.setLampColorAndMessage("FIRE DETECTION OFF", 0); // Alarms deactivate  
                        si.setLampColorAndMessage("SPRINKLER DEACTIVATED", 0); // Alarms deactivate  

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
    }
    
    
    
    
  private void Activatedoor(boolean ON)throws Exception {
        // Here we create the event.
        String message;
        if (alarmsStatus){
            if (ON) {
                message = Component.DOOR_ON;
            }
            else {
                message = Component.DOOR_OFF;
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
        if (alarmsStatus){
            if (ON) {
                message = Component.WINDOW_ON;
            }
            else {
                message = Component.WINDOW_OFF;
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
        if (alarmsStatus){
            if (ON) {
                message = Component.MOVEMENT_ON;
            }
            else {
                message = Component.MOVEMENT_OFF;
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
        if (alarmsStatus){
            if (ON) {
                message = Component.FIRE_ON;
            }
            else {
                message = Component.FIRE_OFF;
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
        if (sprinklerStatus){
            if (ON) {
                message = Component.SPRINKLER_ON;
            }
            else {
                message = Component.SPRINKLER_OFF;
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
