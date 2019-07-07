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
import java.util.List;
import java.util.stream.IntStream;

public class TestClient {

    public static void main(String... args) throws IOException {
        final List<Thread> workers = new ArrayList<>();
        IntStream.iterate(0, n -> n + 1)
                .limit(4)
                .forEach((n) -> workers.add(
                        new Thread(() -> new TestClient().work())
                        )
                );
        workers.stream().forEach(worker -> worker.start());
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
            printWriter.println("POST initialize/40/4/4/1/1/1/1 HTTP/1.0");
            printWriter.flush();
            printWriter.println();
            printWriter.flush();
            
            String read = buffReader.readLine();
            while(!read.isEmpty()) {
            	System.out.println(read);
            	read = buffReader.readLine();
            }
            
            // end
        } catch (IOException ex) {
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
}
