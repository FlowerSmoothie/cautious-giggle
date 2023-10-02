package Server;

import java.io.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import java.net.*;
import java.math.*;

public class ServerPartTwo {

    private static final InetAddress HOST;
    private static final int PORT = 8001;

    static {
        try {
            HOST = InetAddress.getByName("127.0.0.1");
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) throws SocketException {
        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            System.out.println("Server is up!");
            while (!socket.isClosed()){
                byte[] buf = new byte[256];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String[] parsed = new String(packet.getData()).trim().split(" ");
                double X = Double.parseDouble(parsed[0]);
                double Y = Double.parseDouble(parsed[1]);
                double Z = Double.parseDouble(parsed[2]);
                System.out.println("Sever got: X = " + X + "; Y = " + Y + "; Z = " + Z);
                double answer = Math.abs(Math.pow(X, (Y/X)) - Math.sqrt(Y/X)) + (Y - X)*((Math.cos(Y) - Math.exp(Z/(Y - X)))/(1 + (Y - X)*(Y - X)));
                System.out.println("Answer: " + answer + "\n-----------------------------------");
                buf = ("" + answer).getBytes();
                packet = new DatagramPacket(buf, buf.length, packet.getAddress(), packet.getPort());
                socket.send(packet);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
