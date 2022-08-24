package Slagalica;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server {
	
	public static LinkedList<ClientHandler> onlineUsers = new LinkedList<>();
	public static int[] poeni=new int[2];


	public static void main(String[] args) {

		int port=9001;
		ServerSocket serverSoket = null;
		Socket soketZaKomunikaciju = null;
		
		try {
			serverSoket = new ServerSocket(port);
			
			while(true) {
				System.out.println("Cekam na konekciju...");
				soketZaKomunikaciju = serverSoket.accept();
				System.out.println("Doslo je do konekcije!");
				ClientHandler klijent=new ClientHandler(soketZaKomunikaciju);
				onlineUsers.add(klijent);
				System.out.println("br klijenata je" + Server.onlineUsers.size());
				klijent.start();
			}
			
		} catch (IOException e) {
			System.out.println("Greska prilikom pokretanja servera!");
		}
	}

}
