package comtrade.so.Korisnik;

import comtrade.db.Broker;
import comtrade.domen.Korisnik;
import comtrade.sistemskaOperacija.OpstaSo;

public class IzmeniZahtevPrijateljstvaSo extends OpstaSo{

	@Override
	public void izvrsiKonkretnuTransakciju(Object obj) {
		Korisnik kz= (Korisnik) obj;
		Broker.vratiObjekat().IzmeniZahtevPrijateljstva(kz);
	}

}
