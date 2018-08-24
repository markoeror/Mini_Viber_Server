package comtrade.so.Grupe;

import comtrade.db.Broker;
import comtrade.domen.Grupna_Poruka;
import comtrade.sistemskaOperacija.OpstaSo;

public class UpisiGrupnuPorukuUBazuSo extends OpstaSo{

	@Override
	public void izvrsiKonkretnuTransakciju(Object obj) {
		Grupna_Poruka gp= (Grupna_Poruka) obj;
		Broker.vratiObjekat().upisiGrupnuPorukuUBazu(gp);
		
		
	}

}
