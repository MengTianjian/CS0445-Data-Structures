import java.io.*;
import java.util.*;

public class Assig3_diagonally
{
    public static void main(String [] args)
	{
        Scanner inScan = new Scanner(System.in);
        char[][] theBoard = readFile(inScan);

        System.out.println();
        System.out.println("Please enter phrase (sep. by single spaces):");
        String phrase = (inScan.nextLine()).toLowerCase();
		while (!(phrase.equals("")))
		{
            String[] words = phrase.split(" ");
            System.out.println("Looking for: "+phrase);
            System.out.println("Containing "+words.length+" words");

            searchPhrase(theBoard, words);

            System.out.println("Please enter phrase (sep. by single spaces):");
        	phrase = (inScan.nextLine()).toLowerCase();
        }
	}

    private static char[][] readFile(Scanner inScan)
    {
		Scanner fReader;
		File fName;
        String fString = "";

       	// Make sure the file name is valid
        while (true)
        {
           try
           {
               System.out.println("Please enter grid filename:");
               fString = inScan.nextLine();
               fName = new File(fString);
               fReader = new Scanner(fName);

               break;
           }
           catch (IOException e)
           {
               System.out.println("Problem " + e);
           }
        }

		// Parse input file to create 2-d grid of characters
		String [] dims = (fReader.nextLine()).split(" ");
		int rows = Integer.parseInt(dims[0]);
		int cols = Integer.parseInt(dims[1]);

		char [][] theBoard = new char[rows][cols];

		for (int i = 0; i < rows; i++)
		{
			String rowString = fReader.nextLine();
			for (int j = 0; j < rowString.length(); j++)
			{
				theBoard[i][j] = Character.toLowerCase(rowString.charAt(j));
			}
		}

		// Show user the grid
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				System.out.print(theBoard[i][j] + " ");
			}
			System.out.println();
		}

        return theBoard;
    }

    private static void searchPhrase(char[][] theBoard, String[] words)
    {
        int rows = theBoard.length;
        int cols = theBoard[0].length;
        Stack<ArrayList> log = new Stack<ArrayList>();
        boolean found = false;
        int[] newCoor;
        for (int i = 0; i < rows*cols; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                newCoor = move(i/cols, i%cols, -1, j);
                if (searchWord(theBoard, words, 0, newCoor[0], newCoor[1], log, j))
                {
                    found = true;
                    break;
                }
            }
            if (found)
            {
                break;
            }
        }
        if (found)
        {
            char[][] newBoard = new char[rows][cols];
            for (int i = 0; i < rows; i++)
    		{
    			for (int j = 0; j < cols; j++)
    			{
    				newBoard[i][j] = theBoard[i][j];
    			}
    		}
            String[] result = new String[words.length];
            ArrayList<Integer> coor;
            for (int i = words.length-1; i >= 0; i--)
            {
                for (int j = 0; j < words[i].length(); j++)
                {
                    coor = log.pop();
                    if (j == 0)
                    {
                        result[i] = ") to ("+coor.get(0)+","+coor.get(1)+")";
                    }
                    if (j == words[i].length()-1)
                    {
                        result[i] = words[i]+": ("+coor.get(0)+","+coor.get(1)+result[i];
                    }
                    newBoard[coor.get(0)][coor.get(1)] = Character.toUpperCase(newBoard[coor.get(0)][coor.get(1)]);
                }
            }
            System.out.println("was found:");
            for (int i = 0; i < words.length; i++)
            {
                System.out.println(result[i]);
            }
            for (int i = 0; i < rows; i++)
    		{
    			for (int j = 0; j < cols; j++)
    			{
    				System.out.print(newBoard[i][j] + " ");
    			}
    			System.out.println();
    		}
            System.out.println();
        }
        else
        {
            System.out.println("was not found");
            System.out.println();
        }
    }

    private static boolean searchWord(char[][] theBoard, String[] words, int index, int r, int c, Stack<ArrayList> log, int direction)
    {
        if (index == words.length)
        {
            return true;
        }
        int rows = theBoard.length;
        int cols = theBoard[0].length;
        boolean found = true;
        ArrayList<Integer> coor;
        int[] newCoor;
        for (int i = 0; i < words[index].length(); i++)
        {
            coor = new ArrayList<Integer>();
            newCoor = move(r, c, i+1, direction);
            coor.add(newCoor[0]);
            coor.add(newCoor[1]);
            if (newCoor[1] == -1 || newCoor[1] == cols || newCoor[0] == -1 || newCoor[0] == rows || log.contains(coor) || words[index].charAt(i) != theBoard[newCoor[0]][newCoor[1]])
            {
                for (int j = 0; j < i; j++)
                {
                    log.pop();
                }
                found = false;
                break;
            }
            log.push(coor);
        }
        if (found)
        {
            coor = log.peek();
            for (int i = 0; i < 4; i++)
            {
                if (i+2 != direction && i-2 != direction)
                {
                    if (searchWord(theBoard, words, index+1, coor.get(0), coor.get(1), log, i))
                    {
                        return true;
                    }
                }
            }
            for (int i = 4; i < 8; i++)
            {
                if (searchWord(theBoard, words, index+1, coor.get(0), coor.get(1), log, i))
                {
                    return true;
                }
            }
            for (int i = 0; i < words[index].length(); i++)
            {
                log.pop();
            }
        }
        return false;
    }

    private static int[] move(int r, int c, int step, int direction)
    {
        int[] result = {0, 0};
        if (direction == 0)
        {
            result[0] = r;
            result[1] = c+step;
        }
        else if (direction == 1)
        {
            result[0] = r+step;
            result[1] = c;
        }
        else if (direction == 2)
        {
            result[0] = r;
            result[1] = c-step;
        }
        else if (direction == 3)
        {
            result[0] = r-step;
            result[1] = c;
        }
        else if (direction == 4)
        {
            result[0] = r+step;
            result[1] = c+step;
        }
        else if (direction == 5)
        {
            result[0] = r+step;
            result[1] = c-step;
        }
        else if (direction == 6)
        {
            result[0] = r-step;
            result[1] = c+step;
        }
        else if (direction == 7)
        {
            result[0] = r-step;
            result[1] = c-step;
        }
        return result;
    }
}
