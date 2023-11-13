import Functionality.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

import Color.Colors;

public class Main {


    //------------------------------Declarations------------------------------//

    public static Piece selectedPiece = null;
    public static Square startSquare;
    public static Square endSquare;
    public static ArrayList<Square> moveList = new ArrayList<>();
    public static Square prevS = null;
    public static Piece prevP = null;
    public static int turn = 1;
    public static boolean inCheck = false;
    public static boolean gameOver = false;
    public static Color primColor = new Color(137, 107, 154);
    public static Color secColor = Color.WHITE;
    public static boolean wrc = false;
    public static boolean wlc = false;
    public static boolean brc = false;
    public static boolean blc = false;
    public static Board board;
    
    
    public static void main(String[] args) throws IOException{

        board = new Board();
        Image[] imgs = new Image[12];
        JFrame frame = new JFrame();
        ImageIcon navIcon = new ImageIcon("crown.png");


        imgs[0] = ImageIO.read(new File("whiteking.png"));
        imgs[1] = ImageIO.read(new File("whitequeen.png"));
        imgs[2] = ImageIO.read(new File("whitebishop.png"));
        imgs[3] = ImageIO.read(new File("whiteknight.png"));
        imgs[4] = ImageIO.read(new File("whiterook.png"));
        imgs[5] = ImageIO.read(new File("whitepawn.png"));
        imgs[6] = ImageIO.read(new File("blackking.png"));
        imgs[7] = ImageIO.read(new File("blackqueen.png"));
        imgs[8] = ImageIO.read(new File("blackbishop.png"));
        imgs[9] = ImageIO.read(new File("blackknight.png"));
        imgs[10] = ImageIO.read(new File("blackrook.png"));
        imgs[11] = ImageIO.read(new File("blackpawn.png"));

        //------------------------------MenuBar Info------------------------------//

        JMenuBar bar = new JMenuBar();

        JMenu file = new JMenu("File"); //First Menu Tag
        bar.add(file);
        JMenuItem ngame = new JMenuItem("New Game"); //Sub MenuItem
        file.add(ngame);

        JMenu edit = new JMenu("Edit"); //Second Menu Tag
        bar.add(edit);
        JMenuItem undo = new JMenuItem("Undo Move"); //Sub Menu
        edit.add(undo);

        JMenu pref = new JMenu("Preferences"); //Third Menu Tag
        bar.add(pref);
        JMenu changeColor = new JMenu("Change Board Color"); //Sub Menu
        pref.add(changeColor);
        JMenuItem primaryColor = new JMenuItem("Primary Color"); //Sub MenuItem
        changeColor.add(primaryColor);
        JMenuItem secondaryColor = new JMenuItem("Secondary Color"); //Sub MenuItem
        changeColor.add(secondaryColor);

        

        //------------------------------JFrame Settings------------------------------//

        frame.setJMenuBar(bar);
        frame.setDefaultCloseOperation(3);
        frame.setResizable(false);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        frame.setTitle("Chess");
        frame.setIconImage(navIcon.getImage());
        
        //------------------------------MenuItem Action Listeners------------------------------//

        ngame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == ngame) {
                    board = new Board();
                    moveList.clear();
                    turn = 1;
                    selectedPiece = null;
                    prevS = null;
                    prevP = null;
                    gameOver = false;
                    inCheck = false;
                    wrc = false;
                    wlc = false;
                    brc = false;
                    blc = false;
                    frame.repaint();
                }
            }
        });

        primaryColor.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == primaryColor) {
                    new JColorChooser();
                    Color temp = JColorChooser.showDialog(null, "Pick A Color", Color.BLACK);

                    if (temp == null)
                    return;

                    primColor = temp;

                    frame.repaint();
                }
            }
            
        });

        secondaryColor.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == secondaryColor) {
                    new JColorChooser();
                    Color temp = JColorChooser.showDialog(null, "Pick A Color", Color.WHITE);

                    if (temp == null)
                    return;

                    secColor = temp;

                    frame.repaint();
                }
            }
            
        });

        //------------------------------Extra JPanels------------------------------//

        JPanel panel2 = new JPanel();
        panel2.setPreferredSize(new Dimension(310, 800));
        panel2.setLayout(null);
        panel2.setBackground(primColor.darker());

        JPanel panel3 = new JPanel();
        panel3.setBounds(10, 10, 290, 780);
        panel3.setBackground(secColor.brighter());
        panel2.add(panel3);


        //------------------------------Main Panel------------------------------//
        //----------------------------Paint Function----------------------------//

        JPanel panel = new JPanel() {
            @Override
            public void paint(Graphics g) {

                super.paintComponent(g);
                boolean isWhite = true;
                Graphics2D g2d = (Graphics2D) g;

                //Draws Board
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {

                        if (isWhite) {
                            g2d.setPaint(secColor);
                        } else {
                            g2d.setPaint(primColor);
                        }
                        
                        g2d.fillRect(j * 100, i * 100, 100, 100);
                        isWhite = !isWhite;
                    }
                    isWhite = !isWhite;
                }

                //Draws Pieces on Board
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        Square square = board.getSquare(i, j);
                        Piece piece = square.getPiece();

                        int index = 0;

                        if (piece != null) {
                            switch (piece.getName()) {
                                case "King" : index = 0;
                                break;
                                case "Queen" : index = 1;
                                break;
                                case "Bishop" : index = 2;
                                break;
                                case "Knight" : index = 3;
                                break;
                                case "Rook" : index = 4;
                                break;
                                case "Pawn" : index = 5;
                                break;
                            }

                            if (piece.getColor() == Colors.BLACK)
                                index += 6;

                            if (piece.onTop == 0)
                            g2d.drawImage(imgs[index], piece.getXP(), piece.getYP(), this);
                        }
                    }
                }

                //Draws Potential Moves
                for (Square s : moveList) {
                    g2d.setPaint(new Color(48, 110, 191, 120));
                    g2d.fillOval((s.getCol() * 100) + 35, (s.getRow() * 100) + 35, 30, 30);
                }

                //Draws the piece that the player is holding last
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        Square square = board.getSquare(i, j);
                        Piece piece = square.getPiece();

                        int index = 0;

                        if (piece != null) {
                            switch (piece.getName()) {
                                case "King" : index = 0;
                                break;
                                case "Queen" : index = 1;
                                break;
                                case "Bishop" : index = 2;
                                break;
                                case "Knight" : index = 3;
                                break;
                                case "Rook" : index = 4;
                                break;
                                case "Pawn" : index = 5;
                                break;
                            }

                            if (piece.getColor() == Colors.BLACK)
                                index += 6;

                            if (piece.onTop == 1)
                            g2d.drawImage(imgs[index], piece.getXP(), piece.getYP(), this);
                        }
                    }
                }

                panel2.setBackground(primColor.darker());
                panel3.setBackground(secColor.brighter());
            }
        };

        //------------------------------Adding Completed Panels to Frame------------------------------//

        panel.setPreferredSize(new Dimension(800, 800));
        frame.add(panel);
        frame.add(panel2);

        //-------------------------------Mouse Listener Methods-------------------------------//


        frame.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                
                if (gameOver)
                    return;

                //Can't drop a piece outside the frame
                if ((((e.getY() - 53) / 100) > 7 || ((e.getY() - 53) / 100) < 0) ||
                   (((e.getX() - 7) / 100) > 7 || ((e.getX() - 7) / 100) < 0)) {
                    moveList.clear();
                    frame.repaint();
                    return;
                }

                selectedPiece = board.getSquare((e.getY() - 53) / 100, (e.getX() - 7) / 100).getPiece();

                if (selectedPiece != null) {

                        if (selectedPiece.getColor() == Colors.WHITE && turn == 1 ||
                            selectedPiece.getColor() == Colors.BLACK && turn == -1) {

                                moveList.clear();
                                startSquare = board.getSquare((e.getY() - 53) / 100, (e.getX() - 7) / 100);

                                for (int i = 0; i < 8; i++) {
                                    for (int j = 0; j < 8; j++) {

                                        endSquare = board.getSquare(i, j);

                                        if (selectedPiece.isValidMove(board, startSquare, endSquare)) {
                                            moveList.add(endSquare);
                                        }
                                    }
                                }

                                frame.repaint();

                        } else {
                            selectedPiece = null;
                            prevS = board.getSquare((e.getY() - 53) / 100, (e.getX() - 7) / 100);
                        }

                } else if (moveList.contains(board.getSquare((e.getY() - 53) / 100, (e.getX() - 7) / 100))) {
                        prevS = board.getSquare((e.getY() - 53) / 100, (e.getX() - 7) / 100);
                } else {
                    moveList.clear();
                }
                
            }



            @Override
            public void mouseReleased(MouseEvent e) {

                if (gameOver)
                    return;

                //Can't drop a piece outside the frame
                if ((((e.getY() - 53) / 100) > 7 || ((e.getY() - 53) / 100) < 0) ||
                (((e.getX() - 7) / 100) > 7 || ((e.getX() - 7) / 100) < 0)) {

                    if (selectedPiece != null) {
                        selectedPiece.setXP(selectedPiece.getX() * 100);
                        selectedPiece.setYP(selectedPiece.getY() * 100);
                    }

                    moveList.clear();
                    frame.repaint();
                    return;
                }

                endSquare = board.getSquare((e.getY() - 53) / 100, (e.getX() - 7) / 100);

                Piece capturedPiece = null;
                if (endSquare.getPiece() != null)
                capturedPiece = endSquare.getPiece();

                    if (selectedPiece != null || (!moveList.isEmpty() && endSquare.equals(prevS))) {

                        if (selectedPiece == null) {
                            selectedPiece = prevP;
                        }

                        selectedPiece.onTop = 0;

                        if (selectedPiece.isValidMove(board, startSquare, endSquare)) {
                            
                            if (haveTheyCastled(board, startSquare, endSquare)) {

                                //finds the direction they want to castle
                                if (endSquare.getCol() > startSquare.getCol()) { 

                                    rightCastle(board, turn); //right side castling
                                } else {

                                    leftCastle(board, turn);  //left side castling
                                }


                            }
                            //undoCastling in case it puts them in check
                            //if they're trying to get out of check through castling, just return

                            board.getSquare(selectedPiece.getY(), selectedPiece.getX()).setPiece(null);
                            board.getSquare((e.getY() - 53) / 100, (e.getX() - 7) / 100).setPiece(selectedPiece);

                            //Forces player to deflect opposing checks
                            //before turn is changed
                            if (board.isKingInCheck(getKingColor(turn))) {

                                board.getSquare((e.getY() - 53) / 100, (e.getX() - 7) / 100).setPiece(capturedPiece);
                                board.getSquare(selectedPiece.getY(), selectedPiece.getX()).setPiece(selectedPiece);
                                selectedPiece.setXP(selectedPiece.getX() * 100);
                                selectedPiece.setYP(selectedPiece.getY() * 100);
                                moveList.clear();
                                frame.repaint();
                                return;
                            }

                            

                            selectedPiece.setXP(((e.getX() - 7) / 100) * 100);
                            selectedPiece.setX(selectedPiece.getXP() / 100);
                            selectedPiece.setYP(((e.getY() - 53) / 100) * 100);
                            selectedPiece.setY(selectedPiece.getYP() / 100);
                            selectedPiece.hasMoved = true;

                            turn *= -1;
                            prevP = null;
                            prevS = null;
                            
                            moveList.clear();

                            //after turn is changed
                            if (board.isKingInCheck(getKingColor(turn))) {
                                inCheck = true;
                                System.out.println("Check");
                                
                                if (board.inCheckmate(board, getKingColor(turn))) {
                                    System.out.println("Checkmate");
                                    moveList.clear();
                                    gameOver = true;
                                }
                            }
                                
                            
                            
                        
                        } else if (startSquare.equals(endSquare)) {
                            prevP = selectedPiece;
                            selectedPiece.setXP(selectedPiece.getX() * 100);
                            selectedPiece.setYP(selectedPiece.getY() * 100);
                        } else {
                            moveList.clear();
                            selectedPiece.setXP(selectedPiece.getX() * 100);
                            selectedPiece.setYP(selectedPiece.getY() * 100);
                        }
                            
                    }

                frame.repaint();
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
            
        });

        //-------------------------------Mouse Motion Listener Methods-------------------------------//

        frame.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {

                if (gameOver)
                    return;
                    
                if (selectedPiece != null) {
                    selectedPiece.setXP(e.getX() - 57);
                    selectedPiece.setYP(e.getY() - 103);
                    selectedPiece.onTop = 1;
                    frame.repaint();
                }
                
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
            
        });

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


        /*
        //Driver starts here
        //board.printBoard();

        while (gameOver == false) {

            //Checks to see if the player is in check / checkmate
            if (inCheck == true) {

                if (board.inCheckmate(board, getKingColor(turn))) {

                    System.out.println("Checkmate");
                    gameOver = true;

                } else {

                    while (inCheck == true) {
                        
                        System.out.println("Enter a move:");
                        String input = scanner.nextLine();

                        //Parse
                        int startCol = convert(input.substring(0, 1));
                        int startRow = Math.abs(Integer.parseInt(input.substring(1,2)) - 8);
                        int endCol = convert(input.substring(2, 3));
                        int endRow = Math.abs(Integer.parseInt(input.substring(3,4)) - 8);


                        if (board.getSquare(startRow, startCol).isEmpty()) {

                            System.out.println("There isn't a piece in that square\n");
                            continue;

                        } else {

                            if ((turn == 1 && board.getSquare(startRow, startCol).getPiece().getColor() == Colors.WHITE) 
                             || (turn == -1 && board.getSquare(startRow, startCol).getPiece().getColor() == Colors.BLACK)) {

                                if (board.getSquare(startRow, startCol).getPiece().isValidMove(board, board.getSquare(startRow, startCol), board.getSquare(endRow, endCol))) {

                                    board.movePiece(startRow, startCol, endRow, endCol);

                                        if (!board.isKingInCheck(getKingColor(turn))) {
                                            turn *= -1;
                                            System.out.println("Check blocked");
                                            inCheck = false;
                                        } else {
                                            board.movePiece(endRow, endCol, startRow, startCol);
                                            System.out.println("You have to block the check");
                                            continue;
                                        }

                                } else {
                                    System.out.println("Move Cancelled, Invalid Move\n");
                                }
                            } else {
                                System.out.println("Move Cancelled, Wrong Color\n");
                            }
                        }
                        board.printBoard();
                    }
                }
            } else {

                System.out.println("Enter a move:"); 
                String input = scanner.nextLine();

                //Parse
                int startCol = convert(input.substring(0, 1));
                int startRow = Math.abs(Integer.parseInt(input.substring(1,2)) - 8);
                int endCol = convert(input.substring(2, 3));
                int endRow = Math.abs(Integer.parseInt(input.substring(3,4)) - 8);


                if (board.getSquare(startRow, startCol).isEmpty()) {

                    System.out.println("There isn't a piece in that square\n");
                } else {

                if ((turn == 1 && board.getSquare(startRow, startCol).getPiece().getColor() == Colors.WHITE) 
                 || (turn == -1 && board.getSquare(startRow, startCol).getPiece().getColor() == Colors.BLACK)) {

                    if (board.getSquare(startRow, startCol).getPiece().isValidMove(board, board.getSquare(startRow, startCol), board.getSquare(endRow, endCol))) {

                        board.movePiece(startRow, startCol, endRow, endCol);
                        turn *= -1;
                        System.out.println("Piece moved\n");

                        if (board.isKingInCheck(getKingColor(turn))) {
                            inCheck = true;
                            System.out.println("Check");
                        }
                    } else {

                        System.out.println("Move Cancelled, Invalid Move\n");
                    }
                } else {

                    System.out.println("Move Cancelled, Wrong Color\n");
                }
            }
            board.printBoard();
            }
            
        }
        panel.repaint();
        scanner.close();
    }

        


    //converts user input to value
    public static int convert(String col) {
        int value = -1;
        switch(col) {
            case "a": value = 0;
            break;
            case "b": value = 1;
            break;
            case "c": value = 2;
            break;
            case "d": value = 3;
            break;
            case "e": value = 4;
            break;
            case "f": value = 5;
            break;
            case "g": value = 6;
            break;
            case "h": value = 7;
            break;
        }
        return value;
            
    }
    */

    protected static boolean haveTheyCastled(Board board, Square startSquare, Square endSquare) {

        if (startSquare.equals(board.getSquare(0, 4)) && endSquare.equals(board.getSquare(0, 6))) { //black rc
            return true;
        }
        if (startSquare.equals(board.getSquare(0, 4)) && endSquare.equals(board.getSquare(0, 2))) { //black lc
            return true;
        }
        if (startSquare.equals(board.getSquare(7, 4)) && endSquare.equals(board.getSquare(7, 6))) { //white rc
            return true;
        }
        if (startSquare.equals(board.getSquare(7, 4)) && endSquare.equals(board.getSquare(7, 2))) { //white lc
            return true;
        }

        return false;
    }

    public static void leftCastle(Board board, int turn) {

        if (board.canLC(board) && selectedPiece.getName().equals("King")) {

            if (turn == 1) {
                Piece tempRook = board.getSquare(7,0).getPiece();
                board.getSquare(7,0).setPiece(null);
                board.getSquare(7,3).setPiece(tempRook);
                tempRook.setXP(300);
                tempRook.setX(3);
                wlc = true;
                System.out.println("White left castled");

            } else if (turn == -1) {

                Piece tempRook = board.getSquare(0,0).getPiece();
                board.getSquare(0,0).setPiece(null);
                board.getSquare(0,3).setPiece(tempRook);
                tempRook.setXP(300);
                tempRook.setX(3);
                blc = true;
                System.out.println("Black left castled");

            }
        }
    }

    public static void rightCastle(Board board, int turn) {

        if (board.canRC(board) && selectedPiece.getName().equals("King")) {

            if (turn == 1) {
                Piece tempRook = board.getSquare(7,7).getPiece();
                board.getSquare(7,7).setPiece(null);
                board.getSquare(7,5).setPiece(tempRook);
                tempRook.setXP(500);
                tempRook.setX(5);
                wrc = true;
                System.out.println("White right castled");

            } else if (turn == -1) {

                Piece tempRook = board.getSquare(0,7).getPiece();
                board.getSquare(0,5).setPiece(tempRook);
                board.getSquare(0,7).setPiece(null);
                tempRook.setXP(500);
                tempRook.setX(5);
                brc = true;
                System.out.println("Black right castled");

            }

        }
    }

    public static Colors getKingColor(int turn) {
        if (turn == 1) {
            return Colors.WHITE;
        } else {
            return Colors.BLACK;
        }
    }
}

        
