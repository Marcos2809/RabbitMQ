/**
 * File:MovementSensor.java
 * Course: Software Architecture 
 * Project: Event Architectures
 * Institution: CIMAT
 * Reviewer: Perla Velasco Elizondo
 * Update by: Equipo MEETMECORP
 * Institution: CIMAT
 * Date: 29/04/2016
 * Sensor de Movimiento
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 **/
package sensors;

import common.Component;
import instrumentation.MessageWindow;


/**
 *
 * @author YMGM,MGL,JMMM
 */
public class MovementSensor extends Sensor implements Runnable {
     private float CurrentState;
     boolean MovementState = false;

    private static MovementSensor INSTANCE = new MovementSensor();
    
        private MovementSensor(){
            super();
        }
            public void run(){
             if (evtMgrI != null){
                  /*
                  float winPosX= 0.5f;
                  float winPosY= 0.3f;
                  MessageWindow messageWin = new MessageWindow("MovementSensor", winPosX,winPosY);
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
               postEvent(evtMgrI,MOVEMENT,CurrentState);
               //messageWin.writeMessage("Current State:: " + CurrentState); //post de current state
                       // Get the message queue
                try {
                evtMgrI.getEvent();
                } // try
                catch (Exception e) {
                    //messageWin.writeMessage("Error getting event queue::" + e);
                } //
                 //Get the message queue
                  // Get the message queue



                                        // If there are messages in the queue, we read through them.
                                        // We are looking for EventIDs = -5, this means the the heater
                                        // or chiller has been turned on/off. Note that we get all the messages
                                        // at once... there is a 2.5 second delay between samples,.. so
                                        // the assumption is that there should only be a message at most.
                                        // If there are more, it is the last message that will effect the
                                        // output of the temperature as it would in reality.
                if (evtMgrI.returnid() == MOVEMENT_SENSOR )
                    {
                      if (evtMgrI.returnMessage().equalsIgnoreCase(MOVEMENT_ON)) // chiller on
                            {
                                    MovementState = true;
                          } // if
                            if (evtMgrI.returnMessage().equalsIgnoreCase(MOVEMENT_OFF)) // chiller off
                            {
                                    MovementState = false;  
                            } // if


                       //     CurrentState = evtMgrI.returnMessage();
                    }
                if (MovementState) {
                    CurrentState += getRandomNumber();
                }
                if (evtMgrI.returnid() == END) {
                        isDone = true;
                        //messageWin.writeMessage("\n\nSimulation Stopped. \n");
                    }
                try
                {
                        Thread.sleep(delay);
                } // try
                catch( Exception e )
                {
                        //messageWin.writeMessage("Sleep error:: " + e );
                } // catch

                                } // while
                        } else {
                                System.out.println("Unable to register with the event manager.\n\n" );
                        }//fin de else

        }//fin de run


     private static void createInstance() {
        if (INSTANCE == null) {
            synchronized (MovementSensor.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovementSensor();
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
    public static MovementSensor getInstance() {
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
        //if(args[0] != null) Component.SERVER_IP = args[0];
        MovementSensor sensor = MovementSensor.getInstance();
        sensor.run();
    }//fin de main

}//fin de clase


