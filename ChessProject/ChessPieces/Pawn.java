package ChessPieces;

import Color.Colors;
import Functionality.*;

public class Pawn extends Piece {

    public Pawn(Colors color, int xp, int yp, String name) {
        super(color, xp, yp, name);
    }

    @Override
    public boolean isValidMove(Board board, Square start, Square end) {

        int startCol = start.getCol();
        int startRow = start.getRow();
        int endCol = end.getCol();
        int endRow = end.getRow();
        int dir = (getColor() == Colors.WHITE) ? -1 : 1;

        if (end.isEmpty()) { //no piece to capture

            if (startCol == endCol) { //moving forward
                if (endRow == startRow + dir) { // 1 square
                    return true;
                } else { // 2 squares
                    if (board.getSquare(startRow + dir, startCol).isEmpty() && endRow == startRow + (dir * 2) && (startRow == 6 || startRow == 1)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            } else { //can't move diagonally with no capture
                return false; 
            }

        } else { // piece to capture

            if (Math.abs(endCol - startCol) == 1 && endRow == startRow + dir) { // checking to see if it can move diagonally one square
                Piece capturedPiece = board.getSquare(endRow, endCol).getPiece();
                if (capturedPiece.getColor() != getColor()) {
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public String toString() {
        if (this.getColor() == Colors.WHITE) {
            return "P";
        } else {
            return "p";
        }
    }
}