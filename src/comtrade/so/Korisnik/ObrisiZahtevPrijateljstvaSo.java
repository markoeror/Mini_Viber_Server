package comtrade.so.Korisnik;

import comtrade.db.Broker;
import comtrade.domen.Korisnik;
import comtrade.sistemskaOperacija.OpstaSo;

public class ObrisiZahtevPrijateljstvaSo extends OpstaSo{

	@Override
	public void izvrsiKonkretnuTransakciju(Object obj) {
		Korisnik kOdb= (Korisnik) obj;
		Broker.vratiObjekat().ObrisiZahtevZaPrijateljstvo(kOdb);
	}

}
