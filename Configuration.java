package assignment4Game;

public class Configuration {
	
	public int[][] board;
	public int[] available;
	boolean spaceLeft;
	
	public Configuration(){
		board = new int[7][6];
		available = new int[7];
		spaceLeft = true;
	}
	
	public void print(){
		System.out.println("| 0 | 1 | 2 | 3 | 4 | 5 | 6 |");
		System.out.println("+---+---+---+---+---+---+---+");
		for (int i = 0; i < 6; i++){
			System.out.print("|");
			for (int j = 0; j < 7; j++){
				if (board[j][5-i] == 0){
					System.out.print("   |");
				}
				else{
					System.out.print(" "+ board[j][5-i]+" |");
				}
			}
			System.out.println();
		}
	}
	
	public void addDisk (int index, int player){
		this.board[index][this.available[index]] = player;
		this.available[index]++;
	}
	
	public boolean isWinning (int lastColumnPlayed, int player){
		int rowP = this.available[lastColumnPlayed]-1;
		if ((checkVertical(lastColumnPlayed, player, rowP)==true)||(checkHorizontal(lastColumnPlayed, player, rowP)==true)||(checkDiagonalR(lastColumnPlayed, player, rowP)==true)||(checkDiagonalL(lastColumnPlayed, player, rowP)==true))
			return true;
		else
			return false; 
	}
	
	public int canWinNextRound (int player){
		int arr[] = new int[7]; int p = 0;
		for (int i=0; i<arr.length; i++)
			arr[i] = 1000;
		
		for (int i=0; i<7; i++)
		{
			int rowP = this.available[i];  
			if(rowP !=6)
				this.addDisk(i, player);
			else
				continue;
			if((checkVertical(i, player, rowP)==true)||(checkHorizontal(i, player, rowP)==true)||(checkDiagonalR(i, player, rowP)==true)||(checkDiagonalL(i, player, rowP)==true))
			{
				arr[p] = i;
				p++;
			}
			this.board[i][this.available[i]-1] = 0;
			this.available[i]--;
		}
		if(isEmpty(arr))
			return -1; 
		else
			return findSmallest(arr);		
	}
	
	public int canWinTwoTurns (int player){	
		int player2; 
		if(player==1)
			player2 = 2;
		else
			player2 = 1;
		
		for (int i=0; i<7; i++)
		{
			int counter = 0;
			int n = this.available[i];
			if(n<=4)
				this.addDisk(i, player);                                   
			else
				continue;
			if(canWinNextRound(player2)==-1)
			{                                                     
				if(n<=3)
				{
					this.addDisk(i, player2);this.addDisk(i, player);      
					if((checkVertical(i, player, this.available[i]-1)==true)||(checkHorizontal(i, player, this.available[i]-1)==true)||(checkDiagonalR(i, player, this.available[i]-1)==true)||(checkDiagonalL(i, player, this.available[i]-1)==true))
					{
						counter++;
					}
					this.board[i][this.available[i]-1] = 0;
					this.available[i]--;
					this.board[i][this.available[i]-1] = 0;
					this.available[i]--;                              
				}
				else if(n==4)
				{
					this.addDisk(i, player2);     
					if((checkVertical(i, player, this.available[i]-1)==true)||(checkHorizontal(i, player, this.available[i]-1)==true)||(checkDiagonalR(i, player, this.available[i]-1)==true)||(checkDiagonalL(i, player, this.available[i]-1)==true))
					{
						counter++;
					}
					this.board[i][this.available[i]-1] = 0;
					this.available[i]--;                            
				}
				
				this.addDisk(i, player);                       
				if((checkVertical(i, player, this.available[i]-1)==true)||(checkHorizontal(i, player, this.available[i]-1)==true)||(checkDiagonalR(i, player, this.available[i]-1)==true)||(checkDiagonalL(i, player, this.available[i]-1)==true))
				{
					counter++;
				}
				this.board[i][this.available[i]-1] = 0;
				this.available[i]--;                           
			}
			this.board[i][this.available[i]-1] = 0;
			this.available[i]--;  
			
			if(counter==2) return i;           
		}
		return -1; 
	}
	
	public boolean checkVertical (int c, int player, int r)
	{
		int arr[] = new int[4];
		for (int i=0; i<arr.length; i++)
			arr[i] = 1000;
		
		int i=0;
		for (int j=r; j>=0; j--)
		{
			if(board[c][j]==player)
			{
				arr[i] = j;
				i++;
			}
			else
			{
				if(exactly4(arr)==true)
					return true;
				else
					return false;
			}
		}
		if(exactly4(arr)==true)
			return true;
		else
			return false;
	}
	
	public boolean checkHorizontal (int c, int player, int r)
	{
		int arr[] = new int[7];
		for (int i=0; i<arr.length; i++)
			arr[i] = 1000;
		
		int i=0;
		for (int k=0; k<7; k++)
		{
			if(board[k][r]==player)
			{
				arr[i] = k;
				i++;
			}
		}
		if(atLeast4(arr)==false)
			return false;
		else
		{
			if(haveElement(arr, c)==false)
				return false;
			if(strickBy1(sortArray(arr))==false)
				return false;
			else
				return true;	
		}
	}
	
	public boolean checkDiagonalR (int c, int player, int r)
	{
		int arr[] = new int[6]; int p=0;
		for (int i=0; i<arr.length; i++)
			arr[i] = 1000;
		
		for (int j=0; j<7; j++)
		{
			for (int i=0; i<6; i++)
			{
				if (((j-i)==(c-r)) && (board[j][i] == player))
				{
					arr[p] = j;
					p++;
				}
			}
		}
		if(haveElement(arr, c)==false)
			return false;
		if(strickBy1(sortArray(arr))==false)
			return false;
		else
			return true;	
	}
	
	public boolean checkDiagonalL (int c, int player, int r)
	{	
		if(((c+r)<3)||((c+r)>8))
			return false;
		
		int arr[] = new int[6]; int p=0;
		for (int i=0; i<arr.length; i++)
			arr[i] = 1000;
		for (int j=0; j<7; j++)
		{
			for (int i=0; i<6; i++)
			{
				if (((j+i)==(c+r)) && (board[j][i] == player))
				{
					arr[p] = j;
					p++;
				}
			}
		}
		if(haveElement(arr, c)==false)
			return false;
		if(strickBy1(sortArray(arr))==false)
			return false;
		else
			return true;	
	}
	
	public boolean exactly4 (int arr[])
	{
		int counter = 0;
		for (int i=0; i<arr.length; i++)
		{
			if(arr[i]!=1000)
				counter++;
		}
		if(counter==4)
			return true;
		else
			return false;
	}
	
	public boolean atLeast4 (int arr[])
	{
		int counter = 0;
		for (int i=0; i<arr.length; i++)
		{
			if(arr[i]!=1000)
				counter++;
		}
		if(counter>=4)
			return true;
		else
			return false;
	}
	
	public boolean haveElement (int arr[], int a)
	{
		for (int i=0; i<arr.length; i++)
		{
			if (arr[i]==a)
				return true;
		}
		return false;
	}
	
	public int[] sortArray (int arr[])
	{
		for (int i=arr.length-1; i>0; i--)
		{
			for (int j=0; j<i; j++)
			{
				if(arr[j]>arr[j+1])
				{
					int temp = arr[j];
					arr[j] = arr[j+1];
					arr[j+1] = temp;
				}
			}
		}
		int counter = 0;
		for (int p=0; p<arr.length; p++)
		{
			if (arr[p]!=1000)
				counter++;
		}
		int array[] = new int[counter];
		
		for (int q=0; q<counter; q++)
			array[q] = arr[q];
		
		return array;
	}
	
	public boolean strickBy1 (int arr[])
	{
		int counter = 0;
		for (int i=0; i<arr.length-1; i++)
		{
			boolean comp = (arr[i]+1)==arr[i+1];
			if(comp == true)
				counter++;
			else
				counter = 0;
			if(counter>=3)
				return true;
		}
		return false;
	}
	
	public boolean isEmpty (int arr[])
	{
		for (int i=0; i<arr.length; i++)
		{
			if(arr[i]!=1000)
				return false;
		}
		return true;
	}
	
	public int findSmallest (int arr[])
	{
		int smallest = arr[0];
		for (int i=0; i<arr.length; i++)
		{
			if(arr[i]<smallest)
				smallest = arr[i];
		}
		return smallest;
	}
}
