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

public class TestClient {

	private final int playerNumber;
	
	public TestClient(int playerNumber) {
		this.playerNumber = playerNumber;
	}
    
	private int getPlayerNumber() {
		return playerNumber;
	}


    private void work(){
        final int port = getPort();
        System.out.println(Thread.currentThread().getName() + " : " + port);
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
            printWriter.println("POST initialize/40/4/4/1/1/1/1 HTTP/1.0");
            printWriter.flush();
            printWriter.println();
            printWriter.flush();
            
            		//init response
            String read = buffReader.readLine();
            List<String> elementsOfRead = Arrays.asList(read.split("\\s"));
            System.out.println("expected : 200; have : " + elementsOfRead.get(1));
            System.out.println("expected : true; have : " + elementsOfRead.get(2));
            while(!buffReader.readLine().isEmpty());
            
            	//move 5
            		//test if playerTurn
            int playerTurn = -2;
            while(playerTurn != getPlayerNumber()) {
	            printWriter.println("GET getPlayerTurnID HTTP/1.0");
	            printWriter.flush();
	            printWriter.println();
	            printWriter.flush();
	            
	     					//test if playerTurn response
	            read = buffReader.readLine();
	            elementsOfRead = Arrays.asList(read.split("\\s"));
	            System.out.println("expected : 200; have : " + elementsOfRead.get(1));
	            System.out.println("expected : 0-3, own : "+ getPlayerNumber() +"; have : " + elementsOfRead.get(2));
	            while(!buffReader.readLine().isEmpty());
	            playerTurn = Integer.parseInt(elementsOfRead.get(2));
	            Thread.sleep(100);
            }
            
            	// move
            printWriter.println("PUT move/0/6 HTTP/1.0");
            printWriter.flush();
            printWriter.println();
            printWriter.flush();
            
     					//move Response
            read = buffReader.readLine();
            Objects.requireNonNull(read);
            elementsOfRead = Arrays.asList(read.split("\\s"));
            System.out.println("expected : 200; have : " + elementsOfRead.get(1));
            System.out.println("expected : true; have : " + elementsOfRead.get(2));
            while(!buffReader.readLine().isEmpty());
            
        		// move
            printWriter.println("PUT move/0/5 HTTP/1.0");
            printWriter.flush();
            printWriter.println();
            printWriter.flush();
            
     					//move Response
            read = buffReader.readLine();
            elementsOfRead = Arrays.asList(read.split("\\s"));
            System.out.println("expected : 200; have : " + elementsOfRead.get(1));
            System.out.println("expected : true; have : " + elementsOfRead.get(2));
            while(!buffReader.readLine().isEmpty());
            
            
            	//exit
            printWriter.println("PUT exit HTTP/1.0");
            printWriter.flush();
            printWriter.println();
            printWriter.flush();
            // end
        } catch (IOException | InterruptedException ex) {
            throw new AssertionError(ex );
        }
    }


    private int getPort() {
        final int newPort;
        try(final Socket socket = new Socket("localhost", 1234);

            final OutputStream streamWriter = socket.getOutputStream();
            final BufferedOutputStream buffStreamWriter = new BufferedOutputStream(streamWriter);
            final PrintStream printStream = new PrintStream(buffStreamWriter);

            final InputStream streamReader = socket.getInputStream();
            final InputStreamReader inStreamReader = new InputStreamReader(streamReader);
            final BufferedReader buffReader = new BufferedReader(inStreamReader)
        ){
            newPort = Integer.parseInt(buffReader.readLine().trim());
        } catch (IOException ex) {
            throw new AssertionError(ex);
        }
        System.out.println(newPort);
        return newPort;
    }
    
    
    public static void main(String... args) throws IOException {
        final List<Thread> workers = new ArrayList<>();
        IntStream.iterate(0, n -> n + 1)
                .limit(4)
                .forEach((n) -> workers.add(
                        new Thread(() -> new TestClient(n).work())
                        )
                );
        workers.stream().forEach(worker -> worker.start());
    }
}
