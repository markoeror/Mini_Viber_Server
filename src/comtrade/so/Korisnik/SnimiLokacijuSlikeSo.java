package comtrade.so.Korisnik;

import comtrade.db.Broker;
import comtrade.domen.Korisnik;
import comtrade.sistemskaOperacija.OpstaSo;

public class SnimiLokacijuSlikeSo extends OpstaSo{

	@Override
	public void izvrsiKonkretnuTransakciju(Object obj) {
		Korisnik k=(Korisnik) obj;
		Broker.vratiObjekat().snimiLokacijuSlike(k);
		
	}

}
