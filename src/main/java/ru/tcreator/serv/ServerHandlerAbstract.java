package ru.tcreator.serv;

import ru.tcreator.clientmap.ClientMap;
import ru.tcreator.entity.Message;
import ru.tcreator.inerfaces.MessageEntityies;

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
     * Флаг, если пользователь не ввёл никнейм. Запрещает рассылку сообщений пользователю.
     */
    protected boolean isNotStarted = Boolean.TRUE;

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
    protected void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }


    /**
     * Отсылает сообщение для текущего соединения
     * @param msg объект сообщения {@link MessageEntityies}
     * @throws IOException
     */
    protected void writeOut(MessageEntityies msg) throws IOException {
        out.write(msg + "\n");
        out.flush();
    }

    /**
     * Считывает из входящего потока соединения
     * @return Message
     * @throws IOException
     */
    protected MessageEntityies readIn() throws IOException {
        return Message.getMessage(
                in.readLine()
        );
    }

    /**
     * Пишет в потоки всем пользователям в списке подключенных пользователей
     * @param msg
     * @throws IOException
     */
    protected void sendMessageToAllUser(MessageEntityies msg) throws IOException {
        Iterator<ClientHandler> iterator = ClientMap
                .getInstance()
                .getIterator();
        while (iterator.hasNext()) {
            ClientHandler clientHandler = iterator.next();
            if (!clientHandler.isNotStarted()) {
                clientHandler.writeOut(msg);
            }

        }
    }

    /**
     * Флаг если пользователь не ввёл никнейм. Запрещает рассылку сообщений пользователю.
     * @return
     */
    public boolean isNotStarted() {
        return isNotStarted;
    }

    /**
     * Устанавливает флаг в положение false. Разрешает рассылку пользователю.
     */
    protected void setStartFlag() {
        isNotStarted = Boolean.FALSE;
    }

    protected void removeMeInBase(ClientHandler clientHandler) {
        ClientMap clientMap = ClientMap.getInstance();
        clientMap.remove(clientHandler);

    }

    protected void setDisconnected() {
        this.disconnected = Boolean.TRUE;
    }
}
