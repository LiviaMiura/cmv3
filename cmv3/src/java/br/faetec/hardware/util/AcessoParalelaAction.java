package br.faetec.hardware.util;

/*
 * This is a further modified version of Jnpout32.DLL now works as a package, e.g.
package jnpout32;
The new DLL is named "Jnpout32pkg.DLL" to avoid confusion with the others.
This updated version was provided to me by Holger Hoffman from Germany.

jnpout32\ioPort.java - wrapper class for Jnpout32.DLL
jnpout32\pPort.java  - control functions which interface to the ioPort class

Only 1 Assumption:
You have Jnpout32pkg.DLL in your Windows\System32 folder.

13-APR-2005
Douglas Beattie Jr.
<beattidp@ieee.org>
http://www.hytherion.com/beattidp/
 *
 */
import jnpout32.*;

/**
 *Acesso a porta paralela em Java, utilizando dll de terceiros.
 *A dll foi construida a partir de informacoes JNI - Java Native Interface
@author Antonio Cassiano
 **/
public class AcessoParalelaAction {

    short datum;
    short Addr;
    pPort lpt = new pPort();

    /**
     *Define a direcao de leitura e acesso de leitura na porta x37A
    @author Antonio Cassiano
     **/
    void direcaoRead() {
        Addr = 0x378 + 2;
        datum = 0x20;
        System.out.println("Write to Port: " + Integer.toHexString(Addr)
                + " with data = " + Integer.toHexString(datum));

        lpt.output(Addr, datum);

    }

    /**
     *Converte um byte em inteiro
    @author Antonio Cassiano
    @param  b
    @return int  valor convertido.
     **/
    public int unsignedByteToInt(byte b) {
        return (int) b & 0xFF;
    }

    /**
     *Leitura da porta paralela
    @author Antonio Cassiano
    @return short  valor lido.
     **/
    public short readData() {
        // Read from the port  D7, D6, D5,D4 e D3
        datum = (short) lpt.input(Addr);
        datum = (short) ((short) datum & 0xF8);  // pega 5 bits MSB
        return datum;
    }

    /**
     *Define a direcao de escrita e acesso de leitura na porta 0x37A
    @author Antonio Cassiano
     **/
    public void direcaoWrite() {
        Addr = 0x378 + 2;
        datum = 0x0;
        System.out.println("Write to Port: " + Integer.toHexString(Addr)
                + " with data = " + Integer.toHexString(datum));

        lpt.output(Addr, datum);
    }

    /**
     *Escrita na porta paralela
    @author Antonio Cassiano  .
     **/
   public void writeData() {
        // Notify the console
        // System.out.println("Write to Port: " + Integer.toHexString(Addr)
        //       + " with data = " + Integer.toHexString(datum));
        //Write to the port
        lpt.output(Addr, datum);


    }

    /**
     *Escrita na porta paralela com parametro
    @author Antonio Cassiano
    @param  valor
     **/
   public void writeData2(short valor) {
        datum = valor;
        lpt.output(Addr, datum);
    }

    /**
     *Leitura na porta paralela na faixa entre 0x378 e 0x37F
    @author Antonio Cassiano
   
     **/
   public void readRange() {
        // Try to read 0x378..0x37F, LPT1:
        for (Addr = 0x378; (Addr < 0x380); Addr++) {
            //Read from the port
            datum = (short) lpt.input(Addr);
            // Notify the console
            System.out.println("Port: " + Integer.toHexString(Addr)
                    + " = " + Integer.toHexString(datum));
        }
    }
}
