package com.bull.samples.loom.structured;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ScopedValueTest {

    public final static ScopedValue<Socket> SOCKETSV = ScopedValue.newInstance();

    void serve(ServerSocket serverSocket) throws IOException {
        while (true) {
            var socket = serverSocket.accept();
            ScopedValue.where(SOCKETSV, socket).run(this::handle);
        }
    }

    private void handle() {
        var socket = SOCKETSV.get();
        // handle incoming traffic
    }

    public static void main(String[] args) {

    }
}
