package com.xaral;

import com.xaral.BlackJack.BlackJack;

public class Main {
    public static void main(String[] args) {
        while (true)
            new BlackJack(10).playPlayer();
    }
}