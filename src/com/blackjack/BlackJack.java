package com.blackjack;

import java.util.Scanner;

public class BlackJack {
    public static void main(String[] args) {
        System.out.println("Добро пожаловать в БлэкДжек!");
        Deck playingDeck = new Deck();
        playingDeck.createFullDeck();
        playingDeck.shuffle();

        Deck playerDeck = new Deck();
        Deck dealerDeck = new Deck();

        double playerMoney = 100.00;

        Scanner userInput = new Scanner(System.in);

        while (playerMoney > 0){
            System.out.println("У вас есть $" + playerMoney + " долларов. На какую сумму вы бы хотели сыграть?");

            double playerBet = userInput.nextDouble();
            if(playerBet > playerMoney){
                System.out.println("Нельзя ставить больше, чем имеешь. На сегодня игра для вас закончена. Досвидания");
                break;
            }

            boolean endRound = false;

            playerDeck.draw(playingDeck);
            playerDeck.draw(playingDeck);

            dealerDeck.draw(playingDeck);
            dealerDeck.draw(playingDeck);

            while (true){
                System.out.println("Ваши карты:");
                System.out.println(playerDeck.toString());
                System.out.println("Ваши очки: " + playerDeck.cardsValue());

                System.out.println("\nКарты дилера: " + dealerDeck.getCard(0).toString() + " и [Неизвестная карта]");

                System.out.println("Вы хотите (1)взять карту или (2)остаться при своем?");
                int response = userInput.nextInt();
                if(response == 1){
                    playerDeck.draw(playerDeck);
                    System.out.println("Ваша карта: " + playerDeck.getCard(playerDeck.deckSize() - 1).toString());

                    if(playerDeck.cardsValue() > 21){
                        System.out.println("Перебор. Ваши очки - " + playerDeck.cardsValue());
                        playerMoney -= playerBet;
                        endRound = true;
                        break;
                    }
                }
                else{
                    break;
                }
            }

            System.out.println("Карты дилера - " + dealerDeck.toString());

            if((dealerDeck.cardsValue() > playerDeck.cardsValue()) && (!endRound)){
                System.out.println("Дилер выиграл!");
                playerMoney -= playerBet;
                endRound = true;
            }

            while ((dealerDeck.cardsValue() < 17) && !endRound){
                dealerDeck.draw(playingDeck);
                System.out.println("Карта дилера: " + dealerDeck.getCard(dealerDeck.deckSize() - 1).toString());
            }

            System.out.println("Очки дилера - " + dealerDeck.cardsValue());

            if((dealerDeck.cardsValue() > 21) && !endRound){
                System.out.println("Дилер проиграл. Поздравляю!");
                playerMoney += playerBet;
                endRound = true;
            }

//            if((playerDeck.cardsValue() == dealerDeck.cardsValue()) && endRound == false){
//                System.out.println("Ничья");
//                endRound = true;
//            }

            if((playerDeck.cardsValue() > dealerDeck.cardsValue()) && !endRound){
                System.out.println("Вы победили!");
                playerMoney += playerBet;
                endRound = true;
            }
            else if (!endRound){
                System.out.println("Вы проиграли");
                playerMoney -= playerBet;
                endRound = true;
            }

            playerDeck.moveAllToDeck(playingDeck);
            dealerDeck.moveAllToDeck(playingDeck);
            System.out.println("Конец игры");
        }
        System.out.println("Конец игры! Вы проиграли все деньги. :(");
    }
}
