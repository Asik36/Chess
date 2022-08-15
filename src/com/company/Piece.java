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
    private Piece tmpPiece;
    private Point point0;
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
        setImageIcon(name, team);
        // add into team
        if(team){
            board.whitePieces.add(this);
        } else {
            board.blackPieces.add(this);

        }





    }

    private void setImageIcon(String name, Boolean team) {
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
        /*if(getName() != "pawn"){
            for (int i = 0; i < moves.size(); i++) {
                moves.get(i).setAttacked(true);
            }
        }*/
    }




    public void showMoveSet(){
        for (int i = 0; i < moves.size(); i++) {
            board.getSquare(moves.get(i).getCoordinatesX() , moves.get(i).getCoordinatesY()).setBackground(new Color(129, 129, 136));
        }
    }


    public void move(Square newPos) {

        Square playedPoint = newPos;

        // I hate en passant...
        if (getName() == "pawn") {
            if (point != null) {
                if (playedPoint.getCoordinatesX() == point.getCoordinatesX() || playedPoint.getCoordinatesY() == point.getCoordinatesY()) {
                    if (team) {
                        board.getSquare(newPos.getCoordinatesX(), newPos.getCoordinatesY() + 1).getPiece().delete();
                    } else {
                        board.getSquare(newPos.getCoordinatesX(), newPos.getCoordinatesY() - 1).getPiece().delete();
                    }

                }
            }
            if ((playedPoint.getCoordinatesX() == x && playedPoint.getCoordinatesY() == y + 2) || (playedPoint.getCoordinatesX() == x && playedPoint.getCoordinatesY() == y - 2)) {
                enPassant = true;
            }
        }

        if (getName() == "king") {
                if (playedPoint.getCoordinatesX() == x - 2) {
                    board.getSquare(0, playedPoint.getCoordinatesY()).getPiece().move(board.getSquare(2, playedPoint.getCoordinatesY()));
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


        if(name == "pawn"){
            if(team){
                if(y == 0){
                    remove();
                    this.name = "queen";
                    setImageIcon(name,team);
                }
            }
            else{
                if(y == 7){
                    remove();
                    this.name = "queen";
                    setImageIcon(name,team);
                }
            }
        }
    }

    public void invisibleMove(Square newPos){
        tmpPiece = newPos.getPiece(); // save Square contain
        if (tmpPiece != null){
            if(tmpPiece.getTeam()){
                board.whitePieces.remove(tmpPiece);
            } else{
                board.blackPieces.remove(tmpPiece);
            }
        }

        newPos.setPiece(this); // put this Piece
        this.getSquare().setPiece(null);

        point0 = new Point(x,y);

        this.x = newPos.getCoordinatesX();
        this.y = newPos.getCoordinatesY();

    }
    public void invisibleMoveBack(){
        this.getSquare().setPiece(tmpPiece);
        if (tmpPiece != null){
            if(tmpPiece.getTeam()){
                board.whitePieces.add(tmpPiece);
            } else{
                board.blackPieces.add(tmpPiece);
            }
        }

        board.getSquare(point0.x,point0.y).setPiece(this);
        x = point0.x;
        y = point0.y;



    }




    public void remove() {
        board.getSquare(x , y).removeAll();
        board.getSquare(x, y).setPiece(null);
        board.getSquare(x, y).updateUI();

    }
    public void delete() {
        if(getTeam()){
            board.whitePieces.remove(this);
        } else {
            board.blackPieces.remove(this);

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
                if(y == 6) {
                    if (board.getSquare(x, y - 2).getPiece() == null) {
                        // Two forward
                        moves.add(board.getSquare(x, y - 2));


                    }
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
                if (y == 1){

            if (board.getSquare(x, y + 2).getPiece() == null) {
                // Two forward
                moves.add(board.getSquare(x, y + 2));


            }
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
        // Castle
        if(team){
            if(firstMove)
            if(board.getSquare(0,7).getPiece() != null){
                if(board.getSquare(0,7).getPiece().getName() == "rock"){
                    if(board.getSquare(1,7).getPiece() == null &&
                            board.getSquare(2,7).getPiece() == null)
                    if(firstMove && board.getSquare(0,7).getPiece().firstMove){
                        moves.add(board.getSquare(1, 7));
                    }
                }
            }
            if(board.getSquare(7,7).getPiece() != null){
                if(board.getSquare(7,7).getPiece().getName() == "rock"){
                    if(board.getSquare(5,7).getPiece() == null &&
                            board.getSquare(4,7).getPiece() == null)
                    if(firstMove && board.getSquare(7,7).getPiece().firstMove){
                        moves.add(board.getSquare(5, 7));
                    }
                }
            }
        } else{
            if(firstMove)
                if(board.getSquare(0,0).getPiece() != null && this.x == 4 && this.y == 1){
                    if(board.getSquare(0,0).getPiece().getName() == "rock"){
                        if(firstMove && board.getSquare(0,0).getPiece().firstMove){
                            moves.add(board.getSquare(1, 0));
                        }
                    }
                }
            if(board.getSquare(7,7).getPiece() == null && this.x == 4 && this.y == 7){
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

    public void colorMoves(Color color){
        for (int i = 0; i < moves.size(); i++) {
            moves.get(i).setBackground(color);
        }
    }

    public boolean checkMoves(Square newSquare){
        for (int i = 0; i < moves.size(); i++) {
            if(board.getSquare(moves.get(i).getCoordinatesX() , moves.get(i).getCoordinatesY()) == newSquare){
                return true;
            }
        }
        return false;
    }
    public ArrayList<Square> getMoves(){
        return moves;
    }
}
