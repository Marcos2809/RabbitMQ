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
import controllers.TemperatureController;
import com.rabbitmq.client.Channel;
//import event.RabbitMQInterface;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.rabbitmq.client.*;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
//import common.conectorrabbit;

public class ECSMonitor extends Thread {


  //  private conectorrabbit em = null;
    private float tempRangeHigh = 100;                  // These parameters signify the temperature and humidity ranges in terms
    private float tempRangeLow = 0;			// of high value and low values. The ECSmonitor will attempt to maintain
    private float humiRangeHigh = 100;                  // this temperature and humidity. Temperatures are in degrees Fahrenheit
    private float humiRangeLow = 0;			// and humidity is in relative humidity percentage.
    boolean registered = true;				// Signifies that this class is registered with an event manager.
    MessageWindow messageWin = null;			// This is the message window
    Indicator tempIndicator;				// Temperature indicator
    Indicator humIndicator;				// Humidity indicator
    private Connection connection;
    private Channel channel, canalTempSensor, canalTempControlador, canalHumSensor, canalHumControlador,  canalsecSensor, canalsecControlador;
    float currentTemperature = 0;           // Current temperature as reported by the temperature sensor
    float currentHumidity = 0;
    
    public ECSMonitor() {
        // event manager is on the local system
        try{
            canalTempSensor = conectorrabbit("TempSensor");
            canalTempControlador = conectorrabbit("TempControlador");
            canalHumSensor = conectorrabbit("HumiditySensor");
            canalHumControlador = conectorrabbit("HumidityControlador");
            canalsecSensor = conectorrabbit("SecSensor");
            canalsecControlador = conectorrabbit("SecControlador");

        }catch(Exception e){
            //  System.out.println("ECSMonitor::Error instantiating event manager interface: " + e);
        }
    } //Constructor
    
  protected Channel conectorrabbit(String connector) throws IOException, TimeoutException{
                ConnectionFactory factory = new ConnectionFactory();
                factory.setHost("localhost");
                // Declaration Publish 
                connection = factory.newConnection();
                channel = connection.createChannel();
                channel.queueDeclare(connector, false, false, false, null);
                return channel; 
    }
   
    @Override
    public void run() {
        int delay = 1000;			// The loop delay (1 second)
        boolean isDone = false;			// Loop termination flag
        boolean on = true;			// Used to turn on heaters, chillers, humidifiers, and dehumidifiers
        boolean off = false;			// Used to turn off heaters, chillers, humidifiers, and dehumidifiers

        
            // Now we create the ECS status and message panel
            // Note that we set up two indicators that are initially yellow. This is
            // because we do not know if the temperature/humidity is high/low.
            // This panel is placed in the upper left hand corner and the status 
            // indicators are placed directly to the right, one on top of the other

            messageWin = new MessageWindow("ECS Monitoring Console", 0, 0);
            tempIndicator = new Indicator("TEMP UNK", messageWin.getX() + messageWin.width(), 0);
            humIndicator = new Indicator("HUMI UNK", messageWin.getX() + messageWin.width(), (int) (messageWin.height() / 2), 2);

            messageWin.writeMessage("Registered with the event manager.");

            

            /**
             * ******************************************************************
             ** Here we start the main simulation loop
             * *******************************************************************
             */
            while (!isDone) {
                // Here we get our event queue from the event manager
                Consumer consumer = new DefaultConsumer(canalTempSensor) {
                    public void handleDelivery(String consumerTag, Envelope envelope, 
                        AMQP.BasicProperties properties, byte[] body) throws java.io.IOException {
			String message = new String(body, "UTF-8");
                        setTemperature(Float.valueOf(message));
                    }
                };
                
                try {
                    canalTempSensor.basicConsume("TempSensor", true, consumer);
                } catch (IOException ex) { 
                Logger.getLogger(ECSMonitor.class.getName()).log(Level.SEVERE, null, ex);
            }
                
                Consumer consumerHumSensor = new DefaultConsumer(canalHumSensor) {
                    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws java.io.IOException {
			String message = new String(body, "UTF-8");
                        setHumidity(Float.valueOf(message));
                    }
                };
                
                try {
                    canalTempSensor.basicConsume("HumiditySensor", true, consumerHumSensor);
                } catch (IOException ex) {
                    Logger.getLogger(ECSMonitor.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
             /*

                    if (evt.getEventId() == 2) { // Humidity reading
                        try {
                            currentHumidity = Float.valueOf(evt.getMessage()).floatValue();
                        } // try
                        catch (Exception e) {
                            messageWin.writeMessage("Error reading humidity: " + e);
                        } // catch // catch
                    } // if

                    // If the event ID == 99 then this is a signal that the simulation
                    // is to end. At this point, the loop termination flag is set to
                    // true and this process unregisters from the event manager.
                    /*if (evt.getEventId() == 99) {
                        isDone = true;
                        try {
                            em.unRegister();
                        } // try
                        catch (Exception e) {
                            messageWin.writeMessage("Error unregistering: " + e);
                        } // catch // catch

                        messageWin.writeMessage("\n\nSimulation Stopped. \n");
                        // Get rid of the indicators. The message panel is left for the
                        // user to exit so they can see the last message posted.
                        humIndicator.dispose();
                        tempIndicator.dispose();
                    } // if*/
                

                messageWin.writeMessage("Temperature:: " + currentTemperature + "F  Humidity:: " + currentHumidity);
                // Check temperature and effect control as necessary
                if (currentTemperature < tempRangeLow) { // temperature is below threshhold
                    tempIndicator.setLampColorAndMessage("TEMP LOW", 3);
                    try{
                        heater(on);
                        chiller(off);
                    }catch(Exception e){
                        
                    }
                }
                else {
                    if (currentTemperature > tempRangeHigh) { // temperature is above threshhold
                        tempIndicator.setLampColorAndMessage("TEMP HIGH", 3);
                        try{
                            heater(off);
                            chiller(on);
                        }catch(Exception e){
                        
                        }
                    }
                    else {
                        tempIndicator.setLampColorAndMessage("TEMP OK", 1); // temperature is within threshhold
                        try{
                            heater(off);
                            chiller(off);
                        }catch(Exception e){
                        
                        }
                    } // if
                } // if

                // Check humidity and effect control as necessary
                if (currentHumidity < humiRangeLow) {
                    humIndicator.setLampColorAndMessage("HUMI LOW", 3); // humidity is below threshhold
                    try{    
                        humidifier(on);
                        dehumidifier(off);
                    }catch(Exception e){
                        
                    }
                }
                else {
                    if (currentHumidity > humiRangeHigh) { // humidity is above threshhold
                        humIndicator.setLampColorAndMessage("HUMI HIGH", 3);
                        try{
                            humidifier(off);
                            dehumidifier(on);
                        }catch(Exception e){
                        
                        }
                    }
                    else {
                        humIndicator.setLampColorAndMessage("HUMI OK", 1); // humidity is within threshhold
                        try{
                            humidifier(off);
                            dehumidifier(off);
                        }catch(Exception e){
                        
                        }
                    } // if
                } // if

                // This delay slows down the sample rate to Delay milliseconds
                try {
                    Thread.sleep(delay);
                } // try
                catch (Exception e) {
                    System.out.println("Sleep error:: " + e);
                } // catch
            } // while
        
       
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
     * This method sets the temperature range
     *
     * @param lowtemp low temperature range
     * @param hightemp high temperature range
     */
    public void setTemperatureRange(float lowtemp, float hightemp) {
        tempRangeHigh = hightemp;
        tempRangeLow = lowtemp;
        messageWin.writeMessage("***Temperature range changed to::" + tempRangeLow + "F - " + tempRangeHigh + "F***");
    } // setTemperatureRange

    /**
     * This method sets the humidity range
     *
     * @param lowhumi low humidity range
     * @param highhumi high humidity range
     */
    public void setHumidityRange(float lowhumi, float highhumi) {
        humiRangeHigh = highhumi;
        humiRangeLow = lowhumi;
        messageWin.writeMessage("***Humidity range changed to::" + humiRangeLow + "% - " + humiRangeHigh + "%***");
    } // setTemperatureRange
    
    public void setTemperature(float temp){
        
        currentTemperature = temp;
    }
    
    public void setHumidity(float hum){
        currentHumidity = hum;
    }
    /**
     * This method posts an event that stops the environmental control system.
     * Exceptions: Posting to event manager exception
     */
    /**
     * This method posts events that will signal the temperature controller to
     * turn on/off the heater
     *
     * @param ON indicates whether to turn the heater on or off. Exceptions:
     * Posting to event manager exception
     */
    private void heater(boolean ON) {
            // Here we create the event.
            String message;
            if (ON) {
                message = Component.HEATER_ON;
            }
            else {
                message = Component.HEATER_OFF;
           } // if
            try {
            canalTempControlador.basicPublish("", "TempControlador" , null, message.getBytes());
            } // heater
            catch (Exception e) {
             }
    }

    /**
     * This method posts events that will signal the temperature controller to
     * turn on/off the chiller
     *
     * @param ON indicates whether to turn the chiller on or off. Exceptions:
     * Posting to event manager exception
     */
    private void chiller(boolean ON) {

            // Here we create the event.
            String message;
            if (ON) {
                message = Component.CHILLER_ON;
                //  canalTempControlador.basicPublish("", "TempControlador" , null, message.getBytes());
                
            }
            else {
                message = Component.CHILLER_OFF;
                //canalTempControlador.basicPublish("", "TempControlador" , null, message.getBytes());
            } // if
            try {
                canalTempControlador.basicPublish("", "TempControlador" , null, message.getBytes());
            // Here we send the event to the event manager.
            } // Chiller
            catch (Exception e) {
            }
    }

    /**
     * This method posts events that will signal the humidity controller to turn
     * on/off the humidifier
     *
     * @param ON indicates whether to turn the humidifier on or off. Exceptions:
     * Posting to event manager exception
     */
    private void humidifier(boolean ON)throws Exception {
        // Here we create the event.
         String message;
            if (ON) {
                message = Component.HUMIDIFIER_ON;
            }
            else {
                message = Component.HUMIDIFIER_OFF;
           } // if
            try {
            canalTempControlador.basicPublish("", "HumidityControlador" , null, message.getBytes());
            } // heater
            catch (Exception e) {
             }  
        
    } // Humidifier

    /**
     * This method posts events that will signal the humidity controller to turn
     * on/off the dehumidifier
     *
     * @param ON indicates whether to turn the dehumidifier on or off.
     * Exceptions: Posting to event manager exception
     */
    private void dehumidifier(boolean ON)throws Exception {
        // Here we create the event.
           String message;
            if (ON) {
                message = Component.DEHUMIDIFIER_ON;
            }
            else {
                message = Component.DEHUMIDIFIER_OFF;
           } // if
            try {
            canalTempControlador.basicPublish("", "HumidityControlador" , null, message.getBytes());
            } // heater
            catch (Exception e) {
             }
    }
          // } // Dehumidifier
} // ECSMonitor
