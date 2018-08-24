package comtrade.so.Korisnik;

import comtrade.db.Broker;
import comtrade.domen.Korisnik;
import comtrade.sistemskaOperacija.OpstaSo;

public class ObrisiPrijateljaSo extends OpstaSo{

	@Override
	public void izvrsiKonkretnuTransakciju(Object obj) {
		Korisnik korObrPrijatelja= (Korisnik) obj;
		Broker.vratiObjekat().obrisiPrijatelja(korObrPrijatelja);
		
	}

}
