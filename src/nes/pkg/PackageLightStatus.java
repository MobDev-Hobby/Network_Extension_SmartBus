/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nes.pkg;

/**
 *
 * @author Admin
 */
public class PackageLightStatus extends SmartBusHDLPackage{
            
    @Override
    public boolean isInstance()
    {
        return this.getOperation() == 0x0033;        
    }
    
    public SmartBusHDLPackage build()
    {
        this.hdl_operation[0] = 0x00;
        this.hdl_operation[1] = 0x33;
        this.hdl_arg = new byte[0];
        
        return this;  
    }
            
}
