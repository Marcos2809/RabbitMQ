/***
 * File:DoorSensor.java 
 * Course: Software Architecture 
 * Project: Event Architectures
 * Institution: CIMAT
 * Reviewer: Perla Velasco Elizondo
 * Update by: Equipo MEETMECORP
 * Date: 29/04/2016
 * Conection Rabbit MQ 
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
    public static String message = "";
    public static int event_id;
    public static String x;
    Channel channel;
    Connection connection;
    public int a = 1;

      
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
            try{
                connection = factory.newConnection();
                }catch(Exception e){
                        e.printStackTrace();
            }

    }
    /**
     * This method allows participants to get their participant Id.
     * 
     * @return The ID
     * @throws event.RabbitMQInterface.ParticipantNotRegisteredException 
     */
    public long getMyId() throws ParticipantNotRegisteredException {
       // if (participantId != -1) {
            return participantId;
     //   } else {
      //      throw new ParticipantNotRegisteredException("Participant not registered");
      //  } // if
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

      public void sendEvent(String messages, String ideven) throws Exception {
            channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            channel.basicPublish(EXCHANGE_NAME, "", null, messages.getBytes("UTF-8"));
            message=messages;
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
    public String getEvent () throws Exception {
        Channel channel = connection.createChannel();
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "");
        Consumer consumer = new DefaultConsumer(channel) {
          @Override
          public void handleDelivery(String consumerTag, Envelope envelope,
                                     AMQP.BasicProperties properties, byte[] body) throws IOException {
            message = new String(body, "UTF-8");
          }
        };
        channel.basicConsume(queueName, true, consumer);
        return message;
  }
    
     public String returnMessage(){
        String []values = this.message.split("&");
        
        if (values.length == 2)
          return values[0];
        
        return "";
    } 
    public int returnid(){
        String []values = this.message.split("&");
        
        if (values.length == 2)
           
           try {
               return Integer.parseInt(values[1]);
           } catch (NumberFormatException e) {
               return -1;
           }
       
        return -1;
    }
    
} // EventManagerInterface
