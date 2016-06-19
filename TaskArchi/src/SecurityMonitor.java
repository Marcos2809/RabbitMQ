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
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SecurityMonitor extends Thread {

     private Indicator wi;               
     private Indicator di;               
     private Indicator mi;               
     private Indicator fi;               
     private Indicator si;
     String DoorStatus="OK";
    boolean registered = true;				// Signifies that this class is registered with an event manager.     
    private Connection connection;
    private Channel channel, canalDoor, canalWindow, canalMotionSensor, canalDoorControlador;
    MessageWindow messageWin = null;   
    boolean alarmsStatus= true;     
    
    public SecurityMonitor() {
        // event manager is on the local system
        try{
            canalDoor = conectorrabbit("DoorSensor");
            canalDoorControlador = conectorrabbit("DoorControlador");
          
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
        int delay = 2500;			// The loop delay (1 second)
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
          // } // Dehumidifier
} // ECSMonitor
