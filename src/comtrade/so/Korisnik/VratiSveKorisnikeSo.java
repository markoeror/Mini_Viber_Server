package comtrade.so.Korisnik;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import comtrade.db.Broker;
import comtrade.domen.Korisnik;
import comtrade.domen.OpstiDomen;
import comtrade.domen.Zahtev;
import comtrade.sistemskaOperacija.OpstaSo;


public class VratiSveKorisnikeSo extends OpstaSo{

	@Override
	public void izvrsiKonkretnuTransakciju(Object obj) {
		HashMap<String, Object> hm= (HashMap<String, Object>) obj;
		Korisnik k= (Korisnik) hm.get("objekat");
		try {
			List<OpstiDomen> lo=Broker.vratiObjekat().vratiSveKorisnike(k);
			hm.put("lista_objekata", lo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
