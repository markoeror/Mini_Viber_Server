package comtrade.so.Poruka;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import comtrade.db.Broker;
import comtrade.domen.OpstiDomen;
import comtrade.domen.Poruka;
import comtrade.sistemskaOperacija.OpstaSo;

public class VratiSveNeprimljenePorukeSo extends OpstaSo{

	@Override
	public void izvrsiKonkretnuTransakciju(Object obj) {
		HashMap<String, Object> hm= (HashMap<String, Object>) obj;
		Poruka np=(Poruka) hm.get("objekat");
		
		try {
			List<OpstiDomen> lNepPoruka= Broker.vratiObjekat().vratiSveNeprocitanePoruke(np);
			hm.put("lista_neprocitanih_poruka", lNepPoruka);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
