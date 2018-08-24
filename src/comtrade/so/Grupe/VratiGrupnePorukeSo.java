package comtrade.so.Grupe;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import comtrade.db.Broker;
import comtrade.domen.Grupna_Poruka;
import comtrade.domen.OpstiDomen;
import comtrade.sistemskaOperacija.OpstaSo;

public class VratiGrupnePorukeSo extends OpstaSo {

	@Override
	public void izvrsiKonkretnuTransakciju(Object obj) {
		HashMap<String, Object> hm= (HashMap<String, Object>) obj;
		Grupna_Poruka gp=  (Grupna_Poruka) hm.get("objekat");
		
		try {
			List<OpstiDomen> lGrupnihPoruka= Broker.vratiObjekat().vratiGrupnePoruke(gp);
			hm.put("lista_grupnih_poruka", lGrupnihPoruka);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
