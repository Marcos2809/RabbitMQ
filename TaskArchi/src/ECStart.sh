echo "TemperatureController"
xterm -T "TemperatureController" -e "java cp .:rabbitmq-cliente.jar controllers.TemperatureController" &
echo "TemperatureSensor"
xterm -T "TemperatureSensor" -e "java .:rabbitmq-cliente.jar sensor.TemperatureSensor" &
echo "HumidityController"
xterm -T "HumidityController" -e "java  .:rabbitmq-cliente.jar controllers.HumidityController" &
echo "HumiditySensor"
xterm -T "HumiditySensor" -e "java  .:rabbitmq-cliente.jar sensor.HumiditySensor" &
echo "DoorController"
xterm -T "DoorController" -e "java .:rabbitmq-cliente.jar controllers.DoorController" &
echo "DoorController"
xterm -T "DoorSensor" -e "java .:rabbitmq-cliente.jar sensor.DoorSensor" &
echo "DoorController"
xterm -T "DoorSensor" -e "java .:rabbitmq-cliente.jar sensor.Sensor" &

echo Working...