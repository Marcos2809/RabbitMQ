/**
 * File:DoorSensor.java 
 * Course: Software Architecture 
 * Project: Event Architectures
 * Institution: CIMAT
 * Reviewer: Perla Velasco Elizondo
 * Authors: Equipo EasyMeetCorp
 * Date: 29/04/2016
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensors;

import common.Component;
import instrumentation.MessageWindow;

public class DoorSensor extends Sensor implements Runnable {
    private int CurrentState;
    private boolean DoorState = false;
<<<<<<< HEAD
=======
    //private boolean Done = false;
>>>>>>> 55274911ce51d42e54caddc6a8545934489c0b92

    private static DoorSensor INSTANCE = new DoorSensor();

    private DoorSensor(){
        super();
    }
    @Override
     public void run(){
        if (evtMgrI != null){
            /*
             float winPosX= 0.5f;
             float winPosY= 0.3f;
            
             MessageWindow messageWin = new MessageWindow("DoorSensor", winPosX,winPosY);
             messageWin.writeMessage ("Registered with the event manager.");

        try{
             messageWin.writeMessage("  Participant id:" + evtMgrI.getMyId());
             messageWin.writeMessage("  Registration Time:" + evtMgrI.getRegistrationTime());
        }// fin del try
        catch(Exception e){
            messageWin.writeMessage("Error::" + e);
         }//catch


        messageWin.writeMessage("\n Initializating simulation ... ");
        */
        
     
     while (!isDone)
     {
         //post de current state
          CurrentState = getRandomNumberent();
         postEvent(evtMgrI,DOOR,CurrentState);
         //messageWin.writeMessage("Current State:: " + CurrentState);
         
          //Get the message queue
          // Get the message queue
            try {
                evtMgrI.getEvent();
            } // try
            catch (Exception e) {
                //messageWin.writeMessage("Error getting event queue::" + e);
            } // catch
        // If there are messages in the queue, we read through them.
        // We are looking for EventIDs = -5, this means the the heater
        // or chiller has been turned on/off. Note that we get all the messages
        // at once... there is a 2.5 second delay between samples,.. so
        // the assumption is that there should only be a message at most.
        // If there are more, it is the last message that will effect the
        // output of the temperature as it would in reality.

                if ( evtMgrI.returnid() == DOOR_SENSOR)
                {

<<<<<<< HEAD
                        if (evtMgrI.returnMessage().equalsIgnoreCase(DOOR_ON)) // 
=======
                        if (evtMgrI.returnMessage().equalsIgnoreCase(DOOR_ON)) // chiller on
>>>>>>> 55274911ce51d42e54caddc6a8545934489c0b92
                        {
                                DoorState = true;
                                CurrentState=1;

                         } // if

<<<<<<< HEAD
                        if (evtMgrI.returnMessage().equalsIgnoreCase(DOOR_OFF)) // 
=======
                        if (evtMgrI.returnMessage().equalsIgnoreCase(DOOR_OFF)) // chiller off
>>>>>>> 55274911ce51d42e54caddc6a8545934489c0b92
                        {
                                DoorState = false;
                                CurrentState=0;
                         } // if
                        //CurrentState = evtMgrI.getEvent()

                // Here we wait for a 2.5 seconds before we start the next sample
                } // Now we trend the relative humidity according to the status of the
                // humidifier/dehumidifier controller.
               // Here we wait for a 2.5 seconds before we start the next sample
                if (DoorState) {
                    CurrentState += getRandomNumber();
                }
                if (evtMgrI.returnid() == END) {
                        isDone = true;
<<<<<<< HEAD
                        //messageWin.writeMessage("\n\nSimulation Stopped. \n");
=======
                                   
                    messageWin.writeMessage("\n\nSimulation Stopped. \n");
>>>>>>> 55274911ce51d42e54caddc6a8545934489c0b92
                }
                 try {
                    Thread.sleep(delay);
                }
                catch (Exception e) {
                  //messageWin.writeMessage("Sleep error:: " + e);
                } 
            } 
        //}
        //else {
            //System.out.println("Unable to register with the event manager.\n\n");
       // } 
    }
  }
    
    private static void createInstance() {
        if (INSTANCE == null) {
            synchronized (DoorSensor.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DoorSensor();
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
    public static DoorSensor getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    /**
     * Start this sensor
     * 
     * @param args IP address of the event manager (on command line). 
     * If blank, it is assumed that the event manager is on the local machine.
     */
    public static void main(String args[]) {
        if(args[0] != null) Component.SERVER_IP = args[0];
        Component.SERVER_IP = "127.0.0.1";
        DoorSensor sensor = DoorSensor.getInstance();
        sensor.run();
    }

} 
