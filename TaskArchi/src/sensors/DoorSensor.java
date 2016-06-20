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

import com.rabbitmq.client.*;
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
public class DoorSensor extends Sensor implements Runnable {
    private int CurrentState;
    private boolean DoorState = false;
        private String channelSensor,channelContReturn;
    private Channel channel,channel2;
 
     private DoorSensor(String channelSensor){
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
        
     
        while (!isDone){

            CurrentState = getRandomNumberent();
            
            //Get the message queue

            final Consumer consumer = new DefaultConsumer(channel2) {
                public void handleDelivery(String consumerTag, Envelope envelope, 
                        AMQP.BasicProperties properties, byte[] body) throws java.io.IOException {
                    String message = new String(body, "UTF-8");

             
                    if (message.equalsIgnoreCase(DOOR_ON)){
                            DoorState = true;

                        } // if

                    if (message.equalsIgnoreCase(DOOR_OFF)){
                            DoorState = false;
                    } 
                }
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
            }

            try {
                Thread.sleep(delay);
            }
            catch (Exception e) {
            } 
        } 
    }  
     
     /**
     * Start this sensor
     * 
     * @param args IP address of the event manager (on command line). 
     * If blank, it is assumed that the event manager is on the local machine.
     */
    public static void main(String args[]) {
        //if(args[0] != null) Component.SERVER_IP = args[0];
        //Component.SERVER_IP = "127.0.0.1";
        //DoorSensor sensor = DoorSensor.getInstance();
        DoorSensor sensor = new DoorSensor("DoorSensor");
        sensor.run();
    }

} 
