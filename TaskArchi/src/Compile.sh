echo "Compile Controllers"
javac -cp .rabbitmq-client.jar controllers/*.java

echo "Compile Sensors"
javac -cp .rabbitmq-client.jar sensors/*.java


echo Done. 
echo Press enter to continue...
read