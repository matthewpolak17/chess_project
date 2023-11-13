package ChessPieces;

import Color.Colors;
import Functionality.*;

public class Knight extends Piece {

    public Knight(Colors color, int xp, int yp, String name) {
        super(color, xp, yp, name);
    }

    @Override
    public boolean isValidMove(Board board, Square start, Square end) {

        int startCol = start.getCol();
        int startRow = start.getRow();
        int endCol = end.getCol();
        int endRow = end.getRow();

        int vertDir = Math.abs(endRow - startRow);
        int horDir = Math.abs(endCol - startCol);

        if ((vertDir == 2 && horDir == 1) || (horDir == 2 && vertDir == 1)) {

        //empty square or captured piece
        return (board.getSquare(endRow, endCol).getPiece() == null ||
                board.getSquare(endRow, endCol).getPiece().getColor() != getColor());
        }

        return false;
    }

    @Override
    public String toString() {
        if (this.getColor() == Colors.WHITE) {
            return "N";
        } else {
            return "n";
        }
    }
}