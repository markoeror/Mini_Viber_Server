package comtrade.so.Grupe;

import comtrade.db.Broker;
import comtrade.domen.Grupa;
import comtrade.sistemskaOperacija.OpstaSo;

public class UpisiIdUlaskaUGrupuSo extends OpstaSo{

	@Override
	public void izvrsiKonkretnuTransakciju(Object obj) {
		Grupa gMax=(Grupa) obj;
		Broker.vratiObjekat().upisiIdUlaskaUGrupu(gMax);
		
	}

}
