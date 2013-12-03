import RPi.GPIO as GPIO
import sys
import time
import random
import socket
import os
import glob
from threading import Thread

#INITIALIZING GLOBAL VARIABLES
global base_dir
global device_folder
global device_file
global has_webcam
global has_tempsensor
global has_hrmonitor
global k
#global hostname = '10.0.0.23'

# INITIALIZING GERTBOARD BUTTONS AND LIGHTS
GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM)


for i in range(24,26): # set up ports 24-25
    GPIO.setup(i, GPIO.IN, pull_up_down=GPIO.PUD_UP) # as inputs pull-ups high (buttons)

GPIO.setup(23, GPIO.OUT) # set up port 23 for output (lights)

ports = [11, 10, 9, 8, 7]
for port_num in ports:
    GPIO.setup(port_num, GPIO.OUT) # set up ports for output

n=7
while n < 12:
    GPIO.output(n,0)
    n = n+1

previous_status = ''
z = 0
x = 0

#INITIALIZING SYSTEM CONSTANTS & ARGUMENTS
os.system('modprobe w1-gpio')
os.system('modprobe w1-therm')
base_dir = '/sys/bus/w1/devices/'
device_folder = glob.glob(base_dir + '28*')[0]
device_file = device_folder + '/w1_slave'

#This function reads in the 12 bit temperature word from the I2C data pin
def read_temp_raw():
    f = open(device_file, 'r')
    lines = f.readlines()
    f.close()
    return lines

#This function calls the read_temp_raw function, and converts the raw bits into a human readable temp.
#It then returns the temp in *C
def read_temp():
    lines = read_temp_raw()
    while lines[0].strip()[-3:] != 'YES':
        time.sleep(0.2)
        lines = read_temp_raw()
    equals_pos = lines[1].find('t=')
    if equals_pos != -1:
        temp_string = lines[1][equals_pos+2:]
        temp_c = float(temp_string) / 1000.0
        return temp_c

def warning_button():
    while True:
        try:
            if GPIO.input(25)==0: 	#check if the "warning button" was pressed
                n=7
                while n<12:
                     GPIO.output(n,1)	#turn the lights on
                     n = n+1
                k=0
        
            if GPIO.input(24)==0 and k==0:    #check if the "everything is ok button" was pressed and if the light has already be turned on (k==1)
                n=7
                while n<12:
                     GPIO.output(n,0)	#turn the lights off
                     n = n+1
                k=1
        except KeyboardInterrupt:
		print "returned from button cheking"	# allows us to close the thread when Ctrl+C pressed
#RUNNING THE PROGRAM
k=1
try: 
    t1 = Thread(target=warning_button, args = ())	#make a thread for the buttons
    t1.daemon=True		#setting thread as background thread
    t1.start()			# starts the thread
    while True:
       print(read_temp())	# starts the reading of the temperature sensor
       time.sleep(1)		#only sends temperature once every second

except KeyboardInterrupt:
    print "returned from thread"	# allows us to close the thread when Ctrl+C pressed


        

        
