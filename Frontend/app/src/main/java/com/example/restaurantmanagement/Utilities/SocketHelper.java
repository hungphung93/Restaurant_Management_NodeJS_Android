package com.example.restaurantmanagement.Utilities;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class SocketHelper {

    static private String SERVER_URL = "https://res-man.herokuapp.com/";
    static  private Socket mSocket;

    private SocketHelper(){

    }

    public static Socket getSocket() {
        if(mSocket != null) {
            return mSocket;
        }else{
            try {
                mSocket = IO.socket(SERVER_URL);
                mSocket.connect();
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            return  mSocket;
        }
    }
}
