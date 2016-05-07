echo "Ejecutando Controllers"
java -cp TaskAri.jar &
java -cp TaskAri.jar sensors/DoorSensor &
java -cp TaskAri.jar sensors/HumiditySensor &
java -cp TaskAri.jar sensors/MovementSensor &
java -cp TaskAri.jar sensors/TemperatureSensor &
java -cp TaskAri.jar sensors/WindowSensor &
java -cp TaskAri.jar sensors/MovementSensor
echo Done. 
echo Press enter to continue...
read