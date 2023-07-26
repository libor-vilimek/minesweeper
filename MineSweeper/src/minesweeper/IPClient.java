/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author libik
 */
public class IPClient {

    int port;
    String IPAdress;

    public IPClient(int port, String IPAdress) {
        this.port = port;
        this.IPAdress = IPAdress;
    }

    public void read() {
        Socket s = null;
        BufferedReader b = null;

        try {
            s = new Socket(InetAddress.getLocalHost(), 5555);
            b = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String inp;
            while ((inp = b.readLine()) != null) {
                System.out.println(inp);
            }
            b.close();
            s.close();
        } catch (IOException ex) {
            Logger.getLogger(IPClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
