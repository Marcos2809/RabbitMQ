/**
 * **************************************************************************************
 * File:EventManagerInterface.java 
 * Course: Software Architecture 
 * Project: Event Architectures
 * Institution: Autonomous University of Zacatecas 
 * Date: November 2015
 * Developer: Ferman Ivan Tovar 
 * Reviewer: Perla Velasco Elizondo
 * **************************************************************************************
 * This class provides an interface to the event manager for
 * participants (processes), enabling them to to send and receive events between
 * participants. A participant is any thing (thread, object, process) that
 * instantiates an EventEventManagerInterface object - this automatically
 * attempts to register that entity with the event manager
 * **************************************************************************************
 */
package event;

import java.rmi.*;
import java.net.*;
import java.util.*;
import java.text.SimpleDateFormat;
import com.rabbitmq.client.*;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.util.concurrent.TimeoutException;


public class RabbitMQInterface {

    private long participantId = -1;			// This processes ID
    private static final String EXCHANGE_NAME = "logs";
    private static String message = "";
    private static int eventId;

      
    /**
     * *************************************************************************
     * Exceptions::
     ***************************************************************************
     */
    class SendEventException extends Exception {

        SendEventException() {
            super();
        }

        SendEventException(String s) {
            super(s);
        }

    } // Exception

    class GetEventException extends Exception {

        GetEventException() {
            super();
        }

        GetEventException(String s) {
            super(s);
        }

    } // Exception

    class ParticipantAlreadyRegisteredException extends Exception {

        ParticipantAlreadyRegisteredException() {
            super();
        }

        ParticipantAlreadyRegisteredException(String s) {
            super(s);
        }

    } // Exception

    class ParticipantNotRegisteredException extends Exception {

        ParticipantNotRegisteredException() {
            super();
        }

        ParticipantNotRegisteredException(String s) {
            super(s);
        }

    } // Exception

    class LocatingEventManagerException extends Exception {

        LocatingEventManagerException() {
            super();
        }

        LocatingEventManagerException(String s) {
            super(s);
        }

    } // Exception

    class LocalHostIpAddressException extends Exception {

        LocalHostIpAddressException() {
            super();
        }

        LocalHostIpAddressException(String s) {
            super(s);
        }

    } // Exception

    class RegistrationException extends Exception {

        RegistrationException() {
            super();
        }

        RegistrationException(String s) {
            super(s);
        }

    } // Exception

    /**
     * This method registers participants with the event manager. 
     * This instantiation should be used when the event manager is on the local 
     * machine, using the default port.
     * 
     * @throws event.RabbitMQInterface.LocatingEventManagerException
     * @throws event.RabbitMQInterface.RegistrationException
     * @throws event.RabbitMQInterface.ParticipantAlreadyRegisteredException 
     */
    public RabbitMQInterface () throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

    }
    /**
     * This method allows participants to get their participant Id.
     * 
     * @return The ID
     * @throws event.RabbitMQInterface.ParticipantNotRegisteredException 
     */
    public long getMyId() throws ParticipantNotRegisteredException {
        if (participantId != -1) {
            return participantId;
        } else {
            throw new ParticipantNotRegisteredException("Participant not registered");
        } // if
    } // getMyId

    /**
     * This method allows participants to obtain the time of registration.
     * 
     * @return String time stamp in the format: yyyy MM dd::hh:mm:ss:SSS yyyy =
     * year MM = month dd = day hh = hour mm = minutes ss = seconds SSS =
     * milliseconds
     * @throws event.RabbitMQInterface.ParticipantNotRegisteredException 
     */
    public String getRegistrationTime() throws ParticipantNotRegisteredException {
        Calendar TimeStamp = Calendar.getInstance();
        SimpleDateFormat TimeStampFormat = new SimpleDateFormat("yyyy MM dd::hh:mm:ss:SSS");

        if (participantId != -1) {
            TimeStamp.setTimeInMillis(participantId);
            return (TimeStampFormat.format(TimeStamp.getTime()));

        } else {

            throw new ParticipantNotRegisteredException("Participant not registered");

        } // if

    } // getRegistrationTime

      public void sendEvent(String ideven, String messages) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        String message = messages;
        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + message + "'");
        channel.close();
        connection.close();
      }
//   
    /**
     * This methoourd get an event to the
     * event manager.
     * 
     * @return Event object.
     * @throws event.RabbitMQInterface.ParticipantNotRegisteredException
     * @throws event.RabbitMQInterface.GetEventException 
     */
    public void getEvent (int eventId) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, eventId+"");
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        Consumer consumer = new DefaultConsumer(channel) {
          @Override
          public void handleDelivery(String consumerTag, Envelope envelope,
                                     AMQP.BasicProperties properties, byte[] body) throws IOException {
            message = new String(body, "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
          }
        };
        channel.basicConsume(queueName, true, consumer);
        //return message;
  }
     public String returnMessage(){
        
        return message;
    } 
    public int returnid(){
        return eventId;
    }
    
} // EventManagerInterface
