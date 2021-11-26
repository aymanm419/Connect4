package com.example.connect4;

import java.util.Objects;

public class Piece {
    @Override
    public String toString() {
        return "Piece{" +
                "type=" + type +
                '}';
    }

    private final PieceType type;

    public Piece(PieceType type) {
        this.type = type;
    }

    public final PieceType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return type == piece.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    public enum PieceType {
        RED,
        YELLOW,
        WHITE
    }
}
