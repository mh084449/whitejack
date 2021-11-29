package blackjack;

import java.util.Scanner;

public class BlackJack {

    static Game game = new Game();

    public static void main(String[] args) {

        GUI gui = new GUI();

        Scanner input = new Scanner(System.in);
        String name;
        int score;
        Player player;
        int num;

        for(int i = 0; i < 4; ++i){
            num = i+1;
            if(i == 3){
                System.out.println("Enter Player " + num + " (Dealer) Name:");
            }
            else {
                System.out.println("Enter Player " + num + " Name:");
            }
            name = input.next();
            game.spawnPlayer(name, i);
            player = game.getPlayer()[i];
            score = player.getScore();
            game.updateScore(score);
        }

        System.out.println();

        gui.runGUI( game.getCard(), game.getPlayer()[0].getCard(), game.getPlayer()[1].getCard(),
                game.getPlayer()[2].getCard(), game.getPlayer()[3].getCard() );



        Card tmpCard;

        for(int i = 0; i < 3; ++i){

            player = game.getPlayer()[i];
            score = player.getScore();

            System.out.println(player.getName() + "'s Score: " + score);
            System.out.println("1) Hit");
            System.out.println("2) Stand");
            System.out.println("--------------------");

            num = input.nextInt();

            while(!(num == 1 || num == 2)){
                System.out.println();
                System.out.println("1) Hit");
                System.out.println("2) Stand");
                System.out.println("--------------------");

                num = input.nextInt();
            }

            while(num != 2){
                score = player.getScore();
                tmpCard = game.drawCard();
                score += tmpCard.getValue();

                gui.updatePlayerHand(tmpCard, i);

                System.out.println();
                System.out.println(player.getName() + "'s New Score: " + score);

                if(score == 21){
                    player.setBlackjack(true);
                    System.out.println();
                    System.out.println("GAME STATE: " + player.getName() + " Won!!!");
                    System.exit(0);
                }
                else if(score > 21){
                    System.out.println();
                    System.out.println(player.getName() + " Lost!");
                    System.out.println();
                    player.setLost(true);
                    break;
                }

                player.setScore(score);
                game.updateScore(score);

                System.out.println();
                System.out.println("1) Hit");
                System.out.println("2) Stand");
                System.out.println("--------------------");

                num = input.nextInt();

                while(!(num == 1 || num == 2)){
                    System.out.println();
                    System.out.println("1) Hit");
                    System.out.println("2) Stand");
                    System.out.println("--------------------");

                    num = input.nextInt();
                }
            }

            if(num == 2){
                System.out.println();
            }
        }

        player = game.getPlayer()[3];
        int highScore = game.getHighScore();
        int cnt = 0;
        int index = -1;

        score = player.getScore();
        System.out.println("Highest Score: " + highScore);
        System.out.println("Dealer Score: " + score);
        System.out.println();

        while(!(player.getScore() > highScore)){

            score = player.getScore();
            tmpCard = game.drawCard();
            score += tmpCard.getValue();
            player.setScore(score);

            gui.updateDealerHand(tmpCard, game.getCard());

            System.out.println("Dealer New Score: " + score);

            if(score == 21){
                player.setBlackjack(true);
                System.out.println();
                System.out.println("GAME STATE: " + player.getName() + " (Dealer) Won!!!");
                System.exit(0);
            }
            else if(score > 21){
                System.out.println();
                System.out.println(player.getName() + " (Dealer) Lost!");
                System.out.println();
                player.setLost(true);
                for(int i = 0; i < 3; ++i){
                     score = game.getPlayer()[i].getScore();
                     if(score == highScore){
                         cnt++;
                         index = i;
                     }
                }
                if(cnt == 1){
                    System.out.println(game.getPlayer()[index].getName() + " Won!!!");
                }
                else{
                    System.out.println("GAME STATE: A PUSH!");
                }
                System.exit(0);
            }

        }

        System.out.println("GAME STATE: " + player.getName() + " (Dealer) Won!!!");
        System.exit(0);
    }
}