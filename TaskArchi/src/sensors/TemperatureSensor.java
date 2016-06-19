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

import common.Component;
import com.rabbitmq.client.*;
import instrumentation.MessageWindow;
import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import controllers.TemperatureController;

public class TemperatureSensor extends Sensor implements Runnable {

    private boolean heaterState = false;	// Heater state: false == off, true == on
    private boolean chillerState = false;	// Chiller state: false == off, true == on
    private float currentTemperature;		// Current simulated ambient room temperature
    private String channelSensor,channelContReturn;
    private Channel channel,channel2;


    public TemperatureSensor(String channelSensor) {
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
            // We create a message window. Note that we place this panel about 1/2 across 
            // and 1/3 down the screen			
            float winPosX = 0.5f; 	//This is the X position of the message window in terms 
            //of a percentage of the screen height
            float winPosY = 0.3f; 	//This is the Y position of the message window in terms 
            //of a percentage of the screen height 

            MessageWindow messageWin = new MessageWindow("Temperature Sensor", winPosX, winPosY);
            messageWin.writeMessage("Registered with the event manager.");
/*
            try {
                messageWin.writeMessage("   Participant id: " + evtMgrI.getMyId());
                messageWin.writeMessage("   Registration Time: " + evtMgrI.getRegistrationTime());
            } // try
            catch (Exception e) {
                messageWin.writeMessage("Error:: " + e);
            } // catch
*/
            messageWin.writeMessage("\nInitializing Temperature Simulation::");
            currentTemperature = (float) 50.00;
            if (coinToss()) {
                driftValue = getRandomNumber() * (float) -1.0;
            }
            else {
                driftValue = getRandomNumber();
            } // if

            messageWin.writeMessage("   Initial Temperature Set:: " + currentTemperature);
            messageWin.writeMessage("   Drift Value Set:: " + driftValue);

            /**
             * ******************************************************************
             ** Here we start the main simulation loop
             * *******************************************************************
             */
            messageWin.writeMessage("Beginning Simulation... ");

            while (!isDone) {
                // Post the current temperature
                /*postEvent(evtMgrI, TEMPERATURE, currentTemperature);
                messageWin.writeMessage("Current Temperature::  " + currentTemperature + " F");
                */
                // Get the message queue
                /*try {
                    evtMgrI.getEvent();
                } // try
                catch (Exception e) {
                    messageWin.writeMessage("Error getting event queue::" + e);
                } // catch
                */
                // If there are messages in the queue, we read through them.
                // We are looking for EventIDs = -5, this means the the heater
                // or chiller has been turned on/off. Note that we get all the messages
                // at once... there is a 2.5 second delay between samples,.. so
                // the assumption is that there should only be a message at most.
                // If there are more, it is the last message that will effect the
                // output of the temperature as it would in reality.
                //System.out.println(evtMgrI.returnid()+"Valor del ID" );
               // System.out.println(evtMgrI.returnMessage());
               // System.out.println(TEMPERATURE_SENSOR+"Valor del Sensor" );
//                System.out.println(evtMgrI.returnMessage()+"Valor del mensade del sensor" );
                
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

                    // If the event ID == 99 then this is a signal that the simulation
                    // is to end. At this point, the loop termination flag is set to
                    // true and this process unregisters from the event manager.
                     /*if (evtMgrI.returnid() == END) {
                        isDone = true;
                        messageWin.writeMessage("\n\nSimulation Stopped. \n");
                    } 
                    */
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
                messageWin.writeMessage("Current Temperature::  " + currentTemperature + " F");

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
                    messageWin.writeMessage("Sleep error:: " + e);
                } // catch
            } // while
       // }
     //   else {
         //   System.out.println("Unable to register with the event manager.\n\n");
     //   } // if
    }


    /**
     * Start this sensor
     * 
     * @param args IP address of the event manager (on command line). 
     * If blank, it is assumed that the event manager is on the local machine.
     */
      public static void stop() {
      //  if(args[0] != null) Component.SERVER_IP = args[0];
       // Component.SERVER_IP = "127.0.0.1";
        System.exit(0);
    }
    
    public static void main(String args[]) {
        //Component.SERVER_IP = "127.0.0.1";
        TemperatureSensor sensor = new TemperatureSensor("TempSensor");
        sensor.run();
    }

}
