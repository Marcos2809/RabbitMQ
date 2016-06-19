/**
 * **************************************************************************************
 * File:Controller.java 
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
 * This class contains the necessary to build a controller, in order to every 
 * controller extends from this.
 * **************************************************************************************
 */
package controllers;

import common.Component;
import com.rabbitmq.client.*;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Controller extends Component {
    protected int delay = 2500;				// The loop delay (2.5 seconds)
    protected boolean isDone = false;			// Loop termination flag
    protected String conector;
    protected Connection connection;
    private Channel channel;
    
    protected Controller() {
        super();
    }
    
    protected Channel conectorrabbit(String connector) throws IOException, TimeoutException{
                ConnectionFactory factory = new ConnectionFactory();
                factory.setHost("localhost");
                // Declaration Publish 
                connection = factory.newConnection();
                channel = connection.createChannel();
                channel.queueDeclare(connector, false, false, false, null);
                return channel; 
    }
      protected void confirmMessage(String conector, String message) throws Exception {
        // Create the event.
        Channel channel = conectorrabbit(conector);
        channel.basicPublish("", conector, null, message.getBytes());
        channel.close();
	connection.close();
    } // PostMessage*/   
}
