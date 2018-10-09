# -*- coding: utf-8 -*-
"""
Created on Tue Oct  2 13:10:48 2018

@author: coren
"""
import ev3dev.ev3 as ev3
import signal

from time import sleep  #TEMP/////////////////////
# Connect two motors 
mAleft = ev3.LargeMotor('outA')
mBright = ev3.LargeMotor('outB')

#input, light sensors and gyro sensor
lightSensorLeft1 = ev3.ColorSensor('in1')
lightSensorRight2 = ev3.ColorSensor('in2') 
gyro = ev3.GyroSensor('in3')

# Check if the sensors are connected
assert lightSensorLeft1.connected, "LightSensorLeft(ColorSensor) is not connected"
assert lightSensorRight2.connected, "LightSensorRight(LightSensor) is not conected"

# Constantes

THRESHOLD_LEFT = 30 #Value for light sensor
THRESHOLD_RIGHT = 30#Value for light sensor
Stop_Value = 30 #Value for light sensor

No_Speed = 0
Forward_Speed = 30
Turn_Speed = 30

# Set the motor mode
mAleft.run_direct()
mBright.run_direct()
mAleft.polarity = "inversed"
mBright.polarity = "inversed"

# Put the gyro sensor into ANGLE mode.
gyro.mode='GYRO-ANG'

units = gyro.units

# The example doesn't end on its own.
# Use CTRL-C to exit it (needs command line).
def signal_handler(sig, frame):
	print('Shutting down gracefully')
	mAleft.duty_cycle_sp = 0
	mBright.duty_cycle_sp = 0
	exit(0)

# Install the signal handler for CTRL+C
signal.signal(signal.SIGINT, signal_handler)
print('Press Ctrl+C to exit')
#////////////////////////////////////////////////////////////////////////
def go_straight(Nbr_of_Square):
    mAleft.duty_cycle_sp=Forward_Speed
    mBright.duty_cycle_sp = Forward_Speed
    
    sensorLeft = get_value_left_light_sensor()
    sensorRight = get_value_right_light_sensor()
    
    while sensorLeft<Stop_Value and sensorRight<Stop_Value:
        sensorLeft = get_value_left_light_sensor()
        sensorRight = get_value_right_light_sensor()
        
    mAleft.duty_cycle_sp=No_Speed
    mBright.duty_cycle_sp = No_Speed



def get_value_left_light_sensor():
    sensorLeft = lightSensorLeft1.value()
    return sensorLeft
def get_value_right_light_sensor():
    sensorRight = lightSensorRight2.value()
    return sensorRight
def get_gyro_value():
    angle = gyro.value()
    return angle

def Turn (input_angle):
    angle=0
    side=-1
    if input_angle<0:
        side=1
        input_angle=input_angle +360
    i=0
    while i<500:
        mAleft.duty_cycle_sp=-Forward_Speed
        mBright.duty_cycle_sp = -Forward_Speed
        i=i+1
    print(str(angle) + " " + units)    
    while angle<input_angle-1 or angle>input_angle+1:    #précision du capteur impossible, si il détecte pas, fait un tour complet
        mAleft.duty_cycle_sp=-Turn_Speed*side
        mBright.duty_cycle_sp = Turn_Speed*side
        angle = get_gyro_value()
        angle=angle % 360
        print(str(angle) + " " + units)
        
    mAleft.duty_cycle_sp=No_Speed
    mBright.duty_cycle_sp = -No_Speed

Turn(90)
