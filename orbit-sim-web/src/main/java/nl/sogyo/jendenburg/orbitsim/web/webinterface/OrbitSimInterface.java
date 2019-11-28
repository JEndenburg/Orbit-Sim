package nl.sogyo.jendenburg.orbitsim.web.webinterface;

import nl.sogyo.jendenburg.orbitsim.domain.*;
import nl.sogyo.jendenburg.orbitsim.domain.body.Body;
import nl.sogyo.jendenburg.orbitsim.domain.body.Planet;
import nl.sogyo.jendenburg.orbitsim.domain.body.Vessel;
import nl.sogyo.jendenburg.orbitsim.domain.math.Vector3;
import nl.sogyo.jendenburg.orbitsim.web.IWebsocketMessageReceiver;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.time.Duration;

public class OrbitSimInterface implements IWebsocketMessageReceiver 
{
	private Simulation simulation;
	
	@Override
	public String respondToMessage(String message) 
	{
		try
		{
			executeMessage(message);
			return getUpdateJSON();
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
		catch(InvalidMessageException iMEx)
		{
			iMEx.printStackTrace();
			return getMessageErrorResponse(message, iMEx.getMessage());
		}
	}

	private void executeMessage(String message) throws ParseException, InvalidMessageException
	{
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = (JSONObject) parser.parse(message);
		
		String messageTypeName = (String)jsonObject.get("type");
		if(messageTypeName == null)
			throw new InvalidMessageException("type should not be null!");
		MessageType messageType = MessageType.valueOf(messageTypeName);
		
		switch(messageType)
		{
		case CreateSimulation: executeCreateSimulation(); break;
		case Update: executeUpdate(jsonObject); break;
		case AddPlanet: executeAddPlanet((JSONObject)jsonObject.get("planet")); break;
		case AddVessel: executeAddVessel((JSONObject)jsonObject.get("vessel")); break;
		default: throw new InvalidMessageException("type was not recognized!");
		}
	}
	
	private void executeCreateSimulation()
	{
		simulation = new Simulation();
	}
	
	private void executeUpdate(JSONObject jsonObject) throws InvalidMessageException
	{
		if(simulation == null)
			throw new InvalidMessageException("Can't add objects without a simulation!");
		
		long secondsToSimulate = ((Number)jsonObject.getOrDefault("time", 1L)).longValue();
		
		if(secondsToSimulate < 1 || secondsToSimulate > 5184000)
			secondsToSimulate = 1;
		
		simulation.simulate(Duration.ofSeconds(secondsToSimulate));
	}
	
	private void executeAddPlanet(JSONObject jsonObject) throws InvalidMessageException
	{
		if(jsonObject == null)
			throw new InvalidMessageException("planet can't be null!");
		if(simulation == null)
			throw new InvalidMessageException("Can't add objects without a simulation!");
		
		Vector3 position = parseJsonToVec3((JSONObject)jsonObject.get("position"));
		Vector3 velocity = parseJsonToVec3((JSONObject)jsonObject.get("velocity"));
		double mass = ((Number)jsonObject.get("mass")).doubleValue();
		double radius = ((Number)jsonObject.get("radius")).doubleValue();
		
		simulation.addBody(new Planet(position, velocity, mass, radius));
	}
	
	private void executeAddVessel(JSONObject jsonObject) throws InvalidMessageException
	{
		if(jsonObject == null)
			throw new InvalidMessageException("vessel can't be null!");
		if(simulation == null)
			throw new InvalidMessageException("Can't add objects without a simulation!");
		
		Vector3 position = parseJsonToVec3((JSONObject)jsonObject.get("position"));
		Vector3 velocity = parseJsonToVec3((JSONObject)jsonObject.get("velocity"));
		double mass = ((Number)jsonObject.get("mass")).doubleValue();
		
		simulation.addBody(new Vessel(position, velocity, mass));
	}
	
	private Vector3 parseJsonToVec3(JSONObject jsonObject)
	{
		return new Vector3(
				((Number)jsonObject.get("x")).doubleValue(),
				((Number)jsonObject.get("y")).doubleValue(),
				((Number)jsonObject.get("z")).doubleValue()
				);
	}
	
	private JSONObject parseVec3ToJson(Vector3 vec3)
	{
		JSONObject jsonVec3 = new JSONObject();
		jsonVec3.put("x", vec3.getX());
		jsonVec3.put("y", vec3.getY());
		jsonVec3.put("z", vec3.getZ());
		return jsonVec3;
	}
	
	private String getUpdateJSON()
	{
		JSONObject result = new JSONObject();
		JSONObject jsonSimulation;
		
		if(simulation == null)
			jsonSimulation = null;
		else
		{
			jsonSimulation = new JSONObject();
			JSONArray bodyArray = new JSONArray();
			
			for(Body body : simulation.getBodies())
			{
				JSONObject bodyObject = new JSONObject();
				bodyObject.put("position", parseVec3ToJson(body.getPosition()));
				bodyObject.put("velocity", parseVec3ToJson(body.getVelocity()));
				bodyArray.add(bodyObject);
			}
			
			jsonSimulation.put("bodies", bodyArray);
		}
		result.put("simulation", jsonSimulation);
		return result.toJSONString();
	}
	
	private String getParseErrorResponse(String message)
	{
		return "{"
				+ "\"error\": \"Could not parse JSON.\","
				+ "\"json\": \""
				+ message.replace("\"", "\\\"")
				+ "\"}";
	}
	
	private String getMessageErrorResponse(String message, String exceptionMessage)
	{
		return "{"
				+ "\"error\": \"Could not parse JSON.\","
				+ "\"json\": \""
				+ message.replace("\"", "\\\"")
				+ "\",\"message\":"
				+ "\""
				+ exceptionMessage.replace("\"", "\\\"")
				+ "\"}";
	}
	
	static enum MessageType
	{
		CreateSimulation,
		Update,
		AddVessel,
		AddPlanet,
	}
}
