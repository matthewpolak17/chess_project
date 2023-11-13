package ChessPieces;

import Color.Colors;
import Functionality.*;

public class Bishop extends Piece {

    public Bishop(Colors color, int xp, int yp, String name) {
        super(color, xp, yp, name);
    }

    @Override
    public boolean isValidMove(Board board, Square start, Square end) {

        int startCol = start.getCol();
        int startRow = start.getRow();
        int endCol = end.getCol();
        int endRow = end.getRow();

        // checks if it's on the same diagonal
        if (Math.abs(endRow - startRow) != Math.abs(endCol - startCol))
            return false;

        // directional variables
        int vertDir = Integer.signum(endRow - startRow);
        int horDir = Integer.signum(endCol - startCol);

        //placeholder variables
        int rowPlace = startRow + vertDir;
        int colPlace = startCol + horDir;
        while (rowPlace != endRow || colPlace != endCol) {
            if (board.getSquare(rowPlace, colPlace).getPiece() != null)
                return false;

            rowPlace += vertDir;
            colPlace += horDir;
        }

        //empty square or captured piece
        return (board.getSquare(endRow, endCol).getPiece() == null ||
                board.getSquare(endRow, endCol).getPiece().getColor() != getColor());

    }

    @Override
    public String toString() {
        if (this.getColor() == Colors.WHITE) {
            return "B";
        } else {
            return "b";
        }
    }

}