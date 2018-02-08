import java.util.*;

public class Snap
{
    public static void main(String [] args)
    {
        if (args.length != 2)
        {
            System.out.println("Usage: java Main <# rounds> <# players>");
            return;
        }
        System.out.println();
        System.out.println("Welcome to the Game of Snap!");
        System.out.println();
        System.out.println("Now dealing the cards to the players...");
        System.out.println();
        int rounds = Integer.parseInt(args[0]);
        int numOfPlayers = Integer.parseInt(args[1]);
        if (numOfPlayers < 2 || numOfPlayers > 7)
        {
            System.out.println("Usage: java Main <# rounds> <# players between 2 and 7>");
            return;
        }
        MultiDS<Card> deck = new MultiDS<Card>(52);
        for (Card.Ranks rankOfCards : Card.Ranks.values())
        {
            for (Card.Suits suitOfCards : Card.Suits.values())
            {
                Card newCard = new Card(suitOfCards, rankOfCards);
                //System.out.println(newCard.toString());
                deck.addItem(newCard);
            }
        }
        deck.shuffle();
        //System.out.println(deck.toString());
        //MultiDS<Card>[] playerFaceDown = new MultiDS<Card>[numOfPlayers];
        List<MultiDS<Card>> playerFaceDown = new ArrayList();
        List<MultiDS<Card>> playerFaceUp = new ArrayList();
        for (int i = 0; i < numOfPlayers; i++)
        {
            //MultiDS<Card> newPlayer = new MultiDS<Card>(52);
            playerFaceDown.add(new MultiDS<Card>(52));
            playerFaceUp.add(new MultiDS<Card>(52));
        }
        playerFaceUp.add(new MultiDS<Card>(52));
        //MultiDS<Card> snapPool = new MultiDS<Card>(52);
        for (int i = 0; i < 52/numOfPlayers; i++)
        {
            for (int j = 0; j < numOfPlayers; j++)
            {
                playerFaceDown.get(j).addItem(deck.removeItem());
            }
        }
        while (!deck.empty())
        {
            playerFaceUp.get(numOfPlayers).addItem(deck.removeItem());
            //snapPool.addItem(deck.removeItem());
        }
        for (int i = 0; i < numOfPlayers; i++)
        {
            System.out.println("Here is Player "+i+"'s face-down pile:");
            System.out.println("Contents");
            System.out.println(playerFaceDown.get(i).toString());
            System.out.println();
        }
        System.out.println("Here is the Snap Pool:");
        System.out.println("Contents");
        //System.out.println(snapPool.toString());
        System.out.println(playerFaceUp.get(numOfPlayers).toString());
        System.out.println();
        System.out.println("Starting the game!");
        System.out.println();
        int endRound = 0;
        for (int k = 0; k < rounds; k++)
        {
            List<Card> faceUp = new ArrayList();
            for (int i = 0; i < numOfPlayers; i++)
            {
                if (playerFaceDown.get(i).empty())
                {
                    playerFaceUp.get(i).shuffle();
                    while (!playerFaceUp.get(i).empty())
                    {
                        playerFaceDown.get(i).addItem(playerFaceUp.get(i).removeItem());
                    }
                }
                faceUp.add(playerFaceDown.get(i).top());
                playerFaceUp.get(i).addItem(playerFaceDown.get(i).removeItem());
                //System.out.println(playerFaceUp.get(j).toString());
                //System.out.println(playerFaceDown.get(j).toString());
                //faceUp.add(playerFaceUp.get(i).top());
            }
            if (!playerFaceUp.get(numOfPlayers).empty())
            {
                faceUp.add(playerFaceUp.get(numOfPlayers).top());
            }
            List<Integer> matchingPile = new ArrayList();
            String matchingMessage = "";
            for (int i = 0; i < faceUp.size()-1; i++)
            {
                for (int j = i+1; j < faceUp.size(); j++)
                {
                    if (faceUp.get(i).compareTo(faceUp.get(j)) == 0)
                    {
                        matchingMessage = matchingMessage+"\t"+faceUp.get(i).toString()+" of Player "+i+" ("+playerFaceUp.get(i).size()+" cards) matches ";
                        if (j == numOfPlayers)
                        {
                            matchingMessage = matchingMessage+faceUp.get(j).toString()+" of Snap Pool ("+playerFaceUp.get(j).size()+" cards)\n";
                        }
                        else
                        {
                            matchingMessage = matchingMessage+faceUp.get(j).toString()+" of Player "+j+" ("+playerFaceUp.get(j).size()+" cards)\n";
                        }
                        if (!matchingPile.contains(i))
                        {
                            matchingPile.add(i);
                        }
                        if (!matchingPile.contains(j))
                        {
                            matchingPile.add(j);
                        }
                    }
                }
            }
            List<Integer> shoutedPlayer = new ArrayList();
            if (matchingPile.size() != 0)
            {
                //System.out.println("Rnd #"+k+": Match!");
                for (int i = 0; i < numOfPlayers; i++)
                {
                    if (shout(i, 40))
                    {
                        shoutedPlayer.add(i);
                    }
                }
                if (shoutedPlayer.size() != 1)
                {
                    System.out.println("Rnd #"+k+": Match but no winner! "+shoutedPlayer.size()+" players shouted!");
                }
                else
                {
                    System.out.println("Rnd #"+k+": Match! Player "+shoutedPlayer.get(0)+" shouts alone.");
                    System.out.print(matchingMessage);
                    for (int i = 0; i < matchingPile.size(); i++)
                    {
                        playerFaceUp.get(i).shuffle();
                        while (!playerFaceUp.get(i).empty())
                        {
                            playerFaceDown.get(shoutedPlayer.get(0)).addItem(playerFaceUp.get(i).removeItem());
                        }
                    }
                    playerFaceUp.get(numOfPlayers).shuffle();
                    while (!playerFaceUp.get(numOfPlayers).empty())
                    {
                        playerFaceDown.get(shoutedPlayer.get(0)).addItem(playerFaceUp.get(numOfPlayers).removeItem());
                    }
                }
            }
            else
            {
                System.out.println("Rnd #"+k+": No Match!");
                for (int i = 0; i < numOfPlayers; i++)
                {
                    if (shout(i, 1))
                    {
                        shoutedPlayer.add(i);
                    }
                }
                for (int i = 0; i < shoutedPlayer.size(); i++)
                {
                    System.out.println("\tPlayer "+i+" incorrectly shouts Snap!");
                    System.out.println("\t"+playerFaceUp.get(i).size()+" cards moved to the Snap Pool.");
                    playerFaceUp.get(i).shuffle();
                    while (!playerFaceUp.get(i).empty())
                    {
                        playerFaceUp.get(numOfPlayers).addItem(playerFaceUp.get(i).removeItem());
                    }
                }
            }
            for (int i = 0; i < numOfPlayers; i++)
            {
                //System.out.println(faceUp.get(i).toString());
                if (playerFaceUp.get(i).empty() && playerFaceDown.get(i).empty())
                {
                    System.out.println();
                    System.out.println("Player "+i+" ran out of cards!");
                    endRound = k+1;
                }
            }
            if (endRound != 0)
            {
                break;
            }
        }
        if (endRound == 0)
        {
            endRound = rounds;
        }
        System.out.println();
        System.out.println("After "+endRound+" rounds:");
        List<Integer> winningPlayer = new ArrayList();
        int winningScore = 0;
        for (int i = 0; i < numOfPlayers; i++)
        {
            int total = playerFaceUp.get(i).size()+playerFaceDown.get(i).size();
            System.out.println("\tPlayer "+i+" ends with "+total+" cards");
            if (total > winningScore)
            {
                winningScore = total;
                winningPlayer.clear();
                winningPlayer.add(i);
            }
            else if (total == winningScore)
            {
                winningPlayer.add(i);
            }
        }
        System.out.println("\tSnap Pool ends with "+playerFaceUp.get(numOfPlayers).size()+" cards");
        if (winningPlayer.size() == 1)
        {
            System.out.println("\tPlayer "+winningPlayer.get(0)+" wins with "+winningScore+" cards");
        }
        else
        {
            System.out.print("\tA tie!");
            for (int i = 0; i < winningPlayer.size(); i++)
            {
                System.out.print(" Player "+i);
            }
            System.out.print(" all have "+winningScore+" cards\n");
        }
    }

    public static boolean shout(int player, int prob)
    {
        Random rand = new Random();
        return (rand.nextInt(100) < prob);
    }
}
