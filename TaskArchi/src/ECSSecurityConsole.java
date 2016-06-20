



/**
 * **************************************************************************************
 * File:ECSSecurityConsole.java 
 * Update: Equipo EasyMeetCorp
 * Institution: CIMAT
 * **************************************************************************************
 * */

import common.*;
import sensors.*;

public class ECSSecurityConsole {
    
    //private DoorSensor mDoorSensor = null;


    public static void main(String args[]) throws Exception {
        IOManager userInput = new IOManager();	// IOManager IO Object
        boolean isDone = false;			// Main loop flag
        String option;                          // Menu choice from user
        SecurityMonitor SecMonitor;               // Alarmas    
        boolean active = true;                       //este par�metro es una bandera para saber el estado del sistema
        SecMonitor = new SecurityMonitor();
        
        
        
        if (SecMonitor.isRegistered()){
            SecMonitor.start(); // Here we start the monitoring and control thread
                    
            while (!isDone) {
                

                System.out.println("\n\n\n\n");
                System.out.println("Environmental Control System (ECS) Security Console: \n");

                if (args.length != 0) {
                    System.out.println("Using event manager at: " + args[0] + "\n");
                }
                else {
                    System.out.println("Using local event manager \n");
                }

                System.out.println("1: Activate alarms");                
                System.out.println("2: Deactivate alarms");
                //System.out.println("3: Deactivate sprinkler");
                System.out.println("X: Stop Security Console\n");
                System.out.print("Choose an option:\n>>>> ");
                option = userInput.keyboardReadString();

                //////////// option 1 ////////////
                if (option.equals("1")) {
                    // Here we activate alarms
                    
                    active = true;                    
                    System.out.println("ACTIVATE MESSAGE RECEIVED");
                    SecMonitor.setAlarmsStatus(active);
                    
                                    
                }//IF

                //////////// option 2 ////////////
                if (option.equals("2")) {
                    // Here we deactivate alarms
                    
                    active = false;
                    System.out.println("DEACTIVATE MESSAGE RECEIVED");
                    SecMonitor.setAlarmsStatus(active);
            
                } // if

                //////////// option X ////////////
                if (option.equalsIgnoreCase("X")) {
		// Here the user is done, so we set the Done flag and halt
                    // the environmental control system. The monitor provides a method
                    // to do this. Its important to have processes release their queues
                    // with the event manager. If these queues are not released these
                    // become dead queues and they collect events and will eventually
                    // cause problems for the event manager.

                    //monitor.halt();
                    isDone = true;
                    System.out.println("\nConsole Stopped... Exit monitor mindow to return to command prompt.");
                    //SecMonitor.halt();
                    
                } // if
            }//while
        }
        else {
            System.out.println("\n\nUnable start the monitor.\n\n");
        } // if
    } // main
} // ECSSecurityMonitor