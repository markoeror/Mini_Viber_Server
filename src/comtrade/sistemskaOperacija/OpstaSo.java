package comtrade.sistemskaOperacija;

import comtrade.db.Broker;

public abstract class OpstaSo {

	
	public void izvrsiSo(Object obj) {
		try {
			pokreniTransakciju();
			izvrsiKonkretnuTransakciju(obj);
			potvrdiTransakciju();
			
		} catch (Exception e) { // u slucaju da se ne izvrsi cela transakcija da se rollbekuje nazad
			System.out.println("Greska u transakciji sa bazom");
			
			ponistiTransakciju();
		}
		
	}
	public abstract void izvrsiKonkretnuTransakciju(Object obj);  // abstraktra klasa

	private void pokreniTransakciju() {
		Broker.vratiObjekat().pokreniTransakciju();
		
	}
	private void potvrdiTransakciju() {
		Broker.vratiObjekat().potvrdiTransakciju();
	}
	
	private void ponistiTransakciju() {
		Broker.vratiObjekat().ponistiTransakciju();
	}
}
