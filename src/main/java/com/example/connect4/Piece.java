package com.example.connect4;

public class Piece {
    public enum PieceType {
        EMPTY,
        RED,
        YELLOW;
    }
    private final PieceType type;

    public Piece(PieceType type) {
        this.type = type;
    }

    public final PieceType getType() {
        return type;
    }
}
