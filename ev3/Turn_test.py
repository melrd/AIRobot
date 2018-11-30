# -*- coding: utf-8 -*-
"""
Created on Tue Oct  2 13:10:48 2018

@author: coren
"""
import ev3dev.ev3 as ev3
import signal

#from time import sleep  #TEMP/////////////////////
# Connect two motors 
mAleft = ev3.LargeMotor('outA')
mBright = ev3.LargeMotor('outB')
mCFence = ev3.MediumMotor('outC')

#input, light sensors and gyro sensor
lightSensorLeft1 = ev3.ColorSensor('in1')
lightSensorRight2 = ev3.ColorSensor('in4') 
gyro = ev3.GyroSensor('in2')
Sonar = ev3.UltrasonicSensor('in3') 

# Put the US sensor into distance mode.
Sonar.mode='US-DIST-CM'
units = Sonar.units    # reports 'cm' even though the sensor measures 'mm'



# Check if the sensors are connected
assert lightSensorLeft1.connected, "LightSensorLeft(ColorSensor) is not connected"
assert lightSensorRight2.connected, "LightSensorRight(LightSensor) is not conected"

# Constantes

THRESHOLD_LEFT = 20 #Value for light sensor
THRESHOLD_RIGHT = 20#Value for light sensor
Stop_Value = 20 #Value for light sensor

No_Speed = 0
Forward_Speed = -40
Correction_Speed=-20
Turn_Speed = -35
Forward_before_turn_speed=-50

Fence_down_speed=20
Fence_up_speed=-50
# Set the motor mode
mAleft.run_direct()
mBright.run_direct()
mAleft.polarity = "inversed"
mBright.polarity = "inversed"

mCFence.run_direct()
mCFence.polarity = "inversed"

# Put the gyro sensor into ANGLE mode.
gyro.mode='GYRO-ANG'

units = gyro.units

# The example doesn't end on its own.
# Use CTRL-C to exit it (needs command line).
def signal_handler(sig, frame):
	print('Shutting down gracefully')
	mAleft.duty_cycle_sp = 0
	mBright.duty_cycle_sp = 0
	mCFence.duty_cycle_sp=0
	exit(0)

# Install the signal handler for CTRL+C
signal.signal(signal.SIGINT, signal_handler)
print('Press Ctrl+C to exit')
################################################################################
def go_straight(Nbr_of_Square):
    mAleft.duty_cycle_sp=Forward_Speed
    mBright.duty_cycle_sp = Forward_Speed
    #initial_angle=get_gyro_value()
    stop_condition=0
    
    sensorLeft = get_value_left_light_sensor()
    sensorRight = get_value_right_light_sensor()
    print("left :  ")
    print(sensorLeft)
    print("right :  ")
    print(sensorRight)
    if sensorLeft<Stop_Value and sensorRight<Stop_Value:
        while sensorLeft<Stop_Value and sensorRight<Stop_Value:
            sensorLeft = get_value_left_light_sensor()
            sensorRight = get_value_right_light_sensor()
    while stop_condition==0:
        sensorLeft = get_value_left_light_sensor()
        sensorRight = get_value_right_light_sensor()
        print("left :  ")
        print(sensorLeft)
        print("right :  ")
        print(sensorRight)
        stop_condition=correction_trajectoire(sensorLeft,sensorRight)
        
    mAleft.duty_cycle_sp=No_Speed
    mBright.duty_cycle_sp = No_Speed
################################################################################
def correction_trajectoire(sensorLeft,sensorRight):
    if sensorLeft>Stop_Value and sensorRight>Stop_Value:
        mAleft.duty_cycle_sp=Forward_Speed
        mBright.duty_cycle_sp = Forward_Speed
        return 0
    elif sensorLeft>Stop_Value and sensorRight<Stop_Value:
        mAleft.duty_cycle_sp=Forward_Speed
        mBright.duty_cycle_sp = -Correction_Speed
        return 0
    elif sensorLeft<Stop_Value and sensorRight>Stop_Value:
        mAleft.duty_cycle_sp=-Correction_Speed
        mBright.duty_cycle_sp = Forward_Speed
        return 0
    elif sensorLeft<Stop_Value and sensorRight<Stop_Value:
        mAleft.duty_cycle_sp=No_Speed
        mBright.duty_cycle_sp = No_Speed
        return 1
    

def correction_trajectoire_backward(sensorLeft,sensorRight):
    if sensorLeft>Stop_Value and sensorRight>Stop_Value:
        mAleft.duty_cycle_sp=-Forward_Speed
        mBright.duty_cycle_sp = -Forward_Speed
        return 0
    elif sensorLeft>Stop_Value and sensorRight<Stop_Value:
        mAleft.duty_cycle_sp=-Forward_Speed
        mBright.duty_cycle_sp =Correction_Speed
        return 0
    elif sensorLeft<Stop_Value and sensorRight>Stop_Value:
        mAleft.duty_cycle_sp=Correction_Speed
        mBright.duty_cycle_sp = -Forward_Speed
        return 0
    elif sensorLeft<Stop_Value and sensorRight<Stop_Value:
        mAleft.duty_cycle_sp=No_Speed
        mBright.duty_cycle_sp = No_Speed
        return 1    
###############################################################################    
def management_can(distance):
    mAleft.duty_cycle_sp=Forward_Speed
    mBright.duty_cycle_sp = Forward_Speed

        
    distance_to_can = Sonar.value()  # en mm normalement
    print("distance_to_can")
    print(distance_to_can)
    while distance_to_can>50:
        distance_to_can = Sonar.value()
        mAleft.duty_cycle_sp= Forward_Speed*(distance_to_can-20)/distance_to_can
        mBright.duty_cycle_sp = Forward_Speed*(distance_to_can-20)/distance_to_can
        print("distance_to_can")
        print(distance_to_can)
    mAleft.duty_cycle_sp=No_Speed
    mBright.duty_cycle_sp = No_Speed
    print("Descendreeeee")
    move_fence(0)
    i=0
    while i<distance+1:
        go_straight(1)
        i=i+1
        print("case :  ")
        print(i)
    j=0
    while j<20:
        mAleft.duty_cycle_sp=-Forward_before_turn_speed
        mBright.duty_cycle_sp = -Forward_before_turn_speed
        j=j+1
        print("recule :  ")
        print(j)
    move_fence(1)
    stop_condition=0
    while stop_condition==0:
        sensorLeft = get_value_left_light_sensor()
        sensorRight = get_value_right_light_sensor()
        print("left :  ")
        print(sensorLeft)
        print("right :  ")
        print(sensorRight)
        stop_condition=correction_trajectoire_backward(sensorLeft,sensorRight)
    mAleft.duty_cycle_sp=No_Speed
    mBright.duty_cycle_sp = No_Speed    

#### 1 for mooving up, 0 for mooving down########################################
def move_fence(state):
    if state==1:
        i=0
        while i<250:
            mCFence.duty_cycle_sp=Fence_up_speed
            i=i+1
    if state==0:
        i=0
        while i<250:
            mCFence.duty_cycle_sp=Fence_down_speed
            i=i+1

    mCFence.duty_cycle_sp=No_Speed


###############################################################################
def get_value_left_light_sensor():
    sensorLeft = lightSensorLeft1.value()
    return sensorLeft
def get_value_right_light_sensor():
    sensorRight = lightSensorRight2.value()
    return sensorRight
def get_gyro_value():
    angle = gyro.value()
    angle= angle % 360
    return angle
###############################################################################
def Turn (input_angle):
    
    current_angle = get_gyro_value()
    print("angle initial  " + str(current_angle) + " " + units)
    sens_rotation=1
    if input_angle<0:   #Si angle négatif (-90°) On change le sens de rotation
        sens_rotation=1
        
    target_angle = (current_angle + input_angle) %360 # angle a atteindre, condition d'arret de la fonction
    print("angle objectif  " + str(target_angle) + " " + units)   
        
    #On avance un peu le robot pour que le centre de rotation soit au centre de la case
    i=0
    while i<200:
        mAleft.duty_cycle_sp=Forward_before_turn_speed
        mBright.duty_cycle_sp = Forward_before_turn_speed
        i=i+1
    angle = get_gyro_value()    
    print("angle initial  " + str(angle) + " " + units)
    
    if input_angle<0:   
        sens_rotation=-1
        if target_angle<31:
            while angle > target_angle and angle<329:
                mAleft.duty_cycle_sp=Turn_Speed*sens_rotation
                mBright.duty_cycle_sp = -Turn_Speed*sens_rotation
                angle = get_gyro_value()
                print("angle = " + str(angle) + " " + units)  
        else:                
            while angle > target_angle or angle<(target_angle-30)%360:
                mAleft.duty_cycle_sp=Turn_Speed*sens_rotation
                mBright.duty_cycle_sp = -Turn_Speed*sens_rotation
                angle = get_gyro_value()
                print("angle = " + str(angle) + " " + units)  
    else:
        if target_angle>329:
            while angle<target_angle and angle>31:
                mAleft.duty_cycle_sp=Turn_Speed*sens_rotation
                mBright.duty_cycle_sp = -Turn_Speed*sens_rotation
                angle = get_gyro_value()
                print("angle = " + str(angle) + " " + units)  
        else:
            while angle < target_angle or angle>(target_angle+30)%360:     #précision du capteur impossible, si il détecte pas, fait un tour complet
                mAleft.duty_cycle_sp=Turn_Speed*sens_rotation
                mBright.duty_cycle_sp = -Turn_Speed*sens_rotation
                angle = get_gyro_value()
                print("angle = " + str(angle) + " " + units)  

        
    mAleft.duty_cycle_sp=No_Speed
    mBright.duty_cycle_sp = -No_Speed
#################################################################################
def read_order():
    file = open("solver_order.txt", "r")
    order=file.read()
    i=0
    while i < len(order):
        x=file[i]
        if x=='f':        
            go_straight(1)
        elif x=='l':
            Turn(-90)
        elif x=='r':
            Turn(90)
        elif x=='b':
            Turn(180)
        elif x=='c':
            management_can(1)
        i=i+1

        
    file.close()


####Commandes a tetser

management_can(1)
#move_fence(0)
#move_fence(1)
#go_straight(1)
#go_straight(1)
#Turn(-90)
#go_straight(1)
#Turn(-90)
#go_straight(1)
#go_straight(1)
#Turn(-90)
#go_straight(1)
#Turn(-90)
