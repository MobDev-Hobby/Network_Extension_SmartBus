package nes;

/**
 *
 * @author Andrew [Colaero] Kazeko
 */

public class NES {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("NES [Network Extension for Smartbus] by AK - Colaero, 2012 (c) All rights reserved!");
        new HttpServerController(6001).start();
    }
}
