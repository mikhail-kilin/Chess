package kpfu.itis.group11_801.kilin.chess.controllers;

import javafx.application.Platform;
import kpfu.itis.group11_801.kilin.chess.models.Figure;
import kpfu.itis.group11_801.kilin.chess.models.Game;
import kpfu.itis.group11_801.kilin.chess.models.Pawn;
import kpfu.itis.group11_801.kilin.chess.models.Team;
import sun.nio.ch.Net;

import java.io.*;
import java.net.Socket;

public class NetWorkThread extends Thread {
    /**
     * -1   =>  disconnect
     * 0    =>  create room
     * 1    =>  connect to room
     * 2    =>  move
     * 3    =>  special move 1 - horse, 2 - elephant, 3 - queen, 4 - castle
     * 4    =>  give up
     * 5    =>  random game
     * 6    =>  game started if you are white
     * 100  => stop word
     */
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
                        break;
                    case 3:
                        int x = reader.read();
                        int y = reader.read();
                        int figureCode = reader.read();
                        Pawn pawn = (Pawn)game.getItem(x, y);
                        if (game.getCurrentTeam() == Team.WHITE) {
                            switch (figureCode) {
                                case 1:
                                    Platform.runLater(() -> pawn.changeType(Figure.BLACK_QUEEN));
                                    break;
                                case 2:
                                    Platform.runLater(() -> pawn.changeType(Figure.BLACK_ELEPHANT));
                                    break;
                                case 3:
                                    Platform.runLater(() -> pawn.changeType(Figure.BLACK_HORSE));
                                    break;
                                case 4:
                                    Platform.runLater(() -> pawn.changeType(Figure.BLACK_CASTLE));
                                    break;
                            }
                        } else {
                            switch (figureCode) {
                                case 1:
                                    Platform.runLater(() -> pawn.changeType(Figure.WHITE_QUEEN));
                                    break;
                                case 2:
                                    Platform.runLater(() -> pawn.changeType(Figure.WHITE_ELEPHANT));
                                    break;
                                case 3:
                                    Platform.runLater(() -> pawn.changeType(Figure.WHITE_HORSE));
                                    break;
                                case 4:
                                    Platform.runLater(() -> pawn.changeType(Figure.WHITE_CASTLE));
                                    break;
                            }
                        }
                        break;
                }
            }
            System.out.println("Closed");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
