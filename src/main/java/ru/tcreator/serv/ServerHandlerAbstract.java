package ru.tcreator.serv;

import ru.tcreator.clientmap.ClientList;
import ru.tcreator.entity.Message;

import java.io.*;
import java.net.Socket;
import java.util.Iterator;

/**
 * Абстрактый класс обработчика клиентского соединения
 */
abstract class ServerHandlerAbstract {
    protected final BufferedReader in;
    protected final Socket socket;
    protected final BufferedWriter out;
    protected boolean disconnected = Boolean.FALSE;

    /**
     * Конструктор. Инициализирует стримы in и out
     * @param clSocket сокет клиента {@link Socket}
     * @throws IOException
     */
    protected ServerHandlerAbstract(Socket clSocket) throws IOException {
        this.socket = clSocket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    /**
     * Закрывает соединения стримов и сокета
     * @throws IOException
     */
    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }

    /**
     * Отсылает сообщение для текущего соединения
     * @param msg объект сообщения {@link Message}
     * @throws IOException
     */
    public void writeOut(String msg) throws IOException {
        out.write(msg + "\n");
        out.flush();
    }

    /**
     * Считывает из входящего потока соединения
     * @return Message
     * @throws IOException
     */
    public String readIn() throws IOException {
        return in.readLine();
    }

    /**
     * Пишет в потоки всем пользователям в списке подключенных пользователей
     * игнорирует пользоваетелей на этапе ввода ника
     * @param msg
     * @throws IOException
     */
    public void sendMessageToAllUser(String msg) throws IOException {
        Iterator<ClientHandler> iterator = ClientList
                .getInstance()
                .getIterator();
        while (iterator.hasNext()) {
            ClientHandler clientHandler = iterator.next();
            if(!disconnected) {
                clientHandler.writeOut(msg);
            }
        }
    }

    /**
     * удаляет экземпляр из базы пользователей {@link ClientList}
     * @param clientHandler текущий обработчик поток с пользователем через сокет
     */
    public void removeMeInBase(ClientHandler clientHandler) {
        ClientList clientList = ClientList.getInstance();
        clientList.remove(clientHandler);
    }


    /**
     * Отсылает строку конкретному пользователю, дублирует себе в чат
     * @param user пользователь, кому доставить сообщение {@link String}
     * @param msg JSON сообщение {@link String}
     * @throws IOException
     */
    public void sendToUser(String user, String msg) throws IOException {
        Iterator<ClientHandler> iterator = ClientList
                .getInstance()
                .getIterator();
        while (iterator.hasNext()) {
            ClientHandler clientHandler = iterator.next();
            if (clientHandler.nickname.equals(user)) {
                clientHandler.writeOut(msg);
                break;
            }

        }
    }

    /**
     * поднимает флаг disconected в true. Метка прерывания потоков сокета клиента
     */
    public void setDisconnected() {
        this.disconnected = Boolean.TRUE;
    }



}
