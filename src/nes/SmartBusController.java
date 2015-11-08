/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nes;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;
import java.net.*;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class SmartBusController extends Thread{
    
    String ip;
    int port;
    InetAddress IPAddress;
    DatagramSocket clientSocket;
    SmartBusListener listener;
    
    volatile LinkedBlockingQueue<SmartBusHDLPackage> packagesToSend = new LinkedBlockingQueue();
    volatile Object mutex;
    
    SmartBusController(String ip, int port, Object mutex)
    {
        this.port = port;
        this.ip = ip;
        this.mutex = mutex;
    }

    SmartBusController(Object mutex) {
        this.port = 6000;
        this.ip = "192.168.1.255";  
        this.mutex = mutex;
    }
            
    @Override
    public void run()
    {                
        try 
        {
            this.clientSocket = new DatagramSocket(this.port);
            this.clientSocket.setBroadcast(true);
            this.IPAddress = InetAddress.getByName(this.ip); 
            
                                    
            listener = new SmartBusListener(this.clientSocket);
            listener.start();
                                
            System.out.println("[INFO] SmartBus server is running at port "+this.port);

            SmartBusHDLPackage pkg;
                    
            while(true)
            {
               try
               {
                    synchronized(this.mutex)
                    {
                        if(this.packagesToSend.size() == 0)
                            this.mutex.wait();
                        pkg = this.packagesToSend.poll();
                    }
                    if(pkg != null)
                    {
                        byte[] buffer = pkg.Produce();
                        DatagramPacket sendPkg = new DatagramPacket(buffer, buffer.length, this.IPAddress, this.port);
                        
                        try 
                        {
                            clientSocket.send(sendPkg);
                            System.out.println("[INFO] Package sent to the SmartBus:");      
                            for (int i = 0; i < buffer.length; i++) 
                            {
                                System.out.print(Integer.toHexString(buffer[i]& 0xFF));
                                System.out.print(',');
                            }
                            System.out.println();

                        } 
                        catch (IOException e) 
                        {
                                    System.out.println("[WARN] Can't send package to SmartBus network: "); 
                                    e.printStackTrace();
                        }
                    }
                }
                catch(InterruptedException e)
                {
                    System.out.println("[WARN] Queue lock error, may be too many requests were recieved!");     
                }
            }
        } 
        catch (UnknownHostException e) 
        {
            System.out.println("[ERROR] Wrong Broadcast IP " + this.ip + " defined, try another ip or contact with autors with log info appended below, please!");
            e.printStackTrace();            
        } 
        catch (SocketException e) 
        {
            System.out.println("[ERROR] Can't create Smartbus server for port " + this.port + ", try another port or contact with autors with log info appended below, please!");
            e.printStackTrace();
        }
      
    }

}
