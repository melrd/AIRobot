#!/usr/bin/env python3
#
# Check
#  https://github.com/ev3dev/ev3dev-lang-python
# on more info for this ev3dev2 script.
#
# Or read
#  https://sites.google.com/site/ev3python/
# for the older ev3dev (v1) interface.

from ev3dev2.motor import LargeMotor, \
                          SpeedPercent, \
                          OUTPUT_A
#from ev3dev2.sensor import INPUT_1
#from ev3dev2.sensor.lego import TouchSensor
#from ev3dev2.led import Leds

m = LargeMotor(OUTPUT_A)
m.on_for_rotations(SpeedPercent(75), 5)
