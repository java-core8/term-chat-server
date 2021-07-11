package ru.tcreator.serv;

import ru.tcreator.entity.Message;
import ru.tcreator.inerfaces.MessageEntityies;

import java.io.*;
import java.net.Socket;

abstract class ServerHandlerAbstract {
    protected final BufferedReader in;
    protected final Socket socket;
    protected final BufferedWriter out;

    protected ServerHandlerAbstract(Socket clSocket) throws IOException {
        this.socket = clSocket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    protected void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }

    protected void writeOut(MessageEntityies msg) throws IOException {
        out.write(msg + "\n");
        out.flush();
    }

    protected MessageEntityies readIn() throws IOException {
        String readLine = in.readLine();
        MessageEntityies readLineMsg = null;
        if (readLine != null ) {
            readLineMsg = Message.getMessage(
                    in.readLine()
            );
        }
        return readLineMsg;
    }
}
