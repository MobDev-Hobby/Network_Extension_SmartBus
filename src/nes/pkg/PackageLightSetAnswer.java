/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nes.pkg;

import nes.SmartBusHDLPackage;

/**
 *
 * @author Admin
 */
public class PackageLightSetAnswer extends PackageWrapper{
            
    public PackageLightSetAnswer(SmartBusHDLPackage pkg)
    {
        super(pkg);
    }
    
    @Override
    public boolean isInstance()
    {
        return this.pkg.getOperation() == 0x0032;        
    }
    
    public void setChannel(byte channel)
    {
        this.pkg.hdl_arg[0] = channel;
    }
    
    public void setSuccessStatus(boolean success)
    {
        this.pkg.hdl_arg[1] = (byte)(success?0xF8:0xF5);
    }
        
    public void setLightIntensity(byte intensity)
    {
        this.pkg.hdl_arg[2] = intensity;
    }
          
    public void setLightDelay(int delay)
    {
        this.pkg.hdl_arg[3] = (byte)((delay&0xFF00)>>8);
        this.pkg.hdl_arg[4] = (byte)(delay&0xFF);
    }
    
    public byte getChannel()
    {
        return (byte)(this.pkg.hdl_arg[0]&0xFF);
    }
    
    public boolean getSuccessStatus()
    {
        return (this.pkg.hdl_arg[1]&0xFF) == 0xF8;
    }
    public byte getLightIntensity()
    {
        return (byte)(0xFF&this.pkg.hdl_arg[2]);
    }
        
    public Integer getLightDelay()
    {
        return ((this.pkg.hdl_arg[3]&0xFF)<<8) + (0xFF&this.pkg.hdl_arg[4]);
    }
            
}
