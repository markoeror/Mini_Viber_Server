package comtrade.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import comtrade.domen.Grupa;
import comtrade.domen.Grupna_Poruka;
import comtrade.domen.Korisnik;
import comtrade.domen.OpstiDomen;
import comtrade.domen.Poruka;
import comtrade.niti.NitObradeZahteva;

public class Broker {

	public static Broker objekat;
	private Connection konekcija;
	private Korisnik k;
	private static NitObradeZahteva noz;
	
	

	private Broker() {
		ucitajDriver();
		try {
			konekcija = DriverManager.getConnection("jdbc:mysql://localhost/miniViber", "eror", "1234");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void ucitajDriver() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static Broker vratiObjekat() {
		if (objekat == null) {
			objekat = new Broker();
		}
		return objekat;
	}

	public void pokreniTransakciju() {
		try {
			konekcija.setAutoCommit(false);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void potvrdiTransakciju() {
		try {
			konekcija.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void ponistiTransakciju() {
		try {
			konekcija.rollback();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public List<OpstiDomen> vratiSveKorisnike(OpstiDomen k) throws SQLException {
		List<OpstiDomen> lista = new ArrayList<>();
		String sqlVratiKorisnike = "SELECT * FROM `korisnik`";
		Statement st = konekcija.createStatement();
		ResultSet rs = st.executeQuery(sqlVratiKorisnike);
		lista = k.srediSelect(rs);

		return lista;
	}

	public List<OpstiDomen> vratiSvaPrijateljstva(OpstiDomen k) throws SQLException {
		List<OpstiDomen> lista = new ArrayList<>();
		String sqlVratiKorisnike = "SELECT * FROM `listaprijatelja` WHERE vrstaPrijateljstva=20";
		Statement st = konekcija.createStatement();
		ResultSet rs = st.executeQuery(sqlVratiKorisnike);
		lista = k.srediSelectZahteva(rs);

		return lista;

	}

	public List<OpstiDomen> vratiListuZahtevaZaPrijateljstvo(Korisnik k) throws SQLException {
		List<OpstiDomen> lista = new ArrayList<>();
		String sqlVratiZahteve = "SELECT * FROM `listaprijatelja` WHERE vrstaPrijateljstva=12";
		Statement st = konekcija.createStatement();
		ResultSet rs = st.executeQuery(sqlVratiZahteve);
		lista = k.srediSelectZahteva(rs);

		return lista;
	}

	public List<OpstiDomen> vratiPrijatelje(Korisnik k) throws SQLException {
		List<OpstiDomen> listaPrijatelja = new ArrayList<>();
		String sqlVratiPrijatelje = "SELECT * FROM `korisnik` WHERE idKorisnika IN" + k.getRed();
		System.out.println("poslat upit");
		// a ovde
		Statement st = konekcija.createStatement();
		System.out.println("/////");
		ResultSet rs = st.executeQuery(sqlVratiPrijatelje);
		System.out.println("/1/1//1/1/1/1//1/1/1");

		listaPrijatelja = k.srediListuPrijatelja(rs);
		return listaPrijatelja;
	}

	public void sacuvajKorisnika(OpstiDomen od) {
		String sqlSacuvajKorisnika = "INSERT INTO " + k.vratiNazivTabele() + " " + k.vratiZaInsert();
		Statement st;
		try {
			st = konekcija.createStatement();
			st.executeUpdate(sqlSacuvajKorisnika);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void KreirajGrupu(Grupa g) {
		String sqlKreirajGrupu = "INSERT INTO `grupa` ( `nazivGrupe`) " +g.vratiZaInsert();
		Statement st;
		try {
			st = konekcija.createStatement();
			st.executeUpdate(sqlKreirajGrupu);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void upisiPorukuUBazu(Poruka p) {
		String sqlUpisiPorukuUBazu = "INSERT INTO `privatnaporuka` (`idKorisnika`, `poruka`, `idPrimaoca`,status_poruke) VALUES"
				+ p.vratiZaInsert();
		Statement st;
		try {
			st = konekcija.createStatement();
			st.executeUpdate(sqlUpisiPorukuUBazu);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void upisiGrupnuPorukuUBazu(Grupna_Poruka gp) {
		String sqlUpisiGrupnuPorukuUBazu = "INSERT INTO `grupnaporuka` (`idKorisnika`, `idGrupe`, `poruka`) VALUES "+gp.vratiZaInsert();
		Statement st;
		try {
			st = konekcija.createStatement();
			st.executeUpdate(sqlUpisiGrupnuPorukuUBazu);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void izmenaKorisnika(OpstiDomen k) {
		String sqlIzmenaKorisnika = "UPDATE " + k.vratiZaUpdate(k);
		Statement st;
		try {
			st = konekcija.createStatement();
			st.executeUpdate(sqlIzmenaKorisnika);
		} catch (SQLException e) {
			System.out.println("Greska pri konektovanju na bazu!!!");
			e.printStackTrace();
		}
	}

	public void postaviPrijateljstvo(OpstiDomen k) {
		String sqlpostaviPrijateljstvo = "INSERT INTO `listaprijatelja` (`idKorisnika`, `idPrijatelja`, `vrstaPrijateljstva`) "
				+ k.vratiZaZahtevPrijateljstva();
		Statement st;
		try {
			st = konekcija.createStatement();
			st.executeUpdate(sqlpostaviPrijateljstvo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void IzmeniZahtevPrijateljstva(OpstiDomen k) {
		String sqlIzmenaZahteva = "UPDATE " + k.vratiZaUpdateZahteva(k);
		Statement st;
		try {
			st = konekcija.createStatement();
			st.executeUpdate(sqlIzmenaZahteva);
		} catch (SQLException e) {
			System.out.println("Greska pri konektovanju na bazu!!!");
			e.printStackTrace();
		}

	}

	public void ObrisiZahtevZaPrijateljstvo(Korisnik k) {
		String sqlObrisiZahtev = "DELETE FROM `listaprijatelja` WHERE `listaprijatelja`.`idKorisnika` = "
				+ k.getIdKorisnika() + " AND `listaprijatelja`.`idPrijatelja` = " + k.getIdPrimaoca();
		Statement st;
		try {
			st = konekcija.createStatement();
			st.executeUpdate(sqlObrisiZahtev);
		} catch (SQLException e) {
			System.out.println("Greska pri konektovanju na bazu!!!");
			e.printStackTrace();
		}

	}

	public List<OpstiDomen> vratiSvePoruke(Korisnik k) throws SQLException {
		List<OpstiDomen> listaPoruka = new ArrayList<>();
		String sqlVratiPoruke = "SELECT idKorisnika, poruka, idPrimaoca FROM privatnaporuka WHERE "+ k.vratiZaPoruke();
		System.out.println("poslat upit za poruke");
		Statement st = konekcija.createStatement();
		System.out.println("/////");
		ResultSet rs = st.executeQuery(sqlVratiPoruke);
		System.out.println("/1/1//1/1/1/1//1/1/1");

		listaPoruka = k.srediListuPoruka(rs);
		return listaPoruka;
	}
	public List<OpstiDomen> vratiSveNeprocitanePoruke(Poruka np)  throws SQLException{
		List<OpstiDomen> listaNeprocitanihPoruka = new ArrayList<>();
		String sqlVratiNeprimljenePoruke = "SELECT idKorisnika, poruka, idPrimaoca FROM privatnaporuka WHERE "+ np.vratiZaNeprimljenePoruke();
		System.out.println("poslat upit za neprimljene poruke");
		Statement st = konekcija.createStatement();
		System.out.println("/////");
		ResultSet rs = st.executeQuery(sqlVratiNeprimljenePoruke);
		System.out.println("/1/1//1/1/1/1//1/1/1");

		listaNeprocitanihPoruka = np.srediListuPoruka(rs);
		return listaNeprocitanihPoruka;
	}


	public List<OpstiDomen> vratiGrupe(Grupa g) throws SQLException{
		List<OpstiDomen> listaGrupa= new ArrayList<>();
		String sqlVratiGrupe= "SELECT * FROM `grupa` ";
		System.out.println("poslat upit za grupe");
		Statement st = konekcija.createStatement();	
		ResultSet rs = st.executeQuery(sqlVratiGrupe);
		System.out.println("/g/g");
		listaGrupa = g.srediSelect(rs);
		return listaGrupa;
	}
	
	public List<OpstiDomen> vratiMojeGrupe(Grupa g) throws SQLException {
		List<OpstiDomen> listaMojihGrupa= new ArrayList<>();
		String sqlVratiListuGrupa= "SELECT korisnik_grupa.idGrupe, grupa.nazivGrupe from korisnik_grupa INNER JOIN grupa on korisnik_grupa.idGrupe=grupa.idGrupe where korisnik_grupa.idKorisnika="+g.getIdKorisnika();
		System.out.println("poslat upit za moje grupe");
		Statement st = konekcija.createStatement();	
		ResultSet rs = st.executeQuery(sqlVratiListuGrupa);
		System.out.println("/g/g");
		listaMojihGrupa = g.srediSelectZaMojeGrupe(rs);
		return listaMojihGrupa;
	}
	
	public List<OpstiDomen> vratiKorisnikGrupe(int idK) throws SQLException {
		List<OpstiDomen> listaKorisnikGrupa= new ArrayList<>();
		String sqllistaKorisnikGrupa= "SELECT * FROM `korisnik_grupa` WHERE idKorisnika= "+idK;
		System.out.println("poslat upit za moje grupe");
		Statement st = konekcija.createStatement();	
		ResultSet rs = st.executeQuery(sqllistaKorisnikGrupa);
		System.out.println("/g/g");
		listaKorisnikGrupa = srediKorisnikGrupa(rs);
		return listaKorisnikGrupa;
	}
	
	private List<OpstiDomen> srediKorisnikGrupa(ResultSet rs) throws SQLException {
		List<OpstiDomen> listaKorisnikGrupa= new ArrayList<>();
		while(rs.next()) {
			int idKorisnika=rs.getInt("idKorisnika");
			int idGrupe=rs.getInt("idGrupe");
			int idUlaska=rs.getInt("idUlaska");
			Grupa grupa= new Grupa(idKorisnika, idGrupe,idUlaska);
			listaKorisnikGrupa.add(grupa);
		}
		return listaKorisnikGrupa;
	}

	public List<OpstiDomen> vratiGrupnePoruke(Grupna_Poruka gp) throws SQLException {
		List<OpstiDomen> listaGrupnihPoruka= new ArrayList<>();
		String sqlVratiListuGrupnihPoruka= "SELECT grupnaporuka.idGrupnaPoruka, grupnaporuka.idGrupe, grupnaporuka.poruka, korisnik.ime,grupnaporuka.idKorisnika from grupnaporuka INNER JOIN korisnik ON korisnik.idKorisnika=grupnaporuka.idKorisnika";
		System.out.println("poslat upit za moje grupne poruke");
		Statement st = konekcija.createStatement();	
		ResultSet rs = st.executeQuery(sqlVratiListuGrupnihPoruka);
		System.out.println("/g/g");
		listaGrupnihPoruka = gp.srediSelect(rs);
		return listaGrupnihPoruka;
	}
	public int vratiPorukeGrupe(Grupa g) throws SQLException{
		int idMax;
		String sqlVratiListuPorukaGrupe= "SELECT MAX(idGrupnaPoruka) FROM `grupnaporuka` WHERE idGrupe="+g.getIdGrupe();
		System.out.println("poslat upit za poruke grupe");
		Statement st = konekcija.createStatement();	
		ResultSet rs = st.executeQuery(sqlVratiListuPorukaGrupe);
		System.out.println("/g/g");
		idMax = g.srediSelectPorukeGrupe(rs);
		return idMax;
	}

	public void DodajKorisnikaUGrupu(Grupa g) {
		String sqlDodajKorisnikaUGrupu = "INSERT INTO `korisnik_grupa` (`idKorisnika`, `idGrupe`) VALUES" +g.dodajUGrupu()  ;
		Statement st;
		try {
			st = konekcija.createStatement();
			st.executeUpdate(sqlDodajKorisnikaUGrupu);
		} catch (Exception e) {	
			System.out.println("Vec upisan korisnik");
			noz.setVecUpisan(2);
			e.printStackTrace();
			
		}
		
	}
	
	

	public void upisiIdUlaskaUGrupu(Grupa gMax) {
		String sqlUpisiIdUlaskaUGrupu = "UPDATE `korisnik_grupa` SET idUlaska="+gMax.getIdUlaska()+" "+ srediZaUpisIdUlaskaUGrupu(gMax);
		Statement st;
		try {
			st = konekcija.createStatement();
			st.executeUpdate(sqlUpisiIdUlaskaUGrupu);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private String srediZaUpisIdUlaskaUGrupu(Grupa gMax) {
	
		return  "WHERE idKorisnika="+gMax.getIdKorisnika()+" AND idGrupe= "+gMax.getIdGrupe();
	}

	public void napustiGrupu(Grupa g) {
		String sqlNapustiGrupu = "DELETE FROM `korisnik_grupa` WHERE `korisnik_grupa`.`idKorisnika` = "+g.getIdKorisnika()+" AND `korisnik_grupa`.`idGrupe` = "+g.getIdGrupe();
		Statement st;
		try {
			st = konekcija.createStatement();
			st.executeUpdate(sqlNapustiGrupu);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void obrisiPrijatelja(Korisnik kop) {
		String sqlObrisiPrijatelja = "DELETE FROM `listaprijatelja` WHERE idKorisnika="+kop.getIdKorisnika()+" and `idPrijatelja`="+kop.getIdPrimaoca()+" or idKorisnika="+kop.getIdPrimaoca()+" and idPrijatelja="+kop.getIdKorisnika();
		Statement st;
		try {
			st = konekcija.createStatement();
			st.executeUpdate(sqlObrisiPrijatelja);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	

	public void promeniStatusPoruke(Poruka p) {
		String sqlPromeniStatusPoruka = "UPDATE `privatnaporuka` SET `status_poruke`=41 WHERE "+p.vratiZaUpdate(p) ;
		Statement st;
		try {
			st = konekcija.createStatement();
			st.executeUpdate(sqlPromeniStatusPoruka);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static NitObradeZahteva getNoz() {
		return noz;
	}

	public static void setNoz(NitObradeZahteva noz) {
		Broker.noz = noz;
	}

	public List<OpstiDomen> vratiKorisnikeUGrupi(int idKUG) throws SQLException{
		List<OpstiDomen> listaKorisnikaUGrupi= new ArrayList<>();
		String sqllistaKorisnikaUGrupi= "SELECT korisnik.idKorisnika,korisnik.ime,korisnik.prezime,korisnik.korisnickoIme FROM korisnik INNER JOIN korisnik_grupa on korisnik_grupa.idKorisnika=korisnik.idKorisnika WHERE korisnik_grupa.idGrupe="+idKUG;
		System.out.println("poslat upit za korisnike u grupi");
		Statement st = konekcija.createStatement();	
		ResultSet rs = st.executeQuery(sqllistaKorisnikaUGrupi);
		System.out.println("/g/g");
		listaKorisnikaUGrupi = srediListuKorisnikaUGrupi(rs);
		return listaKorisnikaUGrupi;
	}

	private List<OpstiDomen> srediListuKorisnikaUGrupi(ResultSet rs) throws SQLException {
		List<OpstiDomen> listaKorisnikaUgrupi= new ArrayList<>();
		while(rs.next()) {
			int idKorisnika=rs.getInt("idKorisnika");
			String ime=rs.getString("ime");
			String prezime=rs.getString("prezime");
			String korisnickoIme=rs.getString("korisnickoIme");
			Korisnik k= new Korisnik(idKorisnika,ime,prezime,korisnickoIme);
			listaKorisnikaUgrupi.add(k);
		}
		return listaKorisnikaUgrupi;
	}

	public void snimiLokacijuSlike(Korisnik k) {
		String sqlSnimiLokacijuSlike = "UPDATE `korisnik` SET `slika`="+srediLokacijuSLike(k);
		Statement st;
		try {
			st = konekcija.createStatement();
			st.executeUpdate(sqlSnimiLokacijuSlike);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private String srediLokacijuSLike(Korisnik k) {
		
		return "'"+k.getLokacija()+"' WHERE idKorisnika='"+k.getIdKorisnika()+"'";
	}

	public List<OpstiDomen> vratiLokacijuProfilneSlike(Korisnik k) throws SQLException {
		List<OpstiDomen> lokacijaProfilneSlike= new ArrayList<>();
		String sqlLokacijaProfilneSlike= "SELECT `slika` FROM `korisnik` WHERE idKorisnika="+k.getIdKorisnika();
		System.out.println("poslat upit za lokaciju profilne slike");
		Statement st = konekcija.createStatement();	
		ResultSet rs = st.executeQuery(sqlLokacijaProfilneSlike);
		System.out.println("/g/g");
		lokacijaProfilneSlike = srediZaProfilnuSliku(rs);
		return lokacijaProfilneSlike;
	}

	private List<OpstiDomen> srediZaProfilnuSliku(ResultSet rs) throws SQLException {
		List<OpstiDomen> lokacijaProfilneSlike= new ArrayList<>();
		while(rs.next()) {
			String slika=rs.getString("slika");
			Korisnik k= new Korisnik(slika);
			lokacijaProfilneSlike.add(k);
		}
		return lokacijaProfilneSlike;
	}


	

	

	

	

	

	
	

	

	

}
