package comtrade.so.Grupe;

import comtrade.db.Broker;
import comtrade.domen.Grupa;
import comtrade.sistemskaOperacija.OpstaSo;

public class KreirajGrupuSo extends OpstaSo{

	@Override
	public void izvrsiKonkretnuTransakciju(Object obj) {
		Grupa g=(Grupa) obj;
		Broker.vratiObjekat().KreirajGrupu(g);
	}

	
}
