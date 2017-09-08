package lab_6;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Stack;
class NonCoordinateException extends Exception{
	public NonCoordinateException(String message)
	{
		super(message);
	}
}
class StackEmptyException extends Exception{
	public StackEmptyException(String message)
	{
		super(message);
	}
}
class OverlapException extends Exception{
	public OverlapException(String message)
	{
		super(message);
	}
}
class QueenFoundException extends Exception{
	public QueenFoundException(String message)
	{
		super(message);
	}
}
class queen
{
	private int x;
	private int y;
	int qf=0;
	public queen(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
}
class knight
{
	String name;
	int x,y;
	int m;
	int qf=0;
	int flag=0;
	int cr=0;
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	knight()
	{		
	}
	knight(String name, int x, int y, int m) {
		super();
		this.name = name;
		this.x = x;
		this.y = y;
		this.m = m;
	}
	Stack<String> box=new Stack<String>();
	public void sempty()throws StackEmptyException
	{
		if(this.box.empty())
		{
			this.flag=1;
			throw new StackEmptyException("StackEmptyException: Stack Empty exception");
		}

	}
	public int tri(ArrayList<knight> kn,int j)
	{
		int index=-1;
		for(int i=0;i<kn.size();i++)
		{	
			if(i==j)
				i++;
			if(i<kn.size())
			{if(kn.get(i).x==this.x)
			{
				if(kn.get(i).y==this.y)
					{
						index=i;
					}
			}}
		}
		return index;
	}
	public void col1(ArrayList<knight> kn,int index) throws OverlapException
	{
		String name=kn.get(index).name;
		kn.remove(kn.get(index));
		throw new OverlapException("OverlapException: Knights Overlap Exception "+name);
	}
	public void col2(ArrayList<knight> kn) throws QueenFoundException
	{
		throw new QueenFoundException("QueenFoundException: Queen has been Found. Abort!");
	}
	public int check(queen q,int x,int y)
	{
		if(x==this.x&&y==this.y)
			{
				this.qf=1;
				q.qf=1;
			}
		return this.qf;
	}
	public void getout(PrintWriter w)throws NonCoordinateException
	{
		String c[]=new String[2];
		String temp="";
		int x=0;
		temp=this.box.pop();
		c=temp.split(" ");
		if(!(c[0].equals("Coordinate")))
		{
			if(c[0].equals("String"))
			{
				c[1]=temp.substring(7);
			}
			this.cr=0;
			throw new NonCoordinateException("NonCoordinateException: Not a coordinate Exception "+c[1]);
		}
		else
		{
			this.cr=1;
			w.println("No exception "+ c[1]+" "+c[2]);
			this.x=Integer.parseInt(c[1]);
			this.y=Integer.parseInt(c[2]);			
		}
	}
	
}
class srt implements Comparator<knight>
{
    public int compare(knight a, knight b)
    {
        return a.name.compareTo(b.name);
    }
}
public class lab6 {
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		PrintWriter w = new PrintWriter("./src/"+"output.txt", "UTF-8");
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter number of Knights");
		int n=sc.nextInt();
		System.out.println("Enter number of Iterations");
		int it=sc.nextInt();
		System.out.println("Enter Coordinates of Queen");
		int x=sc.nextInt();
		int y=sc.nextInt();
		queen q=new queen(x,y);
		ArrayList<knight> kn=new ArrayList<knight>(n);

		knight kk=new knight();
		for(int i=0;i<n;i++)
		{
			String source="./src/";
			int temp=i+1;
			source+=temp;
			source+=".txt";
			FileReader file=new FileReader(source);
			BufferedReader rd=new BufferedReader(file);
			String name=rd.readLine();
			String xy=rd.readLine();
			String c[]=new String[2];
			c=xy.split(" ");
			String m=rd.readLine();
			kk=new knight(name,Integer.parseInt(c[0]),Integer.parseInt(c[1]),Integer.parseInt(m));
			kn.add(kk);
			for(int j=0;j<Integer.parseInt(m);j++)
			{
				//System.out.println(j);
				kn.get(i).box.push(rd.readLine());
			}

		}
		Collections.sort(kn,new srt());
		int r=1;
		while(!kn.isEmpty()&&r<it&&q.qf==0)
		{
			for(int i=0;i<kn.size();i++)
			{
				w.println(r+" "+kn.get(i).name+" "+kn.get(i).x+" "+kn.get(i).y);
				try
				{
					kn.get(i).sempty();
				}
				catch(StackEmptyException e)
				{
					w.println(e.getMessage());
				}
				if(kn.get(i).flag==1&&kn.get(i).qf==0)
				{
					kn.remove(kn.get(i));
				}
				else
				{
					try
					{
						kn.get(i).getout(w);
					}
					catch(NonCoordinateException e)
					{
						w.println(e.getMessage());
					}
					if(kn.get(i).cr==1)
					{
						try
						{
							int tem=kn.get(i).tri(kn,i);
							if(tem>-1)
							{
								kn.get(i).col1(kn,tem);
							}
						}
						catch(OverlapException e)
						{
							w.println(e.getMessage());
						}
						try
						{
							int f=kn.get(i).check(q,q.getX(),q.getY());
							if(f==1)
								kn.get(i).col2(kn);
						}
						catch(QueenFoundException e)
						{
							w.println(e.getMessage());
						}
					}

				}
				if(q.qf==1)
				{
					w.close();
					break;
				}

			}
			r++;
		}
	}

}

