package assignment4Game;

import java.io.*;

public class Game {
	
	public static int play(InputStreamReader input){
		BufferedReader keyboard = new BufferedReader(input);
		Configuration c = new Configuration();
		int columnPlayed = 3; int player;
		
		// first move for player 1 (played by computer) : in the middle of the grid
		c.addDisk(firstMovePlayer1(), 1);
		int nbTurn = 1;
		
		while (nbTurn < 42){ // maximum of turns allowed by the size of the grid
			player = nbTurn %2 + 1;
			if (player == 2){
				columnPlayed = getNextMove(keyboard, c, 2);
			}
			if (player == 1){
				columnPlayed = movePlayer1(columnPlayed, c);
			}
			System.out.println(columnPlayed);
			c.addDisk(columnPlayed, player);
			if (c.isWinning(columnPlayed, player)){
				c.print();
				System.out.println("Congrats to player " + player + " !");
				return(player);
			}
			nbTurn++;
		}
		return -1;
	}
	
	public static int getNextMove(BufferedReader keyboard, Configuration c, int player){
		System.out.println("Please enter the column you want to put disk in~");
		c.print();
		try 
		{
			int n = Integer.parseInt(keyboard.readLine());
			while(c.available[n]==6)
			{
				System.out.println("Already full, please choose again~");
				n = Integer.parseInt(keyboard.readLine());
			}
			return n;
		} 
		catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0; 
	}
	
	public static int firstMovePlayer1 (){
		return 3;
	}
	
	public static int movePlayer1 (int columnPlayed2, Configuration c){
		if(c.canWinNextRound(1)!=-1) { 
			return c.canWinNextRound(1);}
		else if(c.canWinTwoTurns(1)!=-1)
			return c.canWinTwoTurns(1);  
		else
		{                                                 
			if(c.available[columnPlayed2]!=6)
				return columnPlayed2;
			else 
			{
				int arr[]= {-1, 1, -2, 2, -3, 3, -4, 4, -5, 5, -6, 6};
				int l=0;
				for(int i=0; i<arr.length; i++)
				{
					l = arr[i];                                           
					if(((columnPlayed2+l)>=0)&&((columnPlayed2+l)<=6))
					{
						if(c.available[columnPlayed2+l]!=6)
							return columnPlayed2+l;
					}
				}
			}
		}
		return 0; 
	}
	
}
