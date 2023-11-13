package Functionality;

public class Square {

    private Piece piece;
    private int row;
    private int col;

    public Square(int row, int col) {
        this.piece = null;
        this.row = row;
        this.col = col;
    }

    public boolean isEmpty() {
        return this.piece == null;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getRow() {
        return row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getCol() {
        return col;
    }

    public void setPiece(Piece piece) {
        this.piece =  piece;
    }

    public Piece getPiece() {
        return this.piece;
    }

}
