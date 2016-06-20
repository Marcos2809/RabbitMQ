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
 
        
    }
    
    @Override
    public void run() {

        // Here we check to see if registration worked.
        
        try{
            channel = super.conectorrabbit(channelController);
        }catch(Exception e){
           
        }
            
           
        /**
         * ******************************************************************
         ** Here we start the main simulation loop
         * *******************************************************************
         */
        while (!isDone) {

            // If there are messages in the queue, we read through them.
            
              final Consumer consumer = new DefaultConsumer(channel) {
                public void handleDelivery(String consumerTag, Envelope envelope, 
                    AMQP.BasicProperties properties, byte[] body) throws java.io.IOException {
                    String message = new String(body, "UTF-8");

                  if (message.equalsIgnoreCase(DOOR_ON)) { 
                        DoorState = true;
                        try {
                            confirmMessage(channelContReturn, String.valueOf(DOOR_ON));
                        } catch (Exception ex) {
                            Logger.getLogger(SecurityController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    if (message.equalsIgnoreCase(DOOR_OFF)) { 
                        DoorState = false;
                        try {
                            confirmMessage(channelContReturn, String.valueOf(DOOR_OFF));
                        } catch (Exception ex) {
                            Logger.getLogger(SecurityController.class.getName()).log(Level.SEVERE, null, ex);
                        }                       
                    }
                      if (message.equalsIgnoreCase(WINDOW_ON)) { 
                        WindowState = true;
                        try {
                            confirmMessage(channelContReturn, String.valueOf(WINDOW_ON));
                        } catch (Exception ex) {
                            Logger.getLogger(SecurityController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    if (message.equalsIgnoreCase(WINDOW_OFF)) { 
                        WindowState = false;
                        try {
                            confirmMessage(channelContReturn, String.valueOf(WINDOW_OFF));
                        } catch (Exception ex) {
                            Logger.getLogger(SecurityController.class.getName()).log(Level.SEVERE, null, ex);
                        }                       
                    }
                     if (message.equalsIgnoreCase(MOVEMENT_ON)) { 
                        MovementState = true;
                        try {
                            confirmMessage(channelContReturn, String.valueOf(MOVEMENT_ON));
                        } catch (Exception ex) {
                            Logger.getLogger(SecurityController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    if (message.equalsIgnoreCase(MOVEMENT_OFF)) { 
                        MovementState = false;
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
