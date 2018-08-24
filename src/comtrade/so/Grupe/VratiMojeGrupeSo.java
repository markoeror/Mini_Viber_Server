package comtrade.so.Grupe;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import comtrade.db.Broker;
import comtrade.domen.Grupa;
import comtrade.domen.OpstiDomen;
import comtrade.sistemskaOperacija.OpstaSo;

public class VratiMojeGrupeSo extends OpstaSo {

	@Override
	public void izvrsiKonkretnuTransakciju(Object obj) {
		HashMap<String, Object> hm= (HashMap<String, Object>) obj;
		Grupa mg= (Grupa) hm.get("objekat");
		try {
			List<OpstiDomen> lmg=Broker.vratiObjekat().vratiMojeGrupe(mg);
			hm.put("lista_mojih_grupa", lmg);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
