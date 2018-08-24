package comtrade.so.Korisnik;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import comtrade.db.Broker;
import comtrade.domen.OpstiDomen;
import comtrade.sistemskaOperacija.OpstaSo;

public class VratiListuKorisnikaUGrupiSo extends OpstaSo {

	@Override
	public void izvrsiKonkretnuTransakciju(Object obj) {
		// TODO Auto-generated method stub
		HashMap<String, Object> hm= (HashMap<String, Object>) obj;
		int idKUG= (int) hm.get("objekat");
		
		try {
			List<OpstiDomen> lkug= Broker.vratiObjekat().vratiKorisnikeUGrupi(idKUG);
			hm.put("lista_korisnika_u_grupi", lkug);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}

}
