package test.model.network;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class TestClient {
	public static void main(String... args) throws IOException {
		
		final List<Thread> workers = new ArrayList<>();
		IntStream.iterate(0, n -> n+1)
			.limit(10)
			.forEach((n) -> workers.add(
						new Thread(() -> new TestClient().work())
					)
				);
		workers.stream().forEach(worker -> worker.start());
		
	}


	private void work(){
		try(final Socket socket = new Socket("localhost", getPort());
				
			final OutputStream streamWriter = socket.getOutputStream();
			final BufferedOutputStream buffStreamWriter = new BufferedOutputStream(streamWriter);
			final PrintStream printStream = new PrintStream(buffStreamWriter);
					
			final InputStream streamReader = socket.getInputStream();
			final InputStreamReader inStreamReader = new InputStreamReader(streamReader);
			final BufferedReader buffReader = new BufferedReader(inStreamReader)
		){
			System.out.println(Thread.currentThread().getName() + buffReader.readLine());
			System.out.flush();
		} catch (IOException ex) {
			throw new AssertionError(ex);
		}
	}

	
	private int getPort() {
		int newPort;
		try(final Socket socket = new Socket("localhost", 1234);
				
			final OutputStream streamWriter = socket.getOutputStream();
			final BufferedOutputStream buffStreamWriter = new BufferedOutputStream(streamWriter);
			final PrintStream printStream = new PrintStream(buffStreamWriter);
					
			final InputStream streamReader = socket.getInputStream();
			final InputStreamReader inStreamReader = new InputStreamReader(streamReader);
			final BufferedReader buffReader = new BufferedReader(inStreamReader)
		){
			
			newPort = Integer.parseInt(buffReader.readLine().trim());
			System.out.println(newPort);
		} catch (IOException ex) {
			throw new AssertionError(ex);
		}
		return newPort;
	}
}
