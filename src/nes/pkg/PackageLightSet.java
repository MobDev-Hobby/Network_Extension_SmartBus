/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nes.pkg;


/**
 *
 * @author Admin
 */
public class PackageLightSet extends SmartBusHDLPackage{
            
    @Override
    public boolean isInstance()
    {
        return this.getOperation() == 0x0031;        
    }
    
    public void setChannel(byte channel)
    {
        this.hdl_arg[0] = channel;
    }
       
    public void setLightIntensity(byte intensity)
    {
        this.hdl_arg[1] = intensity;
    }
          
    public void setLightDelay(int delay)
    {
        this.hdl_arg[2] = (byte)((delay&0xFF00)>>8);
        this.hdl_arg[3] = (byte)(delay&0xFF);
    }
    
    public byte getChannel()
    {
        return (byte)(0xFF&this.hdl_arg[0]);
    }
    
    public byte getLightIntensity()
    {
        return (byte)(0xFF&this.hdl_arg[1]);
    }
        
    public Integer getLightDelay()
    {
        return ((0xFF&this.hdl_arg[2])<<8) + (0xFF&this.hdl_arg[3]);
    }
            
}
