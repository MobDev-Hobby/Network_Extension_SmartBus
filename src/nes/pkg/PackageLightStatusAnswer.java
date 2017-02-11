/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nes.pkg;

/**
 *
 * @author Admin
 */
public class PackageLightStatusAnswer extends SmartBusHDLPackage{
                
    @Override
    public boolean isInstance()
    {
        return this.getOperation() == 0x0034;        
    }
    
    public void setChannelsQuantity(byte quantity)
    {
        this.hdl_arg[0] = quantity;
    }
    
    public void setChannelIntensity(byte channel, byte intensity)
    {
        this.hdl_arg[channel] = intensity;
    }
           
    public byte getChannelsQuantity()
    {
        return (byte)(this.hdl_arg[0]&0xFF);
    }
    
    public byte getChannelIntensity(byte channel)
    {
        return (byte)(this.hdl_arg[channel]&0xFF);
    }
    
            
}
