package kpfu.itis.group11_801.kilin.chess.controllers;

import javafx.application.Platform;
import kpfu.itis.group11_801.kilin.chess.models.Game;
import kpfu.itis.group11_801.kilin.chess.models.Team;
import sun.nio.ch.Net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.net.Socket;

public class NetWorkThread extends Thread {
    private OutputStream writer;
    private InputStream reader;

    public NetWorkThread(Socket socket) throws IOException {
        writer = socket.getOutputStream();
        reader = socket.getInputStream();
    }

    @Override
    public void run() {
        try {
            Game game = Game.getCurrentGame();
            NetWorkClient netWorkClient = NetWorkClient.getCurrentNetwork();
            int code;
            while (netWorkClient.isHasRoom()) {
                code = reader.read();
                System.out.println(code);
                switch (code) {
                    case 2:
                        game.getItem(reader.read(), reader.read())
                                .move(reader.read(), reader.read());
                        NetWorkClient.setYourMove(true);
                        if (game.isShah(game.getCurrentTeam())) {
                            Platform.runLater(() -> game.setMessage("Your move: Shah"));
                        } else {
                            Platform.runLater(() -> game.setMessage("Your move"));
                        }
                        break;
                    case 6:
                        NetWorkClient.setYourMove(true);
                        Platform.runLater(() -> game.setMessage("Your move"));
                        break;
                    case 4:
                        netWorkClient.quit();
                        Platform.runLater(() -> game.setMessage("You win: enemy gived up"));
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}