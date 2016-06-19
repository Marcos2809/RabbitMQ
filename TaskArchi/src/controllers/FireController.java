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
 * Date: 19/06/2016
 * **************************************************************************************
 */

package controllers;

import common.Component;
import instrumentation.Indicator;
import instrumentation.MessageWindow;
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
            System.out.println("Registered with the event manager.");

            /* Now we create the temperature control status and message panel
             ** We put this panel about 1/3 the way down the terminal, aligned to the left
             ** of the terminal. The status indicators are placed directly under this panel
             */
            float winPosX = 0.0f; 	//This is the X position of the message window in terms 
            //of a percentage of the screen height
            float winPosY = 0.3f; 	//This is the Y position of the message window in terms 
            //of a percentage of the screen height 

            MessageWindow messageWin = new MessageWindow("Fire Controller Status Console", winPosX, winPosY);

            // Put the status indicators under the panel...
            Indicator FireIndicator = new Indicator("Fire OFF", messageWin.getX(), messageWin.getY() + messageWin.height());
            Indicator SprinklerIndicator = new Indicator("Sprinkler OFF", messageWin.getX() + (FireIndicator.width() * 2), messageWin.getY() + messageWin.height());

            messageWin.writeMessage("Registered with the event manager.");

            /*try {
                messageWin.writeMessage("   Participant id: " + evtMgrI.getMyId());
                messageWin.writeMessage("   Registration Time: " + evtMgrI.getRegistrationTime());
            }
            catch (Exception e) {
                System.out.println("Error:: " + e);
            }*/

            /**
             * ******************************************************************
             ** Here we start the main simulation loop
             * *******************************************************************
             */
            while (!isDone) {
              /*try {
                  evtMgrI.getEvent();
              }
              catch (Exception e){
                  
              }*/

                // If there are messages in the queue, we read through them.
                // We are looking for EventIDs = 5, this is a request to turn the
                // heater or chiller on. Note that we get all the messages
                // at once... there is a 2.5 second delay between samples,.. so
                // the assumption is that there should only be a message at most.
                // If there are more, it is the last message that will effect the
                // output of the temperature as it would in reality.
//mod                int qlen = queue.getSize();
                //System.out.println(evtMgrI.returnMessage()+ "Valor que yo regreso");

 //mod               for (int i = 0; i < qlen; i++) {
//mod                    evt = queue.getEvent();
                   final Consumer consumer = new DefaultConsumer(channel) {
                    public void handleDelivery(String consumerTag, Envelope envelope, 
                        AMQP.BasicProperties properties, byte[] body) throws java.io.IOException {
			String message = new String(body, "UTF-8");
                        if (message.equalsIgnoreCase(FIRE_ON)) { // fire on
                            FireState = true;
                            messageWin.writeMessage("Received fire on event");
                            try {
                                confirmMessage(channelContReturn, String.valueOf(FIRE_ON));
                            } catch (Exception ex) {
                                Logger.getLogger(FireController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        
                        if (message.equalsIgnoreCase(FIRE_OFF)) { // fire off
                            FireState = false;
                            messageWin.writeMessage("Received fire off event");
                            try {
                                confirmMessage(channelContReturn, String.valueOf(FIRE_OFF));
                            } catch (Exception ex) {
                                Logger.getLogger(FireController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                        if (message.equalsIgnoreCase(SPRINKLER_ON)) { // sprinkler on
                            SprinklerState = true;
                            messageWin.writeMessage("Received sprinkler on event");
                            try {
                               confirmMessage(channelContReturn, String.valueOf(SPRINKLER_ON));
                            } catch (Exception ex) {
                                Logger.getLogger(FireController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                        if (message.equalsIgnoreCase(SPRINKLER_OFF)) { // sprinkler off
                            SprinklerState = false;
                            messageWin.writeMessage("Received sprinkler off event");
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
                    

                // Update the lamp status
                if (FireState) {
                    // Set to green, heater is on
                    FireIndicator.setLampColorAndMessage("FIRE DETECTED", 3);
                }
                else {
                    // Set to black, heater is off
                    FireIndicator.setLampColorAndMessage("NOT FIRE DETECTED", 1);
                }
                if (SprinklerState) {
                    // Set to green, chiller is on
                    SprinklerIndicator.setLampColorAndMessage("SPRINKLER ON", 1);
                }
                else {
                    // Set to black, chiller is off
                    SprinklerIndicator.setLampColorAndMessage("SPRINKLER OFF", 0);
                }
                try {
                    Thread.sleep(delay);
                }
                catch (Exception e) {
                    System.out.println("Sleep error:: " + e);
                }
            }
        //}
//        else {
       //     System.out.println("Unable to register with the event manager.\n\n");
     //   }
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
