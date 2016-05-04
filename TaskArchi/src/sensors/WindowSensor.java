/**
 * File: WindowSensor.java
 * Course: Software Architecture 
 * Project: Event Architectures
 * Institution: CIMAT
 * Reviewer: Perla Velasco Elizondo
 * Update by: Equipo MEETMECORP
 * Institution: CIMAT
 * Date: 29/04/2016
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensors;

import common.Component;
import instrumentation.MessageWindow;


/**
 *
 * @author YMGM,MGL,JMMM
 */
public class WindowSensor extends Sensor implements Runnable {
     private float CurrentState;
     boolean WindowState = false;
     //boolean Done = false;

private static WindowSensor INSTANCE = new WindowSensor();
    private WindowSensor(){
        super();
    }
     public void run(){
     if (evtMgrI != null){
          float winPosX= 0.5f;
          float winPosY= 0.3f;
          MessageWindow messageWin = new MessageWindow("WindowSensor", winPosX,winPosY);
	  messageWin.writeMessage ("Registered with the event manager.");

     try{
          messageWin.writeMessage("  Participant id:" + evtMgrI.getMyId());
          messageWin.writeMessage("  Registration Time:" + evtMgrI.getRegistrationTime());
     }// fin del try

     catch(Exception e){
         messageWin.writeMessage("Error::" + e);
      }//catch


     messageWin.writeMessage("\n Initializating simulation ... ");
     
     while (!isDone)
     {
         //post de current state
         
         postEvent(evtMgrI,WINDOW,CurrentState);
         
         
         messageWin.writeMessage("Current State:: " + CurrentState);
         
          //Get the message queue
          // Get the message queue 
            try {
                evtMgrI.getEvent();
            } // try
            catch (Exception e) {
                messageWin.writeMessage("Error getting event queue::" + e);
            } // catch
				// If there are messages in the queue, we read through them.
				// We are looking for EventIDs = -5, this means the the heater
				// or chiller has been turned on/off. Note that we get all the messages
				// at once... there is a 2.5 second delay between samples,.. so
				// the assumption is that there should only be a message at most.
				// If there are more, it is the last message that will effect the
				// output of the temperature as it would in reality.

            if ( evtMgrI.returnid() == WINDOW_SENSOR )
            {
                    if (evtMgrI.returnMessage().equalsIgnoreCase(WINDOW_ON)) // chiller on
                    {
                            WindowState = true;
                            CurrentState = 1;
                    } // if
                    if (evtMgrI.returnMessage().equalsIgnoreCase(WINDOW_OFF)) // chiller off
                    {
                            WindowState = false;
                            CurrentState= 0;
                    } // if

            } // if

            // If the event ID == 99 then this is a signal that the simulation
            // is to end. At this point, the loop termination flag is set to
            // true and this process unregisters from the event manager.
            if (evtMgrI.returnid() == END) {
                        isDone = true;
                        messageWin.writeMessage("\n\nSimulation Stopped. \n");
                }
                 try {
                    Thread.sleep(delay);
                }
                catch (Exception e) {
                  messageWin.writeMessage("Sleep error:: " + e);
                } 
            } }

}//fin de run


      private static void createInstance() {
        if (INSTANCE == null) {
            synchronized (WindowSensor.class) {
                if (INSTANCE == null) {
                    INSTANCE = new WindowSensor();
                }//fin de if
            }//fin de método sinchronized
        }//fin de if
    }//fin de método createInstance
    
    /**
     * This method calls createInstance method to creates and ensure that 
     * only one instance of this class is created. Singleton design pattern.
     * 
     * @return The instance of this class.
     */
    public static WindowSensor getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }// fin de getInstance

    /**
     * Start this sensor
     * 
     * @param args IP address of the event manager (on command line). 
     * If blank, it is assumed that the event manager is on the local machine.
     */
    public static void main(String args[]) {
        if(args[0] != null) Component.SERVER_IP = args[0];
        WindowSensor sensor = WindowSensor.getInstance();
        sensor.run();
    }//fin de main

}//fin de clase

