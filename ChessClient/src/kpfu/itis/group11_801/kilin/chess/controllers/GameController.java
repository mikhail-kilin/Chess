package kpfu.itis.group11_801.kilin.chess.controllers;


import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import kpfu.itis.group11_801.kilin.chess.Main;
import kpfu.itis.group11_801.kilin.chess.models.Game;
import kpfu.itis.group11_801.kilin.chess.models.Team;
import sun.net.NetworkClient;
import sun.nio.ch.Net;

import java.lang.reflect.GenericArrayType;
import java.util.Map;
import java.util.TreeMap;


public class GameController {
    private static Map<String, ImageView> images;
    private static Label messageLabel;

    @FXML private Button giveUpBtn;
    @FXML private Button offerDrawBtn;

    @FXML private Pane table;
    @FXML private Label message;

    @FXML private ImageView w_pawn1;
    @FXML private ImageView w_pawn2;
    @FXML private ImageView w_pawn3;
    @FXML private ImageView w_pawn4;
    @FXML private ImageView w_pawn5;
    @FXML private ImageView w_pawn6;
    @FXML private ImageView w_pawn7;
    @FXML private ImageView w_pawn8;
    @FXML private ImageView w_castle1;
    @FXML private ImageView w_castle2;
    @FXML private ImageView w_elephant1;
    @FXML private ImageView w_elephant2;
    @FXML private ImageView w_queen;
    @FXML private ImageView w_horse1;
    @FXML private ImageView w_horse2;
    @FXML private ImageView w_king;

    @FXML private ImageView b_pawn1;
    @FXML private ImageView b_pawn2;
    @FXML private ImageView b_pawn3;
    @FXML private ImageView b_pawn4;
    @FXML private ImageView b_pawn5;
    @FXML private ImageView b_pawn6;
    @FXML private ImageView b_pawn7;
    @FXML private ImageView b_pawn8;
    @FXML private ImageView b_castle1;
    @FXML private ImageView b_castle2;
    @FXML private ImageView b_elephant1;
    @FXML private ImageView b_elephant2;
    @FXML private ImageView b_queen;
    @FXML private ImageView b_horse1;
    @FXML private ImageView b_horse2;
    @FXML private ImageView b_king;





    public void toMenu() throws Exception {
        try {
            NetWorkClient.getCurrentNetwork().giveUp();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        Stage stage = Main.getPrimaryStage();
        stage.setScene(new Scene(FXMLLoader.load(Main.class.getResource("views/Menu.fxml")), 880, 880));
    }

    public void giveUp() {
        NetWorkClient.getCurrentNetwork().giveUp();
    }

    public void draw() {
        NetWorkClient.getCurrentNetwork().drawOffer();
    }

    @FXML
    public void initialize() {
        System.out.println("init");
        images = new TreeMap<>();
        images.put("w_pawn1", w_pawn1);
        images.put("w_pawn2", w_pawn2);
        images.put("w_pawn3", w_pawn3);
        images.put("w_pawn4", w_pawn4);
        images.put("w_pawn5", w_pawn5);
        images.put("w_pawn6", w_pawn6);
        images.put("w_pawn7", w_pawn7);
        images.put("w_pawn8", w_pawn8);
        images.put("w_castle1", w_castle1);
        images.put("w_castle2", w_castle2);
        images.put("w_elephant1", w_elephant1);
        images.put("w_elephant2", w_elephant2);
        images.put("w_horse1", w_horse1);
        images.put("w_horse2", w_horse2);
        images.put("w_queen", w_queen);
        images.put("w_king", w_king);

        images.put("b_pawn1", b_pawn1);
        images.put("b_pawn2", b_pawn2);
        images.put("b_pawn3", b_pawn3);
        images.put("b_pawn4", b_pawn4);
        images.put("b_pawn5", b_pawn5);
        images.put("b_pawn6", b_pawn6);
        images.put("b_pawn7", b_pawn7);
        images.put("b_pawn8", b_pawn8);
        images.put("b_castle1", b_castle1);
        images.put("b_castle2", b_castle2);
        images.put("b_elephant1", b_elephant1);
        images.put("b_elephant2", b_elephant2);
        images.put("b_horse1", b_horse1);
        images.put("b_horse2", b_horse2);
        images.put("b_queen", b_queen);
        images.put("b_king", b_king);

        table.disableProperty().bind(NetWorkClient.yourMove.not());
        giveUpBtn.disableProperty().bind(NetWorkClient.getCurrentNetwork().gameIsGoingProperty().not());
        offerDrawBtn.disableProperty().bind(NetWorkClient.getCurrentNetwork().gameIsGoingProperty().not());

        messageLabel = message;
    }

    public static Label getMessageLabel() {
        return messageLabel;
    }

    public static Map<String, ImageView> getImages() {
        return images;
    }
}
