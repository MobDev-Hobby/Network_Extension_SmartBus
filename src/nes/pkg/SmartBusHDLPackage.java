/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nes.pkg;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author Admin
 */
public class SmartBusHDLPackage {

    public byte[] hdl_udp_ip = {(byte)192, (byte)168, (byte)1, (byte)255};
    public byte[] hdl_udp_header = {'H', 'D', 'L', 'M', 'I', 'R', 'A', 'C', 'L', 'E'};
    public byte[] hdl_header = {(byte)0xAA, (byte)0xAA};
    public byte[] hdl_length = new byte[1];
    public byte[] hdl_orig_subnet = {(byte)0x01};
    public byte[] hdl_orig_id = {(byte)0xFA};
    public byte[] hdl_orig_type = {(byte)0xFE, (byte)0xFF};
    public byte[] hdl_operation = new byte[2];
    public byte[] hdl_targ_subnet = new byte[1];
    public byte[] hdl_targ_id = new byte[1];
    public byte[] hdl_arg = new byte[0];
    
    public SmartBusHDLPackage()
    {
        
    }
          
    public boolean isInstance()
    {
        return true;       
    }
        
    public Integer getOperation()
    {
        return (this.hdl_operation[0]<<8)+this.hdl_operation[1];
    }
              
    public SmartBusHDLPackage setIp (String ip_str)
    {
        try {
            InetAddress ip = InetAddress.getByName(ip_str);
            this.hdl_udp_ip = ip.getAddress();
        } catch (UnknownHostException ex) {
            System.out.println("[WARN] Cant set local IP to "+ip_str+" default IP 192.168.1.255 stated active");
        }
        return this;
    }
    
    public SmartBusHDLPackage setOrigSubnet (byte[] value)
    {
        if(value == null)
        {
            System.out.println("[WARN] Wrong argument for Original Subnet (no date specified) , no changes saved");
            return this;
        }
        this.hdl_orig_subnet[0] = value[0];
        return this;
    }
    
    public SmartBusHDLPackage setTargSubnet (byte[] value)
    {
        if(value == null)
        {
            System.out.println("[WARN] Wrong argument for Target Subnet (no date specified) , no changes saved");
            return this;
        }
        this.hdl_targ_subnet[0] = value[0];
        return this;
    }
    
    public SmartBusHDLPackage setOrigType (byte[] value)
    {
        if(value == null)
        {
            System.out.println("[WARN] Wrong argument for Original Device Type (no data specified) , type 65534 stated");
            return this;
        }
        if(value.length != 2)
        {
            System.out.println("[WARN] Wrong argument for Original Device Type (2 bytes required) , type 65534 stated");
            return this;
        }
        this.hdl_orig_type[0] = value[0];
        this.hdl_orig_type[1] = value[1];
        return this;
    }
    
    public SmartBusHDLPackage setOrigId (byte value[])
    {
        if(value == null)
        {
            System.out.println("[WARN] Wrong argument for source id [no data specified], no value setted ");
            return this;
        }
        this.hdl_orig_id[0] = value[0];
        return this;
    }
    
    public SmartBusHDLPackage setTargId (byte value[])
    {
        if(value == null)
        {
            System.out.println("[WARN] Wrong argument for target id [no data specified], no value setted ");
            return this;
        }
        this.hdl_targ_id[0] = value[0];
        return this;
    }
    
    public SmartBusHDLPackage setOperation (byte[] value)
    {
        if(value == null)
        {
            System.out.println("[WARN] Wrong argument for operation [no data specified], no value setted ");
            return this;
        }
        if(value.length != 2)
        {
            System.out.println("[WARN] Wrong argument for operation [length of data must equal 2 bytes], no value setted ");
            return this;
        }
        this.hdl_operation[0] = value[0];
        this.hdl_operation[1] = value[1];
        return this;
    }
    
    public SmartBusHDLPackage setArg (byte[] value)
    {    
        if(value == null)
        {
            System.out.println("[WARN] Wrong argument for arg [no data specified], no value setted ");
            this.hdl_arg = new byte[0];
            return this;
        }
        this.hdl_arg = new byte[value.length];
        System.arraycopy(value, 0, this.hdl_arg, 0, this.hdl_arg.length);
        return this;
    }
    
    public byte[] Produce ()
    {
        int pkg_len = 2 + this.hdl_arg.length+this.hdl_header.length+this.hdl_length.length+this.hdl_operation.length+this.hdl_orig_id.length+this.hdl_orig_subnet.length+this.hdl_orig_type.length+this.hdl_targ_id.length+this.hdl_targ_subnet.length+this.hdl_udp_header.length+this.hdl_udp_ip.length;
        byte[] pkg = new byte[pkg_len];
        this.hdl_length[0] = (byte)(pkg_len - 16);
        int z = 0;
        for(int i = 0; i < this.hdl_udp_ip.length; i++)
            pkg[z++] = this.hdl_udp_ip[i];
        for(int i = 0; i < this.hdl_udp_header.length; i++)
            pkg[z++] = this.hdl_udp_header[i];   
        for(int i = 0; i < this.hdl_header.length; i++)
            pkg[z++] = this.hdl_header[i];   
        for(int i = 0; i < this.hdl_length.length; i++)
            pkg[z++] = this.hdl_length[i];  
        for(int i = 0; i < this.hdl_orig_subnet.length; i++)
            pkg[z++] = this.hdl_orig_subnet[i];  
        for(int i = 0; i < this.hdl_orig_id.length; i++)
            pkg[z++] = this.hdl_orig_id[i];
        for(int i = 0; i < this.hdl_orig_type.length; i++)
            pkg[z++] = this.hdl_orig_type[i]; 
        for(int i = 0; i < this.hdl_operation.length; i++)
            pkg[z++] = this.hdl_operation[i];
        for(int i = 0; i < this.hdl_targ_subnet.length; i++)
            pkg[z++] = this.hdl_targ_subnet[i];
        for(int i = 0; i < this.hdl_targ_id.length; i++)
            pkg[z++] = this.hdl_targ_id[i];    
        for(int i = 0; i < this.hdl_arg.length; i++)
            pkg[z++] = this.hdl_arg[i];
        pkg[z++] = 0;
        pkg[z++] = 0;
        this.putCRC(pkg);
        return pkg;
    }
    
    @Override
    public String toString()
    {
        StringBuilder pkg = new StringBuilder();
        byte[] pkg_bytes = this.Produce();
        for (int i = 0; i < pkg_bytes.length; i++) 
        {
            pkg.append(Integer.toHexString(pkg_bytes[i] & 0xFF));
            pkg.append(',');
        } 
        return pkg.toString();
    }
    
    public SmartBusHDLPackage Build (byte[] pkg, int length)
    {
        int z = 0;
        for(int i = 0; i < this.hdl_udp_ip.length; i++)
            this.hdl_udp_ip[i] = pkg[z++];
        for(int i = 0; i < this.hdl_udp_header.length; i++)
            this.hdl_udp_header[i] = pkg[z++];   
        for(int i = 0; i < this.hdl_header.length; i++)
            this.hdl_header[i] = pkg[z++];   
        for(int i = 0; i < this.hdl_length.length; i++)
            this.hdl_length[i] = pkg[z++];  
        for(int i = 0; i < this.hdl_orig_subnet.length; i++)
            this.hdl_orig_subnet[i] = pkg[z++];  
        for(int i = 0; i < this.hdl_orig_id.length; i++)
            this.hdl_orig_id[i] = pkg[z++];
        for(int i = 0; i < this.hdl_orig_type.length; i++)
            this.hdl_orig_type[i] = pkg[z++]; 
        for(int i = 0; i < this.hdl_operation.length; i++)
            this.hdl_operation[i] = pkg[z++];
        for(int i = 0; i < this.hdl_targ_subnet.length; i++)
            this.hdl_targ_subnet[i] = pkg[z++];
        for(int i = 0; i < this.hdl_targ_id.length; i++)
            this.hdl_targ_id[i] = pkg[z++];   
        this.hdl_arg = new byte[length-2-z];
        for(int i = 0; i < this.hdl_arg.length; i++)
            this.hdl_arg[i] = pkg[z++];
        return this;
    }
        
    static int crc_table[] =
    {
        0x0000, 0x1021, 0x2042, 0x3063, 0x4084, 0x50A5, 0x60C6, 0x70E7,
        0x8108, 0x9129, 0xA14A, 0xB16B, 0xC18C, 0xD1AD, 0xE1CE, 0xF1EF,
        0x1231, 0x0210, 0x3273, 0x2252, 0x52B5, 0x4294, 0x72F7, 0x62D6,
        0x9339, 0x8318, 0xB37B, 0xA35A, 0xD3BD, 0xC39C, 0xF3FF, 0xE3DE,
        0x2462, 0x3443, 0x0420, 0x1401, 0x64E6, 0x74C7, 0x44A4, 0x5485,
        0xA56A, 0xB54B, 0x8528, 0x9509, 0xE5EE, 0xF5CF, 0xC5AC, 0xD58D,
        0x3653, 0x2672, 0x1611, 0x0630, 0x76D7, 0x66F6, 0x5695, 0x46B4,
        0xB75B, 0xA77A, 0x9719, 0x8738, 0xF7DF, 0xE7FE, 0xD79D, 0xC7BC,
        0x48C4, 0x58E5, 0x6886, 0x78A7, 0x0840, 0x1861, 0x2802, 0x3823,
        0xC9CC, 0xD9ED, 0xE98E, 0xF9AF, 0x8948, 0x9969, 0xA90A, 0xB92B,
        0x5AF5, 0x4AD4, 0x7AB7, 0x6A96, 0x1A71, 0x0A50, 0x3A33, 0x2A12,
        0xDBFD, 0xCBDC, 0xFBBF, 0xEB9E, 0x9B79, 0x8B58, 0xBB3B, 0xAB1A,
        0x6CA6, 0x7C87, 0x4CE4, 0x5CC5, 0x2C22, 0x3C03, 0x0C60, 0x1C41,
        0xEDAE, 0xFD8F, 0xCDEC, 0xDDCD, 0xAD2A, 0xBD0B, 0x8D68, 0x9D49,
        0x7E97, 0x6EB6, 0x5ED5, 0x4EF4, 0x3E13, 0x2E32, 0x1E51, 0x0E70,
        0xFF9F, 0xEFBE, 0xDFDD, 0xCFFC, 0xBF1B, 0xAF3A, 0x9F59, 0x8F78,
        0x9188, 0x81A9, 0xB1CA, 0xA1EB, 0xD10C, 0xC12D, 0xF14E, 0xE16F,
        0x1080, 0x00A1, 0x30C2, 0x20E3, 0x5004, 0x4025, 0x7046, 0x6067,
        0x83B9, 0x9398, 0xA3FB, 0xB3DA, 0xC33D, 0xD31C, 0xE37F, 0xF35E,
        0x02B1, 0x1290, 0x22F3, 0x32D2, 0x4235, 0x5214, 0x6277, 0x7256,
        0xB5EA, 0xA5CB, 0x95A8, 0x8589, 0xF56E, 0xE54F, 0xD52C, 0xC50D,
        0x34E2, 0x24C3, 0x14A0, 0x0481, 0x7466, 0x6447, 0x5424, 0x4405,
        0xA7DB, 0xB7FA, 0x8799, 0x97B8, 0xE75F, 0xF77E, 0xC71D, 0xD73C,
        0x26D3, 0x36F2, 0x0691, 0x16B0, 0x6657, 0x7676, 0x4615, 0x5634,
        0xD94C, 0xC96D, 0xF90E, 0xE92F, 0x99C8, 0x89E9, 0xB98A, 0xA9AB,
        0x5844, 0x4865, 0x7806, 0x6827, 0x18C0, 0x08E1, 0x3882, 0x28A3,
        0xCB7D, 0xDB5C, 0xEB3F, 0xFB1E, 0x8BF9, 0x9BD8, 0xABBB, 0xBB9A,
        0x4A75, 0x5A54, 0x6A37, 0x7A16, 0x0AF1, 0x1AD0, 0x2AB3, 0x3A92,
        0xFD2E, 0xED0F, 0xDD6C, 0xCD4D, 0xBDAA, 0xAD8B, 0x9DE8, 0x8DC9,
        0x7C26, 0x6C07, 0x5C64, 0x4C45, 0x3CA2, 0x2C83, 0x1CE0, 0x0CC1,
        0xEF1F, 0xFF3E, 0xCF5D, 0xDF7C, 0xAF9B, 0xBFBA, 0x8FD9, 0x9FF8,
        0x6E17, 0x7E36, 0x4E55, 0x5E74, 0x2E93, 0x3EB2, 0x0ED1, 0x1EF0
    };


    public void putCRC(byte[] data) 
        {
            int crc = 0;
            int dat;
            
            for (int i = 16; i < data.length - 2; i++) 
            {
                dat = (int)(0xFF&(crc >> 8));
                crc = crc << 8;
                crc ^= crc_table[dat ^ data[i] & 0xFF];
            }
            data[data.length - 2] = (byte)((crc>>8)&0xFF);
            data[data.length - 1] = (byte)(crc&0xFF);
        }
    
}
