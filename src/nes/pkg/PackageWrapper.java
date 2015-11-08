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
public class PackageWrapper {

    public SmartBusHDLPackage pkg;
        
    public PackageWrapper(SmartBusHDLPackage pkg)
    {
        this.pkg = pkg;
    }
    
    public boolean isInstance()
    {
        return true;       
    }
    
    @Override
    public String toString()
    {
        return this.pkg.toString();
    }
    
}
