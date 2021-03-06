/**
 * **************************************************************************************
 * File:Component.java 
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
 * This class is the parent of all devices on this system, sensors and controllers.
 * Contains the IP address of the server where is the event manager.
 * **************************************************************************************
 */
package common;

import event.Event;
import event.RabbitMQInterface;
//import event.EventManagerInterface;
import event.EventQueue;

public class Component {

    public static String SERVER_IP = "127.0.0.1";

    public final static int TEMPERATURE = 1;
    public final static int HUMIDITY = 2;
    public final static int DOOR = 3;
    public final static int WINDOW= 6;
    public final static int MOVEMENT=7;
    public final static int FIRE=10;
    public final static int SPRINKE=11;

    public final static int TEMPERATURE_SENSOR = -5;
    public final static int HUMIDITY_SENSOR = -4;
    public final static int DOOR_SENSOR= -3;
    public final static int WINDOW_SENSOR= -6;
    public final static int MOVEMENT_SENSOR = -7;
    public final static int FIRE_SENSOR= -9;
    public final static int TEMPERATURE_CONTROLLER = 5;
    public final static int HUMIDITY_CONTROLLER = 4;
    public final static int SECURITY_CONTROLLER= 8;
    public final static int FIRE_CONTROLLER= 9;
    public final static int END = 99;
    
    public final static String HEATER_ON = "H1";
    public final static String HEATER_OFF = "H0";
    public final static String CHILLER_ON = "C1";
    public final static String CHILLER_OFF = "C0";

    public final static String HUMIDIFIER_ON = "H1";
    public final static String HUMIDIFIER_OFF = "H0";
    public final static String DEHUMIDIFIER_ON = "D1";
    public final static String DEHUMIDIFIER_OFF = "D0";
    public final static String DOOR_ON = "DO1";
    public final static String DOOR_OFF = "DO0";
    public final static String WINDOW_ON = "W1";
    public final static String WINDOW_OFF = "W0";
    public final static String MOVEMENT_ON = "M1";
    public final static String MOVEMENT_OFF = "M0";
    public final static String FIRE_ON = "FD1";
    public final static String FIRE_OFF = "FD0";
    public final static String SPRINKLE_ON = "SP1";
    public final static String SPRINKLE_OFF = "SP0";

    protected RabbitMQInterface evtMgrI = null;         // Interface object to the RabbitMQ Interface

    protected Component() {
            System.out.println("\n\nAttempting to register on the local machine...");
            try {
                // Here we create an event manager interface object. This assumes
                // that the event manager is on the local machine
                evtMgrI = new RabbitMQInterface();
            }
            catch (Exception e) {
                System.out.println("Error instantiating event manager interface: " + e);
            } // catch
    }
    // This is to accomplish with singleton pattern
    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}
