/**
 * File:DoorSensor.java 
 * Course: Software Architecture 
 * Project: Event Architectures
 * Institution: CIMAT
 * Reviewer: Perla Velasco Elizondo
 * Update by: Equipo MEETMECORP
 * Date: 29/04/2016
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensors;

import common.Component;
import com.rabbitmq.client.*;
import instrumentation.MessageWindow;
import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import controllers.TemperatureController;


/**
 *
 * @author YMGM,MGL,JMMM
 */
public class MovementSensor extends Sensor implements Runnable {
    private int CurrentState;
        boolean MovementState = false;
        private String channelSensor,channelContReturn;
    private Channel channel,channel2;
 
     private MovementSensor(String channelSensor){
        this.channelSensor = channelSensor;
        channelContReturn = "DR";
    }
    @Override
     public void run(){
         try {
              channel = super.conectorrabbit(channelSensor);
              channel2 = super.conectorrabbit(channelContReturn);
            }catch (Exception e) {
            System.out.println("Error al establecer la conexión:: " + e);
        } 
             float winPosX= 0.5f;
             float winPosY= 0.3f;
            
             MessageWindow messageWin = new MessageWindow("MovementSensor", winPosX,winPosY);
             messageWin.writeMessage ("Registered with the event manager.");

             messageWin.writeMessage("\n Initializating simulation ... ");
       
        
     
     while (!isDone)
     {
         
         CurrentState = getRandomNumberent3();
         
       messageWin.writeMessage("Current State:: " + CurrentState);
       final Consumer consumer = new DefaultConsumer(channel2) {
                     public void handleDelivery(String consumerTag, Envelope envelope, 
                             AMQP.BasicProperties properties, byte[] body) throws java.io.IOException {
                         String message = new String(body, "UTF-8");
         
          //Get the message queue
          // Get the message queue
         // If there are messages in the queue, we read through them.
        // We are looking for EventIDs = -5, this means the the heater
        // or chiller has been turned on/off. Note that we get all the messages
        // at once... there is a 2.5 second delay between samples,.. so
        // the assumption is that there should only be a message at most.
        // If there are more, it is the last message that will effect the
        // output of the temperature as it would in reality.

                 if (message.equalsIgnoreCase(MOVEMENT_ON)) // 
                        {
                                MovementState = true;

                         } // if

                        if (message.equalsIgnoreCase(MOVEMENT_OFF)) // 
                        {
                                MovementState = false;
                         } // if
                        //CurrentState = evtMgrI.getEvent()
                     }
                // Here we wait for a 2.5 seconds before we start the next sample
                };
                try {
                    channel2.basicConsume(channelContReturn, true, consumer);
                } catch (IOException e) { 
             }
            try {
                // Post the current temperature
                postEvent(channelSensor, String.valueOf(CurrentState));
            } catch (IOException e) {
            } catch (TimeoutException e) {
            }// Now we trend the relative humidity according to the status of the
                // humidifier/dehumidifier controller.
               // Here we wait for a 2.5 seconds before we start the next sample
               
                 try {
                    Thread.sleep(delay);
                }
                catch (Exception e) {
                  messageWin.writeMessage("Sleep error:: " + e);
                } 
            } 
        //}
        //else {
            //System.out.println("Unable to register with the event manager.\n\n");
       // } 
     }  /**
     * Start this sensor
     * 
     * @param args IP address of the event manager (on command line). 
     * If blank, it is assumed that the event manager is on the local machine.
     */
    public static void main(String args[]) {
        //if(args[0] != null) Component.SERVER_IP = args[0];
        //Component.SERVER_IP = "127.0.0.1";
        //DoorSensor sensor = DoorSensor.getInstance();
        MovementSensor sensor = new MovementSensor("DoorSensor");
        sensor.run();
    }

} 
