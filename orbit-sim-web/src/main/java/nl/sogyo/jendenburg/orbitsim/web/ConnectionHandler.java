package nl.sogyo.jendenburg.orbitsim.web;

import java.net.*;
import java.util.Base64;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ConnectionHandler implements Runnable
{
	private Socket client;
	private IWebsocketMessageReceiver receiver;
	private final static Pattern websocketKeyPattern = Pattern.compile("Sec-WebSocket-Key: (.*)");
	
	public ConnectionHandler(Socket client, IWebsocketMessageReceiver receiver)
	{
		this.client = client;
		this.receiver = receiver;
	}
	
	public void run()
	{
		try
		{
			performHandshake();
			InputStream in = client.getInputStream();
			OutputStream out = client.getOutputStream();
			while(client.isConnected())
			{
				out.write(encodeMessage(receiver.respondToMessage(decodeMessage(in))));
			}
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
	
	private void performHandshake() throws IOException
	{
		Scanner scanner = new Scanner(client.getInputStream(), "UTF-8");
		String data = scanner.useDelimiter("\\r\\n\\r\\n").next();
		if(data.startsWith("GET"))
		{
			Matcher keyMatcher = websocketKeyPattern.matcher(data);
			keyMatcher.find();
			byte[] response = getHandshakeHTTPResponse(keyMatcher.group(1)).getBytes("UTF-8");
			client.getOutputStream().write(response, 0, response.length);
		}
	}
	
	private String getHandshakeHTTPResponse(String websocketKey)
	{
		return ("HTTP/1.1 101 Switching Protocols\n"
				+ "Connection: Upgrade\n"
				+ "Upgrade: websocket\n"
				+ "Sec-WebSocket-Accept: " + getHandshakeKeyEncrypt(websocketKey) + "\n\n");
	}
	
	private String getHandshakeKeyEncrypt(String websocketKey)
	{
		try {
			return Base64.getEncoder().encodeToString(MessageDigest.getInstance("SHA-1").digest((websocketKey + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11").getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	private String decodeMessage(InputStream inputStream) throws IOException
	{
		OpCode opCode = OpCode.fromEncodedValue(inputStream.read());
		int messageLength = getMessageLength(inputStream);
		byte[] key = new byte[4];
		
		for(int i = 0; i < 4; i++)
			key[i] = (byte) inputStream.read();
		
		byte[] message = new byte[messageLength];
		for(int i = 0; i < messageLength; i++)
			message[i] = (byte) (inputStream.read() ^ key[i &0x3]);
		
		return new String(message);
	}
	
	private int getMessageLength(InputStream inputStream) throws IOException
	{
		int length = inputStream.read() - 128;
		if(length == 126)
			length = (inputStream.read() << 8) + (inputStream.read());
		else if(length == 127)
			length = (inputStream.read() << 24) + (inputStream.read() << 16) + (inputStream.read() << 8) + (inputStream.read());
		
		return length;
	}
	
	private byte[] encodeMessage(String message)
	{
		int actualLength;
		int length = actualLength = message.length();
		byte[] response;
		int maskStartIndex;
		if(message.length() > 125)
		{
			length = 126;
			response = new byte[4 + 4 + actualLength];
			response[1] = (byte)(length + 128);
			response[2] = (byte)(actualLength >> 8);
			response[3] = (byte)actualLength;
			maskStartIndex = 4;
		}
		else
		{
			response = new byte[2 + 4 + actualLength];
			response[1] = (byte)(actualLength + 128);
			maskStartIndex = 2;
		}
		
		response[0] = OpCode.Text.getEncodedValue();
		int messageStartIndex = maskStartIndex + 4;
		
		byte[] mask = new byte[4];
		for(int i = 0; i < 4; i++)
			mask[i] = response[maskStartIndex + i] = (byte)(Math.random() * 255 - 128);
		
		for(int i = 0; i < actualLength; i++)
			response[messageStartIndex + i] = (byte)(message.charAt(i) ^ mask[i & 0x3]);
		
		return response;
	}
	
	static enum OpCode
	{
		Text((byte)1),
		;
		
		private final byte val;
		
		private OpCode(byte val)
		{
			this.val = val;
		}
		
		public byte getEncodedValue()
		{
			return (byte)(this.val + 128);
		}
		
		public static OpCode fromDecodedValue(int value)
		{
			for(OpCode oc : OpCode.values())
			{
				if(oc.val == (byte)value)
					return oc;
			}
			return null;
		}
		
		public static OpCode fromEncodedValue(int value)
		{
			return fromDecodedValue(((byte)value) - 128);
		}
	}
}
