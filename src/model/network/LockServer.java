package model.network;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class LockServer {
	public static void main(String... args) throws IOException {
		try(final ServerSocket serverSocket = new ServerSocket(1234)){
			final Set<Integer> ports = new HashSet<>();
			ports.add(0);
			final Object key = new Object();
			while(true) {
				synchronized(key){
					getNewConnection(serverSocket, ports);
				}
			}
		}
	}

	private static int getNewConnection(final ServerSocket serverSocket, final Set<Integer> ports) throws IOException {
		int port = 0;
		try(final Socket socket = serverSocket.accept();
			
			final OutputStream outStream = socket.getOutputStream();
			final OutputStreamWriter streamWriter = new OutputStreamWriter(outStream);
			final PrintWriter printWriter = new PrintWriter(streamWriter)
		){
			while(ports.contains(port))
				port = (int)(Math.random()*10_000) + 1234;
			ports.add(port);
			printWriter.write(((Integer)port).toString() + System.lineSeparator());
			printWriter.flush();
			new LockServer().start(port);
			
			System.out.print(port + System.lineSeparator());
			System.out.flush();
		}
		return port;
	}
	
	private synchronized void start(int port) throws IOException {
		final Thread lockedServer = new Thread(() -> lockedServer(port));
		lockedServer.start();
	}
	
	private void lockedServer(int port) {
		try(final ServerSocket serverSocket = new ServerSocket(port, 1)){
			serverSocket.setSoTimeout(1_000);
			try(final Socket socket = serverSocket.accept();
				
				final OutputStream streamWriter = socket.getOutputStream();
				final OutputStreamWriter outStreamWriter = new OutputStreamWriter(streamWriter);
				final PrintWriter printWriter = new PrintWriter(outStreamWriter)
			){
				socket.setSoTimeout(0);
				System.out.println("JEYYYYY");
				printWriter.write("hello" + System.lineSeparator());
				printWriter.flush();
			}
		} catch (IOException ex) {
			throw new AssertionError();
		}
	}
}
