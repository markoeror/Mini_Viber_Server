package comtrade.so.Grupe;

import comtrade.db.Broker;
import comtrade.domen.Grupa;
import comtrade.sistemskaOperacija.OpstaSo;

public class DodajKorisnikaUGrupuSo extends OpstaSo {

	@Override
	public void izvrsiKonkretnuTransakciju(Object obj) {
		Grupa kg= (Grupa) obj;
		Broker.vratiObjekat().DodajKorisnikaUGrupu(kg);
	}

}
