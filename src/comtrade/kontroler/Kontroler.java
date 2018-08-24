package comtrade.kontroler;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import comtrade.domen.Grupa;
import comtrade.domen.Grupna_Poruka;
import comtrade.domen.Korisnik;
import comtrade.domen.Poruka;
import comtrade.domen.Zahtev;
import comtrade.forma.FmServer;
import comtrade.niti.NitObradeZahteva;
import comtrade.sistemskaOperacija.OpstaSo;
import comtrade.so.Grupe.DodajKorisnikaUGrupuSo;
import comtrade.so.Grupe.KreirajGrupuSo;
import comtrade.so.Grupe.NapustiGrupuSo;
import comtrade.so.Grupe.UpisiGrupnuPorukuUBazuSo;
import comtrade.so.Grupe.UpisiIdUlaskaUGrupuSo;
import comtrade.so.Grupe.VratiGrupeSo;
import comtrade.so.Grupe.VratiGrupnePorukeSo;
import comtrade.so.Grupe.VratiKorisnikGrupaSo;
import comtrade.so.Grupe.VratiMojeGrupeSo;
import comtrade.so.Grupe.VratiPorukeGrupeSo;
import comtrade.so.Korisnik.IzmenaKorisnikaSo;
import comtrade.so.Korisnik.IzmeniZahtevPrijateljstvaSo;
import comtrade.so.Korisnik.ObrisiPrijateljaSo;
import comtrade.so.Korisnik.ObrisiZahtevPrijateljstvaSo;
import comtrade.so.Korisnik.PostaviPrijateljstvoSo;
import comtrade.so.Korisnik.RegistracijaKorisnikaSo;
import comtrade.so.Korisnik.SnimiLokacijuSlikeSo;
import comtrade.so.Korisnik.VratiListuKorisnikaUGrupiSo;
import comtrade.so.Korisnik.VratiListuPrijateljaSo;
import comtrade.so.Korisnik.VratiListuZahtevaZaPrijateljstvoSo;
import comtrade.so.Korisnik.VratiLokacijuProfilneSlikeSo;
import comtrade.so.Korisnik.VratiSvaPrijateljstvaSo;
import comtrade.so.Korisnik.VratiSveKorisnikeSo;
import comtrade.so.Poruka.PromeniStatusPorukeSo;
import comtrade.so.Poruka.UpisiPorukuUBazuSo;
import comtrade.so.Poruka.VratiSveNeprimljenePorukeSo;
import comtrade.transfer.TransferKlasa;





public class Kontroler {

	
	private NitObradeZahteva noz;
	private static Kontroler instanca;
	private FmServer sf;
	private List<NitObradeZahteva> listaAktivnihKorisnika= new ArrayList<>();
	private Map<String, Object>hm= new HashMap<>();
	private Kontroler() {

	}
	public static Kontroler vratiInstancu() {
		if (instanca == null) {
			instanca = new Kontroler();
		}
		return instanca;
	}
	public void logujSe(Korisnik korisnik) {
		OpstaSo logujSe=new VratiSveKorisnikeSo();
		logujSe.izvrsiSo(korisnik);
	}
	
	public List<Korisnik> vratiSveKorisnike(){
		List<Korisnik> listaKorisnika= new ArrayList<>();
		//Map<String, Object>hm= new HashMap<>();
		hm.put("objekat", new Korisnik());
		OpstaSo vratiSveKorisnike= new VratiSveKorisnikeSo();
		vratiSveKorisnike.izvrsiSo(hm);
		listaKorisnika=(List<Korisnik>) hm.get("lista_objekata");	
		return listaKorisnika;
	}
	
	public List<Grupa> vratiListuGrupa() {
		List<Grupa> listaGrupa= new ArrayList<>();
		hm.put("objekat", new Grupa());
		OpstaSo vratiGrupe= new VratiGrupeSo();
		vratiGrupe.izvrsiSo(hm);
		listaGrupa= (List<Grupa>) hm.get("lista_grupa");
		return listaGrupa;
	}
	
	public List<Grupa> vratiListuMojihGrupa(Grupa g) {
		List<Grupa> listaMojihGrupa= new ArrayList<>();
		hm.put("objekat", g);
		OpstaSo vratiMojeGrupe= new VratiMojeGrupeSo();
		vratiMojeGrupe.izvrsiSo(hm);
		listaMojihGrupa= (List<Grupa>) hm.get("lista_mojih_grupa");
		return listaMojihGrupa;
	}
	
	public List<Korisnik> vratiSpisakPrijateljstava() {
		List<Korisnik> listaPrijateljstava= new ArrayList<>();
		hm.put("objekat", new Korisnik());
		OpstaSo vratiSvaPrijateljstva= new VratiSvaPrijateljstvaSo();
		vratiSvaPrijateljstva.izvrsiSo(hm);
		listaPrijateljstava=(List<Korisnik>) hm.get("lista_zahteva");
		return listaPrijateljstava;
		
	}
	public List<Korisnik> vratiListuZahteva() {
		List<Korisnik> listaZahteva= new ArrayList<>();
		hm.put("objekat", new Korisnik());
		OpstaSo vratiListuZahteva= new VratiListuZahtevaZaPrijateljstvoSo();
		vratiListuZahteva.izvrsiSo(hm);
		listaZahteva=(List<Korisnik>) hm.get("lista_zahteva_za_prijateljstvo");
		return listaZahteva;
	}
	
	
	public List<Korisnik> vratiPrijatelje(Korisnik kRed) {
			List<Korisnik> listaPrijatelja= new ArrayList<>();
			hm.put("objekat", kRed);
			OpstaSo vratiPrijatelje= new VratiListuPrijateljaSo();
			vratiPrijatelje.izvrsiSo(hm);
			listaPrijatelja= (List<Korisnik>) hm.get("lista_prijatelja");
		return listaPrijatelja;
	}
	
	public List<Poruka> vratiSvePoruke(Korisnik k) {
		List<Poruka> listaPoruka= new ArrayList<>();
		hm.put("objekat", k);
		OpstaSo vratiPoruke= new comtrade.so.Poruka.VratiSvePorukeSo();
		vratiPoruke.izvrsiSo(hm);
		listaPoruka= (List<Poruka>) hm.get("lista_poruka");
		return listaPoruka;
	}
	public List<Poruka> vratiSveNeprimljenePoruke(Poruka np) {
		List<Poruka> listaNeprimljenihPoruka= new ArrayList<>();
		hm.put("objekat", np);
		OpstaSo vratiNeprimljenePoruke= new VratiSveNeprimljenePorukeSo();
		vratiNeprimljenePoruke.izvrsiSo(hm);
		listaNeprimljenihPoruka= (List<Poruka>) hm.get("lista_neprocitanih_poruka");
		return listaNeprimljenihPoruka;
	}
	
	
	public List<Grupna_Poruka> vratiListuGrupnihPoruka() {
		List<Grupna_Poruka> listaGrupnihPoruka= new ArrayList<>();
		hm.put("objekat", new Grupna_Poruka());
		OpstaSo vratiGrupnePoruke= new VratiGrupnePorukeSo();
		vratiGrupnePoruke.izvrsiSo(hm);
		listaGrupnihPoruka= (List<Grupna_Poruka>) hm.get("lista_grupnih_poruka");		
		return listaGrupnihPoruka;
	}
	
	public List<Grupa> vratiListuKorisnikGrupa(int idKorisnika4) {
		List<Grupa> listaKorisnikGrupa= new ArrayList<>();
		hm.put("objekat", idKorisnika4);
		OpstaSo vratiKorisnikGrupa= new VratiKorisnikGrupaSo();
		vratiKorisnikGrupa.izvrsiSo(hm);
		listaKorisnikGrupa= (List<Grupa>) hm.get("lista_korisnik_grupa");		
		return listaKorisnikGrupa;
	}
	
	
	public int vratiListuPorukaGrupe(Grupa kg) {
		
		hm.put("objekat", kg);
		OpstaSo vratiPorukeGrupe= new VratiPorukeGrupeSo();
		vratiPorukeGrupe.izvrsiSo(hm);
		int idMax= (int) hm.get("max_poruka_grupe");		
		return idMax;
	}
	
	public List<Korisnik> vratiListuKorisnikaUGrupi(int idGrupeKUG) {
		List<Korisnik> listaKorisnikaUGrupi= new ArrayList<>();
		hm.put("objekat", idGrupeKUG);
		OpstaSo vratiListuKorisnikaUGrupi= new VratiListuKorisnikaUGrupiSo();
		vratiListuKorisnikaUGrupi.izvrsiSo(hm);
		listaKorisnikaUGrupi= (List<Korisnik>) hm.get("lista_korisnika_u_grupi");		
		return listaKorisnikaUGrupi;
	}
	
	public List<Korisnik> vratiLokacijuSLike(Korisnik korSlika1) {
		List<Korisnik> lokacijaProfilneSLike= new ArrayList<>();
		hm.put("objekat",korSlika1);
		OpstaSo vratiLokacijuProfilneSlike= new VratiLokacijuProfilneSlikeSo();
		vratiLokacijuProfilneSlike.izvrsiSo(hm);
		lokacijaProfilneSLike=(List<Korisnik>) hm.get("lokacija_profilne_slike");		
		return lokacijaProfilneSLike;
	}

	
	
	public void sacuvajKorisnika(Korisnik k2) {
		OpstaSo so1= new RegistracijaKorisnikaSo();
		so1.izvrsiSo(k2);
	}
	
	public void kreirajGrupu(Grupa g) {
		OpstaSo kg= new KreirajGrupuSo();
		kg.izvrsiSo(g);
	}
	
	public void dodajKorisnikaUGrupu(Grupa kg) {
		OpstaSo dkSo= new DodajKorisnikaUGrupuSo();
		dkSo.izvrsiSo(kg);
		
	}
	
	public void upisiPorukuUBazu(Poruka p) {
		OpstaSo upisiPoruku= new UpisiPorukuUBazuSo();
		upisiPoruku.izvrsiSo(p);
	}
	
	public void upisiGrupnuPorukuUBazu(Grupna_Poruka gp) {
		OpstaSo upisiGrupPoruku= new UpisiGrupnuPorukuUBazuSo();
		upisiGrupPoruku.izvrsiSo(gp);
		
	}
	
	public void upisiIdUlaskaUGrupu(Grupa gMax) {
		OpstaSo upisiIdUlaskaUGrupu= new UpisiIdUlaskaUGrupuSo();
		upisiIdUlaskaUGrupu.izvrsiSo(gMax);
		
	}
	public void snimiLokacijuSlike(Korisnik korSlika) {
		OpstaSo snimiLokacijuSlike= new SnimiLokacijuSlikeSo();
		snimiLokacijuSlike.izvrsiSo(korSlika);
		
	}
	
	
	public void izmeniKorisnika(Korisnik k3) {
		OpstaSo so2= new IzmenaKorisnikaSo();
		so2.izvrsiSo(k3);
	}
	public void izmeniZahtevPrijateljstva(Korisnik korZahtevPrijatelj) {
		OpstaSo so4= new IzmeniZahtevPrijateljstvaSo();
		so4.izvrsiSo(korZahtevPrijatelj);
		
	}
	public void promeniStatusPoruke(Poruka pp) {
		OpstaSo so5= new PromeniStatusPorukeSo();
		so5.izvrsiSo(pp);
		
	}
	public void obrisiZahtevZaPrijateljstvo(Korisnik korZahtevOdbijen) {
		OpstaSo obrZahtev=new ObrisiZahtevPrijateljstvaSo();
		obrZahtev.izvrsiSo(korZahtevOdbijen);
		
	}
	public void napustiGrupu(Grupa g) {
		OpstaSo napustiGrupu= new NapustiGrupuSo();
		napustiGrupu.izvrsiSo(g);
		
	}
	
	public void obrisiPrijatelja(Korisnik korObrisiPrijatelja) {
		OpstaSo obrisiPrijatelja= new ObrisiPrijateljaSo();
		obrisiPrijatelja.izvrsiSo(korObrisiPrijatelja);
		
	}
	
	public void dodajNovogKorisnika(NitObradeZahteva noz) {
		listaAktivnihKorisnika.add(noz);
		
	}
	
	/*public void prikaziNaServeru(String korIme) {
		sf.postaviText(korIme+"\n");

	}*/
	
	public void postaviFormu(FmServer sf) {
		// TODO Auto-generated method stub
		this.sf=sf;
		
	}
	public void postaviPrijateljstvo(Korisnik k4) {
		OpstaSo so3= new PostaviPrijateljstvoSo(); 
		so3.izvrsiSo(k4);
		
	}
	public void obavestiPrimaoca(TransferKlasa tk, NitObradeZahteva noz, String korImePrimaoca) {
		for(NitObradeZahteva lk:listaAktivnihKorisnika) {
			if(lk.getKorImeNitiObradeZahteva().equals(korImePrimaoca)) {
				System.out.println(korImePrimaoca);
				lk.posalji(tk);	
			}
		}
	}
	public void posaljiPorukuSvima(TransferKlasa tk, NitObradeZahteva noz) {
		for(NitObradeZahteva lak:listaAktivnihKorisnika) {
			if(!lak.equals(noz)) {
				lak.posalji(tk);
			}
		}
		
	}
	public void posaljiPojedinacnuPoruku(TransferKlasa tkPojedicnaPoruka, int primalacPoruke, NitObradeZahteva nitObradeZahteva) {
		for(NitObradeZahteva lak1:listaAktivnihKorisnika) {
			if(lak1.getIdKorisnika4()==primalacPoruke) {
				lak1.posalji(tkPojedicnaPoruka);
				lak1.setStatusPoruke(true);
				
			}System.out.println(lak1.isStatusPoruke());
		}
		
	}
	public void osveziListuPrijateljaPosiljaoca(int idKorisnika, NitObradeZahteva noz) {
		for(NitObradeZahteva lak:listaAktivnihKorisnika) {
			if(lak.getIdKorisnika4()==idKorisnika) {
				lak.vratiListuPrijatelja(idKorisnika);
			}
		}
		
	}
	public void posaljiGrupnuPoruku(TransferKlasa tkgp, NitObradeZahteva nitObradeZahteva) {
		for(NitObradeZahteva lak:listaAktivnihKorisnika) {
			if(!lak.equals(nitObradeZahteva)) {
				lak.posalji(tkgp);
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
