/**
 * **************************************************************************************
 * File:HumidityController.java 
 * Course: Software Architecture 
 * Project: Event Architectures
 * Reviewer: Perla Velasco Elizondo
 * Update: Equipo MEETMECORP
 * Institution: CIMAT
 * Date: 29/04/2016
 */
package controllers;

import common.Component;
import instrumentation.Indicator;
import instrumentation.MessageWindow;

public class Alarmas extends Controller implements Runnable {

    private boolean humidifierState = false;	// Heater state: false == off, true == on
    private boolean dehumidifierState = false;	// Dehumidifier state: false == off, true == on

    private static Alarmas INSTANCE = new Alarmas();

    private Alarmas() {
    }

    @Override
    public void run() {
        // Here we check to see if registration worked. If em is null then the
        // event manager interface was not properly created.
        //if (evtMgrI != null) {
            System.out.println("Registered with the event manager.");

            /* Now we create the humidity control status and message panel
             ** We put this panel about 2/3s the way down the terminal, aligned to the left
             ** of the terminal. The status indicators are placed directly under this panel
             */
            float winPosX = 0.0f; 	//This is the X position of the message window in terms 
            //of a percentage of the screen height
            float winPosY = 0.60f;	//This is the Y position of the message window in terms 
            //of a percentage of the screen height 

            MessageWindow messageWin = new MessageWindow("Humidity Controller Status Console", winPosX, winPosY);

            // Now we put the indicators directly under the humitity status and control panel
            Indicator humIndicator = new Indicator("Humid OFF", messageWin.getX(), messageWin.getY() + messageWin.height());
            Indicator dehumIndicator = new Indicator("DeHumid OFF", messageWin.getX() + (humIndicator.width() * 2), messageWin.getY() + messageWin.height());

            messageWin.writeMessage("Registered with the event manager.");

            try {
                messageWin.writeMessage("   Participant id: " + evtMgrI.getMyId());
                messageWin.writeMessage("   Registration Time: " + evtMgrI.getRegistrationTime());
            }
            catch (Exception e) {
                System.out.println("Error:: " + e);               
            }

            /**
             * ******************************************************************
             ** Here we start the main simulation loop gls
             * *******************************************************************
             */
            while (!isDone) {
                 try {
                  evtMgrI.returnMessage();
              }
              catch (Exception e){
                  
              }

                // If there are messages in the queue, we read through them.
                // We are looking for EventIDs = 4, this is a request to turn the
                // humidifier or dehumidifier on/off. Note that we get all the messages
                // at once... there is a 2.5 second delay between samples,.. so
                // the assumption is that there should only be a message at most.
                // If there are more, it is the last message that will effect the
                // output of the humidity as it would in reality.
               
                    if (evtMgrI.returnid()   == HUMIDITY_CONTROLLER) {
                        if (evtMgrI.returnMessage().equalsIgnoreCase(HUMIDIFIER_ON)) { // humidifier on
                            humidifierState = true;
                            messageWin.writeMessage("Received humidifier on event");

                            // Confirm that the message was recieved and acted on
                            confirmMessage(evtMgrI, HUMIDITY_SENSOR, HUMIDIFIER_ON);
                        }
                        if (evtMgrI.returnMessage().equalsIgnoreCase(HUMIDIFIER_OFF)) { // humidifier off
                            humidifierState = false;
                            messageWin.writeMessage("Received humidifier off event");

                            // Confirm that the message was recieved and acted on
                            confirmMessage(evtMgrI, HUMIDITY_SENSOR, HUMIDIFIER_OFF);
                        }
                        if (evtMgrI.returnMessage().equalsIgnoreCase(DEHUMIDIFIER_ON)) { // dehumidifier on
                            dehumidifierState = true;
                            messageWin.writeMessage("Received dehumidifier on event");

                            // Confirm that the message was recieved and acted on
                            confirmMessage(evtMgrI, HUMIDITY_SENSOR, DEHUMIDIFIER_ON);
                        }

                        if (evtMgrI.returnMessage().equalsIgnoreCase(DEHUMIDIFIER_OFF)) { // dehumidifier off
                            dehumidifierState = false;
                            messageWin.writeMessage("Received dehumidifier off event");

                            // Confirm that the message was recieved and acted on
                            confirmMessage(evtMgrI, HUMIDITY_SENSOR, DEHUMIDIFIER_OFF);
                       
                    }

                    // If the event ID == 99 then this is a signal that the simulation
                    // is to end. At this point, the loop termination flag is set to
                    // true and this process unregisters from the event manager.
                    if (evtMgrI.returnid() == END) {
                        isDone = true;
                        messageWin.writeMessage("\n\nSimulation Stopped. \n");

                        // Get rid of the indicators. The message panel is left for the
                        // user to exit so they can see the last message posted.
                        humIndicator.dispose();
                        dehumIndicator.dispose();
                    }
                }

                // Update the lamp status
                if (humidifierState) {
                    // Set to green, humidifier is on
                    humIndicator.setLampColorAndMessage("HUMID ON", 1);
                }
                else {
                    // Set to black, humidifier is off
                    humIndicator.setLampColorAndMessage("HUMID OFF", 0);
                }

                if (dehumidifierState) {
                    // Set to green, dehumidifier is on
                    dehumIndicator.setLampColorAndMessage("DEHUMID ON", 1);
                }
                else {
                    // Set to black, dehumidifier is off
                    dehumIndicator.setLampColorAndMessage("DEHUMID OFF", 0);
                }
                try {
                    Thread.sleep(delay);
                }
                catch (Exception e) {
                    System.out.println("Sleep error:: " + e);
                }
            }
    }

    private static void createInstance() {
        if (INSTANCE == null) {
            synchronized (Alarmas.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Alarmas();
                }
            }
        }
    }

    /**
     * This method calls createInstance method to creates and ensure that 
     * only one instance of this class is created. Singleton design pattern.
     * 
     * @return The instance of this class.
     */
    public static Alarmas getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    /**
     * Start this controller
     * 
     * @param args IP address of the event manager (on command line). 
     * If blank, it is assumed that the event manager is on the local machine.
     */
    public static void main(String args[]) {
        Component.SERVER_IP = "127.0.0.1";
        Alarmas sensor = Alarmas.getInstance();
        sensor.run();
    }
}
