package nl.sogyo.jendenburg.orbitsim.web;

import java.net.*;
import java.io.*;

public class ConnectionHandler implements Runnable
{
	private Socket client;
	
	public ConnectionHandler(Socket client)
	{
		this.client = client;
	}
	
	public void run()
	{
		try
		{
			InputStream inputStream = client.getInputStream();
			StringBuilder sb = new StringBuilder();
			
			int readByte;
			while(inputStream.available() > 0 && (readByte = inputStream.read()) != -1)
				sb.append((char)readByte);
			
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			writer.write("Echo: " + sb.toString());
			writer.flush();
		}
		catch(IOException ioEx)
		{
			ioEx.printStackTrace();
		}
		finally
		{
			try
			{
				if(client != null)
					client.close();
			}
			catch(IOException ioEx)
			{
				ioEx.printStackTrace(); //And cry
			}
		}
	}
}
