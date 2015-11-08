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
public class PackageLightStatus extends PackageWrapper{
            
    public PackageLightStatus(SmartBusHDLPackage pkg)
    {
        super(pkg);
    }
    
    @Override
    public boolean isInstance()
    {
        return this.pkg.getOperation() == 0x0033;        
    }
    
    public SmartBusHDLPackage build()
    {
        this.pkg.hdl_operation[0] = 0x00;
        this.pkg.hdl_operation[1] = 0x33;
        this.pkg.hdl_arg = new byte[0];
        
        return this.pkg;  
    }
            
}
