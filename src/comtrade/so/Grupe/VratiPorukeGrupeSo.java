package comtrade.so.Grupe;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import comtrade.db.Broker;
import comtrade.domen.Grupa;
import comtrade.domen.Grupna_Poruka;
import comtrade.domen.OpstiDomen;
import comtrade.sistemskaOperacija.OpstaSo;

public class VratiPorukeGrupeSo extends OpstaSo{

	@Override
	public void izvrsiKonkretnuTransakciju(Object obj) {
		HashMap<String, Object> hm= (HashMap<String, Object>) obj;
		Grupa g=  (Grupa) hm.get("objekat");
		
		try {
			int idMax= Broker.vratiObjekat().vratiPorukeGrupe(g);
			hm.put("max_poruka_grupe", idMax);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
