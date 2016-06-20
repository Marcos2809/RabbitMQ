/**
 * **************************************************************************************
 * File:TemperatureSensor.java 
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
 * This class simulates a temperature sensor. It polls the event manager for
 * events corresponding to changes in state of the heater or chiller and reacts
 * to them by trending the ambient temperature up or down. The current ambient
 * room temperature is posted to the event manager.
 * **************************************************************************************
 */
package sensors;

import com.rabbitmq.client.*;
import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TemperatureSensor extends Sensor implements Runnable {

    private boolean heaterState = false;	// Heater state: false == off, true == on
    private boolean chillerState = false;	// Chiller state: false == off, true == on
    private float currentTemperature;		// Current simulated ambient room temperature
    private String channelSensor,channelContReturn;
    private Channel channel,channel2;


    private TemperatureSensor(String channelSensor) {
        this.channelSensor = channelSensor;
        channelContReturn = "contReturn";
    }

    public void run() {
        // Here we check to see if registration worked. If ef is null then the
        // event manager interface was not properly created.
        //   if (evtMgrI != null) {
        
        try{
            channel = super.conectorrabbit(channelSensor);
            channel2 = super.conectorrabbit(channelContReturn);
        }catch(Exception e){

        }
            
        currentTemperature = (float) 50.00;
        if (coinToss()) {
            driftValue = getRandomNumber() * (float) -1.0;
        }
        else {
            driftValue = getRandomNumber();
        } // if

        
        /**
         * ******************************************************************
         ** Here we start the main simulation loop
         * *******************************************************************
         */

        while (!isDone) {
            // Post the current temperature
           
            final Consumer consumer = new DefaultConsumer(channel2) {
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws java.io.IOException {
                    String message = new String(body, "UTF-8");

                    if (message.equalsIgnoreCase(HEATER_ON)) // heater on
                    {
                        heaterState = true;
                    } // if

                    if (message.equalsIgnoreCase(HEATER_OFF)) // heater off
                    {
                        heaterState = false;
                    } // if

                    if (message.equalsIgnoreCase(CHILLER_ON)) // chiller on
                    {
                        chillerState = true;
                    } // if

                    if (message.equalsIgnoreCase(CHILLER_OFF)) // chiller off
                    {
                        chillerState = false;
                  } // if
                
                }
            };

            try {
                channel2.basicConsume(channelContReturn, true, consumer);
            } catch (IOException ex) {
          ///     Logger.getLogger(TemperatureController.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                // Post the current temperature
                postEvent(channelSensor, String.valueOf(currentTemperature));
            } catch (IOException ex) {
           //     Logger.getLogger(TemperatureSensor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TimeoutException ex) {
             //   Logger.getLogger(TemperatureSensor.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Now we trend the temperature according to the status of the
            // heater/chiller controller.
            if (heaterState) {
                currentTemperature += getRandomNumber();
            } // if heater is on
            if (!heaterState && !chillerState) {
                currentTemperature += driftValue;
            } // if both the heater and chiller are off
            if (chillerState) {
                currentTemperature -= getRandomNumber();
            } // if chiller is on
            // Here we wait for a 2.5 seconds before we start the next sample
            try {
                Thread.sleep(delay);
            } // try
            catch (Exception e) {
            } // catch
        } // while
    }


    /**
     * Start this sensor
     * 
     * @param args IP address of the event manager (on command line). 
     * If blank, it is assumed that the event manager is on the local machine.
     */
    public static void main(String args[]) {
        //Component.SERVER_IP = "127.0.0.1";
        TemperatureSensor sensor = new TemperatureSensor("TempSensor");
        sensor.run();
    }

}
