package comtrade.so.Poruka;

import comtrade.db.Broker;
import comtrade.domen.Poruka;
import comtrade.sistemskaOperacija.OpstaSo;

public class PromeniStatusPorukeSo extends OpstaSo{

	@Override
	public void izvrsiKonkretnuTransakciju(Object obj) {
		Poruka p= (Poruka) obj;
		Broker.vratiObjekat().promeniStatusPoruke(p);
		
	}

}
