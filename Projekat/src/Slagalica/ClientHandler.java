package Slagalica;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;


public class ClientHandler extends Thread{
	
	int a=0;
	BufferedReader clientInput=null;
	PrintStream clientOutput=null;
	Socket soketZaKomunikaciju=null;
	
	public ClientHandler(Socket soket) {	
		soketZaKomunikaciju=soket;
	}
	
	public void run() {
		
		try {
			clientInput=new BufferedReader(new InputStreamReader(soketZaKomunikaciju.getInputStream()));
			clientOutput=new PrintStream(soketZaKomunikaciju.getOutputStream());
			
			
			//reci prvom da je na potezu a drugom zabrani ekran
			if(Server.onlineUsers.size()==2) {
				System.out.println("Dvoje korisnika, štampam drugom zabrani ekran");
				Server.onlineUsers.get(1).clientOutput.println("zabraniEkran");
				for(ClientHandler klijent: Server.onlineUsers) {
					klijent.clientOutput.println("započniTajmer");
					
				}
			}
			String input;
			
			do {
				//System.out.println("ceka na input server");
				input=clientInput.readLine();
				//System.out.println("dobio input"+input);
				switch(input) {
				case "završenTurn":
					this.clientOutput.println("zabraniEkran");
					for(ClientHandler klijent: Server.onlineUsers) {
						if(klijent!=this) {
							//svakome sem ovome treba da se update polje
							klijent.clientOutput.println("omogućiEkran");
						}
					}
					a++;
					break;
				case "A1Kliknut":
						for(ClientHandler klijent: Server.onlineUsers) {
							if(klijent!=this) {
								//svakome sem ovome treba da se update polje
								klijent.clientOutput.println("A1Kliknut");
							}
						}
						break;
				case "A2Kliknut":
					for(ClientHandler klijent: Server.onlineUsers) {
						if(klijent!=this) {
							klijent.clientOutput.println("A2Kliknut");
						}
					}
					break;
				case "A3Kliknut":
					for(ClientHandler klijent: Server.onlineUsers) {
						if(klijent!=this) {
							klijent.clientOutput.println("A3Kliknut");
						}
					}
					break;
				case "A4Kliknut":
					for(ClientHandler klijent: Server.onlineUsers) {
						if(klijent!=this) {
							klijent.clientOutput.println("A4Kliknut");
						}
					}
					break;
				case "B1Kliknut":
						for(ClientHandler klijent: Server.onlineUsers) {
							if(klijent!=this) {
								//svakome sem ovome treba da se update polje
								klijent.clientOutput.println("B1Kliknut");
							}
						}
						break;
				case "B2Kliknut":
					for(ClientHandler klijent: Server.onlineUsers) {
						if(klijent!=this) {
							klijent.clientOutput.println("B2Kliknut");
						}
					}
					break;
				case "B3Kliknut":
					for(ClientHandler klijent: Server.onlineUsers) {
						if(klijent!=this) {
							klijent.clientOutput.println("B3Kliknut");
						}
					}
					break;
				case "B4Kliknut":
					for(ClientHandler klijent: Server.onlineUsers) {
						if(klijent!=this) {
							klijent.clientOutput.println("B4Kliknut");
						}
					}
					break;
				case "C1Kliknut":
						for(ClientHandler klijent: Server.onlineUsers) {
							if(klijent!=this) {
								//svakome sem ovome treba da se update polje
								klijent.clientOutput.println("C1Kliknut");
							}
						}
						break;
				case "C2Kliknut":
					for(ClientHandler klijent: Server.onlineUsers) {
						if(klijent!=this) {
							klijent.clientOutput.println("C2Kliknut");
						}
					}
					break;
				case "C3Kliknut":
					for(ClientHandler klijent: Server.onlineUsers) {
						if(klijent!=this) {
							klijent.clientOutput.println("C3Kliknut");
						}
					}
					break;
				case "C4Kliknut":
					for(ClientHandler klijent: Server.onlineUsers) {
						if(klijent!=this) {
							klijent.clientOutput.println("C4Kliknut");
						}
					}
					break;
				case "D1Kliknut":
						for(ClientHandler klijent: Server.onlineUsers) {
							if(klijent!=this) {
								//svakome sem ovome treba da se update polje
								klijent.clientOutput.println("D1Kliknut");
							}
						}
						break;
				case "D2Kliknut":
					for(ClientHandler klijent: Server.onlineUsers) {
						if(klijent!=this) {
							klijent.clientOutput.println("D2Kliknut");
						}
					}
					break;
				case "D3Kliknut":
					for(ClientHandler klijent: Server.onlineUsers) {
						if(klijent!=this) {
							klijent.clientOutput.println("D3Kliknut");
						}
					}
					break;
				case "D4Kliknut":
					for(ClientHandler klijent: Server.onlineUsers) {
						if(klijent!=this) {
							klijent.clientOutput.println("D4Kliknut");
						}
					}
					break;
				case "AKonacnoUpdate":
					for(ClientHandler klijent: Server.onlineUsers) {
						if(klijent!=this) {
							klijent.clientOutput.println("AKonacnoUpdate");
						}
					}
					break;
				case "BKonacnoUpdate":
					for(ClientHandler klijent: Server.onlineUsers) {
						if(klijent!=this) {
							klijent.clientOutput.println("BKonacnoUpdate");
						}
					}
					break;
				case "CKonacnoUpdate":
					for(ClientHandler klijent: Server.onlineUsers) {
						if(klijent!=this) {
							klijent.clientOutput.println("CKonacnoUpdate");
						}
					}
					break;
				case "DKonacnoUpdate":
					for(ClientHandler klijent: Server.onlineUsers) {
						if(klijent!=this) {
							klijent.clientOutput.println("DKonacnoUpdate");
						}
					}
					break;
				case "KonacnoUpdate":
					for(ClientHandler klijent: Server.onlineUsers) {
						if(klijent!=this) {
							klijent.clientOutput.println("KonacnoUpdate");
						}
					}
					break;
				case "ZavršenaPartija":
					for(ClientHandler klijent: Server.onlineUsers) {
						//svakom klijent handleru kaže da pošalje svom klijentu kad je završena partija
						//trebalo bi da se kaže ko je dobio
						klijent.clientOutput.println("ZavršenaPartija");
						
					}
					break;
				case "updatePoeni":
					int suma=0;
					for(ClientHandler klijent: Server.onlineUsers) {
						suma=suma+klijent.a;
					}
					if(suma%2==0) {
						//znaci da je prvi igrac na potezu
						//i da je on zvao update poeni
						//znaci da bi trebali da se updatuju samo poeni prvog kod svih
						input=clientInput.readLine();
						for(ClientHandler klijent: Server.onlineUsers) {
							klijent.clientOutput.println("updatePoeniPrvog");
							klijent.clientOutput.println(input);
						}
					}
					if(suma%2==1) {
						//znaci da je drugi igrac na potezu
						//i da je on zvao update poeni
						//znaci da bi trebali da se updatuju samo poeni drugog kod svih
						input=clientInput.readLine();
						for(ClientHandler klijent: Server.onlineUsers) {
							klijent.clientOutput.println("updatePoeniDrugog");
							klijent.clientOutput.println(input);
						}
					}
					break;
				case "Izlaz":clientOutput.println("Izlaz");	
							break;
				}
				
			}while(!input.equals("Izlaz"));
			
			Server.onlineUsers.remove(this);
			System.out.println("ClientHandler se ugasio, izbacio klijenta iz liste");
				
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
