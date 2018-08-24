package comtrade.so.Grupe;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import comtrade.db.Broker;
import comtrade.domen.Grupa;
import comtrade.domen.Korisnik;
import comtrade.domen.OpstiDomen;
import comtrade.sistemskaOperacija.OpstaSo;

public class VratiGrupeSo extends OpstaSo {

	@Override
	public void izvrsiKonkretnuTransakciju(Object obj) {
		HashMap<String, Object> hm= (HashMap<String, Object>) obj;
		Grupa g= (Grupa) hm.get("objekat");
		try {
			List<OpstiDomen> lg=Broker.vratiObjekat().vratiGrupe(g);
			hm.put("lista_grupa", lg);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

}
