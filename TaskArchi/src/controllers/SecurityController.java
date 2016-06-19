/**
 * **************************************************************************************
 * File:SecurityController.java 
 * Course: Software Architecture 
 * Project: Event Architectures
 * Institution: CIMAT
 * Reviewer: Perla Velasco Elizondo
 * Update by: Equipo MEETMECORP
 * Date: 29/04/2016
 * 
 **/
package controllers;

import common.Component;
import instrumentation.Indicator;
import instrumentation.MessageWindow;
import com.rabbitmq.client.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SecurityController  extends Controller implements Runnable {

    boolean WindowState = false;	// Heater state: false == off, true == on
    boolean DoorState = false;	// Chiller state: false == off, true == on
    boolean MovementState = false;
    int Delay = 500;
    boolean isDone = false;
    private String channelController, channelContReturn, channelContReturn2;
    private Channel channel;
    
    //private static SecurityController INSTANCE = new SecurityController();
    private SecurityController(String channelController){
        this.channelController = channelController;
        channelContReturn = "DR";
        // WINDOWS 
        
    }
    
    @Override
   public void run() {

        // Here we check to see if registration worked. If ef is null then the
        // event manager interface was not properly created.
        //if (evtMgrI != null) {
        try{
            channel = super.conectorrabbit(channelController);
        }catch(Exception e){
           
        }
            System.out.println("Registered with the event manager.");

            /* Now we create the temperature control status and message panel
             ** We put this panel about 1/3 the way down the terminal, aligned to the left
             ** of the terminal. The status indicators are placed directly under this panel
             */
            float winPosX = 0.0f; 	//This is the X position of the message window in terms 
            //of a percentage of the screen height
            float winPosY = 0.3f; 	//This is the Y position of the message window in terms 
            //of a percentage of the screen height 

            MessageWindow messageWin = new MessageWindow("Security Controller Status Console", winPosX, winPosY);

            // Put the status indicators under the panel...
            Indicator wi = new Indicator("WindowsState OFF", messageWin.getX(), messageWin.getY() + messageWin.height());
            Indicator di = new Indicator("DoorState OFF", messageWin.getX() + (wi.width() * 3), messageWin.getY() + messageWin.height());
            Indicator mi= new Indicator ("MovementState OFF", messageWin.getX() + (di.width() * 6), messageWin.getY() + messageWin.height());
            messageWin.writeMessage("Registered with the event manager.");

           
            /**
             * ******************************************************************
             ** Here we start the main simulation loop
             * *******************************************************************
             */
            while (!isDone) {
       
                // If there are messages in the queue, we read through them.
                // We are looking for EventIDs = 5, this is a request to turn the
                // heater or chiller on. Note that we get all the messages
                // at once... there is a 2.5 second delay between samples,.. so
                // the assumption is that there should only be a message at most.
                // If there are more, it is the last message that will effect the
                // output of the temperature as it would in reality.
                  final Consumer consumer = new DefaultConsumer(channel) {
                    public void handleDelivery(String consumerTag, Envelope envelope, 
                        AMQP.BasicProperties properties, byte[] body) throws java.io.IOException {
                        String message = new String(body, "UTF-8");
                       
                      //System.out.println("MENSJA DEL A COAL:::"+message);
                      if (message.equalsIgnoreCase(DOOR_ON)) { // chiller on
                            DoorState = true;
                            messageWin.writeMessage("Received security event");
                            try {
                                confirmMessage(channelContReturn, String.valueOf(DOOR_ON));
                            } catch (Exception ex) {
                                Logger.getLogger(SecurityController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                        if (message.equalsIgnoreCase(DOOR_OFF)) { // chiller off
                            DoorState = false;
                            messageWin.writeMessage("Received security off event");
                            try {
                                confirmMessage(channelContReturn, String.valueOf(DOOR_OFF));
                            } catch (Exception ex) {
                                Logger.getLogger(SecurityController.class.getName()).log(Level.SEVERE, null, ex);
                            }                       
                        }
                          if (message.equalsIgnoreCase(WINDOW_ON)) { // chiller on
                            WindowState = true;
                            messageWin.writeMessage("Received security event");
                            try {
                                confirmMessage(channelContReturn, String.valueOf(WINDOW_ON));
                            } catch (Exception ex) {
                                Logger.getLogger(SecurityController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                        if (message.equalsIgnoreCase(WINDOW_OFF)) { // chiller off
                            WindowState = false;
                            messageWin.writeMessage("Received security off event");
                            try {
                                confirmMessage(channelContReturn, String.valueOf(WINDOW_OFF));
                            } catch (Exception ex) {
                                Logger.getLogger(SecurityController.class.getName()).log(Level.SEVERE, null, ex);
                            }                       
                        }
                         if (message.equalsIgnoreCase(MOVEMENT_ON)) { // chiller on
                            MovementState = true;
                            messageWin.writeMessage("Received security event");
                            try {
                                confirmMessage(channelContReturn, String.valueOf(MOVEMENT_ON));
                            } catch (Exception ex) {
                                Logger.getLogger(SecurityController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                        if (message.equalsIgnoreCase(MOVEMENT_OFF)) { // chiller off
                            MovementState = false;
                            messageWin.writeMessage("Received security off event");
                            try {
                                confirmMessage(channelContReturn, String.valueOf(MOVEMENT_OFF));
                            } catch (Exception ex) {
                                Logger.getLogger(SecurityController.class.getName()).log(Level.SEVERE, null, ex);
                            }                       
                        }
                  
                    
                        
                    }
                 };
                   try {
                    channel.basicConsume(channelController, true, consumer);
                } catch (IOException ex) {
                    Logger.getLogger(SecurityController.class.getName()).log(Level.SEVERE, null, ex);
                }

               if (DoorState) {
                    // Set to green, door is on
                    di.setLampColorAndMessage("CHECK DOOR", 3);
                }
                 else {            
                    // Set to black, chiller is off
                    di.setLampColorAndMessage("DOOR FINE", 1);
                }
                 if (WindowState) {
                    // Set to green, door is on
                    wi.setLampColorAndMessage("CHECK WINDOWS", 3);
                }
                else{            
                    // Set to black, chiller is off
                    wi.setLampColorAndMessage("WINDOW FINE", 1);
                }
                if (MovementState) {
                    // Set to green, chiller is on
                    mi.setLampColorAndMessage("EXISTS MOVEMENT ", 3);
                }
                else {
                    // Set to black, chiller is off
                    mi.setLampColorAndMessage("NOT MOVEMENT", 1);
                }
                
                try {
                    Thread.sleep(Delay);
                }
                catch (Exception e) {
                    System.out.println("Sleep error:: " + e);
                }
            }
    }
    /**
     * Start this controller
     * 
     * @param args IP address of the event manager (on command line). 
     * If blank, it is assumed that the event manager is on the local machine.
     */
    public static void main(String args[]) {
       // if(args[0] != null) Component.SERVER_IP = args[0];
     //  Component.SERVER_IP = "127.0.0.1";
        //SecurityController sensor = SecurityController.getInstance();
        SecurityController sensor = new SecurityController("DoorControlador");
        sensor.run();
    }

} // SecurityController
