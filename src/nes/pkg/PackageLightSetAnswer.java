/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nes.pkg;

/**
 *
 * @author Admin
 */
public class PackageLightSetAnswer extends SmartBusHDLPackage{

    
    @Override
    public boolean isInstance()
    {
        return this.getOperation() == 0x0032;        
    }
    
    public void setChannel(byte channel)
    {
        this.hdl_arg[0] = channel;
    }
    
    public void setSuccessStatus(boolean success)
    {
        this.hdl_arg[1] = (byte)(success?0xF8:0xF5);
    }
        
    public void setLightIntensity(byte intensity)
    {
        this.hdl_arg[2] = intensity;
    }
          
    public void setLightDelay(int delay)
    {
        this.hdl_arg[3] = (byte)((delay&0xFF00)>>8);
        this.hdl_arg[4] = (byte)(delay&0xFF);
    }
    
    public byte getChannel()
    {
        return (byte)(this.hdl_arg[0]&0xFF);
    }
    
    public boolean getSuccessStatus()
    {
        return (this.hdl_arg[1]&0xFF) == 0xF8;
    }
    public byte getLightIntensity()
    {
        return (byte)(0xFF&this.hdl_arg[2]);
    }
        
    public Integer getLightDelay()
    {
        return ((this.hdl_arg[3]&0xFF)<<8) + (0xFF&this.hdl_arg[4]);
    }
            
}
