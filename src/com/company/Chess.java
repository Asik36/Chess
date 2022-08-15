package com.company;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Chess implements MouseListener{
    Board board;
    private Square lastSelectedPos;
    boolean whiteMove = true;
    Piece whiteKing;
    Piece blackKing;


    Chess(Board board){
        this.board = board;





    }
    public Board getBoard(){
        return board;
    }


    public void selectAndMove(MouseEvent e) {
        Square select = ((Square) e.getSource());


        // Select a piece
        if (select.getPiece() != null)
        {
            // Check if its this team turn
            if(select.getPiece().getTeam() == whiteMove)
            {
                // Check if a piece already have been selected, if so, remove the mark
                if (lastSelectedPos != null)
                {
                    board.boardSetDefault();
                }
                lastSelectedPos = select;
                lastSelectedPos.setBackground(Color.yellow);
                lastSelectedPos.getPiece().showMoveSet();
            }
        }

        // Check if piece selected
        if(lastSelectedPos != null){
            if(lastSelectedPos.getPiece() != null){
                // MOVE. or well, try at least
                if(lastSelectedPos.getPiece().checkMoves(select)) {
                    lastSelectedPos.getPiece().move(select);
                    board.boardSetDefault();
                    lastSelectedPos.setBackground(new Color(206, 236, 0));
                    select.setBackground(new Color(255, 255, 0));

                    nextMove();


                    lastSelectedPos = null;
                }

            }else if(!lastSelectedPos.getPiece().checkMoves(select)){
                board.boardSetDefault();
                lastSelectedPos = null;

            }

        }
    }

    private void nextMove() {
        board.blackPieces.forEach(Piece::updateMoveSet);
        board.whitePieces.forEach(Piece::updateMoveSet);

        if (whiteMove) {
            whiteMove = false;
            // pawn attack even if they cant move
            board.updateAttack(board.whitePieces);
            board.blackPieces.forEach(Piece::updateMoveSet);


        }
        else {
            whiteMove = true;
            // pawn attack even if they cant move
            board.updateAttack(board.blackPieces);
            board.whitePieces.forEach(Piece::updateMoveSet);

        }
        board.updateBoard();
        // checks system
        if(whiteMove) { // white
            for (int i = 0; i < board.whitePieces.size(); i++) { // for each white Piece
                ArrayList badMoves = new ArrayList<>();
                for (int j = 0; j < board.whitePieces.get(i).getMoves().size(); j++) { // for each move

                    // play it invisible
                    board.whitePieces.get(i).invisibleMove(board.whitePieces.get(i).getMoves().get(j));
                    board.blackPieces.forEach(Piece::updateMoveSet);
                    board.updateAttack(board.blackPieces);
                    board.updateBoard();


                    // check if king attacked after the invisible move, if so delete the move from the move list
                    if (whiteKing.getSquare().attacked) {
                        board.whitePieces.get(i).getMoves().get(j).setBackground(Color.red);
                        badMoves.add(board.whitePieces.get(i).getMoves().get(j));
                    }

                    // return to the original position
                    board.whitePieces.get(i).invisibleMoveBack();
                    board.blackPieces.forEach(Piece::updateMoveSet);
                    board.updateAttack(board.blackPieces);
                    board.updateBoard();


                }
                for (int q = 0; q < badMoves.size(); q++) {
                    board.whitePieces.get(i).getMoves().remove(badMoves.get(q));
                }

            }
            for (int i = 0; i < board.whitePieces.size(); i++) {
                if(!board.whitePieces.get(i).getMoves().isEmpty()){
                    break;
                }
                if(i == board.whitePieces.size()-1){
                    if(whiteKing.getSquare().attacked) {
                        System.out.println("BLACK WINNNN!!!");
                    }
                    else{
                        System.out.println("draw");
                    }
                }

            }
        }

        else{
            for (int i = 0; i < board.blackPieces.size(); i++) { // for each white Piece
                ArrayList badMoves = new ArrayList<>();
                for (int j = 0; j < board.blackPieces.get(i).getMoves().size(); j++) { // for each move

                    // play it invisible
                    board.blackPieces.get(i).invisibleMove(board.blackPieces.get(i).getMoves().get(j));
                    board.whitePieces.forEach(Piece::updateMoveSet);
                    board.updateAttack(board.whitePieces);
                    board.updateBoard();


                    // check if king attacked after the invisible move, if so delete the move from the move list
                    if (blackKing.getSquare().attacked) {
                        board.blackPieces.get(i).getMoves().get(j).setBackground(Color.red);
                        badMoves.add(board.blackPieces.get(i).getMoves().get(j));
                    }

                    // return to the original position
                    board.blackPieces.get(i).invisibleMoveBack();
                    board.whitePieces.forEach(Piece::updateMoveSet);
                    board.updateAttack(board.whitePieces);
                    board.updateBoard();


                }
                for (int q = 0; q < badMoves.size(); q++) {
                    board.blackPieces.get(i).getMoves().remove(badMoves.get(q));
                }

            }
            for (int i = 0; i < board.blackPieces.size(); i++) {
                if(!board.blackPieces.get(i).getMoves().isEmpty()){
                    break;
                }

                    if (i == board.blackPieces.size() - 1) {
                        if(blackKing.getSquare().attacked) {
                            System.out.println("WHITE WINNNN!!!");
                        }
                        else {
                            System.out.println("draw");
                    }
                }

            }
        }




    }




    public void setPieces(){

         for (int i = 0; i < 8; i++) {
            new Piece("pawn", true, i, 6);
            new Piece("pawn", false, i, 1);
        }



        new Piece("rock", true, 0, 7);
        new Piece("rock", false, 0, 0);
        new Piece("rock", false, 7, 0);
        new Piece("rock", true, 7, 7);

        new Piece("knight", true, 1, 7);
        new Piece("knight", true, 6, 7);
        new Piece("knight", false, 1, 0);
        new Piece("knight", false, 6, 0);

        new Piece("bishop", true, 2, 7);
        new Piece("bishop", true, 5, 7);
        new Piece("bishop", false, 2, 0);
        new Piece("bishop", false, 5, 0);

        new Piece("queen", true, 4, 7);
        new Piece("queen", false, 4, 0);

        blackKing = new Piece("king", false, 3, 0);
        whiteKing = new Piece("king", true, 3, 7);


        board.whitePieces.forEach(Piece::updateMoveSet);
        board.blackPieces.forEach(Piece::updateMoveSet);



    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        selectAndMove(e);
    }


    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {


    }
}
