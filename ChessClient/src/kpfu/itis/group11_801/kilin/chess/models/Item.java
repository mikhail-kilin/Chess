package kpfu.itis.group11_801.kilin.chess.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import kpfu.itis.group11_801.kilin.chess.controllers.NetWorkClient;

public abstract class Item {
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

        if (Game.getCurrentGame().getCurrentTeam() == team) {
            imageView.setOnMousePressed(event -> {
                boardX = posToInt(posX);
                boardY = posToInt(posY);
            });
            imageView.setOnMouseDragged(event -> {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    if (event.getX() >= OFFSET && event.getX() <= 8 * SIZE &&
                            event.getY() >= OFFSET && event.getY() <= 8 * SIZE) {
                        posX.set(event.getX() - SIZE / 2);
                        posY.set(event.getY() - SIZE / 2);
                    }
                }
            });
            imageView.setOnMouseReleased(event -> {
                int x = posToInt(posX);
                int y = posToInt(posY);
                NetWorkClient.getCurrentNetwork()
                        .move(boardX, boardY, x, y);
                move(x, y);
            });
        }
        Game.getCurrentGame().createItem(getX(), getY(), this);
    }

    public void destroy() {
        imageView.setVisible(false);
    }

    public void restore() {
        imageView.setVisible(true);
    }

    public boolean move(int x, int y) {
        if(canMove(x, y)) {
            Item item = Game.getCurrentGame().getItem(x, y);
            posX.set(intToPos(x));
            posY.set(intToPos(y));
            Game.getCurrentGame().deleteElem(x, y);
            Game.getCurrentGame().moveItem(boardX, boardY, x, y);

            if (Game.getCurrentGame().isShah(team)) {
                posX.set(intToPos(boardX));
                posY.set(intToPos(boardY));
                Game.getCurrentGame().moveItem(x, y, boardX, boardY);
                if (item != null) {
                    Game.getCurrentGame().restoreElem(x, y, item);
                }
                return false;
            } else {
                boardX = getX();
                boardY = getY();
                return true;
            }

        } else {
            posX.set(intToPos(boardX));
            posY.set(intToPos(boardY));
            return false;
        }

    }

    public boolean isEngaged(int x, int y) {
        Game currentGame = Game.getCurrentGame();
        Item item = currentGame.getItem(x, y);
        if (boardY == y && boardX == x || (item != null && item.team == team)) {
            return true;
        }
        return false;
    }

    public abstract boolean canMove(int x, int y);

    public int getX() {
        return posToInt(posX);
    }

    public  int getY() {
        return posToInt(posY);
    }

    protected static int posToInt(DoubleProperty arg) {
        return (int)Math.round((arg.get() - OFFSET) / SIZE);
    }

    protected static double intToPos(int arg) {
        return arg * SIZE + OFFSET;
    }


}
