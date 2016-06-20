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
       
        monitor = new ECSMonitor();
        
        // Here we check to see if registration worked. If ef is null then the
        // event manager interface was not properly created.
        if (monitor.isRegistered()){// && smonitor.isRegistered()) {
            monitor.start(); // Here we start the monitoring and control thread
          
        }
    } // main
} // ECSConsole
