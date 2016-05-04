/**
 * **************************************************************************************
 * File:SecurityController.java 
 * Course: Software Architecture 
 * Project: Event Architectures
 * Institution: CIMAT
 * Reviewer: Perla Velasco Elizondo
 * Update by: Equipo MEETMECORP
 * Institution: CIMAT
 * Date: 29/04/2016
 * 
* Monitor de seguridad: Esta clase controla el sensor de puerta, de ventana y de movimiento
******************************************************************************************************************/

import common.Component;
import instrumentation.*;
import event.*;
import event.RabbitMQInterface;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author YMGM,MGL,JMMM
 */

class SecurityMonitor extends Thread
{
   // private EventManagerInterface em = null;            // Interface object to the event manager
    private String evtMgrIP = null;			// Event Manager IP address
    boolean isActive=true;
    Indicator wi;                                       //indicador de ventana rota
    Indicator di;                                       //indicador de puerta rota
    Indicator mi;                                       //indicador de movimiento
    Indicator fi;
    Indicator si;
    
    boolean registered = true;				// Signifies that this class is registered with an event manager.
    MessageWindow messageWin = null;			// This is the message window
    

    public SecurityMonitor() {
        // event manager is on the local system
        try {
            // Here we create an event manager interface object. This assumes
            // that the event manager is on the local machine
         //   em = new EventManagerInterface();
        }
        catch (Exception e) {
            System.out.println("SecurityMonitor::Error instantiating event manager interface: " + e);
            registered = false;
        } // catch // catch
    } //Constructor

    @SuppressWarnings("UseSpecificCatch")
    public SecurityMonitor(String evmIpAddress) {
        // event manager is not on the local system
        evtMgrIP = evmIpAddress;
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
    } // Constructor

    @Override
    @SuppressWarnings({"UseSpecificCatch", "SleepWhileInLoop", "null"})
    public void run() {
        RabbitMQInterface em = null;
        Event evt = null;			// Event object
        EventQueue eq = null;			// Message Queue
        int evtId = 0;				// User specified event ID
        int delay = 1000;			// The loop delay (1 second)
        boolean isDone = false;			// Loop termination flag
        boolean on = true;			// Used to turn on security
        boolean off = false;			// Used to turn off security
        String CurrentState= "";
        try {
            em = new RabbitMQInterface();
        } catch (Exception ex) {
            Logger.getLogger(ECSMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            si= new Indicator("SPRINKLE ACTIVATIONN", messageWin.getX() + messageWin.width(), fi.height()*2);

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
                } 
                // If there are messages in the queue, we read through them.
                // We are looking for EventIDs = 1 or 2. Event IDs of 1 are temperature
                // readings from the temperature sensor; event IDs of 2 are humidity sensor
                // readings. Note that we get all the messages at once... there is a 1
                // second delay between samples,.. so the assumption is that there should
                // only be a message at most. If there are more, it is the last message
                // that will effect the status of the temperature and humidity controllers
                // as it would in reality.
                   if (em.returnid()  == 3) { // Door reading
                        try {
                            CurrentState = em.returnMessage();
                        } // try
                        catch (Exception e) {
                            messageWin.writeMessage("Error reading door alarm: " + e);
                        } // catch // catch
                    } // if

                    if (em.returnid()== 6) { // Window reading
                        try {
                             CurrentState = em.returnMessage();
                        } // try
                        catch (Exception e) {
                            messageWin.writeMessage("Error reading window: " + e);
                        } // catch // catch
                    } // if
                    
                    if (em.returnid() == 7) { // movement reading
                        try {
                            CurrentState = em.returnMessage();
                        } // try
                        catch (Exception e) {
                            messageWin.writeMessage("Error reading movement: " + e);
                        } // catch // catch
                    } // if

                    if (em.returnid() == 10) { //  fire reading
                        try {
                             CurrentState = em.returnMessage();
                        } // try
                        catch (Exception e) {
                            messageWin.writeMessage("Error reading movement: " + e);
                        } // catch // catch
                    } // if
                    
                    if (em.returnid() == 11) { //  sprinkle reading
                        try {
                             CurrentState = em.returnMessage();
                        } // try
                        catch (Exception e) {
                            messageWin.writeMessage("Error reading movement: " + e);
                        } // catch // catch
                    } // if
                    
                    
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
                if(isActive && em.returnMessage()!= null){

                if(em.returnMessage().equalsIgnoreCase("W1")){ //Window

                        messageWin.writeMessage("Security:: ALERT! Window broken");
                        wi.setLampColorAndMessage("Window broken", 1); // Window is broken

                }
                if (em.returnMessage().equalsIgnoreCase("W0")){

                        messageWin.writeMessage("Security:: Window broken: False");
                        wi.setLampColorAndMessage("Window OK", 0); // Window is ok

                }

                if(em.returnMessage().equalsIgnoreCase("DO1")){

                        messageWin.writeMessage("Security:: ALERT! Door broken");
                        di.setLampColorAndMessage("Door Broken", 1); // Door is broken

                }
                if(em.returnMessage().equalsIgnoreCase("DO0")) {

                        messageWin.writeMessage("Security:: Door broken: False");
                        di.setLampColorAndMessage("Door OK", 0); // Door is ok

                }

                if(em.returnMessage().equalsIgnoreCase("M1")){

                        messageWin.writeMessage("Security:: ALERT! Movement detection");
                        mi.setLampColorAndMessage("Movement Broken", 1); // Movement detection

                }
                if(em.returnMessage().equalsIgnoreCase("M0")) {

                        messageWin.writeMessage("Security:: Movement detection: False");
                        mi.setLampColorAndMessage("nOT Movement", 0); // Movement is ok

                }

                if(em.returnMessage().equalsIgnoreCase("FD1")){

                        messageWin.writeMessage("Security:: ALERT! Fire detection");
                        fi.setLampColorAndMessage("Fire detected", 1); // Movement detection
                        messageWin.writeMessage("Sprinkler activated");
                        si.setLampColorAndMessage("Sprinkler activated",1);

                }
                if(em.returnMessage().equalsIgnoreCase("FD0")) {

                        messageWin.writeMessage("Security:: Fire detection: False");
                        mi.setLampColorAndMessage("Not Fire", 0); // Movement is ok
                        messageWin.writeMessage("Sprinkler not activated");
                        si.setLampColorAndMessage("Sprinkler not activated",1);

                }
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
    } // main

    /**
     * This method returns the registered status
     *
     * @return boolean true if registered, false if not registered
     */
    public boolean isRegistered() {
        return (registered);
    } // setTemperatureRange

    
    /**
     * This method posts an event that stops the environmental control system.
     * Exceptions: Posting to event manager exception
     */
    @SuppressWarnings("UseSpecificCatch")
    public void halt() {
        messageWin.writeMessage("***HALT MESSAGE RECEIVED - SHUTTING DOWN SYSTEM***");
        // Here we create the stop event.
        //Event evt;
        //evt = new Event(Component.END, "XXX");
        // Here we send the event to the event manager.
        try {
            //em.sendEvent(evt);
        }
        catch (Exception e) {
            System.out.println("Error sending halt message:: " + e);
        }
    } // halt

    	public void Activate()
	{
		messageWin.writeMessage( "***ACTIVATE MESSAGE RECEIVED - ACTIVATING SYSTEM***" );

		isActive = true;
	} // Activate

	public void Desactivate()
	{
		messageWin.writeMessage( "***DESACTIVATE MESSAGE RECEIVED - DESACTIVATING SYSTEM***" );

		isActive = false;
	} // Desactivate

	   
} // SecurityMonitor