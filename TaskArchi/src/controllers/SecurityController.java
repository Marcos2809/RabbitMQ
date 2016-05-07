/**
 * **************************************************************************************
 * File:SecurityController.java 
 * Course: Software Architecture 
 * Project: Event Architectures
 * Institution: CIMAT
 * Reviewer: Perla Velasco Elizondo
 * Update by: Equipo MEETMECORP
 * Date: 29/04/2016
 * 
 **/
package controllers;

import common.Component;
import instrumentation.Indicator;
import instrumentation.MessageWindow;


public class SecurityController  extends Controller implements Runnable {

    boolean WindowState = false;	// Heater state: false == off, true == on
    boolean DoorState = false;	// Chiller state: false == off, true == on
    boolean MovementState = false;
    int Delay = 1000;
<<<<<<< HEAD
    boolean isDone = false;
=======
    //boolean Done = false;
>>>>>>> 55274911ce51d42e54caddc6a8545934489c0b92
    
    private static SecurityController INSTANCE = new SecurityController();
    
    private SecurityController(){
    
    
    }

    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run() {

        // Here we check to see if registration worked. If ef is null then the
        // event manager interface was not properly created.
        if (evtMgrI != null) {
            //System.out.println("Registered with the event manager.");

            /* Now we create the temperature control status and message panel
             ** We put this panel about 1/3 the way down the terminal, aligned to the left
             ** of the terminal. The status indicators are placed directly under this panel
             */
            //float winPosX = 0.0f; 	//This is the X position of the message window in terms 
            //of a percentage of the screen height
            //float winPosY = 0.3f; 	//This is the Y position of the message window in terms 
            //of a percentage of the screen height 

            //MessageWindow messageWin = new MessageWindow("Security Controller Status Console", winPosX, winPosY);

            // Put the status indicators under the panel...
            /*
            Indicator wi = new Indicator("WindowsState OFF", messageWin.getX(), messageWin.getY() + messageWin.height());
            Indicator di = new Indicator("DoorState OFF", messageWin.getX() + (wi.width() * 3), messageWin.getY() + messageWin.height());
            Indicator mi= new Indicator ("MovementState OFF", messageWin.getX() + (di.width() * 6), messageWin.getY() + messageWin.height());
            messageWin.writeMessage("Registered with the event manager.");

            try {
                messageWin.writeMessage("   Participant id: " + evtMgrI.getMyId());
                messageWin.writeMessage("   Registration Time: " + evtMgrI.getRegistrationTime());
            }
            catch (Exception e) {
                System.out.println("Error:: " + e);
            }
            */

            /**
             * ******************************************************************
             ** Here we start the main simulation loop
             * *******************************************************************
             */
            while (!isDone) {
                  try {
                  evtMgrI.getEvent();
              }
              catch (Exception e){
                  
              }

                // If there are messages in the queue, we read through them.
                // We are looking for EventIDs = 5, this is a request to turn the
                // heater or chiller on. Note that we get all the messages
                // at once... there is a 2.5 second delay between samples,.. so
                // the assumption is that there should only be a message at most.
                // If there are more, it is the last message that will effect the
                // output of the temperature as it would in reality.
                
                   if (evtMgrI.returnid() == SECURITY_CONTROLLER) {
                        if (evtMgrI.returnMessage().equalsIgnoreCase(WINDOW_ON)) { // heater on
                            WindowState = true;
                            //messageWin.writeMessage("Received heater on event");
                            // Confirm that the message was recieved and acted on
                            //confirmMessage(evtMgrI, TEMPERATURE_SENSOR, HEATER_ON);
                            //messageWin.writeMessage("Received security event");
                        //Confirmar que el mensaje fue reicibo y actuado 
                        confirmMessage(evtMgrI,WINDOW_SENSOR,WINDOW_ON);
                        }

                        if (evtMgrI.returnMessage().equalsIgnoreCase(WINDOW_OFF)) { // heater off
                            WindowState = false;
                            //messageWin.writeMessage("Received heater off event");
                            // Confirm that the message was recieved and acted on
                            //confirmMessage(evtMgrI, TEMPERATURE_SENSOR, HEATER_OFF);
                            //messageWin.writeMessage("Received security off event");
                        //Confirmar que el mensaje fue reicibo y actuado 
                        confirmMessage(evtMgrI,WINDOW_SENSOR,WINDOW_OFF);
                        }

                        if (evtMgrI.returnMessage().equalsIgnoreCase(DOOR_ON)) { // chiller on
                            DoorState = true;
                            //messageWin.writeMessage("Received chiller on event");
                            // Confirm that the message was recieved and acted on
                            //confirmMessage(evtMgrI, TEMPERATURE_SENSOR, CHILLER_ON);
                            //messageWin.writeMessage("Received security event");
                        //Confirmar que el mensaje fue reicibo y actuado 
                        confirmMessage(evtMgrI,DOOR_SENSOR,DOOR_ON);
                        }

                        if (evtMgrI.returnMessage().equalsIgnoreCase(DOOR_OFF)) { // chiller off
                            DoorState = false;
                            //messageWin.writeMessage("Received chiller off event");
                            // Confirm that the message was recieved and acted on
                            //confirmMessage(evtMgrI, TEMPERATURE_SENSOR, CHILLER_OFF);
                            //messageWin.writeMessage("Received security off event");
                        //Confirmar que el mensaje fue reicibo y actuado 
                        confirmMessage(evtMgrI,DOOR_SENSOR,DOOR_OFF);
                        }
                    if (evtMgrI.returnMessage().equalsIgnoreCase(MOVEMENT_ON)) { // chiller on
                            MovementState = true;
                            //messageWin.writeMessage("Received chiller on event");
                            // Confirm that the message was recieved and acted on
                            //confirmMessage(evtMgrI, TEMPERATURE_SENSOR, CHILLER_ON);
                            //messageWin.writeMessage("Received security event");
                        //Confirmar que el mensaje fue reicibo y actuado 
                        confirmMessage(evtMgrI,MOVEMENT_SENSOR,MOVEMENT_ON);
                        }

                        if (evtMgrI.returnMessage().equalsIgnoreCase(MOVEMENT_OFF)) { // chiller off
                            MovementState = false;
                            //messageWin.writeMessage("Received chiller off event");
                            // Confirm that the message was recieved and acted on
                            //confirmMessage(evtMgrI, TEMPERATURE_SENSOR, CHILLER_OFF);
                            //messageWin.writeMessage("Received security off event");
                        //Confirmar que el mensaje fue reicibo y actuado 
                        confirmMessage(evtMgrI,MOVEMENT_SENSOR,MOVEMENT_OFF);
                        }
                    
                        
                    }
                    // If the event ID == 99 then this is a signal that the simulation
                    // is to end. At this point, the loop termination flag is set to
                    // true and this process unregisters from the event manager.
                    if (evtMgrI.returnid() == END) {
                        isDone = true;
                        //messageWin.writeMessage("\n\nSimulation Stopped. \n");
                        // Get rid of the indicators. The message panel is left for the
                        // user to exit so they can see the last message posted.
                        //wi.dispose();
                        //di.dispose();
                        //mi.dispose();
                    }
                }

                // Update the lamp status
                /*
                if (WindowState) {
                    // Set to green, heater is on
                    wi.setLampColorAndMessage("WINDOW BROKEN", 1);
                }
                else {
                    // Set to black, heater is off
                    wi.setLampColorAndMessage("WINDOW FINE", 0);
                }
                if (DoorState) {
                    // Set to green, chiller is on
                    di.setLampColorAndMessage("DOOR BROKEN", 1);
                }
                else {
                    // Set to black, chiller is off
                    di.setLampColorAndMessage("DOOR FINE", 0);
                }
                if (MovementState) {
                    // Set to green, chiller is on
                    mi.setLampColorAndMessage("EXISTS MOVEMENT ", 1);
                }
                else {
                    // Set to black, chiller is off
                    mi.setLampColorAndMessage("NOT MOVEMENT", 0);
                }
                */
                try {
                    Thread.sleep(Delay);
                }
                catch (Exception e) {
                    System.out.println("Sleep error:: " + e);
                }
            }
    }

    private static void createInstance() {
        if (INSTANCE == null) {
            synchronized (SecurityController.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SecurityController();
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
    public static SecurityController getInstance() {
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
       // if(args[0] != null) Component.SERVER_IP = args[0];
       Component.SERVER_IP = "127.0.0.1";
        SecurityController sensor = SecurityController.getInstance();
        sensor.run();
    }

} // SecurityController
