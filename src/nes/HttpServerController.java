/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nes;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import nes.pkg.PackageLightStatus;

/**
 *
 * @author Admin
 */
public class HttpServerController extends Thread
{
    
    private int port;
    volatile Object mutex = new Object();
               
    public HttpServerController(int port)
    {
        this.port = port;
    }
    
    @Override
    public void run() 
    {
        try
        {
            SmartBusController sb = new SmartBusController(this.mutex);
            sb.start();
            InetSocketAddress address = new InetSocketAddress(this.port);
            HttpServer server = HttpServer.create(address, 0);
            server.createContext("/", new HttpServerHandler(sb, this.mutex));
            server.setExecutor(Executors.newCachedThreadPool());
            server.start();
            System.out.println("[INFO] HTTP server is running at port "+this.port+". Now you would open in your browser page http://<yourip>:"+port +" for control your SmartBus via WEB.");
       
        }
        catch(IOException e)
        {
            System.out.println("[ERROR] Can't create HTTP server for port " + this.port + ", try another port or contact with autors with log info appended below, please!");
            e.printStackTrace();
        }  
    }
}
