package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class Piece {
    boolean team;
    String name;
    JLabel label;
    int x;
    int y;
    private boolean firstMove = true;
    public boolean enPassant;
    private Square point;
    Board board;
    ArrayList<Square> moves = new ArrayList<>();
    ArrayList<Square> attackMoves = new ArrayList<>();


    Piece(String name, Boolean team, int x, int y) {
        this.board = MyFrame.chess.getBoard();
        this.name = name;
        this.team = team;
        this.x = x;
        this.y = y;

        //image chooser
        ImageIcon img = null;
        switch (name) {
            case "pawn":
                if (team) {
                    img = new ImageIcon("images/pawn_white.png");
                } else {
                    img = new ImageIcon("images/pawn_black.png");
                }
                break;
            case "knight":
                if (team) {
                    img = new ImageIcon("images/knight_white.png");
                } else {
                    img = new ImageIcon("images/knight_black.png");
                }
                break;
            case "bishop":
                if (team) {
                    img = new ImageIcon("images/bishop_white.png");
                } else {
                    img = new ImageIcon("images/bishop_black.png");
                }
                break;
            case "rock":
                if (team) {
                    img = new ImageIcon("images/rock_white.png");
                } else {
                    img = new ImageIcon("images/rock_black.png");
                }
                break;

            case "queen":
                if (team) {
                    img = new ImageIcon("images/queen_white.png");
                } else {
                    img = new ImageIcon("images/queen_black.png");
                }
                break;
            case "king":
                if (team) {
                    img = new ImageIcon("images/king_white.png");
                } else {
                    img = new ImageIcon("images/king_black.png");
                }
                break;
        }
        // add into team
        if(team){
            MyFrame.chess.whitePieces.add(this);
        } else {
            MyFrame.chess.blackPieces.add(this);

        }

        //resize image
        img = this.imageResize(img, board.getSquare(x, y).getWidth(), board.getSquare(x, y).getHeight());
        this.label = new JLabel(img);

        // set image at Square
        board.getSquare(x, y).add(label);
        board.getSquare(x, y).updateUI();
        board.getSquare(x, y).setPiece(this);



    }

    public Square getSquare(){
        return board.getSquare(x,y);
    }
    public void updateMoveSet(){
        moves = new ArrayList<>();
        switch (name) {
            case ("pawn"):
                pawnMoveSet();
                break;
            case("knight"):
                knightMoveSet();
                break;
            case("bishop"):
                bishopMoveSet();
                break;
            case("rock"):
                rockMoveSet();
                break;
            case("queen"):
                queenMoveSet();
                break;
            case("king"):
                kingMoveSet();
                break;
        }
    }




    public void showMoveSet(){
        for (int i = 0; i < moves.size(); i++) {
            board.getSquare(moves.get(i).getCoordinatesX() , moves.get(i).getCoordinatesY()).setBackground(new Color(129, 129, 136));
        }
    }

    public boolean theoreticalMove(Square newPos, Piece king){
        Board copyBoard = board;
        newPos = copyBoard.getSquare(newPos.getCoordinatesX(),newPos.getCoordinatesY());
        Square playedPoint = newPos;

        // I hate en passant...
        if (getName() == "pawn") {
            if ((playedPoint.getCoordinatesX() == x && playedPoint.getCoordinatesY() == y + 2) || (playedPoint.getCoordinatesX() == x && playedPoint.getCoordinatesY() == y - 2)) {
                enPassant = true;
            }
        }
        if (getName() == "king") {
            if (playedPoint.getCoordinatesX() == x - 2) {
                copyBoard.getSquare(0, playedPoint.getCoordinatesY()).getPiece().move(copyBoard.getSquare(2, playedPoint.getCoordinatesY()));
            }
        }
        if (point != null) {
            if (playedPoint.getCoordinatesX() == point.getCoordinatesX() || playedPoint.getCoordinatesY() == point.getCoordinatesY()) {
                if (team) {
                    copyBoard.getSquare(newPos.getCoordinatesX(), newPos.getCoordinatesY() + 1).getPiece().delete();
                } else {
                    copyBoard.getSquare(newPos.getCoordinatesX(), newPos.getCoordinatesY() - 1).getPiece().delete();
                }

            }
        }


        if (firstMove) {
            firstMove = false;
        }
        remove();
        if (newPos.getPiece() != null) {
            newPos.getPiece().delete();
        }
        newPos.updateUI();
        newPos.setPiece(this);
        this.x = newPos.getCoordinatesX();
        this.y = newPos.getCoordinatesY();
        if(!copyBoard.getSquare(king.x, king.y).attacked){
            return true;
        }
        else return false;
    }
    public boolean move(Square newPos) {

        Square playedPoint = newPos;

        // I hate en passant...
        if (getName() == "pawn") {
            if ((playedPoint.getCoordinatesX() == x && playedPoint.getCoordinatesY() == y + 2) || (playedPoint.getCoordinatesX() == x && playedPoint.getCoordinatesY() == y - 2)) {
                enPassant = true;
            }
        }
            if (getName() == "king") {
                if (playedPoint.getCoordinatesX() == x - 2) {
                    System.out.println("ok");
                    board.getSquare(0, playedPoint.getCoordinatesY()).getPiece().move(board.getSquare(2, playedPoint.getCoordinatesY()));
                }
            }
            if (point != null) {
                if (playedPoint.getCoordinatesX() == point.getCoordinatesX() || playedPoint.getCoordinatesY() == point.getCoordinatesY()) {
                    System.out.println("en ");
                    if (team) {
                        board.getSquare(newPos.getCoordinatesX(), newPos.getCoordinatesY() + 1).getPiece().delete();
                    } else {
                        board.getSquare(newPos.getCoordinatesX(), newPos.getCoordinatesY() - 1).getPiece().delete();
                    }

                }
            }


            if (firstMove) {
                firstMove = false;
            }
            remove();
            if (newPos.getPiece() != null) {
                newPos.getPiece().delete();
            }
            newPos.add(label);
            newPos.updateUI();
            newPos.setPiece(this);
            this.x = newPos.getCoordinatesX();
            this.y = newPos.getCoordinatesY();
            return true;

    }







    public void remove() {
        board.getSquare(x , y).removeAll();
        board.getSquare(x, y).setPiece(null);
        board.getSquare(x, y).updateUI();

    }
    public void delete() {
        if(getTeam()){
            MyFrame.chess.whitePieces.remove(this);
        } else {
            MyFrame.chess.blackPieces.remove(this);

        }
        board.getSquare(x , y).removeAll();
        board.getSquare(x, y).setPiece(null);
        board.getSquare(x, y).updateUI();

    }

    public boolean getTeam() {
        return this.team;
    }


    public String getName() {
        return this.name;
    }

    private void pawnMoveSet() {
        moves = new ArrayList<>();
        if(enPassant){
            enPassant = false;
        }
        //white
        if (this.team) {

            if (board.getSquare(x, y - 1).getPiece() == null) {
                // One forward
                moves.add(board.getSquare(x, y - 1));
                if (board.getSquare(x, y - 2).getPiece() == null && firstMove){
                    // Two forward
                    moves.add(board.getSquare(x, y - 2));



                }
            }
            if (x + 1 < board.getLength()) {
                attackMoves.add(board.getSquare(x + 1, y - 1));
                if (board.getSquare(x + 1, y - 1).getPiece() != null) {
                    if (board.getSquare(x + 1, y - 1).getPiece().getTeam() != getTeam()) {
                        // Attack right
                        moves.add(board.getSquare(x + 1, y - 1));
                    }
                }else {
                    if(board.getSquare(x+1, y).getPiece() != null){
                        if(board.getSquare(x+1, y).getPiece().getTeam() != getTeam()){
                            if(board.getSquare(x+1,y).getPiece().enPassant){
                                //enPassant
                                moves.add(board.getSquare(x + 1, y - 1));
                                point = moves.get(moves.size()-1);

                            }
                        }
                    }
                }
            }

            if(x-1 >= 0) {
                attackMoves.add(board.getSquare(x - 1, y - 1));
                if (board.getSquare(x - 1, y - 1).getPiece() != null) {
                    if (board.getSquare(x - 1, y - 1).getPiece().getTeam() != getTeam()) {
                        // Attack left
                         moves.add(board.getSquare(x - 1, y - 1));
                    }
                } else {
                    if(board.getSquare(x-1, y).getPiece() != null){
                        if(board.getSquare(x-1, y).getPiece().getTeam() != getTeam()){
                            if(board.getSquare(x-1,y).getPiece().enPassant){
                                //enPassant
                                moves.add(board.getSquare(x - 1, y - 1));
                                point = moves.get(moves.size()-1);


                            }
                        }
                    }
                }

            }


        } else {

                if (board.getSquare(x, y + 1).getPiece() == null) {
                    // One forward
                    moves.add(board.getSquare(x, y + 1));
                    if (board.getSquare(x, y + 2).getPiece() == null && firstMove) {
                        // Two forward
                        moves.add(board.getSquare(x, y + 2));


                    }
                }
                if (x + 1 < board.getLength()) {
                    attackMoves.add(board.getSquare(x + 1, y + 1));
                    if (board.getSquare(x + 1, y + 1).getPiece() != null) {
                        if (board.getSquare(x + 1, y + 1).getPiece().getTeam() != getTeam()) {
                            // Attack right
                            moves.add(board.getSquare(x + 1, y + 1));
                        }
                    } else {
                        if (board.getSquare(x + 1, y).getPiece() != null) {
                            if (board.getSquare(x + 1, y).getPiece().getTeam() != getTeam()) {
                                if (board.getSquare(x + 1, y).getPiece().enPassant) {
                                    //enPassant
                                    moves.add(board.getSquare(x + 1, y + 1));
                                    point = moves.get(moves.size()-1);

                                }
                            }
                        }
                    }
                }

                if (x - 1 >= 0) {
                    attackMoves.add(board.getSquare(x - 1, y + 1));
                    if (board.getSquare(x - 1, y + 1).getPiece() != null) {
                        if (board.getSquare(x - 1, y + 1).getPiece().getTeam() != getTeam()) {
                            // Attack left
                            moves.add(board.getSquare(x - 1, y + 1));
                        }
                    } else {
                        if (board.getSquare(x - 1, y).getPiece() != null) {
                            if (board.getSquare(x - 1, y).getPiece().getTeam() != getTeam()) {
                                if (board.getSquare(x - 1, y).getPiece().enPassant) {
                                    //enPassant
                                    moves.add(board.getSquare(x - 1, y + 1));
                                    point = moves.get(moves.size()-1);

                                }
                            }
                        }
                    }

                }

        }

    }

    private void knightMoveSet() {
        Point[] arr =new Point[]{new Point(x+2,y+1), new Point(x+2,y-1),new Point(x-2,y+1),new Point(x-2,y-1)
                ,new Point(x+1,y+2),new Point(x-1,y+2),new Point(x+1,y-2), new Point(x-1,y-2)};


        for (int i = 0; i < arr.length; i++) {
            if(arr[i].y < board.getLength() && arr[i].y >= 0 &&
                    arr[i].x < board.getLength() && arr[i].x >= 0) {
                if (board.getSquare(arr[i].x, arr[i].y).getPiece() != null) {
                    if (board.getSquare(arr[i].x, arr[i].y).getPiece().getTeam() == this.getTeam()) {
                        continue;
                    }
                }
                moves.add(board.getSquare(arr[i].x,arr[i].y));
            }
        }
            
        }

    private void bishopMoveSet(){

        for (int i = x+1, j = y+1; i < board.getLength() && j < board.getLength(); i++, j++) {
            // Check if Piece in the way
            if(board.getSquare(i,j).getPiece() != null) {
                // friendly
                if (board.getSquare(i,j).getPiece().getTeam() == this.getTeam()) {
                    break;

                    // less so
                } else {
                    moves.add(board.getSquare(i, j));
                    break;
                }
            }

            moves.add(board.getSquare(i, j));
        }

        for (int i = x+1, j = y-1; i < board.getLength() && j >= 0; i++, j--) {
            // Check if Piece in the way
            if(board.getSquare(i,j).getPiece() != null) {
                // friendly
                if (board.getSquare(i,j).getPiece().getTeam() == this.getTeam()) {
                    break;

                    // less so
                } else {
                    moves.add(board.getSquare(i, j));
                    break;
                }
            }

            moves.add(board.getSquare(i, j));
        }

        for (int i = x-1, j = y+1; i >= 0 && j < board.getLength(); i--, j++) {
            // Check if Piece in the way
            if(board.getSquare(i,j).getPiece() != null) {
                // friendly
                if (board.getSquare(i,j).getPiece().getTeam() == this.getTeam()) {
                    break;

                    // less so
                } else {
                    moves.add(board.getSquare(i, j));
                    break;
                }
            }

            moves.add(board.getSquare(i, j));
        }

        for (int i = x-1, j = y-1; i >= 0  && j >= 0 ; i--, j--) {
            // Check if Piece in the way
            if(board.getSquare(i,j).getPiece() != null) {
                // friendly
                if (board.getSquare(i,j).getPiece().getTeam() == this.getTeam()) {
                    break;

                    // less so
                } else {
                    moves.add(board.getSquare(i, j));
                    break;
                }
            }

            moves.add(board.getSquare(i, j));
        }



    }


    private void rockMoveSet() {
        moves = new ArrayList<>();
        //right
        for (int i = this.x+1; i < board.getLength(); i++) {

            // Check if Piece in the way
            if(board.getSquare(i,y).getPiece() != null) {
                // friendly
                if (board.getSquare(i,y).getPiece().getTeam() == this.getTeam()) {
                    break;

                    // less so
                } else {
                    moves.add(board.getSquare(i, this.y));
                    break;
                }
            }

            moves.add(board.getSquare(i, this.y));
        }
        // left
        for (int i = this.x-1; i >= 0; i--) {

            // Check if Piece in the way
            if(board.getSquare(i,y).getPiece() != null) {
                // friendly
                if (board.getSquare(i,y).getPiece().getTeam() == this.getTeam()) {
                    break;

                    // less so
                } else {
                    moves.add(board.getSquare(i, this.y));
                    break;
                }
            }

            moves.add(board.getSquare(i, this.y));
        }
        // up
        for (int i = this.y-1; i >= 0; i--) {

            // Check if Piece in the way
            if(board.getSquare(x , i).getPiece() != null) {
                // friendly
                if (board.getSquare(x , i).getPiece().getTeam() == this.getTeam()) {
                    break;

                    // less so
                } else {
                    moves.add(board.getSquare(this.x, i));
                    break;
                }
            }

            moves.add(board.getSquare(this.x, i));
        }
        // down
        for (int i = this.y+1; i < board.getLength(); i++) {

            // Check if Piece in the way
            if(board.getSquare(x , i).getPiece() != null) {
                // friendly
                if (board.getSquare(x , i).getPiece().getTeam() == this.getTeam()) {
                    break;

                // less so
                } else {
                    moves.add(board.getSquare(this.x, i));
                    break;
                }
            }

            moves.add(board.getSquare(this.x, i));
        }


    }


    private void queenMoveSet() {
        // wow... i didn't think it's going to work
        rockMoveSet();
        bishopMoveSet();
    }

    private void kingMoveSet() {

        Point[] arr = new Point[]{new Point(x+1,y), new Point(x+1,y+1),new Point(x+1,y-1),new Point(x,y+1)
                ,new Point(x,y-1),new Point(x-1,y-1),new Point(x-1,y),new Point(x-1,y+1)};


        for (int i = 0; i < arr.length; i++) {
            if (arr[i].x >= 0 && arr[i].x < board.getLength() && arr[i].y >= 0 && arr[i].y < board.getLength()) {
                if (!MyFrame.chess.attackedSquares.contains(board.getSquare(arr[i].x, arr[i].y))) {

                    if (arr[i].x < board.getLength() && arr[i].x >= 0 &&
                            arr[i].y < board.getLength() && arr[i].y >= 0) {
                        if (board.getSquare(arr[i].x, arr[i].y).getPiece() != null) {
                            if (board.getSquare(arr[i].x, arr[i].y).getPiece().getTeam() == this.getTeam()) {
                                continue;
                            }
                        }
                        moves.add(board.getSquare(arr[i].x, arr[i].y));
                    }

                }
            }
        }
        // Castle
        if(team){
            if(firstMove)
            if(board.getSquare(0,7).getPiece() != null){
                if(board.getSquare(0,7).getPiece().getName() == "rock"){
                    if(board.getSquare(1,7).getPiece() == null && !board.getSquare(1,7).attacked &&
                            board.getSquare(2,7).getPiece() == null && !board.getSquare(2,7).attacked &&
                            !board.getSquare(3,7).attacked)
                    if(firstMove && board.getSquare(0,7).getPiece().firstMove){
                        moves.add(board.getSquare(1, 7));
                    }
                }
            }
            if(board.getSquare(7,7).getPiece() != null){
                if(board.getSquare(7,7).getPiece().getName() == "rock"){
                    if(board.getSquare(5,7).getPiece() == null && !board.getSquare(5,7).attacked &&
                            board.getSquare(4,7).getPiece() == null && !board.getSquare(4,7).attacked &&
                            !board.getSquare(3,7).attacked)
                    if(firstMove && board.getSquare(7,7).getPiece().firstMove){
                        moves.add(board.getSquare(5, 7));
                    }
                }
            }
        } else{
            if(firstMove)
                if(board.getSquare(0,0).getPiece() != null){
                    if(board.getSquare(0,0).getPiece().getName() == "rock"){
                        if(firstMove && board.getSquare(0,0).getPiece().firstMove){
                            moves.add(board.getSquare(1, 0));
                        }
                    }
                }
            if(board.getSquare(7,7).getPiece() != null){
                if(board.getSquare(7,7).getPiece().getName() == "rock"){
                    if(firstMove && board.getSquare(7,0).getPiece().firstMove){
                        moves.add(board.getSquare(5, 0));
                    }
                }
            }
        }

    }

    private ImageIcon imageResize(ImageIcon imageIcon, int newW, int newH) {
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(newW, newH, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        imageIcon = new ImageIcon(newimg);  // transform it back
        return imageIcon;


    }


    public boolean checkMoves(Square newSquare){
        for (int i = 0; i < moves.size(); i++) {
            if(board.getSquare(moves.get(i).getCoordinatesX() , moves.get(i).getCoordinatesY()) == newSquare){
                return true;
            }
        }
        return false;
    }
    public ArrayList getMoves(){
        return moves;
    }
}
