/**
 * **************************************************************************************
 * File:ECSSecurityConsole.java 
 * Update: Equipo EasyMeetCorp
 * Institution: CIMAT
 * **************************************************************************************
 * */

import common.*;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class ECSSecurityConsole {
    
    //private DoorSensor mDoorSensor = null;

//String options = "";                          // Menu choice from user
    //@SuppressWarnings("SleepWhileInLoop")
    public static void main(String args[]) throws Exception {
        IOManager userInput = new IOManager();	// IOManager IO Object
        boolean isDone = false;			// Main loop flag
        String option = "";                          // Menu choice from user
        SecurityMonitor SecMonitor;               // Alarmas    
        boolean active = true;                       //este par�metro es una bandera para saber el estado del sistema
        boolean sprinkleractive = false;
        SecMonitor = new SecurityMonitor();
        int delay =0500;                // Tiempo de espera para recibir activar los roseadores
        //int cont = 15;
        //TTimer timernew;
        //timernew = new TTimer();
       //private static Timer timer;
        
        
        
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
                System.out.println("Presione '3' para activar rociador o '4' para cancelar el roseador, \n si no presiona nada automáticamente en 15 segundos se activará el roseador");
                System.out.println("X: Stop Security Console\n");
                System.out.print("Choose an option:\n>>>> ");
              
                option = userInput.keyboardReadString();
                
                
               
                
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
            
                } 
                 if (option.equals("3")){
                  //  System.out.println("Aqui entra al 3");
                    sprinkleractive = true;
                    SecMonitor.setSprinklerStatus(sprinkleractive);
                    System.out.println("ACTIVATE MESSAGE RECEIVED");
                } 
                 
                 if (option.equals("4")) {
                //    System.out.println("Aqui entra al 4");
                    sprinkleractive = false;
                    SecMonitor.setSprinklerStatus(sprinkleractive);
                    System.out.println("DEACTIVATE MESSAGE RECEIVED");
                }
                                                   
                 if (option.equals("X")) {
                    //System.out.println("Aqui entra al 4");
                 try {
                        String cmd = "killall java"; //Comando de apagado en linux
                        Runtime.getRuntime().exec(cmd); 
                } catch (IOException ioe) {
                        System.out.println (ioe);
                }
                }

                if (option.equals("")){
                Timer timer = new Timer();
                 System.out.println("Se activará el rociador si no presiona nada");
                
                //TimerTask task = null;
		timer.schedule(new TimerTestTask() {
                    @Override
                    public void run() {
                        boolean sprinkeractive = true;
                        SecMonitor.setSprinklerStatus(true);
                        
                    }
                }, 15*100);
                Thread.sleep(delay);
                System.out.println("ACTIVATE MESSAGE RECEIVED");
                timer.cancel();
                    
                   } 
                 
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
    
           /*public String confirmSprinkler(){ //  timernew = new TTimer();
              boolean activateSprinkler= true;
                             
               TimerTask task = new TimerTask(){
                   @Override
                   public void run(){
                        if (options.equals("")){
                            System.out.println("No tecleó nada, Seran activados los roceadores");
                            activateSprinkler = true;
                            
                            //options ="4";
                            //SecMonitor.setSprinklerStatus(sprinkleractive);
                           //System.out.println("ACTIVATE MESSAGE RECEIVED");
                        } 
                   }
               
           };*/

    private static abstract class TimerTestTask extends TimerTask {

        public TimerTestTask() {
            
                      
            
        }
    }
               

}

