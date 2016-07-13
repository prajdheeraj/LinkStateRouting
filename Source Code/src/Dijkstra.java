import java.awt.peer.SystemTrayPeer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


public class Dijkstra {

	static int[][] distance;
	static int[][] visited;
	static int[][] matrix;
	static int[][] pred;
	static int nextnode = 0;
	static int min;
	static int src,rows=0,cols=0;
	static int des;
	static int[] route;
	public String Display="";

	//Dijkstra constructor that intializes the Router matrix 
	Dijkstra(String Fpath) throws IOException
	{
		Scanner count = new Scanner(new File(Fpath));

		while(count.hasNextLine())
		{

			if(count.hasNextInt())
			{
				rows++;
				cols=1;
			}
			try
			{	
				String s=count.nextLine()+" ";
				Scanner count1=new Scanner(s);
				count1.useDelimiter(" ");
				while(count1.hasNext())
				{
					//matrix[i][j] = Integer.parseInt(sc1.next());

					//System.out.println(matrix[i][j]);
					Display=Display+count1.next()+" ";
					if(count1.hasNextInt())
						cols++;

				}
				Display=Display+"\n";
			}

			catch(NumberFormatException e)
			{

				continue;

			}

			//rows++;
		}
		System.out.println(Display);
		/*System.out.println(rows);
		System.out.println(cols);*/

		distance= new int[rows][cols];
		visited=new int[rows][cols];
		matrix=new int[rows][cols];
		pred=new int[rows][cols];
		route=new int[rows];
		//sc.close();

		if(rows==cols)
		{
			System.out.println("No of nodes ="+rows);
			Scanner sc = new Scanner(new File(Fpath));
			for(int i=0;i<rows&&sc.hasNextLine();i++)                  	// checks whether there is a next line.
			{
				int j=0;

				try
				{	
					String str=sc.nextLine()+" ";						// Reads the next line.
					Scanner sc1=new Scanner(str);
					sc1.useDelimiter(" ");								// The scanner class uses the delimiter space to read input vales
					while(sc1.hasNext()&&j<cols)							// checks whether there is a number.
					{
						matrix[i][j] = Integer.parseInt(sc1.next());	//Stores the values in matrix array. 
						//System.out.println(matrix[i][j]);
						visited[i][j]=0;								//Visited matrix represents the value 0 intially.  
						pred[i][j]=i;									//pred matrix is used to store the traversed(shortest) path 
						if(matrix[i][j]==-1)								
							matrix[i][j]=999;							//It intializes matrix elements with "0" to 999.
						//System.out.println(matrix[i][j]);
						j++;
					}
					System.out.println();
				}

				catch(NumberFormatException e)							//Catches the Number format exception
				{

					continue;

				}
				catch(Exception e)										// Catches any exceptions other than Number format.
				{
					e.printStackTrace();
				}

			}

			for(int i=0;i<rows;i++)
			{
				for(int j=0;j<cols;j++)
				{
					distance[i][j]=matrix[i][j];						// Duplicates the cost matrix to a distance matrix which is used to get the optimum distance between a source and destination
					if(i==j)
					{
						matrix[i][j]=0;
						distance[i][j] =0;								// Distance from same source to destination is set to 0
						visited[i][j]=1;								// Visited of same node to node is set 1.
					}
				}
			}
		}

	}

	int Dimplementation(int x,int y)
	{
		/*Scanner s=new Scanner(System.in);
		System.out.println("enter the source");
		src=s.nextInt();
		System.out.println("enter the destination");
		des=s.nextInt();*/

		src=x;
		des=y;
		if(rows!=cols)
		{
			System.out.println("Incorrect matrix format");
			return -1;
		}
		else
		{
			for(int k=0;k<rows;k++)
			{
				for(int i=0;i<rows;i++)
				{
					min=999;												// Intially sets the minimum value to largest value 999.
					for(int j=0;j<cols;j++)
					{
						if(min>distance[k][j] && visited[k][j]!=1) 			// Checks whether min is less than Distance to a particular node and visited is not equal to 0.
						{
							min=distance[k][j];								// If true min value is updated until minimum value is updated.
							nextnode=j;										// minimum distance node is assigned to nextnode.
						}
					}

					visited[k][nextnode]=1;									// Visited from source to nextnode is changed to 1.

					for(int c=0;c<rows;c++)
					{
						if(visited[k][c]!=1)								
						{
							if(min+matrix[nextnode][c]<distance[k][c])		// Checks min value and cost for nextnode is less than actual distance 
							{
								distance[k][c]=min+matrix[nextnode][c];		// Distance for that node is updated with min plus cost to nextnode  
								pred[k][c]=nextnode;						// pred stores the shortest path 
								c++;
							}
						}
					}		
				}
			}
			return 0;
		}
	}


	String printPath()
	{
		if(rows!=cols)
			return "No path Exists";
		System.out.print("pred 0 1 2 3 4 5"+"\n     ");
		for(int k=0;k<rows;k++)
		{
			System.out.print(pred[src][k]+"-");							
		}

		System.out.println();
		int j;
		System.out.print("path =" );
		j=des;
		String Path= " "+des;
		String temp="",act="";
		do
		{

			j=pred[src][j];												// pred matrix has the path from destination to source.
			//System.out.print("-" + j);
			Path=Path+"-"+j;											// Path is converted to string

		}while(j!=src);
		StringBuffer a = new StringBuffer(Path);						// reverse the string path
		//System.out.println(a.reverse());			
		//String s1=a.reverse().toString();
		//
		String s=a.reverse().toString();								// Convertes the string buffer to string.
System.out.println(s);
		//Scanner read=new Scanner(s);
		for(int k=0;k<s.length();k++)
		{
			temp=temp+s.charAt(k);
			if(s.charAt(k) == '-')
			{
				StringBuffer test = new StringBuffer(temp);
				act+=test.reverse().toString();
				temp="";
			}


		}
		StringBuffer test = new StringBuffer(temp);
		act+="-"+test.reverse().toString();
		act=act.replace(" ", "");
		if(act.charAt(0) == '-')
		{
			act = act.substring(1, act.length());
		}
		System.out.println(act);
		return(act);
		//s.charAt(k)
	}
		
	// Returns the string s.

	int printDis()
	{
		if(rows==cols)
		{
			System.out.println("\nsrc-des\tDistance");
			//for(int k=0;k<6;k++)
			//{
			System.out.println(src+"---"+des+"\t    "+distance[src][des]);	
			return distance[src][des];									// returns the distance from source to destination
		}
		else
			return -1;

	}
	String printRoute()
	{
		for(int i=0;i<rows;i++)
		{
			int j=i;
			do
			{
				route[i]=j;												// Route array is used to store the next node from source to detination
				j=pred[src][j];

			}while(j!=src);
		}
		System.out.println("Source---Destination---Nextnode");
		String rout="";
		for(int i=0;i<rows;i++)
		{
			rout=rout+"\t"+src+"\t"+"   "+i+"\t   "+route[i]+"\n";
			System.out.println(src+"\t"+"     "+i+"\t\t"+"   "+route[i]); // appends all the Source destination and nextnode and
		}
		return rout;														// returns the rout.
	}


	public static void main(String[] args) throws IOException 
	{
		// TODO Auto-generated method stub

		//Dijkstra d=new Dijkstra("D:/Eclipse/Dijkstras/src/myfile.txt");
		/*d.Dimplementation(2,3);
		d.print();*/
	}

}




