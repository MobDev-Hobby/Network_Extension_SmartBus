/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nes;

import nes.pkg.SmartBusHDLPackage;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.util.HashMap;

/**
 *
 * @author Admin
 */
public class HttpServerHandler implements HttpHandler{

    SmartBusController sb;
    volatile Object mutex;
    
    public HttpServerHandler(SmartBusController sb, Object mutex)
    {
        this.sb = sb;
        this.mutex = mutex;
    }

    @Override
    public void handle(HttpExchange request) throws IOException {
        System.out.println("[INFO] Page "+request.getRequestURI().toString() + " was sent to user with IP "+request.getRemoteAddress().toString());
        String requestMethod = request.getRequestMethod();
        DataOutputStream dos = new DataOutputStream(request.getResponseBody());
        switch(requestMethod)
        {
            default:
            {
                this.answeringMachine(request, dos);
            }
        }
        dos.flush();
        dos.close();
    }
    
    public void answeringMachine(HttpExchange request, DataOutputStream dos) throws IOException
    {
        String URI = request.getRequestURI().toString();
        if(URI.contains(".."))
        {
            request.sendResponseHeaders(500, 0);
            return;
        }
        String scriptName;
        if(URI.contains("?"))
        {
            scriptName = URI.substring(0, URI.indexOf('?'));
        }
        else
        {
            scriptName = URI;
        }
        String[] params = new String[0];
        if(URI.contains("?"))
            if(URI.substring(scriptName.length() + 1).contains("&"))
                params = URI.substring(scriptName.length() + 1).split("&");
            else
            {
                params = new String[1];
                params[0] = URI.substring(scriptName.length() + 1);
            }
        if(!scriptName.endsWith(".nes"))
        {
            File requestFile = new File("nes/"+scriptName);
            if(!requestFile.exists() || !requestFile.isFile())
            {
                request.sendResponseHeaders(404, 0);
                requestFile = new File("sys/404.html");
            }
            else
            {
                request.sendResponseHeaders(200, 0);
            }
            if(!requestFile.exists() || !requestFile.isFile())
            {
                dos.writeBytes("<h1>Error 404 - File Not Found</h1><br/><i>NES [Network Extension for Smartbus] by AK - Colaero, 2012 (c) All rights reserved!");    
            }
            else
            {    
                byte[] buffer = new byte[1024];
                int readedBytes;
                DataInputStream dis = new DataInputStream(new FileInputStream(requestFile));
                while((readedBytes = dis.read(buffer)) != -1)
                    dos.write(buffer, 0, readedBytes); 
                dis.close();
                
            }
        }
        else
        {
            // Parse agrs
            HashMap<String,String> args = new HashMap();
            HashMap<String,byte[]> byte_args = new HashMap();
            for (String param : params) {
                if (param.contains("=")) {
                    if (!param.contains("=")) {
                        continue;
                    }
                    String arg = param.split("=")[0];
                    String value = param.split("=")[1];
                    if(value.startsWith("0x"))
                    {    
                        try
                        {
                            byte[] g = new byte[value.length()/2-1];
                            for(int z = 2; z < value.length(); z+=2)
                            {
                                g[z/2-1] = (byte)Integer.parseInt(value.substring(z,z+2),16);
                            }   
                            byte_args.put(arg, g);
                        }
                        catch(Exception e)
                        {
                            System.out.println("[WARN] Wrong Symbol identified in contents of HEX argument "+ arg+" = "+value+" no transformation to HEX will be performed");
                        }
                    }
                    args.put(arg, value);
                }
            }
            // Send SB requests

            if(scriptName.equals("/do.nes"))
            {
                request.sendResponseHeaders(200, 0);
                try
                {
                    synchronized(this.mutex)
                    {
                        sb.packagesToSend
                            .put(
                                new SmartBusHDLPackage()
                                    .setOperation(byte_args.get("operation"))
                                    .setTargId(byte_args.get("target_id"))
                                    .setTargSubnet(byte_args.get("target_subnet"))
                                    .setArg(byte_args.get("arguments"))
                                    .setOperation(byte_args.get("operation"))
                                );
                        this.mutex.notify();
                    }
                }
                catch(Exception e)
                {
                    System.out.println("[WARN] Package can't be created, beacuse of wrong arguments. No package will be sent to the SmartBus network");
                    e.printStackTrace();
                }
                this.sendFileToClientStream(dos, "sys/"+scriptName+".html");
                System.out.println("[STAT] Package  added to SMartBus Controller's queue");
            } 
            else             
            if(scriptName.equals("/lightstatus.nes"))
            {
                request.sendResponseHeaders(200, 0);
                Byte status;
                try
                {
                    status = this.sb.listener.lightStatus.get(byte_args.get("target_subnet")[0]).get(byte_args.get("target_id")[0]).get(byte_args.get("channel")[0]);
                }catch(NullPointerException e)
                {
                    status = -1;
                }
                dos.write(("{\"subnet\":"+byte_args.get("target_subnet")[0]+", \"id\":"+byte_args.get("target_id")[0]+", \"channel\":"+byte_args.get("channel")[0]+", \"intensity\":"+status+"}").getBytes());                
                System.out.println("[STAT] Status request [SUBNET: "+byte_args.get("target_subnet")[0]+", ID: "+byte_args.get("target_id")[0]+", SUBNET: "+byte_args.get("channel")[0]+"], RESULT: "+status);
            }
            else
                request.sendResponseHeaders(404, 0);
                        
        }
    }
    public void sendFileToClientStream(DataOutputStream dos, String fileName) throws IOException
    {
        File requestFile = new File(fileName);
        if(!requestFile.exists())
        {
            dos.writeBytes(" ");
        }
        else
        {
            byte[] buffer = new byte[1024];
            int readedBytes;
            DataInputStream dis;
            
            dis = new DataInputStream(new FileInputStream(requestFile));
            while((readedBytes = dis.read(buffer)) != -1)
                dos.write(buffer, 0, readedBytes);                
            dis.close();
        }    
     }
        
}

