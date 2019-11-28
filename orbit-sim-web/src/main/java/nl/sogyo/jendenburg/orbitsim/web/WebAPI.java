package nl.sogyo.jendenburg.orbitsim.web;

import java.net.*;
import java.io.*;

public class WebAPI
{

	public static void main(String[] args) 
	{
		handleConnections();
	}
	
	private static void handleConnections()
	{
		try
		{
			ServerSocket server = new ServerSocket(1337);
			while(true)
			{
				try
				{
					Socket client = server.accept();
					new Thread(new ConnectionHandler(client, msg -> System.out.println(msg) )).start();
				}
				catch(IOException ioEx)
				{
					ioEx.printStackTrace();
				}
			}
		}
		catch(IOException ioEx)
		{
			ioEx.printStackTrace();
		}
	}

}
