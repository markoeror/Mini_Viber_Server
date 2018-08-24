package comtrade.so.Korisnik;

import comtrade.db.Broker;
import comtrade.domen.Korisnik;
import comtrade.sistemskaOperacija.OpstaSo;


public class IzmenaKorisnikaSo extends OpstaSo{

	@Override
	public void izvrsiKonkretnuTransakciju(Object obj) {
		Korisnik k= (Korisnik) obj;
		Broker.vratiObjekat().izmenaKorisnika(k);
	}

}
