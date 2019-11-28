package kpfu.itis.group11_801.kilin.chess.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;

abstract class Item {
    protected final static int OFFSET = 40;
    protected final static int SIZE = 100;

    protected DoubleProperty posX;
    protected DoubleProperty posY;
    protected ImageView imageView;
    protected int boardX;
    protected int boardY;
    protected Team team;

    public Item(ImageView imageView, Team team) {
        posX = new SimpleDoubleProperty();
        posY = new SimpleDoubleProperty();
        posX.bindBidirectional(imageView.xProperty());
        posY.bindBidirectional(imageView.yProperty());
        boardX = posToInt(posX);
        boardY = posToInt(posY);
        this.imageView = imageView;
        this.team = team;

        if (Game.getCurrentGame().getCurrentTeam() == )
        imageView.setOnMousePressed(event -> {
            boardX = posToInt(posX);
            boardY = posToInt(posY);
        });
        imageView.setOnMouseDragged(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (event.getX() >= OFFSET + SIZE / 2 && event.getX() <= 7.5 * SIZE + OFFSET &&
                    event.getY() >= OFFSET + SIZE / 2 && event.getY() <= 7.5 * SIZE + OFFSET ){
                    posX.set(event.getX() - SIZE / 2);
                    posY.set(event.getY() - SIZE / 2);
                }
            }
        });
        imageView.setOnMouseReleased(event -> {
            move(posToInt(posX), posToInt(posY));
        });
    }

    public boolean move(int x, int y) {
        if(canMove(x, y)) {
            posX.set(intToPos(x));
            posY.set(intToPos(y));
            return true;
        } else {
            posX.set(intToPos(boardX));
            posY.set(intToPos(boardY));
            return false;
        }
    }

    abstract boolean canMove(int x, int y);

    public int getX() {
        return posToInt(posX);
    }

    public  int getY() {
        return posToInt(posY);
    }

    protected static int posToInt(DoubleProperty arg) {
        return (int)Math.round((arg.get() - OFFSET) / SIZE);
    }

    protected static double intToPos(int x) {
        return x * SIZE + OFFSET;
    }

}