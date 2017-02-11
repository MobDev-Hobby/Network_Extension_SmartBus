/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nes;

import nes.pkg.SmartBusHDLPackage;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import nes.pkg.PackageLightSetAnswer;
import nes.pkg.PackageLightStatusAnswer;

/**
 *
 * @author Admin
 */
public class SmartBusListener extends Thread{
    
    DatagramSocket clientSocket;
    HashMap<Byte, HashMap<Byte, HashMap<Byte, Byte>>> lightStatus = new HashMap<>();
    
    SmartBusListener(DatagramSocket clientSocket)
    {
        this.clientSocket = clientSocket;
    }
    
    @Override
    public void run()
    { 
        byte[] receiveData = new byte[2048];
        while(true)
        {
            try 
            {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                this.clientSocket.receive(receivePacket);
                SmartBusHDLPackage pkg = new SmartBusHDLPackage().Build(receiveData, receivePacket.getLength());
                
                /*
                System.out.println("[INFO] Package recieved from SmartBus network: ");
                System.out.print("Operation:");
                for (int i = 0; i < pkg.hdl_operation.length; i++) 
                {
                    System.out.print(Integer.toHexString(pkg.hdl_operation[i]& 0xFF));
                    System.out.print(',');
                }
                System.out.print(" Arguments:");
                for (int i = 0; i < pkg.hdl_arg.length; i++) 
                {
                    System.out.print(Integer.toHexString(pkg.hdl_arg[i]& 0xFF));
                    System.out.print(',');
                }
                System.out.println();
                /* **/

                if(((PackageLightSetAnswer)pkg).isInstance())
                {
                    System.out.println(
                            "[INFO] Light Status Recieved From SmartBus: [S:"
                                    + pkg.hdl_orig_subnet[0]
                                    +"ID: " + pkg.hdl_orig_id[0]
                                    + ", CH: " + ((PackageLightSetAnswer)pkg).getChannel() 
                                    + ", INT: " + ((PackageLightSetAnswer)pkg).getLightIntensity() 
                                    + ", STAT: " + (((PackageLightSetAnswer)pkg).getSuccessStatus()?"success":"error"));
                    
                    if(lightStatus.get(pkg.hdl_orig_subnet[0]) == null){
                        lightStatus.put(pkg.hdl_orig_subnet[0], new HashMap());
                    }
                    if(lightStatus.get(pkg.hdl_orig_subnet[0]).get(pkg.hdl_orig_id[0]) == null){
                        lightStatus.get(pkg.hdl_orig_subnet[0]).put(pkg.hdl_orig_id[0], new HashMap());
                    }
                    
                    lightStatus.get(pkg.hdl_orig_subnet[0]).get(pkg.hdl_orig_id[0]).put(
                            ((PackageLightSetAnswer)pkg).getChannel(),
                            ((PackageLightSetAnswer)pkg).getLightIntensity());
                }

                if(((PackageLightStatusAnswer)pkg).isInstance())
                {
                   System.out.println("[INFO] Batch Light Status Recieved From SmartBus");
                   
                   if(lightStatus.get(pkg.hdl_orig_subnet[0]) == null){
                        lightStatus.put(pkg.hdl_orig_subnet[0], new HashMap());
                   }
                   if(lightStatus.get(pkg.hdl_orig_subnet[0]).get(pkg.hdl_orig_id[0]) == null){
                        lightStatus.get(pkg.hdl_orig_subnet[0]).put(pkg.hdl_orig_id[0], new HashMap());
                   }

                    for(byte i = 1; i <= ((PackageLightStatusAnswer)pkg).getChannelsQuantity(); i++)
                    {
                        System.out.println(
                                "[INFO] Light Status Recieved From SmartBus: [S:"
                                        + pkg.hdl_orig_subnet[0]
                                        +"ID: "+ pkg.hdl_orig_id[0]
                                        + ", CH: " + i 
                                        + ", INT: " + ((PackageLightStatusAnswer)pkg).getChannelIntensity(i) 
                                        + ", STAT: info");
                        
                        lightStatus.get(pkg.hdl_orig_subnet[0]).get(pkg.hdl_orig_id[0]).put(
                                i,
                                ((PackageLightStatusAnswer)pkg).getChannelIntensity(i));
                    }
                 }
            } 
            catch (IOException ex) 
            {
                System.out.println("[WARN] Cant't obtain package from SmartNus network");
            }
        }
    }     
}
