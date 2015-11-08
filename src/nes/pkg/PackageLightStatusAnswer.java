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
public class PackageLightStatusAnswer extends PackageWrapper{
            
    public PackageLightStatusAnswer(SmartBusHDLPackage pkg)
    {
        super(pkg);
    }
    
    @Override
    public boolean isInstance()
    {
        return this.pkg.getOperation() == 0x0034;        
    }
    
    public void setChannelsQuantity(byte quantity)
    {
        this.pkg.hdl_arg[0] = quantity;
    }
    
    public void setChannelIntensity(byte channel, byte intensity)
    {
        this.pkg.hdl_arg[channel] = intensity;
    }
           
    public byte getChannelsQuantity()
    {
        return (byte)(this.pkg.hdl_arg[0]&0xFF);
    }
    
    public byte getChannelIntensity(byte channel)
    {
        return (byte)(this.pkg.hdl_arg[channel]&0xFF);
    }
    
            
}
