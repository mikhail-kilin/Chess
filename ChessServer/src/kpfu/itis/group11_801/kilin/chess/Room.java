package kpfu.itis.group11_801.kilin.chess;

import java.io.IOException;

public class Room {
    private UserThread user1;
    private UserThread user2;

    public Room(UserThread user) {
        user1 = user;
    }

    public void addUser(UserThread userThread) {
        user2 = userThread;
    }

    public void move(UserThread sender, int x1, int y1, int x2, int y2) throws IOException {
        UserThread receiver = sender.equals(user1) ? user2 : user1;
        System.out.println(x1 + " " + y1 + " " + x2 + " " + y2);
        receiver.sendMessage(2);
        receiver.sendMessage(x1);
        receiver.sendMessage(y1);
        receiver.sendMessage(x2);
        receiver.sendMessage(y2);
    }

    public void specialMove(UserThread sender, int x1, int y1, int x2, int y2, int figure) throws Exception {
        UserThread receiver = sender.equals(user1) ? user2 : user1;
        receiver.sendMessage(3);
        receiver.sendMessage(x1);
        receiver.sendMessage(y1);
        receiver.sendMessage(x2);
        receiver.sendMessage(y2);
        receiver.sendMessage(figure);
    }
}