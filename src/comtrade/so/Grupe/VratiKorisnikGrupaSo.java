package comtrade.so.Grupe;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import comtrade.db.Broker;
import comtrade.domen.Grupna_Poruka;
import comtrade.domen.OpstiDomen;
import comtrade.sistemskaOperacija.OpstaSo;

public class VratiKorisnikGrupaSo extends OpstaSo{

	@Override
	public void izvrsiKonkretnuTransakciju(Object obj) {
		HashMap<String, Object> hm= (HashMap<String, Object>) obj;
		int idK= (int) hm.get("objekat");
		
		try {
			List<OpstiDomen> lGrupnihPoruka= Broker.vratiObjekat().vratiKorisnikGrupe(idK);
			hm.put("lista_korisnik_grupa", lGrupnihPoruka);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
