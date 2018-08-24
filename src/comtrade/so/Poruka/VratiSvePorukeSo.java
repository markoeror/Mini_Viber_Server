package comtrade.so.Poruka;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import comtrade.db.Broker;
import comtrade.domen.Korisnik;
import comtrade.domen.OpstiDomen;
import comtrade.domen.Poruka;
import comtrade.sistemskaOperacija.OpstaSo;

public class VratiSvePorukeSo extends OpstaSo {

	@Override
	public void izvrsiKonkretnuTransakciju(Object obj) {
		HashMap<String, Object> hm= (HashMap<String, Object>) obj;
		Korisnik k= (Korisnik) hm.get("objekat");
		
		try {
			List<OpstiDomen> lporuka= Broker.vratiObjekat().vratiSvePoruke(k);
			hm.put("lista_poruka", lporuka);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
