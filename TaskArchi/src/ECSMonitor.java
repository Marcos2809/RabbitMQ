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
import views.MumaMonitor1;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.rabbitmq.client.*;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import java.awt.Color;
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
    MumaMonitor1 mMonitor = MumaMonitor1.getINSTANCE();			
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
              System.out.println("ECSMonitor::Error instantiating event manager interface: " + e);
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

        
        mMonitor.setVisible(true);


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


            mMonitor.txtTemperature.setText(String.valueOf(currentTemperature) + " F");
            mMonitor.txtHumidity.setText(String.valueOf(currentHumidity) + " %");

            // Check temperature and effect control as necessary
            if (currentTemperature < tempRangeLow) { // temperature is below threshhold
                mMonitor.txtTemperature.setBackground(Color.red);
                mMonitor.txtTemperature.setForeground(Color.white);
                try{
                    heater(on);
                    chiller(off);
                }catch(Exception e){

                }
            }
            else {
                if (currentTemperature > tempRangeHigh) { // temperature is above threshhold
                    mMonitor.txtTemperature.setBackground(Color.red);
                    mMonitor.txtTemperature.setForeground(Color.white);
                    try{
                        heater(off);
                        chiller(on);
                    }catch(Exception e){

                    }
                }
                else {
                    mMonitor.txtTemperature.setBackground(Color.green);
                    mMonitor.txtTemperature.setForeground(Color.black);
                    try{
                        heater(off);
                        chiller(off);
                        
                    }catch(Exception e){

                    }
                } // if
            } // if

            // Check humidity and effect control as necessary
            if (currentHumidity < humiRangeLow) {
                mMonitor.txtHumidity.setBackground(Color.red);
                mMonitor.txtHumidity.setForeground(Color.white);
                try{    
                    humidifier(on);
                    dehumidifier(off);
                }catch(Exception e){

                }
            }
            else {
                if (currentHumidity > humiRangeHigh) { // humidity is above threshhold
                    mMonitor.txtHumidity.setBackground(Color.red);
                    mMonitor.txtHumidity.setForeground(Color.white);
                    try{
                        humidifier(off);
                        dehumidifier(on);
                    }catch(Exception e){

                    }
                }
                else {
                    mMonitor.txtHumidity.setBackground(Color.green);
                    mMonitor.txtHumidity.setForeground(Color.black);
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
            
            tempRangeHigh = mMonitor.sldTempMax.getValue();
            tempRangeLow = mMonitor.sldTempMin.getValue();
            humiRangeHigh = mMonitor.sldHumMax.getValue();
            humiRangeLow = mMonitor.sldHumMin.getValue();
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
                mMonitor.lblHeater.setText("HEATER ON");
                mMonitor.txtHeater.setBackground(Color.green);
            }
            else {
                message = Component.HEATER_OFF;
                mMonitor.lblHeater.setText("HEATER OFF");
                mMonitor.txtHeater.setBackground(Color.black);
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
                mMonitor.lblChiller.setText("CHILLER ON");
                mMonitor.txtChiller.setBackground(Color.green);
                
            }
            else {
                message = Component.CHILLER_OFF;
                mMonitor.lblChiller.setText("CHILLER OFF");
                mMonitor.txtChiller.setBackground(Color.black);
                
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
                mMonitor.lblHumidifier.setText("HUMIDIFIER ON");
                mMonitor.txtHumidifier.setBackground(Color.green);
            }
            else {
                message = Component.HUMIDIFIER_OFF;
                mMonitor.lblHumidifier.setText("HUMIDIFIER OFF");
                mMonitor.txtHumidifier.setBackground(Color.black);
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
                mMonitor.lblDehumidifier.setText("DEHUMIDIFIER ON");
                mMonitor.txtDehumidifier.setBackground(Color.green);
            }
            else {
                message = Component.DEHUMIDIFIER_OFF;
                mMonitor.lblDehumidifier.setText("DEHUMIDIFIER OFF");
                mMonitor.txtDehumidifier.setBackground(Color.black);
           } // if
            try {
            canalTempControlador.basicPublish("", "HumidityControlador" , null, message.getBytes());
            } // heater
            catch (Exception e) {
             }
    }
          // } // Dehumidifier
} // ECSMonitor
