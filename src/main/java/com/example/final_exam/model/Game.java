package com.example.final_exam.model;

public enum Game {
    BACCARAT("baccarat"),
    POKER("poker"),
    BLACKJACK("blackjack"),
    CANASTA("canasta"),
    CRIBBAGE("cribbage"),
    FARO("faro"),
    MONTE("monte"),
    RUMMY("rummy"),
    WHIST("whist");
   /* BACCARATDEMO("baccarat-demo"),
    POKERDEMO("poker-demo"),
    BLACKJACKDEMO("blackjack-demo"),
    CANASTADEMO("canasta-demo"),
    CRIBBAGEDEMO("cribbage-demo"),
    FARODEMO("faro-demo"),
    MONTEDEMO("monte-demo"),
    RUMMYDEMO("rummy-demo"),
    WHISTDEMO("whist-demo");*/

    private final String game;

    Game(String game) {
        this.game = game;
    }
}
