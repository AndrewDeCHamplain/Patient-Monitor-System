Patient-Monitor-System
======================

The project is a simplified patient monitoring system like one that would be used in a hospital. We have 4 Raspberry Pi's in the system: 1 to run a Java server with the GUI for easy interaction and the other 3 as patients/clients for gathering data. Each patient/client have a camera, heart rate sensor, and temperature sensor. They stream the video to a local web page that the server displays using VLCJ while the temperature and heart rate are sent using UDP sockets which are displayed under the respective video stream.
