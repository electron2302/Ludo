package test.model.network;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * A little fire test
 * @author Andreas Hager, andreashager19@gmail.com
 *
 */
public class TestClient { 

	/**
	 * 
	 */
	private final int playerNumber;
	
	/**
	 * 
	 * @param playerNumber
	 */
	public TestClient(int playerNumber) {
		this.playerNumber = playerNumber;
	}
    
	/**
	 * 
	 * @return
	 */
	private int getPlayerNumber() {
		return playerNumber;
	}

	/**
	 * 
	 * @param gameNumber
	 */
    private void work(int gameNumber){
        final int port = getPort(gameNumber);
        //System.out.println(Thread.currentThread().getName() + " : " + port);
        try(final Socket socket = new Socket("localhost", port);

            final OutputStream streamWriter = socket.getOutputStream();
            final Writer writer = new OutputStreamWriter(streamWriter);
        	final PrintWriter printWriter = new PrintWriter(writer);

            final InputStream streamReader = socket.getInputStream();
            final InputStreamReader inStreamReader = new InputStreamReader(streamReader);
            final BufferedReader buffReader = new BufferedReader(inStreamReader)
        ){
        	// start 
        		//init
        	writeToServer(printWriter, "POST initialize/40/4/4/1/1/1/1 HTTP/1.0");
            
            		//init response
            String read = buffReader.readLine();
            List<String> elementsOfRead = Arrays.asList(read.split("\\s"));
            // System.out.println("expected : 200; have : " + elementsOfRead.get(1));
            // System.out.println("expected : true; have : " + elementsOfRead.get(2));
            while(!buffReader.readLine().isEmpty()); 
            
            	//move 5
            		//test if playerTurn
            int playerTurn = -2;
            while(playerTurn != getPlayerNumber()) {
            	writeToServer(printWriter, "GET getPlayerTurnID HTTP/1.0");
	            
	     					//test if playerTurn response
	            read = buffReader.readLine();
	            elementsOfRead = Arrays.asList(read.split("\\s"));
	            // System.out.println("expected : 200; have : " + elementsOfRead.get(1));
	            // System.out.println("expected : 0-20, own : "+ getPlayerNumber() +"; have : " + elementsOfRead.get(2));
	            while(!buffReader.readLine().isEmpty());
	            if(!elementsOfRead.get(2).chars().allMatch(Character::isDigit))
	            	throw new IllegalStateException();
	            playerTurn = Integer.parseInt(elementsOfRead.get(2));
	            Thread.sleep(100);
            }
            
            	// move
            writeToServer(printWriter, "PUT move/0/6 HTTP/1.0");
            
     					//move Response
            read = buffReader.readLine();
            elementsOfRead = Arrays.asList(read.split("\\s"));
            // System.out.println("expected : 200; have : " + elementsOfRead.get(1));
            // System.out.println("expected : true; have : " + elementsOfRead.get(2));
            while(!buffReader.readLine().isEmpty());
            
        		// move
            writeToServer(printWriter, "PUT move/0/5 HTTP/1.0");
            
     					//move Response
            read = buffReader.readLine();
            elementsOfRead = Arrays.asList(read.split("\\s"));
            // System.out.println("expected : 200; have : " + elementsOfRead.get(1));
            // System.out.println("expected : true; have : " + elementsOfRead.get(2));
            while(!buffReader.readLine().isEmpty());
            
            
            	//exit
            writeToServer(printWriter, "PUT exit HTTP/1.0");
            // end
        } catch (IOException | InterruptedException ex) {
            throw new AssertionError(ex );
        }
    }
    
    /**
     * 
     * @param printWriter
     * @param message
     */
    private void writeToServer(PrintWriter printWriter, String message) {
    	printWriter.println(message);
        printWriter.flush();
        printWriter.println();
        printWriter.flush();
    }

    /**
     * 
     * @param gameNumber
     * @return
     */
    private int getPort(Integer gameNumber) {
        final int newPort;
        try(final Socket socket = new Socket("localhost", 1234);

            final OutputStream streamWriter = socket.getOutputStream();
            final OutputStream buffStreamWriter = new BufferedOutputStream(streamWriter);
            final PrintStream printStream = new PrintStream(buffStreamWriter);

            final InputStream streamReader = socket.getInputStream();
            final InputStreamReader inStreamReader = new InputStreamReader(streamReader);
            final BufferedReader buffReader = new BufferedReader(inStreamReader)
        ){
        	printStream.println(gameNumber.toString());
        	printStream.flush();
        	
        	final String readLine = buffReader.readLine().trim();
        	if(!readLine.chars().allMatch(Character::isDigit))
        		throw new IllegalStateException();
            newPort = Integer.parseInt(readLine);
        } catch (IOException ex) {
            throw new AssertionError(ex);
        }
        // System.out.println(newPort);
        return newPort;
    }
    
    /**
     * 
     * @param args
     * @throws IOException
     * @throws InterruptedException
     */
    public static void main(String... args) throws IOException, InterruptedException {
        final List<Thread> workers = new ArrayList<>();
        IntStream.iterate(0, n -> n+1)
        	.limit(20)
        	.forEach(n ->  IntStream.iterate(0, m -> m + 1)
	                .limit(4)
	                .forEach((m) -> workers.add(
	                        new Thread(() -> new TestClient(m).work(n))
	                        )
	                )
        	);
	        
        workers.stream().forEach(worker -> worker.start());
        
        Thread.sleep(1_000);
        
        try(final Socket socket = new Socket("localhost", 1234);
        	
            final OutputStream streamWriter = socket.getOutputStream();
            final BufferedOutputStream buffStreamWriter = new BufferedOutputStream(streamWriter);
            final PrintStream printStream = new PrintStream(buffStreamWriter);
        ){
        	printStream.println("exit");
        	printStream.flush();
        }
    }
}
