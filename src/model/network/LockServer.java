package model.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import model.data.ROData;
import model.data.RWData;
import model.logic.CLogic;
import model.logic.Logic;

/**
 * 
 * @author Andreas Hager, andreashager19@gmail.com
 *
 */
public class LockServer {
	/**
	 * 
	 */
	private static final Set<Integer> ports = new HashSet<>();
	
	/**
	 * 
	 */
	private final ROData roData;
	/**
	 * 
	 */
	private final RWData rwData;
	/**
	 * 
	 */
	private final Logic logic;
	
	/**
	 * for GET
	 */
	private final Map<String, Supplier<Integer>> pureGet;
	/**
	 * for GET
	 */
	private final Map<String, UnaryOperator<Integer>> getTypeByID;
	/**
	 * for GET
	 */
	private final Map<String, BinaryOperator<Integer>> getPositionOfTocken;
	/**
	 * for GET
	 */
	private final Map<String, Function<Integer, Boolean>> hasWon;
	
	
	/**
	 * for PUT
	 */
	final Map<String, BiFunction<Integer, Integer, Boolean>> move;
	
	/**
	 * for POST
	 */
	final Map<String, NConsumer<Integer>> initialize;
	
	/**
	 * for DELETE
	 */
	final Map<String, Runnable> reset;
	
	/**
	 * 
	 * @param gameNumber
	 */
	public LockServer(int gameNumber) {
		roData = ROData.getInstanceOfROData(gameNumber);
		rwData = RWData.getInstanceOfRWData(gameNumber);
		logic = CLogic.getInstanceOfLogic(gameNumber);
		
		pureGet = new HashMap<>();
		pureGet.put("getPlayerTurnID", roData::getPlayerTurnID);
		pureGet.put("getBoardLength", roData::getBoardLength);
		pureGet.put("getSpacesBetweenPlayerBases", roData::getSpacesBetweenPlayerBases);
		pureGet.put("getTockenCountPP", roData::getTockenCountPP);
		pureGet.put("getPlayerCount", roData::getPlayerCount);
		pureGet.put("throwDice", logic::throwDice);
		
		
		getTypeByID = new HashMap<>();
		getTypeByID.put("getPlayerType", roData::getPlayerType);
		
		getPositionOfTocken = new HashMap<>();
		getPositionOfTocken.put("getPositionOfTocken", roData::getPositionOfTocken);
		
		hasWon = new HashMap<>();
		hasWon.put("hasPlayerWon", roData::hasPlayerWon);
		
		move = new HashMap<>();
		move.put("move", logic::move);
		
		initialize = new HashMap<>();
		initialize.put("initialize", logic::initialize);
		 
		reset = new HashMap<>();
		reset.put("reset", rwData::reset);
		
	}
	
	/**
	 * 
	 * @param port
	 * @throws IOException
	 */
    private void start(int port) throws IOException {
        final Thread lServer = new Thread(() -> lockedServer(port));
        lServer.start();
    }

    /**
     * 
     * @param port
     */
    private void lockedServer(int port) {
        try(final ServerSocket serverSocket = new ServerSocket(port, 1)){
            serverSocket.setSoTimeout(1_000);
            try(final Socket socket = serverSocket.accept();

                final OutputStream outputStream = socket.getOutputStream();
                final Writer outStreamWriter = new OutputStreamWriter(outputStream);
                final PrintWriter printWriter = new PrintWriter(outStreamWriter);
                		
                final InputStream inputStream = socket.getInputStream();
            	final Reader inputStreamReader = new InputStreamReader(inputStream);
            	final BufferedReader buffReader = new BufferedReader(inputStreamReader)
            ){
                socket.setSoTimeout(0);
                //start
                List<String> firstLine = Arrays.asList(buffReader.readLine().split("\\s"));
                List<String> body = buffReader.lines().takeWhile(line -> !line.isEmpty()).collect(Collectors.toList());
                while(!firstLine.contains("exit")) {
	                
	                boolean errorOccured = false;
	                
	                if(firstLine.size() == 3) {
	                	try {
		                	final Optional<String> outputOfCommand = doCommand(firstLine);
			                if(!outputOfCommand.isEmpty()) {
			                	printWriter.println("HTTP/1.0 200 " + outputOfCommand.get());
			                	printWriter.flush();
			                	printWriter.println("Here is the korrect input" + firstLine);
			                	printWriter.println();
			                	printWriter.flush();
			                }else {
			                	errorOccured = true;
			                }
		                }catch(IllegalArgumentException ex) {
	                		errorOccured = true;
		                }
	                }else {
	                	errorOccured = true;
	                }
	                
	                if(errorOccured) {
	                	printWriter.println("HTTP/1.0 400 SorryICantDoAnything");
	                	printWriter.flush();
	                	printWriter.println("There must be something wrong" + firstLine);
	                	printWriter.println();
	                	printWriter.flush();
	                }
	                firstLine = Arrays.asList(buffReader.readLine().split("\\s"));
	                body = buffReader.lines().takeWhile(line -> !line.isEmpty()).collect(Collectors.toList());
	                //end 
                }
            }
        } catch (IOException ex) {
            throw new AssertionError(ex + " : " + port);
	    }
	    final Object key = new Object();
	    synchronized(key){
	    	ports.remove(port);
        }
    }

    /**
     * 
     * @param firstLine
     * @return
     * @throws IllegalArgumentException
     */
	private Optional<String> doCommand(final List<String> firstLine) throws IllegalArgumentException {
		Objects.requireNonNull(firstLine);
		if(firstLine.size() != 3)
			throw new IllegalArgumentException();
		
		Optional<String> output = Optional.empty();
		
		final List<String> command = Arrays.asList(firstLine.get(1).split("/"));
		if(command.size() < 1)
			throw new IllegalArgumentException();
		
		switch(firstLine.get(0)) {
		    case "GET":
		    	if(pureGet.containsKey(command.get(0)) && command.size() == 1) {
		    		output = Optional.of(pureGet.get(command.get(0))
		    				.get()
		    				.toString());
		    	}
		    	if(getTypeByID.containsKey(command.get(0)) && 
		    			command.size() == 2 && 
		    			command.get(1).chars().allMatch(Character::isDigit)) {
		    		output = Optional.of(getTypeByID.get(command.get(0))
		    				.apply(Integer.parseInt(command.get(1)))
		    				.toString());
		    	}
		    	if(getPositionOfTocken.containsKey(command.get(0)) && 
		    			command.size() == 3 &&
		    			command.get(1).chars().allMatch(Character::isDigit) && 
		    			command.get(2).chars().allMatch(Character::isDigit)) {
		    		output = Optional.of(getPositionOfTocken.get(command.get(0))
		    				.apply(Integer.parseInt(command.get(1)), 
		    						Integer.parseInt(command.get(2)))
		    				.toString());
		    	}
		    	if(hasWon.containsKey(command.get(0)) && 
		    			command.size() == 2 &&
		    			command.get(1).chars().allMatch(Character::isDigit)) {
		    		output = Optional.of(hasWon.get(command.get(0))
		    				.apply(Integer.parseInt(command.get(1)))
		    				.toString());
		    	}	    	
		    	break;
		    case "PUT":
		    	if(move.containsKey(command.get(0)) && 
		    			command.size() == 3 && 
		    			command.get(1).chars().allMatch(Character::isDigit) &&
		    			command.get(2).chars().allMatch(Character::isDigit)) {
		    		output = Optional.of(move.get(command.get(0))
		    				.apply(Integer.parseInt(command.get(1)), 
		    					Integer.parseInt(command.get(2)))
		    				.toString());
		    	}		    	
		    	break;
		    case "POST":
		    	final List<Integer> types = new ArrayList<>();
		    	for(int index = 4; 
		    			index < command.size() && 
		    			command.get(index).chars().allMatch(Character::isDigit); 
		    			index++) {
		    		types.add(Integer.parseInt(command.get(index)));
		    	}
		    	if(initialize.containsKey(command.get(0)) && 
		    			command.size() > 4 &&
		    			command.get(1).chars().allMatch(Character::isDigit) &&
		    			command.get(2).chars().allMatch(Character::isDigit) && 
		    			command.get(3).chars().allMatch(Character::isDigit)) {
		    		initialize
		    			.get(command.get(0))
		    			.accept(Integer.parseInt(command.get(1)), 
		    				Integer.parseInt(command.get(2)), 
		    				Integer.parseInt(command.get(3)), 
		    				types
		    				);
		    		output = Optional.of("true");
		    	}		    	
		    	break;
		    case "DELETE":
		    	if(reset.containsKey(command.get(0)) && command.size() == 1) {
		    		reset.get(command.get(0)).run();
		    		output = Optional.of("true");
		    				
		    	}
		    	break;
		    default:
		    	throw new IllegalArgumentException();
		}
		System.out.println(command + " => " + output.get());
		return output;
	}
    
	/**
	 * 
	 * @param args
	 * @throws IOException
	 */
    public static void main(String... args) throws IOException {
        try(final ServerSocket serverSocket = new ServerSocket(1234)){
            ports.add(0);
            while(getNewConnection(serverSocket, ports));
        }
    }

    /**
     * 
     * @param serverSocket
     * @param ports
     * @return
     * @throws IOException
     */
    private static synchronized boolean getNewConnection(final ServerSocket serverSocket, final Set<Integer> ports) throws IOException {
        Integer port = 0;
        boolean continueInit = true;
        try(final Socket socket = serverSocket.accept();

        	final InputStream inStream = socket.getInputStream();
        	final Reader reader = new InputStreamReader(inStream);
        	final BufferedReader buffReader = new BufferedReader(reader);
        		
            final OutputStream outStream = socket.getOutputStream();
            final Writer streamWriter = new OutputStreamWriter(outStream);
            final PrintWriter printWriter = new PrintWriter(streamWriter)
        ){	
            while(ports.contains(port)) {
                port = (int) (Math.random() * 50_000) + 1234;
            }
            
            final String inLine = buffReader.readLine();
            if(inLine.chars().allMatch(Character::isDigit)) {
            	final int gameNumber = Integer.parseInt(inLine);
            
	            new LockServer(gameNumber).start(port);
	
	            ports.add(port);
	
	            printWriter.println(port.toString());
	            printWriter.flush();
            }else if("exit".equals(inLine))
            	continueInit = false;
            
        }
        return continueInit;
    }
}
