package net.sneak.mcbr.join;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class McbrSocket implements AutoCloseable {
	private ServerSocket serverSocket;
	private Socket socket;
	private BufferedReader br;

	private static McbrSocket instance;

	public static McbrSocket getInstance() {
		return instance;
	}

	public static void init() {
		instance = new McbrSocket();
	}

	private McbrSocket() {
		try {
			this.serverSocket = new ServerSocket(9890);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.recieve();
	}

	private void recieve() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					socket = serverSocket.accept();
					br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					String data = br.readLine();
					System.out.println("Recieved: " + data);
					Join.getInstance().setSettingsAndPlayerList(data);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	public void close() throws Exception {
		if(this.br != null)
			this.br.close();
		if(this.socket != null)
			this.socket.close();
		if(this.serverSocket != null)
			this.serverSocket.close();
	}
}
