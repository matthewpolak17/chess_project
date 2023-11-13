package Functionality;

import ChessPieces.*;
import Color.Colors;

public class Board {

    private Square[][] board;

    public Board() {
        
        board = new Square[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new Square(i, j);
            }
        }

        board[0][0].setPiece(new Rook(Colors.BLACK, 0, 0, "Rook"));
        board[0][1].setPiece(new Knight(Colors.BLACK, 1, 0, "Knight"));
        board[0][2].setPiece(new Bishop(Colors.BLACK, 2, 0, "Bishop"));
        board[0][3].setPiece(new Queen(Colors.BLACK, 3, 0, "Queen"));
        board[0][4].setPiece(new King(Colors.BLACK, 4, 0, "King"));
        board[0][5].setPiece(new Bishop(Colors.BLACK, 5, 0, "Bishop"));
        board[0][6].setPiece(new Knight(Colors.BLACK, 6, 0, "Knight"));
        board[0][7].setPiece(new Rook(Colors.BLACK, 7, 0, "Rook"));

        for (int i = 0; i < 8; i++) {
            board[1][i].setPiece(new Pawn(Colors.BLACK, i, 1, "Pawn"));
        }

        for (int i = 0; i < 8; i++) {
            board[6][i].setPiece(new Pawn(Colors.WHITE, i, 6, "Pawn"));
        }

        board[7][0].setPiece(new Rook(Colors.WHITE, 0, 7, "Rook"));
        board[7][1].setPiece(new Knight(Colors.WHITE, 1, 7, "Knight"));
        board[7][2].setPiece(new Bishop(Colors.WHITE, 2, 7, "Bishop"));
        board[7][3].setPiece(new Queen(Colors.WHITE, 3, 7, "Queen"));
        board[7][4].setPiece(new King(Colors.WHITE, 4, 7, "King"));
        board[7][5].setPiece(new Bishop(Colors.WHITE, 5, 7, "Bishop"));
        board[7][6].setPiece(new Knight(Colors.WHITE, 6, 7, "Knight"));
        board[7][7].setPiece(new Rook(Colors.WHITE, 7, 7, "Rook"));

    }

    public Square getSquare(int row, int col) {
        return board[row][col];
    }

    public void setSquare(int row, int col) {
        this.board[row][col] = new Square(row, col);
    }

    public void movePiece(int startRow, int startCol, int endRow, int endCol) {
        board[endRow][endCol].setPiece(board[startRow][startCol].getPiece());
        board[startRow][startCol].setPiece(null);
    }

    //Prints the board to the console
    public void printBoard() {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!getSquare(i, j).isEmpty()) {
                    System.out.print(getSquare(i, j).getPiece().toString() + "  ");
                } else {
                    System.out.print("-  ");
                }   
            }
            System.out.println();
        }
    }

    //Checks if a king is in check after a move
    public boolean isKingInCheck(Colors kingColor) {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Square square = getSquare(i, j);
                Piece piece = square.getPiece();

                if (piece != null && piece.getColor() != kingColor) {
                    if (piece.isValidMove(this, square, getKingPosition(kingColor)))
                        return true;
                }
            }
        }
        return false;
    }

    //Returns the location of a specified king
    public Square getKingPosition(Colors kingColor) {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Square square = this.getSquare(i, j);
                Piece piece = square.getPiece();

                if (piece instanceof King && piece.getColor() == kingColor) {
                    return square;
                }
            }
        }
        return null;
    }

    public boolean inCheckmate(Board board, Colors colorInCheck) { 


        Board tempBoard = copy(board);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                Square square = tempBoard.getSquare(i, j);
                Piece piece = square.getPiece();

                if (piece != null && piece.getColor() == colorInCheck) { // if  there's a piece in the square and it's the same color

                    for (int k = 0; k < 8; k++) {
                        for (int l = 0; l < 8; l++) {
                            if (piece.isValidMove(tempBoard, square, tempBoard.getSquare(k, l))) { // if the algorithm encounters a valid move

                                if (tempBoard.isKingInCheck(colorInCheck)) {

                                    tempBoard.movePiece(square.getRow(), square.getCol(), tempBoard.getSquare(k, l).getRow(), tempBoard.getSquare(k, l).getCol()); // It moves the piece

                                    if (!tempBoard.isKingInCheck(colorInCheck)) { //It checks to see if it blocked the check
                                        
                                        System.out.println("Start: " + square.getRow() + " " + square.getCol() + "End: " + tempBoard.getSquare(k, l).getRow() + " " + tempBoard.getSquare(k, l).getCol());
                                        System.out.println(piece.getName());
                                        return false;
                                    }
                                    
                                    tempBoard.movePiece(tempBoard.getSquare(k, l).getRow(), tempBoard.getSquare(k, l).getCol(), square.getRow(), square.getCol()); //It moves the piece back
                                }
                                

                            }
                        }
                    }

                }
            }
        }
        //If none of the moves blocked the check
        return true; // Checkmate
    }


    //Creates a copy of the original board
    public Board copy(Board board) {

        Board tempBoard = new Board();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++)  {
                tempBoard.getSquare(i, j).setPiece(board.getSquare(i, j).getPiece());
            }
        }

        return tempBoard;
    }
    
    //can right castle
    public boolean canRC(Board board) {

        if (getKingPosition(Colors.WHITE).getPiece()!= null && !getKingPosition(Colors.WHITE).getPiece().hasMoved && 
        board.getSquare(7, 5).isEmpty() && 
        board.getSquare(7, 6).isEmpty() &&
        board.getSquare(7, 7).getPiece()!= null && !board.getSquare(7, 7).getPiece().hasMoved) {
            return true;
        }
        

        if (getKingPosition(Colors.BLACK).getPiece()!= null && !getKingPosition(Colors.BLACK).getPiece().hasMoved && 
        board.getSquare(0, 5).isEmpty() && 
        board.getSquare(0, 6).isEmpty() &&
        board.getSquare(0, 7).getPiece()!= null && !board.getSquare(0, 7).getPiece().hasMoved) {
            return true;
        }

        return false;
    }

    //can left castle
    public boolean canLC(Board board) {

        if (getKingPosition(Colors.WHITE).getPiece()!= null && !getKingPosition(Colors.WHITE).getPiece().hasMoved && 
        board.getSquare(7, 3).isEmpty() && 
        board.getSquare(7, 2).isEmpty() &&
        board.getSquare(7, 1).isEmpty() &&
        board.getSquare(7, 0).getPiece()!= null && !board.getSquare(7, 0).getPiece().hasMoved) {
            return true;
        }
        

        if (getKingPosition(Colors.BLACK).getPiece()!= null && !getKingPosition(Colors.BLACK).getPiece().hasMoved && 
        board.getSquare(0, 3).isEmpty() && 
        board.getSquare(0, 2).isEmpty() &&
        board.getSquare(0, 1).isEmpty() &&
        board.getSquare(0, 0).getPiece()!= null && !board.getSquare(0, 0).getPiece().hasMoved) {
            return true;
        }

        return false;
    }
}
