# -*- coding: utf-8 -*-
"""
Created on Wed Sep 26 18:00:26 2018

@author: coren
 test avancer tout droit
"""

#!/usr/bin/python3

import ev3dev.ev3 as ev3
import signal

# Connect two motors and two (different) light sensors
mA = ev3.LargeMotor('outA')
mB = ev3.LargeMotor('outB')

lightSensorLeft = ev3.ColorSensor('in1')
lightSensorRight = ev3.ColorSensor('in2') 

# Use constants to later acces motor speeds and sensor thresholds
THRESHOLD_LEFT = 30 
THRESHOLD_RIGHT = 30

BASE_SPEED = 0
TURN_SPEED = 30

# Check if the motors are connected
assert lightSensorLeft.connected, "LightSensorLeft(ColorSensor) is not connected"
assert lightSensorRight.connected, "LightSensorRight(LightSensor) is not conected"

# Set the motor mode
mB.run_direct()
mA.run_direct()

mA.polarity = "inversed"
mB.polarity = "inversed"

# The example doesn't end on its own.
# Use CTRL-C to exit it (needs command line).
# This is a generic way to be informed
# of this event and then take action.
def signal_handler(sig, frame):
	print('Shutting down gracefully')
	mA.duty_cycle_sp = 0
	mB.duty_cycle_sp = 0

	exit(0)

# Install the signal handler for CTRL+C
signal.signal(signal.SIGINT, signal_handler)
print('Press Ctrl+C to exit')

# Endless loop reading sensors and controlling motors.
while True:
	sensorLeft = lightSensorLeft.value()
	sensorRight = lightSensorRight.value()
	print("sensorLeft: ", sensorLeft, " sensorRight: ", sensorRight)
	if (sensorRight < THRESHOLD_RIGHT) and (sensorRight < THRESHOLD_RIGHT):        
        mA.duty_cycle_sp = TURN_SPEED
        mB.duty_cycle_sp = TURN_SPEED
        
    if (sensorRight > THRESHOLD_RIGHT) and (sensorRight < THRESHOLD_RIGHT):
        mA.duty_cycle_sp = BASE_SPEED
        mB.duty_cycle_sp = TURN_SPEED

        
    if (sensorRight < THRESHOLD_RIGHT) and (sensorRight > THRESHOLD_RIGHT) :
        mA.duty_cycle_sp = TURN_SPEED
        mB.duty_cycle_sp = BASE_SPEED
     

	if (sensorRight > THRESHOLD_RIGHT) and (sensorRight > THRESHOLD_RIGHT):
        mA.duty_cycle_sp = BASE_SPEED
        mB.duty_cycle_sp = BASE_SPEED
     

