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
 * This class simulates a device that controls a heater and chiller. 
 * It polls the event manager for event ids = 5 and reacts to them by turning 
 * on or off the heater or chiller. The following command are valid strings for 
 * controlling the heater and chiller.
 * H1 = heater on 
 * H0 = heater off 
 * C1 = chiller on 
 * C0 = chiller off
 * **************************************************************************************
 */

package controllers;


import com.rabbitmq.client.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TemperatureController extends Controller implements Runnable {

    private boolean heaterState = false;	// Heater state: false == off, true == on
    private boolean chillerState = false;	// Chiller state: false == off, true == on
    private String channelController, channelContReturn;
    private Channel channel;
    
    
    public TemperatureController(String channelController){
        this.channelController = channelController;
        channelContReturn = "contReturn";
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
                        if (message.equalsIgnoreCase(HEATER_ON)) { // heater on
                            heaterState = true;
                            try {
                                confirmMessage(channelContReturn, String.valueOf(HEATER_ON));
                            } catch (Exception ex) {
                                Logger.getLogger(TemperatureController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        
                        if (message.equalsIgnoreCase(HEATER_OFF)) { // heater off
                            heaterState = false;
                            try {
                                confirmMessage(channelContReturn, String.valueOf(HEATER_OFF));
                            } catch (Exception ex) {
                                Logger.getLogger(TemperatureController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                        if (message.equalsIgnoreCase(CHILLER_ON)) { // chiller on
                            chillerState = true;
                            try {
                               confirmMessage(channelContReturn, String.valueOf(CHILLER_ON));
                            } catch (Exception ex) {
                                Logger.getLogger(TemperatureController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                        if (message.equalsIgnoreCase(CHILLER_OFF)) { // chiller off
                            chillerState = false;
                            try {
                                confirmMessage(channelContReturn, String.valueOf(CHILLER_OFF));
                            } catch (Exception ex) {
                                Logger.getLogger(TemperatureController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        
                    }
                };
                try {
                    channel.basicConsume(channelController, true, consumer);
                } catch (IOException ex) {
                    Logger.getLogger(TemperatureController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                try {
                    Thread.sleep(delay);
                }
                catch (Exception e) {
                    System.out.println("Sleep error:: " + e);
                }
            }
    
    }

      public static void stop() {
      //  if(args[0] != null) Component.SERVER_IP = args[0];
       // Component.SERVER_IP = "127.0.0.1";
        System.exit(0);
    }
    
   
    public static void main(String args[]) {
      //  if(args[0] != null) Component.SERVER_IP = args[0];
       // Component.SERVER_IP = "127.0.0.1";
        TemperatureController sensor = new TemperatureController("TempControlador");
        sensor.run();
    }

} // TemperatureController
