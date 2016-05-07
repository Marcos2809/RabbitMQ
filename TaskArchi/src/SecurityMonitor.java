/**
 * **************************************************************************************
 * File:ECSMonitor.java 
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
 * This class monitors the environmental control systems that control museum
 * temperature and humidity. In addition to monitoring the temperature and
 * humidity, the ECSMonitor also allows a user to set the humidity and
 * temperature ranges to be maintained. If temperatures exceed those limits
 * over/under alarm indicators are triggered.
 * **************************************************************************************
 */
import common.Component;
import instrumentation.*;
import event.*;
import event.RabbitMQInterface;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SecurityMonitor extends Thread {

    private RabbitMQInterface em = null;            // Interface object to the event manager
    private String evtMgrIP = null;			// Event Manager IP address
    
    private boolean isActive= true;                 // These parameters signify the temperature and humidity ranges in terms
    
    private Indicator wi;      			// of high value and low values. The ECSmonitor will attempt to maintain
    private Indicator di;                    // this temperature and humidity. Temperatures are in degrees Fahrenheit
    private Indicator mi;     			// and humidity is in relative humidity percentage.
    private Indicator fi;    			// and humidity is in relative humidity percentage.
    private Indicator si;
    
    
    boolean registered = true;				// Signifies that this class is registered with an event manager.
    MessageWindow messageWin = null;			// This is the message window
    
    
    public SecurityMonitor() {
        // event manager is on the local system
        try {
            // Here we create an event manager interface object. This assumes
            // that the event manager is on the local machine
            em = new RabbitMQInterface();
        }
         catch (Exception e) {
            System.out.println("SecurityMonitor::Error instantiating event manager interface: " + e);
            registered = false;
        } // catch // catch
    } //Constructor

    public SecurityMonitor(String evmIpAddress) {
        // event manager is not on the local system
       // evtMgrIP = evmIpAddress;
        try {
            // Here we create an event manager interface object. This assumes
            // that the event manager is NOT on the local machine
          //  em = new EventManagerInterface(evtMgrIP);
            Component.SERVER_IP = evtMgrIP;
        }
        catch (Exception e) {
            System.out.println("SecurityMonitor::Error instantiating event manager interface: " + e);
            registered = false;
        } // catch // catch
    } // 


    @Override
    public void run() {
       Event evt = null;			// Event object
        EventQueue eq = null;			// Message Queue
        int evtId = 0;				// User specified event ID
        int delay = 1000;			// The loop delay (1 second)
        boolean isDone = false;			// Loop termination flag
        boolean on = true;			// Used to turn on security
        boolean off = false;			// Used to turn off security
        String CurrentState2= "";
        
       
        if (em != null) {
            // Now we create the ECS status and message panel
            // Note that we set up two indicators that are initially yellow. This is
            // because we do not know if the temperature/humidity is high/low.
            // This panel is placed in the upper left hand corner and the status 
            // indicators are placed directly to the right, one on top of the other

            messageWin = new MessageWindow("Security Monitoring Console", 0, 0);
            wi= new Indicator("WINDOW BROKEN", messageWin.getX() + messageWin.width(), 0);
            di= new Indicator("DOOR BROKEN", messageWin.getX() + messageWin.width(), wi.height());
            mi= new Indicator("MOVEMENT DETECTION", messageWin.getX() + messageWin.width(), di.height()*2);
            fi= new Indicator("FIRE DETECTION", messageWin.getX() + messageWin.width(), mi.height()*2);
            si= new Indicator("SPRINKLER ACTIVATIONN", messageWin.getX() + messageWin.width(), fi.height()*2);

            messageWin.writeMessage("Registered with the event manager.");

           try {
                messageWin.writeMessage("   Participant id: " + em.getMyId());
                messageWin.writeMessage("   Registration Time: " + em.getRegistrationTime());
            } // try // try
            catch (Exception e) {
                System.out.println("Error:: " + e);
            } // catch

            /**
             * ******************************************************************
             ** Here we start the main simulation loop
             * *******************************************************************
             */
            while (!isDone) {
                // Here we get our event queue from the event manager
                try {
                    em.getEvent();
                } // try
                catch (Exception e) {
                    messageWin.writeMessage("Error getting event queue::" + e);
                } // catch // catch
                String DoorStatus="OK";
                String WinStatus="OK";
                String MovStatus="None";
                String FivStatus="None";
                
                // If there are messages in the queue, we read through them.
                // We are looking for EventIDs = 1 or 2. Event IDs of 1 are temperature
                // readings from the temperature sensor; event IDs of 2 are humidity sensor
                // readings. Note that we get all the messages at once... there is a 1
                // second delay between samples,.. so the assumption is that there should
                // only be a message at most. If there are more, it is the last message
                // that will effect the status of the temperature and humidity controllers
                // as it would in reality.
                 
                    //  System.out.println("ID Event: "+ em.returnid());
            
                    if (em.returnid() == 3) { // Door reading
                        try {
                            //messageWin.writeMessage("Seguridad: " + em.returnMessage() + " ID MEssage"+ em.returnid());
                            if (em.returnMessage().equalsIgnoreCase("1.0")){
                                DoorStatus="Broken";
                            }
                        } // try
                        catch (Exception e) {
                            messageWin.writeMessage("Error reading temperature: " + e);
                        } // catch // catch
                    } // if

                    if (em.returnid() == 6) { // Window reading
                        try {
                            //messageWin.writeMessage("Seguridad: " + em.returnMessage()+ " ID MEssage"+ em.returnid());
                            if (em.returnMessage()=="1.0"){
                                WinStatus="Broken";
                            }
                        } // try
                        catch (Exception e) {
                            messageWin.writeMessage("Error reading humidity: " + e);
                        } // catch // catch
                    } // if
                    
                    if (em.returnid() == 7) { // movement reading
                        try {
                            //messageWin.writeMessage("Seguridad: " + em.returnMessage()+ " ID MEssage"+ em.returnid());
                            if (em.returnMessage()=="1.0"){
                                MovStatus="Detected";
                            }
                        } // try
                        catch (Exception e) {
                            messageWin.writeMessage("Error reading humidity: " + e);
                        } // catch // catch
                    }
                    if (em.returnid() == 10) { // movement reading
                        try {
                            //messageWin.writeMessage("Seguridad: " + em.returnMessage()+ " ID MEssage"+ em.returnid());
                            if (em.returnMessage()=="1.0"){
                                FivStatus="Detected";
                            }
                        } // try
                        catch (Exception e) {
                            messageWin.writeMessage("Error reading humidity: " + e);
                        } // catch // catch
                    }// if
                    /*if (em.returnid() == 10) { // fire reading
                        try {
                            messageWin.writeMessage("Seguridad: " + em.returnMessage()+ " ID MEssage"+ em.returnid());
                        } // try
                        catch (Exception e) {
                            messageWin.writeMessage("Error reading humidity: " + e);
                        } // catch // catch
                    } // if
                    if (em.returnid() == 11) { // sprinkler reading
                        try {
                            messageWin.writeMessage("Seguridad: " + em.returnMessage()+ " ID MEssage"+ em.returnid());
                        } // try
                        catch (Exception e) {
                            messageWin.writeMessage("Error reading humidity: " + e);
                        } // catch // catch
                    } // if
                    */
                    messageWin.writeMessage("Door Status: " + DoorStatus + " :: Window Status: "+ WinStatus + " :: Movement Status: " + MovStatus+ " :: Fire Status: " + FivStatus);
                    
                    // If the event ID == 99 then this is a signal that the simulation
                    // is to end. At this point, the loop termination flag is set to
                    // true and this process unregisters from the event manager.
                    if (em.returnid() == 99) {
                        isDone = true;
                       
                        messageWin.writeMessage("\n\nSimulation Stopped. \n");
                        // Get rid of the indicators. The message panel is left for the
                        // user to exit so they can see the last message posted.
                        wi.dispose();
                        di.dispose();
                        mi.dispose();
                        fi.dispose();
                        si.dispose();
                    } // if
                
                // Check temperature and effect control as necessary
                if (em.returnMessage().equalsIgnoreCase("W1")) { // temperature is below threshhold
                    messageWin.writeMessage("Security:: ALERT! Window broken");
                    wi.setLampColorAndMessage("TEMP LOW", 3);
                   Activatewindow(on);
                   
                }
                if (em.returnMessage().equalsIgnoreCase("W0")) { // temperature is above threshhold
                       messageWin.writeMessage("Security:: Window broken: False");
                       wi.setLampColorAndMessage("Window OK", 0);
                       Activatewindow(off);
                    }
                if(em.returnMessage().equalsIgnoreCase("DO1")){
                    Activatedoor(on);
                        messageWin.writeMessage("Security:: ALERT! Door broken");
                        di.setLampColorAndMessage("Door Broken", 1); // Door is broken
           } 
                if(em.returnMessage().equalsIgnoreCase("DO0")) {
                          Activatedoor(on);
                        messageWin.writeMessage("Security:: Door broken: False");
                        di.setLampColorAndMessage("Door OK", 0); // Door is ok

                }
                if(em.returnMessage().equalsIgnoreCase("M1")) {
                          Activatemov(on);
                        messageWin.writeMessage("Security:: ALERT! Movement detection");
                        mi.setLampColorAndMessage("Movement Detected", 1); // Movement detection


                }
                if(em.returnMessage().equalsIgnoreCase("M0")) {
                     Activatemov(off);

                        messageWin.writeMessage("Security:: Movement detection: False");
                        mi.setLampColorAndMessage("nOT Movement", 0); // Movement is ok
                }
                if(em.returnMessage().equalsIgnoreCase("FD1")){
                          Activatefire(on);
                        messageWin.writeMessage("Security:: ALERT! Fire detection");
                        fi.setLampColorAndMessage("Fire detected", 1); // Movement detection
                        messageWin.writeMessage("Sprinkler activated");
                        si.setLampColorAndMessage("Sprinkler activated",1);

                }
                if(em.returnMessage().equalsIgnoreCase("FD0")) {
                        Activatefire(off);
                        messageWin.writeMessage("Security:: Fire detection: False");
                        mi.setLampColorAndMessage("Not Fire", 0); // Movement is ok
                        messageWin.writeMessage("Sprinkler not activated");
                        si.setLampColorAndMessage("Sprinkler not activated",1);

                }
                    
                // This delay slows down the sample rate to Delay milliseconds
                try {
                    Thread.sleep(delay);
                } // try
                catch (Exception e) {
                    System.out.println("Sleep error:: " + e);
                } // catch
            } // while
        }
        else {
            System.out.println("Unable to register with the event manager.\n\n");
        } // if
    }// main

    /**
     * This method returns the registered status
     *
     * @return boolean true if registered, false if not registered
     */
    public boolean isRegistered() {
        return (registered);
    } // setTemperatureRange

    
    public void halt() {
        messageWin.writeMessage("***HALT MESSAGE RECEIVED - SHUTTING DOWN SYSTEM***");
       try {
        }
        catch (Exception e) {
            System.out.println("Error sending halt message:: " + e);
        }
    } // halt

    /**
     * This method posts events that will signal the temperature controller to
     * turn on/off the heater
     *
     * @param ON indicates whether to turn the heater on or off. Exceptions:
     * Posting to event manager exception
     */
    private void Activatewindow(boolean ON) {
        // Here we create the event.
        
      // Here we send the event to the event manager.
        try {
            //RabbitMQInterface em = new RabbitMQInterface();
        if (ON) {
            em.sendEvent(Component.WINDOW_ON+"&"+Component.SECURITY_CONTROLLER, "logs");
            isActive = false;
         //    em.sendEvent(Component.HEATER_ON+"&"+Component.TEMPERATURE_SENSOR, "logs");
             
        }
        else {
            em.sendEvent(Component.WINDOW_OFF+"&"+Component.SECURITY_CONTROLLER, "logs");
            isActive = false;
        }
        } // try
        catch (Exception e) {
            //System.out.println("Error sending heater control message:: " + e);
           
        } // catch
    } // heater
    private void Activatedoor(boolean ON) {
         try {
        if (ON) {
            em.sendEvent(Component.DOOR_ON+"&"+Component.SECURITY_CONTROLLER, "logs");
            isActive = false;
        }
        else {
            em.sendEvent(Component.DOOR_OFF+"&"+Component.SECURITY_CONTROLLER, "logs");
            isActive = false;
        }
        } // try
        catch (Exception e) {
        } // catch
    } 
    private void Activatemov(boolean ON) {
         try {
        if (ON) {
            em.sendEvent(Component.MOVEMENT_ON+"&"+Component.SECURITY_CONTROLLER, "logs");
            isActive = false;
        }
        else {
            em.sendEvent(Component.MOVEMENT_OFF+"&"+Component.SECURITY_CONTROLLER, "logs");
            isActive = false;
        }
        } // try
        catch (Exception e) {
        } // catch
    } 
     private void Activatefire(boolean ON) {
         try {
        if (ON) {
            em.sendEvent(Component.FIRE_ON+"&"+Component.SECURITY_CONTROLLER, "logs");
            isActive = false;
        }
        else {
            em.sendEvent(Component.FIRE_OFF+"&"+Component.SECURITY_CONTROLLER, "logs");
            isActive = false;
        }
        } // try
        catch (Exception e) {
        } // catch
    } 
    
} // ECSMonitor