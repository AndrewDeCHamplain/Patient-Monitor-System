import RPi.GPIO as GPIO
import sys
import time
from random import randint
import socket
import os
import glob
from threading import Thread
from bs4 import BeautifulSoup as Soup

#INITIALIZING GLOBAL VARIABLES
global has_webcam
global has_tempsensor
global has_hrmonitor
global bt
global hr
global CurrentBt
global CurrentHr
global ipaddress
global client
global configuration
global panic
global s

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

def check_config(file_name):
    global has_tempsensor
    global has_webcam
    global has_hrmonitor
    global ipaddress
    global serverip
    file = file_name
    handler = open(file).read()
    soup = Soup(handler)
    if (soup.find('camera').string == "TRUE"):
        has_webcam = 1
    else:
	    has_webcam = 0

    print has_webcam

    if (soup.find('heartmon').string == "TRUE"):
        has_hrmonitor = 1
    else:
	    has_hrmonitor = 0

    print has_hrmonitor

    if (soup.find('tempmon').string == "TRUE"):
        has_tempsensor = 1
    else:
	    has_tempsensor = 0

    print has_tempsensor
    ipaddress = soup.find('ipaddress').string
    print ipaddress

    serverip = soup.find('server').string

    print serverip

#This function reads in the 12 bit temperature word from the I2C data pin
def read_temp_raw():
    f = open(device_file, 'r')
    lines = f.readlines()
    f.close()
    return lines

#This function calls the read_temp_raw function, and converts the raw bits into a human readable temp.
#It then returns the temp in *C
#If this pi doesn't have a temperature sensor, it will randomly generate a temperature.
def read_temp():
    global has_tempsensor
    global CurrentBt
    if has_tempsensor == 1:
        lines = read_temp_raw()
        while lines[0].strip()[-3:] != 'YES':
            time.sleep(0.2)
            lines = read_temp_raw()
        equals_pos = lines[1].find('t=')
        if equals_pos != -1:
            temp_string = lines[1][equals_pos+2:]
            temp_c = float(temp_string) / 1000.0
            return temp_c
    else:
        rand = randint(0,50)
        d = rand/100.0
        if bt > CurrentBt:
            CurrentBt = CurrentBt + d
                    
        else:
            CurrentBt = CurrentBt - d
        return CurrentBt

def read_heart():
    global has_hrmonitor 
    global CurrentHr
    if (has_hrmonitor):
        #should never get here, since none of our raspberry pi's have a HR monitor. This method can be expanded to accomodate one if they are purchased in future.
        print "Fix your XML file, there's no HR monitor!"
    else:
        rand = 0
        ratechange = randint(0,4)
        sign = randint(0,2)
        if (hr - 15 > CurrentHr):
            CurrentHr = CurrentHr + ratechange
        elif (hr + 15 < CurrentHr):
            CurrentHr = CurrentHr - ratechange
        else:
            if(sign == 0):
                CurrentHr = CurrentHr + ratechange
            else:
                CurrentHr = CurrentHr - ratechange
    return CurrentHr

                    
################


def panic_button():
    global panic
    while True:
        if GPIO.input(25)==0 and panic == False: 	#check if the "panic button" was pressed
            n=7
            while n<12:
                 GPIO.output(n,1)	#turn the lights on
                 n = n+1
            panic = True
            print ("Panic: ", panic)
        
        if GPIO.input(24)==0 and panic == True:    #check if the "everything is ok button" was pressed and if the light has already been turned on (k==0)
            n=7
            while n<12:
                 GPIO.output(n,0)	#turn the lights off
                 n = n+1
            panic = False
            print ("Panic: ", panic)
 
################

def make_packet():
    global panic
    global ipaddress
    return getTemp() + getHeartRate() + str(panic)

def send_packet():
    global s
    checkWarning()
    #s.send(make_packet())
    print make_packet()
    
def getTemp():
    return "temp " + str(read_temp()) +" "

def getHeartRate():
    return "hr " + str(read_heart()) +" "

def checkWarning():
    warningList = ""
    if CurrentBt < bt - 2 or CurrentBt > bt + 2:
        warningList = warningList + "temp "
    if


#RUNNING THE PROGRAM

configuration = "config.xml"
check_config(configuration)
hr = 70
if has_tempsensor:
    print "Hold temperature node for auto config"
    roomtemp = read_temp()
    while read_temp() < roomtemp + 1  != read_temp() > roomtemp + 1:
        pass
    print "Configuring BT..."
    time.sleep(3)
    bt = read_temp()
    print "Your standard BT is " + str(bt)

else:
    bt = 37
    CurrentBt=bt
CurrentHr=hr
panic = False
try: 
    t1 = Thread(target=panic_button, args = ())	#make a thread for the buttons
    t1.daemon=True		#setting thread as background thread
    t1.start()			# starts the thread
    #s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    #s.connect((hostname, 5050))
    while True:
        send_packet()
        time.sleep(1)
except KeyboardInterrupt:
    print "Program Ended"	# allows us to close the thread when Ctrl+C pressed


        

        
