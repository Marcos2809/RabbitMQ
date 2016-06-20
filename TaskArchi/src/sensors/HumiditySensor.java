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

import com.rabbitmq.client.*;
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
      
        relativeHumidity = (float) 50.00;
        if (coinToss()) {
            driftValue = getRandomNumber() * (float) -1.0;
        }
        else {
            driftValue = getRandomNumber();
        }

        /**
         * ******************************************************************
         ** Here we start the main simulation loop
         * *******************************************************************
         */
        
        while (!isDone) {

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
        HumiditySensor sensor = new HumiditySensor("HumiditySensor");
        sensor.run();
    }

} 
