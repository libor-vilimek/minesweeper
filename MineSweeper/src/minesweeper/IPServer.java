/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author libik
 */
public class IPServer {

    ServerSocket n1;

    public IPServer(int port) {
        try {
            n1 = new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(IPServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void write(String text) {
        PrintWriter out = null;
        Socket c = null;
        try {
            c = n1.accept();
            System.out.println("Connection from " + c);
            out = new PrintWriter(c.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
            String n;
            BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Ready to type now");
            out.println(text);
        } catch (IOException e) {
            System.out.println("Accept failed");
        }
    }
}
