/**
 * **************************************************************************************
 * File:ECSConsole.java 
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
 * This class is the console for the museum environmental control system. This
 * process consists of two threads. The ECSMonitor object is a thread that is
 * started that is responsible for the monitoring and control of the museum
 * environmental systems. The main thread provides a text interface for the user
 * to change the temperature and humidity ranges, as well as shut down the
 * system.
 * **************************************************************************************
 */

import common.*;

public class ECSConsole {

    public static void main(String args[]) {
        
        ECSMonitor monitor;                     // The environmental control system monitor
        
        /////////////////////////////////////////////////////////////////////////////////
        // Get the IP address of the event manager
        /////////////////////////////////////////////////////////////////////////////////
        if (args.length != 0) {
            // event manager is not on the local system
            monitor = new ECSMonitor(args[0]);
            //smonitor = new SecurityMonitor(args[0]);
        }
        else {
            monitor = new ECSMonitor();
        } // if

        // Here we check to see if registration worked. If ef is null then the
        // event manager interface was not properly created.
        if (monitor.isRegistered()) {
            monitor.start(); // Here we start the monitoring and control thread
                        
        }
        else {
            System.out.println("\n\nUnable start the monitor.\n\n");
        } // if
    } // main
} // ECSConsole
