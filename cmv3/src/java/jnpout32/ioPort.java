/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author cassiano
 */
package jnpout32;

/**
 *Load a Native Library jnpout32.dll for package.
@author Dr. Kenneth G. Schweller
 **/
//Definitions in the build of jnpout32.dll are:
//short _stdcall Inp32(short PortAddress);
//void _stdcall Out32(short PortAddress, short data);
public class ioPort {

    /**
     *declare native methods of 'jnpout32.dll' and output a value to a specified port address.
    @author Dr. Kenneth G. Schweller.
    @param  PortAddress
    @param  data
     **/
    public native void Out32(short PortAddress, short data);

    /**
     * input a value from a specified port address.
    @author Dr. Kenneth G. Schweller.
    @param  PortAddress
     **/
    public native short Inp32(short PortAddress);

    /**
     *load 'jnpout32.dll' for package.
    @author Dr. Kenneth G. Schweller.
     **/
    static {
        System.loadLibrary("jnpout32pkg");
    }
}
