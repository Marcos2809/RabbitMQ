/**
 * **************************************************************************************
 * File:HumidityController.java 
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
 * This class simulates a device that controls a humidifier and dehumidifier.
 * It polls the event manager for event ids = 4 and reacts to them by turning on or
 * off the humidifier/dehumidifier. The following command are valid strings for
 * controlling the humidifier and dehumidifier. 
 * H1 = humidifier on 
 * H0 = humidifier off 
 * D1 = dehumidifier on 
 * D0 = dehumidifier off
 * **************************************************************************************
 */

package controllers;


import com.rabbitmq.client.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HumidityController extends Controller implements Runnable {

    private boolean humidifierState = false;	// Heater state: false == off, true == on
    private boolean dehumidifierState = false;	// Dehumidifier state: false == off, true == on
    private String channelController, channelContReturn;
    private Channel channel;
    

    private HumidityController(String canalcontroller){
        this.channelController = canalcontroller;
        channelContReturn = "hr";
    }

    @Override
    public void run() {
        // Here we check to see if registration worked. If em is null then the
        // event manager interface was not properly created.
        //if (evtMgrI != null) {
         try {
              channel = super.conectorrabbit(channelController);
            }catch (Exception e) {
            System.out.println("Error al establecer la conexión:: " + e);
        }

            

        /**
         * ******************************************************************
         ** Here we start the main simulation loop gls
         * *******************************************************************
         */
        while (!isDone) {

            // If there are messages in the queue, we read through them.
            // We are looking for EventIDs = 4, this is a request to turn the
            // humidifier or dehumidifier on/off. Note that we get all the messages
            // at once... there is a 2.5 second delay between samples,.. so
            // the assumption is that there should only be a message at most.
            // If there are more, it is the last message that will effect the
            // output of the humidity as it would in reality.
             final Consumer consumer = new DefaultConsumer(channel) {
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws java.io.IOException {
                    String message = new String(body, "UTF-8");

                if (message.equalsIgnoreCase(HUMIDIFIER_ON)) { // heater on
                        humidifierState = true;
                        try {
                            confirmMessage(channelContReturn, String.valueOf(HUMIDIFIER_ON));
                        } catch (Exception ex) {
                           Logger.getLogger(HumidityController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    if (message.equalsIgnoreCase(HUMIDIFIER_OFF)) { // heater off
                       humidifierState = false;
                        try {
                            confirmMessage(channelContReturn, String.valueOf(HUMIDIFIER_OFF));
                        } catch (Exception ex) {
                            Logger.getLogger(HumidityController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    if (message.equalsIgnoreCase(DEHUMIDIFIER_ON)) { // chiller on
                        dehumidifierState = true;
                       try {
                            confirmMessage(channelContReturn, String.valueOf(DEHUMIDIFIER_ON));
                        } catch (Exception ex) {
                            Logger.getLogger(HumidityController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    if (message.equalsIgnoreCase(DEHUMIDIFIER_OFF)) { // chiller off
                        dehumidifierState = false;
                        try {
                            confirmMessage(channelContReturn, String.valueOf(DEHUMIDIFIER_OFF));
                        } catch (Exception ex) {
                           Logger.getLogger(HumidityController.class.getName()).log(Level.SEVERE, null, ex);
                           }
                    }

                }
            };
             try {
                    channel.basicConsume(channelController, true, consumer);
                } catch (IOException ex) {
                    Logger.getLogger(HumidityController.class.getName()).log(Level.SEVERE, null, ex);
                }
           // messageWin.writeMessage("\n\nSimulation Stopped. \n");

                    // Get rid of the indicators. The message panel is left for the
                    // user to exit so they can see the last message posted.
          //          humIndicator.dispose();
               //     dehumIndicator.dispose();

            // Update the lamp status
            if (humidifierState) {
                // Set to green, humidifier is on
            }
            else {
                // Set to black, humidifier is off
            }

            if (dehumidifierState) {
                // Set to green, dehumidifier is on
            }
            else {
                // Set to black, dehumidifier is off
            }
            try {
                Thread.sleep(delay);
                //Thread.yield();
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
       // Component.SERVER_IP = "127.0.0.1";
        HumidityController sensor = new HumidityController("HumidityControlador");
       // HumidityController sensor = HumidityController.getInstance();
        sensor.run();
    }
}
