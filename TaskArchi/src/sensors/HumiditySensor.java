/**
 * **************************************************************************************
 * File:HumiditySensor.java 
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
 * This class simulates a humidity sensor. It polls the event manager for events
 * corresponding to changes in state of the humidifier or dehumidifier and
 * reacts to them by trending the relative humidity up or down. The current
 * relative humidity is posted to the event manager.
 * **************************************************************************************
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

public class HumiditySensor extends Sensor implements Runnable {

    private boolean humidifierState = false;	// Humidifier state: false == off, true == on
    private boolean dehumidifierState = false;	// Dehumidifier state: false == off, true == on
    private float relativeHumidity;		// Current simulated ambient room humidity
    private String channelSensor,channelContReturn;
    private Channel channel,channel2;
    
    //private static HumiditySensor INSTANCE = new HumiditySensor();
    
    private HumiditySensor(String channelSensor){
        this.channelSensor = channelSensor;
        channelContReturn = "hr";
    }

    @Override
    public void run() {
        // Here we check to see if registration worked. If ef is null then the
        // event manager interface was not properly created.
        try {
              channel = super.conectorrabbit(channelSensor);
              channel2 = super.conectorrabbit(channelContReturn);
            }catch (Exception e) {
            System.out.println("Error al establecer la conexión:: " + e);
        } // catch
      // if (evtMgrI != null) {

            // We create a message window. Note that we place this panel about 1/2 across 
            // and 2/3s down the screen
            float winPosX = 0.5f; 	//This is the X position of the message window in terms 
            //of a percentage of the screen height
            float winPosY = 0.60f;	//This is the Y position of the message window in terms 
            //of a percentage of the screen height 

            MessageWindow messageWin = new MessageWindow("Humidity Sensor", winPosX, winPosY);
            messageWin.writeMessage("Registered with RABBIT.");
/*
            try {
                messageWin.writeMessage("   Participant id: " + evtMgrI.getMyId());
                messageWin.writeMessage("   Registration Time: " + evtMgrI.getRegistrationTime());
            } 
            catch (Exception e) {
                messageWin.writeMessage("Error:: " + e);
            } 
*/
            messageWin.writeMessage("\nInitializing Humidity Simulation::");
            relativeHumidity = getRandomNumber() * (float) 100.00;
            if (coinToss()) {
                driftValue = getRandomNumber() * (float) 0.0;
            }
            else {
                driftValue = getRandomNumber();
            } 
            messageWin.writeMessage("   Initial Humidity Set:: " + relativeHumidity);
            messageWin.writeMessage("   Drift Value Set:: " + driftValue);
            
            /**
             * ******************************************************************
             ** Here we start the main simulation loop
             * *******************************************************************
             */
            messageWin.writeMessage("Beginning Simulation... ");
            while (!isDone) {
                // Post the current relative humidity
                //postEvent(evtMgrI, HUMIDITY, relativeHumidity);
                // If there are messages in the queue, we read through them.
                // We are looking for EventIDs = -4, this means the the humidify or
                // dehumidifier has been turned on/off. Note that we get all the messages
                // from the queue at once... there is a 2.5 second delay between samples,..
                // so the assumption is that there should only be a message at most.
                // If there are more, it is the last message that will effect the
                // output of the humidity as it would in reality.
      
                 final Consumer consumer = new DefaultConsumer(channel2) {
                     public void handleDelivery(String consumerTag, Envelope envelope, 
                             AMQP.BasicProperties properties, byte[] body) throws java.io.IOException {
                         String message = new String(body, "UTF-8");
                         if (message.equalsIgnoreCase(HUMIDIFIER_ON)) // humidifier on
                        {
                         
                            humidifierState = true;
                        }
                        if (message.equalsIgnoreCase(HUMIDIFIER_OFF)) // humidifier off
                        {
                
                            humidifierState = false;
                        }
                        if (message.equalsIgnoreCase(DEHUMIDIFIER_ON)) // dehumidifier on
                        {
                            dehumidifierState = true;
                            
                        }
                        if (message.equalsIgnoreCase(DEHUMIDIFIER_OFF)) // dehumidifier off
                        {
                          
                            dehumidifierState = false;
                        }
                     }
                 };
                try {
                    channel2.basicConsume(channelContReturn, true, consumer);
                } catch (IOException e) { 
             }
                
            try {
                // Post the current temperature
                postEvent(channelSensor, String.valueOf(relativeHumidity));
            } catch (IOException e) {
            } catch (TimeoutException e) {
            }
                //} catch (IOException ex) {
                  //  Logger.getLogger(HumiditySensor.class.getName()).log(Level.SEVERE, null, ex);
                //} 
                messageWin.writeMessage("Current Relative Humidity:: " + relativeHumidity + "%");
                // Get the message queue
                // Get the message queue
                
                
                    // Now we trend the relative humidity according to the status of the
                // humidifier/dehumidifier controller.
                if (humidifierState) {
                    relativeHumidity += getRandomNumber();
                } // if humidifier is on

                if (!humidifierState && !dehumidifierState) {
                    relativeHumidity += driftValue;
                } // if both the humidifier and dehumidifier are off

                if (dehumidifierState) {
                    relativeHumidity -= getRandomNumber();
                } // if dehumidifier is on

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
    }
    /**
     * Start this sensor
     * 
     * @param args IP address of the event manager (on command line). 
     * If blank, it is assumed that the event manager is on the local machine.
     */
    public static void main(String args[]) {
        HumiditySensor sensor = new HumiditySensor("HumiditySensor");
        sensor.run();
    }

} 
