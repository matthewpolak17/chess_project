package ChessPieces;

import Color.Colors;
import Functionality.*;

public class King extends Piece {

    private boolean inCheck;

    public King(Colors color, int xp, int yp, String name) {
        super(color, xp, yp, name);
        inCheck = false;
    }

    public void setCheck(boolean inCheck) {
        this.inCheck = inCheck;
    }

    public boolean getCheck() {
        return inCheck;
    }

    @Override
    public boolean isValidMove(Board board, Square start, Square end) {
        
        int startRow = start.getRow();
        int startCol = start.getCol();
        int endRow = end.getRow();
        int endCol = end.getCol();

        //Castling Moves
        if (board.canRC(board) && (this.getColor().equals(Colors.WHITE) && end.equals(board.getSquare(7, 6))))
            return true;
            
        if (board.canRC(board) && (this.getColor().equals(Colors.BLACK) && end.equals(board.getSquare(0, 6))))
            return true;

        if (board.canLC(board) && (this.getColor().equals(Colors.WHITE) && end.equals(board.getSquare(7, 2))))
            return true;

        if (board.canLC(board) && (this.getColor().equals(Colors.BLACK) && end.equals(board.getSquare(0, 2))))
            return true;

        if (Math.abs(endRow - startRow) > 1 || Math.abs(endCol - startCol) > 1)
            return false;

        //empty square or captured piece
        return (board.getSquare(endRow, endCol).getPiece() == null ||
                board.getSquare(endRow, endCol).getPiece().getColor() != getColor());
    }

    @Override
    public String toString() {
        if (this.getColor() == Colors.WHITE) {
            return "K";
        } else {
            return "k";
        }
    }
    /*
    public boolean canRC(Board board) {

        if (!this.hasMoved && 
        board.getSquare(7, 5).isEmpty() && 
        board.getSquare(7, 6).isEmpty() &&
        !board.getSquare(7, 7).getPiece().hasMoved) {
            return true;
        }
        

        if (!this.hasMoved && 
        board.getSquare(0, 5).isEmpty() && 
        board.getSquare(0, 6).isEmpty() &&
        !board.getSquare(0, 7).getPiece().hasMoved) {
            return true;
        }

        return false;
    }
    */
    
}