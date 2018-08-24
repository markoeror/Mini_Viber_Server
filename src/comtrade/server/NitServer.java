package comtrade.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import comtrade.niti.NitObradeZahteva;

public class NitServer extends Thread implements IPortServera{

	
	public void run() {
		pokreniServer();
	}

	private void pokreniServer() {
		try {
			ServerSocket ss= new ServerSocket(PORT);
			System.out.println("Server je pokrenut i moze prihvatiti klijenta");
			while(true) {
				Socket soket=ss.accept();
				NitObradeZahteva noz=new NitObradeZahteva();
				noz.setSoket(soket);
				System.out.println(soket);
				noz.start();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
