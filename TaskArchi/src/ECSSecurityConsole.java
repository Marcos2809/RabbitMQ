/**
 * **************************************************************************************
 * File:ECSSecurityConsole.java 
 * Update: Equipo EasyMeetCorp
 * Institution: CIMAT
 * **************************************************************************************
 * */

import common.*;

public class ECSSecurityConsole {
    


    //@SuppressWarnings("SleepWhileInLoop")
    public static void main(String args[]) throws Exception {
        
        SecurityMonitor SecMonitor;               // Alarmas    
        
        SecMonitor = new SecurityMonitor();
            
        
        if (SecMonitor.isRegistered()){
            SecMonitor.start(); // Here we start the monitoring and control thread
            
        }
    } // main
} // ECSSecurityMonitor