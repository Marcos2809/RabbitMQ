/**
 * **************************************************************************************
 * File:TemperatureController.java 
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
 */

package controllers;


import com.rabbitmq.client.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FireController extends Controller implements Runnable {

    private boolean FireState = false;	
    private boolean SprinklerState = false;	
    private String channelController, channelContReturn;
    private Channel channel;
    
    
    private FireController(String channelController){
        this.channelController = channelController;
        channelContReturn = "DR";
    }

    @Override
    public void run() {

        // Here we check to see if registration worked. If ef is null then the
        // event manager interface was not properly created.
        //  if (evtMgrI != null) {
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
            
            final Consumer consumer = new DefaultConsumer(channel) {
             public void handleDelivery(String consumerTag, Envelope envelope, 
                 AMQP.BasicProperties properties, byte[] body) throws java.io.IOException {
                 String message = new String(body, "UTF-8");
                 if (message.equalsIgnoreCase(FIRE_ON)) { // fire on
                     FireState = true;
                     try {
                         confirmMessage(channelContReturn, String.valueOf(FIRE_ON));
                     } catch (Exception ex) {
                         Logger.getLogger(FireController.class.getName()).log(Level.SEVERE, null, ex);
                     }
                 }

                 if (message.equalsIgnoreCase(FIRE_OFF)) { // fire off
                     FireState = false;
                     try {
                         confirmMessage(channelContReturn, String.valueOf(FIRE_OFF));
                     } catch (Exception ex) {
                         Logger.getLogger(FireController.class.getName()).log(Level.SEVERE, null, ex);
                     }
                 }

                 if (message.equalsIgnoreCase(SPRINKLER_ON)) { // sprinkler on
                     SprinklerState = true;
                     try {
                        confirmMessage(channelContReturn, String.valueOf(SPRINKLER_ON));
                     } catch (Exception ex) {
                         Logger.getLogger(FireController.class.getName()).log(Level.SEVERE, null, ex);
                     }
                 }

                 if (message.equalsIgnoreCase(SPRINKLER_OFF)) { // sprinkler off
                     SprinklerState = false;
                     try {
                         confirmMessage(channelContReturn, String.valueOf(SPRINKLER_OFF));
                     } catch (Exception ex) {
                         Logger.getLogger(FireController.class.getName()).log(Level.SEVERE, null, ex);
                     }
                 }

             }
            };
            try {
                channel.basicConsume(channelController, true, consumer);
            } catch (IOException ex) {
                Logger.getLogger(FireController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try {
                Thread.sleep(delay);
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
      //  if(args[0] != null) Component.SERVER_IP = args[0];
       // Component.SERVER_IP = "127.0.0.1";
        FireController sensor = new FireController("DoorControlador");
        sensor.run();
    }

} // TemperatureController
