package nl.sogyo.jendenburg.orbitsim.web.webinterface;

import nl.sogyo.jendenburg.orbitsim.domain.*;
import nl.sogyo.jendenburg.orbitsim.web.IWebsocketMessageReceiver;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class OrbitSimInterface implements IWebsocketMessageReceiver 
{
	@Override
	public String respondToMessage(String message) 
	{
		try
		{
			return handleMessage(message);
		}
		catch(ParseException pEx)
		{
			pEx.printStackTrace();
			return getParseErrorResponse(message);
		}
		catch(ClassCastException classCastException)
		{
			classCastException.printStackTrace();
			return getParseErrorResponse(message);
		}
	}

	private String handleMessage(String message) throws ParseException
	{
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = (JSONObject) parser.parse(message);
		
		return "{\"derp\":555}";
	}
	
	private String getParseErrorResponse(String message)
	{
		return "{"
				+ "\"error\": \"Could not parse JSON.\","
				+ "\"json\": \""
				+ message.replace("\"", "\\\"")
				+ "\"}";
	}
}
