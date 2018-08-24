package comtrade.niti;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.Policy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import comtrade.db.Broker;
import comtrade.domen.Grupa;
import comtrade.domen.Grupna_Poruka;
import comtrade.domen.Korisnik;
import comtrade.domen.Poruka;
import comtrade.domen.PosaljiEmail;
import comtrade.domen.Zahtev;
import comtrade.konstante.Konstante;
import comtrade.kontroler.Kontroler;
import comtrade.transfer.TransferKlasa;

public class NitObradeZahteva extends Thread {

	private int idPosiljaocaPrih;
	private String posiljaoc;
	private Korisnik korZahtev;
	private List<Korisnik> listaKorisnikaVracanje;
	private Korisnik k;
	private Socket soket;
	private String korImeNitiObradeZahteva;
	private String korImePrimaoca;
	private int port;
	private int idKorisnika4;
	private List<Korisnik> listaPrijateljstava = new ArrayList<>();
	private List<Korisnik> listaPrijateljaPrava = new ArrayList<>();
	private List<Poruka> listaSvihPoruka = new ArrayList<>();
	private List<Poruka> listaPorukaPojedID;
	private List<Poruka> listaPorukaPojedNeprimljenih;
	private int vecUpisan;
	public int getVecUpisan() {
		return vecUpisan;
	}

	public void setVecUpisan(int vecUpisan) {
		this.vecUpisan = vecUpisan;
	}
	private List<Korisnik> listaKorisnikaUGrupi;
	private List<Grupna_Poruka> listaGrupnihPoruka= new ArrayList<>();
	private int idMax;
	private List<Grupna_Poruka> listaPorukaGrupe= new ArrayList<>();
	private int primalacPoruke;
	private int iDKorisnikNitObradaZahteva;
	private TransferKlasa tklp = new TransferKlasa();
	private TransferKlasa tkPoruke = new TransferKlasa();
	private TransferKlasa tkNeprimljenePoruke = new TransferKlasa();
	private TransferKlasa tklmg= new TransferKlasa();
	private int idPosiljaocaPor;
	private int idPrimaocaPor;
	private int korId;
	private boolean statusPoruke=false;
	private String korisnickoIme4;
	private Map<Integer, List<String>> hm = new HashMap<>();
	private Map<Integer, List<String>> hmnp = new HashMap<>();
	private Map<Integer, List<String>> hmgp= new HashMap<>();
	private List<Grupa> listaGrupa;
	private List<Grupa> listaMojihGrupa;
	private List<Grupa> listaKorisnikGrupa;
	private Grupa kg;
	private Grupa lmg;
	private int idUlaska;

	public void run() {
		while (true) {
			try {
				ObjectInputStream ois = new ObjectInputStream(soket.getInputStream());
				try {
					TransferKlasa tk = (TransferKlasa) ois.readObject();
					obradiZahtevKlijenta(tk);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void obradiZahtevKlijenta(TransferKlasa tk) {
		switch (tk.getOperacija()) {

		case Konstante.LOGOVANJE:

			Korisnik k = (Korisnik) tk.getKlijentObjekat();
			String korIme = k.getKorisnickoIme();
			String sifra = k.getSifra();
			korImeNitiObradeZahteva = korIme;

			TransferKlasa tk2 = new TransferKlasa();
			List<Korisnik> listaKorisnika = (List<Korisnik>) Kontroler.vratiInstancu().vratiSveKorisnike();

			for (Korisnik k3 : listaKorisnika) {
				if (k3.getKorisnickoIme().equals(korIme) && (k3.getSifra().equals(sifra))) {
					// tk2.setServerPoruka_odgovorInt(Konstante.USPESNO);

					if (k3.getPravaPristupa() == 0) {
						tk2.setServerPoruka_odgovorInt(Konstante.KORISNIK);
						tk2.setServerPoruka_odgovorId(k3.getIdKorisnika());
						idKorisnika4 = k3.getIdKorisnika();
						String ime4 = k3.getIme();
						String prezime4 = k3.getPrezime();
						korisnickoIme4 = k3.getKorisnickoIme();
						String datumRodjenja4 = k3.getDatumRodjenja();
						String sifra4 = k3.getSifra();
						String email4 = k3.getEmail();
						String oMeni4 = k3.getOMeni();
						Korisnik korisnikProfil = new Korisnik(idKorisnika4, ime4, prezime4, korisnickoIme4, sifra4,
								datumRodjenja4, email4, oMeni4);
						tk2.setServer_ObjekatOdgovor(korisnikProfil);
						// Kontroler.vratiInstancu().prikaziNaServeru("Korisnik '"+korisnickoIme4+"' se
						// konektovao na server.");
						Kontroler.vratiInstancu().dodajNovogKorisnika(this);
					} else
						tk2.setServerPoruka_odgovorInt(Konstante.ADMINISTRATOR);
					break;
				} else
					tk2.setServerPoruka_odgovorInt(Konstante.NEUSPESNO);
			}
			posalji(tk2);
			break;

		case Konstante.REGISTRACIJA:

			TransferKlasa tk3 = new TransferKlasa();
			Korisnik k2 = (Korisnik) tk.getKlijentObjekat();
			List<Korisnik> listaKorisnikaReg = (List<Korisnik>) Kontroler.vratiInstancu().vratiSveKorisnike();
			for (Korisnik rk : listaKorisnikaReg) {
				if (k2.getKorisnickoIme().equals(rk.getKorisnickoIme()) || k2.getEmail().equals(rk.getEmail())) {
					tk3.setServerPoruka_odgovor("Korisnicko ime ili email vec postoje, molimo unesite drugo.");
					break;
				} else
					Kontroler.vratiInstancu().sacuvajKorisnika(k2);
				tk3.setOperacija(Konstante.REGISTRACIJA);
				tk3.setServerPoruka_odgovor("Korisnik uspesno dodat");
				break;
			}
			/*
			 * Kontroler.vratiInstancu().sacuvajKorisnika(k2);
			 * tk3.setServerPoruka_odgovor("Korisnik uspesno dodat");
			 */
			posalji(tk3);
			break;

		case Konstante.IZMENI_PROFIL:
			TransferKlasa tk4 = new TransferKlasa();
			Korisnik k3 = (Korisnik) tk.getKlijentObjekat();
			Kontroler.vratiInstancu().izmeniKorisnika(k3);
			tk4.setServerPoruka_odgovor("Korisnik izmenjen");
			posalji(tk4);
			break;

		case Konstante.VRATI_SVE_KORISNIKE:
			vratiSveKorisnike(Konstante.VRATI_SVE_KORISNIKE);
			
			break;

		case Konstante.POSALJI_ZAHTEV:
			// Korisnik k4= (Korisnik) tk.getKlijentObjekat();
			// int idPosiljaoca=k4.getIdPosiljaoca();
			// int idPrimaoca=k4.getIdPrimaoca();
			int vrstaPrijateljstva = Konstante.POSLAT;
			Zahtev z = (Zahtev) tk.getKlijentObjekat();
			Korisnik primalac = z.getPrimalac();
			Korisnik posiljalac = z.getPosiljalac();
			int idPrimaoca = primalac.getIdKorisnika();
			int idPosiljaoca = posiljalac.getIdKorisnika();
			korZahtev = new Korisnik(idPosiljaoca, idPrimaoca, vrstaPrijateljstva);
			listaPrijateljstava = Kontroler.vratiInstancu().vratiSpisakPrijateljstava();
			;
			if (listaPrijateljstava.isEmpty()) {
				Kontroler.vratiInstancu().postaviPrijateljstvo(korZahtev);
				korImePrimaoca = primalac.getKorisnickoIme();
				Korisnik kZahtev = new Korisnik(idPosiljaoca, idPrimaoca);
				TransferKlasa tk6 = new TransferKlasa();
				tk6.setServer_ObjekatOdgovor(kZahtev);
				tk6.setOperacija(Konstante.PRIMLJEN);
				tk6.setServerPoruka_odgovor("zahtev poslat od korisnika " + korImeNitiObradeZahteva);
				Kontroler.vratiInstancu().obavestiPrimaoca(tk6, this, korImePrimaoca);
				break;
			} else {
				for (Korisnik lp : listaPrijateljstava) {
					if (idPosiljaoca == lp.getIdPosiljaoca() && idPrimaoca == lp.getIdPrimaoca()) {
						System.out.println("vec prijatelji");
						break;
					} else if (idPosiljaoca == lp.getIdPrimaoca() && idPrimaoca == lp.getIdPosiljaoca()) {
						System.out.println("vec ste prijatelji");
						break;
					} else {
						Kontroler.vratiInstancu().postaviPrijateljstvo(korZahtev);
						korImePrimaoca = primalac.getKorisnickoIme();
						Zahtev kZahtev = new Zahtev(posiljalac, primalac);
						TransferKlasa tk6 = new TransferKlasa();
						tk6.setServer_ObjekatOdgovor(kZahtev);
						tk6.setOperacija(Konstante.PRIMLJEN);
						tk6.setServerPoruka_odgovor("zahtev poslat od korisnika " + korImeNitiObradeZahteva);
						Kontroler.vratiInstancu().obavestiPrimaoca(tk6, this, korImePrimaoca);
						break;
					}
				}
				break;
			}

		case Konstante.PRIHVACEN:
			// Ovde stize odgovor od primaoca zahteva za prijateljstvo, u zavisnosti od
			// odgovora
			// stavlja se vrstaPrijateljstva na prijatelj ili se brisi zahtev iz baze
			Zahtev kPrihvacen = (Zahtev) tk.getKlijentObjekat();
			Korisnik posiljaoc = kPrihvacen.getPosiljalac();
			Korisnik primaoc = kPrihvacen.getPrimalac();
			int idPrimaocaPrih = primaoc.getIdKorisnika();
			idPosiljaocaPrih = posiljaoc.getIdKorisnika();
			int vrstaPrijatPrihvaceno = Konstante.PRIJATELJ;
			Korisnik KorZahtevPrijatelj = new Korisnik(idPosiljaocaPrih, idPrimaocaPrih, vrstaPrijatPrihvaceno);
			Kontroler.vratiInstancu().izmeniZahtevPrijateljstva(KorZahtevPrijatelj);
			Kontroler.vratiInstancu().osveziListuPrijateljaPosiljaoca(idPosiljaocaPrih, this);

			break;

		case Konstante.PRIHVACEN_NAKNADNO:
			Korisnik kPrihvacen1 = (Korisnik) tk.getKlijentObjekat();
			int idPrimaocaPrih1 = kPrihvacen1.getIdPrimaoca();
			idPosiljaocaPrih = kPrihvacen1.getIdKorisnika();
			int vrstaPrijatPrihvaceno1 = Konstante.PRIJATELJ;
			Korisnik KorZahtevPrijatelj1 = new Korisnik(idPosiljaocaPrih, idPrimaocaPrih1, vrstaPrijatPrihvaceno1);
			Kontroler.vratiInstancu().izmeniZahtevPrijateljstva(KorZahtevPrijatelj1);
			Kontroler.vratiInstancu().osveziListuPrijateljaPosiljaoca(idPosiljaocaPrih, this);

			break;

		case Konstante.ODBIJEN:
			Korisnik kOdbijen = (Korisnik) tk.getKlijentObjekat();
			int idPrimaocaOdb = kOdbijen.getIdPrimaoca();
			int idPosiljaocaOdb = kOdbijen.getIdKorisnika();
			int vrstaPrijatOdb = Konstante.ODBIJEN;
			Korisnik KorZahtevOdbijen = new Korisnik(idPosiljaocaOdb, idPrimaocaOdb);
			Kontroler.vratiInstancu().obrisiZahtevZaPrijateljstvo(KorZahtevOdbijen);
			break;

		case Konstante.OBRISI_PRIJATELJA:
			Zahtev z2= (Zahtev) tk.getKlijentObjekat();
			Korisnik posiljalac2=z2.getPosiljalac();
			Korisnik primalac2=z2.getPrimalac();
			int idPosiljaoca2=posiljalac2.getIdKorisnika();
			int idPrimaoca2=primalac2.getIdKorisnika();
			Korisnik korObrisiPrijatelja= new Korisnik(idPosiljaoca2,idPrimaoca2);
			Kontroler.vratiInstancu().obrisiPrijatelja(korObrisiPrijatelja);
			//vratiListuPrijatelja(idPosiljaoca2);
			Kontroler.vratiInstancu().osveziListuPrijateljaPosiljaoca(idPrimaoca2, this);
			Kontroler.vratiInstancu().osveziListuPrijateljaPosiljaoca(idPosiljaoca2, this);
	
			
			break;
		case Konstante.POSALJI_PORUKU_SVIMA:
			String poruka = (String) tk.getKlijentObjekat();
			System.out.println("poruka je " + poruka);
			TransferKlasa tkPoruka = new TransferKlasa();
			tkPoruka.setServerPoruka_odgovor(korImeNitiObradeZahteva + " kaze" + poruka);
			tkPoruka.setOperacija(Konstante.POSALJI_PORUKU_SVIMA);
			Kontroler.vratiInstancu().posaljiPorukuSvima(tkPoruka, this);
			break;

		case Konstante.POSALJI_PORUKU:
			
			Poruka p = (Poruka) tk.getKlijentObjekat();
			idPosiljaocaPor = p.getIdPosiljaoca();
			System.out.println(idPosiljaocaPor);
			idPrimaocaPor = p.getIdPrimaoca();
			System.out.println(idPrimaocaPor);
			String pojedinacnaPoruka = p.getP();
			System.out.println(pojedinacnaPoruka);
			int statusPrimljena=Konstante.PORUKA_PRIMLJENA;
			int statusNijePrimljena=Konstante.PORUKA_NIJE_PRIMLJENA;
			Poruka porukaPrimljena= new Poruka(idPosiljaocaPor, idPrimaocaPor, pojedinacnaPoruka, statusPrimljena);
			Poruka porukaNijePrimljena=new Poruka(idPosiljaocaPor, idPrimaocaPor, pojedinacnaPoruka, statusNijePrimljena);
			TransferKlasa tkPojedicnaPoruka = new TransferKlasa();
			tkPojedicnaPoruka.setServerPoruka_odgovorId(idPosiljaocaPor);
			tkPojedicnaPoruka.setServerPoruka_odgovor(korImeNitiObradeZahteva + "  : " + pojedinacnaPoruka);
			tkPojedicnaPoruka.setOperacija(Konstante.POSALJI_PORUKU);
			
			Kontroler.vratiInstancu().posaljiPojedinacnuPoruku(tkPojedicnaPoruka, idPrimaocaPor, this);	
			if(statusPoruke==true) {
			Kontroler.vratiInstancu().upisiPorukuUBazu(porukaPrimljena);}
			else {
				Kontroler.vratiInstancu().upisiPorukuUBazu(porukaNijePrimljena);
			}
			break;
		
		
		case Konstante.VRATI_LISTU_PRIJATELJA:
			Korisnik klp = (Korisnik) tk.getKlijentObjekat();
			vratiListuPrijatelja(idKorisnika4);
			break;

		case Konstante.VRATI_LISTU_ZAHTEVA:
			Korisnik klz = (Korisnik) tk.getKlijentObjekat();
			vratiListuZahteva(klz);
			break;
			
		case Konstante.VRATI_LISTU_PORUKA:
			Korisnik kListaPoruka = (Korisnik) tk.getKlijentObjekat();
			vratiListuPoruka(idKorisnika4);
			break;
		
		case Konstante.VRATI_LISTU_NEPRIMLJENIH_PORUKA:		
			for (Korisnik lpp : listaPrijateljaPrava) {
				String imePrijatelja = lpp.getKorisnickoIme();
				List<String> listaPorukaPojed = new ArrayList<>();
				List<String> listaNeprimljenihPoruka= new ArrayList<>();
				int idPrijatelja = lpp.getIdKorisnika();	
				Poruka np= new Poruka(idKorisnika4,idPrijatelja,Konstante.PORUKA_NIJE_PRIMLJENA);
				listaPorukaPojedNeprimljenih = Kontroler.vratiInstancu().vratiSveNeprimljenePoruke(np);	
				for (Poruka lnp : listaPorukaPojedNeprimljenih) {
					if (lnp.getIdPosiljaoca() == idPrijatelja) {
						String porukaPoj = imePrijatelja + "  : " + lnp.getP();
						System.out.println(imePrijatelja + " kaze " + lnp.getP());
						listaNeprimljenihPoruka.add(porukaPoj);
					} /*else {
						String porukaPoj1 = korisnickoIme4 + "   : " + lnp.getP();
						System.out.println(korisnickoIme4 + " kaze : " + lnp.getP());
						listaNeprimljenihPoruka.add(porukaPoj1);
					}*/
				}
				hmnp.put(lpp.getIdKorisnika(), listaNeprimljenihPoruka);
			}
			tkNeprimljenePoruke.setOperacija(Konstante.VRATI_LISTU_NEPRIMLJENIH_PORUKA);;
			tkNeprimljenePoruke.setServer_ObjekatOdgovor(hmnp);
			posalji(tkNeprimljenePoruke);
			
			break;
			
		case Konstante.PORUKA_PRIMLJENA:
			Poruka pp= (Poruka) tk.getKlijentObjekat();
			Kontroler.vratiInstancu().promeniStatusPoruke(pp);
			break;
		case Konstante.KREIRAJ_GRUPU:
			Grupa g= (Grupa) tk.getKlijentObjekat();
			String nazivGrupe=g.getNazivGrupe();
			Kontroler.vratiInstancu().kreirajGrupu(g);
			TransferKlasa tkg= new TransferKlasa();
			tkg.setOperacija(Konstante.KREIRAJ_GRUPU);
			tkg.setServerPoruka_odgovor("Grupa uspesno kreirana.");
			posalji(tkg);
			break;
			
		case Konstante.VRATI_LISTU_GRUPA:
			
			listaGrupa= Kontroler.vratiInstancu().vratiListuGrupa();
			TransferKlasa tklg= new TransferKlasa();
			tklg.setOperacija(Konstante.VRATI_LISTU_GRUPA);
			tklg.setServer_ObjekatOdgovor(listaGrupa);
			posalji(tklg);
			break;
			
		case Konstante.DODAJ_GRUPU:
			kg= (Grupa) tk.getKlijentObjekat();
			Broker.setNoz(this);
			Kontroler.vratiInstancu().dodajKorisnikaUGrupu(kg);
			
			if(vecUpisan!=2) {
			int idG=kg.getIdGrupe();
			int idKor=korId;
		
			idMax= Kontroler.vratiInstancu().vratiListuPorukaGrupe(kg);
			Grupa gMax= new Grupa(idKor,idG,idMax);
			Kontroler.vratiInstancu().upisiIdUlaskaUGrupu(gMax);
			}
			vratiListuMojihGrupa(kg);
			break;
			
		case Konstante.VRATI_LISTU_MOJIH_GRUPA:
			lmg= (Grupa) tk.getKlijentObjekat();
			vratiListuMojihGrupa(lmg);
			break;
			
		case Konstante.NAPUSTI_GRUPU:
			Grupa gnp= (Grupa) tk.getKlijentObjekat();
			Kontroler.vratiInstancu().napustiGrupu(gnp);
			String poruka1="Napustili ste grupu";
			break;
			
		case Konstante.POSALJI_GRUPNU_PORUKU:
			Grupna_Poruka gp= (Grupna_Poruka) tk.getKlijentObjekat();
			int idGrupe=gp.getIdGrupe();
			int idKorG=gp.getIdKorisnika();
			String porukaG=gp.getPoruka();
			String imePosljGP=gp.getIme();
			String grupnaPorukaPosalji=imePosljGP+"  : "+porukaG;
			TransferKlasa tkgp= new TransferKlasa();
			tkgp.setOperacija(Konstante.POSALJI_GRUPNU_PORUKU);
			tkgp.setServerPoruka_odgovor(grupnaPorukaPosalji);
			String grup_poruka=gp.getPoruka();
			Kontroler.vratiInstancu().posaljiGrupnuPoruku(tkgp,this);
			Kontroler.vratiInstancu().upisiGrupnuPorukuUBazu(gp);
			
			break;
			
		case Konstante.VRATI_LISTU_GRUPNIH_PORUKA:
			vratiListuGrupnihPoruka();
			break;
			
		case Konstante.VRATI_SVE_KORISNIKE_ADMIN:
			vratiSveKorisnike(Konstante.VRATI_SVE_KORISNIKE_ADMIN);
			break;
			
		case Konstante.VRATI_LISTU_GRUPA_ADMIN:
			listaGrupa= Kontroler.vratiInstancu().vratiListuGrupa();
			TransferKlasa tklga= new TransferKlasa();
			tklga.setOperacija(Konstante.VRATI_LISTU_GRUPA_ADMIN);
			tklga.setServer_ObjekatOdgovor(listaGrupa);
			posalji(tklga);
			break;
			
		case Konstante.VRATI_LISTU_KORISNIKA_U_GRUPI:
			Grupa grupa=(Grupa) tk.getKlijentObjekat();
			int idGrupeKUG=grupa.getIdKorisnika();
			listaKorisnikaUGrupi=Kontroler.vratiInstancu().vratiListuKorisnikaUGrupi(idGrupeKUG);
			TransferKlasa tklkug= new TransferKlasa();
			tklkug.setOperacija(Konstante.VRATI_LISTU_KORISNIKA_U_GRUPI);
			tklkug.setServer_ObjekatOdgovor(listaKorisnikaUGrupi);
			posalji(tklkug);			
			break;
			
		case Konstante.POSALJI_SIFRU_NA_EMAIL:
			String korImeSifra=(String) tk.getKlijentObjekat();
			listaKorisnika=Kontroler.vratiInstancu().vratiSveKorisnike();
			for(Korisnik lkf:listaKorisnika) {
				if(korImeSifra.equals(lkf.getKorisnickoIme())) {
					String emailSifra=lkf.getEmail();
					String sifraSifra=lkf.getSifra();
				//test	PosaljiEmail posaljiSifru= new PosaljiEmail("eror.bg@gmail.com", "Sifra korisnika"+korImeSifra, "123");
					PosaljiEmail posaljiSifru= new PosaljiEmail(emailSifra, "Sifra korisnika"+korImeSifra, "Vasa sifra je :  "+sifraSifra);
				}
			}
			break;
			
		case Konstante.SNIMI_LOKACIJU_SLIKE:
			Korisnik korSlika= (Korisnik) tk.getKlijentObjekat();
			Kontroler.vratiInstancu().snimiLokacijuSlike(korSlika);
			break;
			
		case Konstante.VRATI_LOKACIJU_PROFILNE_SLIKE:
			Korisnik korSlika1 = new Korisnik(idKorisnika4);
			List<Korisnik> slika= Kontroler.vratiInstancu().vratiLokacijuSLike(korSlika1);
			TransferKlasa tkSlika= new TransferKlasa();
			tkSlika.setOperacija(Konstante.VRATI_LOKACIJU_PROFILNE_SLIKE);
			tkSlika.setServer_ObjekatOdgovor(slika);
			posalji(tkSlika);
			break;
		default:
			break;
		}

	}

	

	
	private void vratiSveKorisnike(int vratiSveKorisnike) {
		listaKorisnikaVracanje = (List<Korisnik>) Kontroler.vratiInstancu().vratiSveKorisnike();
		TransferKlasa tk5 = new TransferKlasa();
		tk5.setOperacija(vratiSveKorisnike);
		tk5.setServer_ObjekatOdgovor(listaKorisnikaVracanje);
		posalji(tk5);
		
	}

	public boolean isStatusPoruke() {
		return statusPoruke;
	}

	public void setStatusPoruke(boolean statusPoruke) {
		this.statusPoruke = statusPoruke;
	}

	private void vratiListuMojihGrupa(Grupa g) {
		listaMojihGrupa=Kontroler.vratiInstancu().vratiListuMojihGrupa(g);
		TransferKlasa tklmg= new TransferKlasa();
		tklmg.setOperacija(Konstante.VRATI_LISTU_MOJIH_GRUPA);
		tklmg.setServer_ObjekatOdgovor(listaMojihGrupa);
		posalji(tklmg);
		
	}

	public void vratiListuZahteva(Korisnik klz) {
		TransferKlasa tklz = new TransferKlasa();
		korId = klz.getIdKorisnika();
		List<Zahtev> listaZahtevaPrava = new ArrayList<>();
		System.out.println("id korisnika kome se vraca lista zahteva je: " + korId);
		List<Korisnik> listaZahteva = Kontroler.vratiInstancu().vratiListuZahteva();
		if (listaZahteva.isEmpty()) {
			tklz.setOperacija(Konstante.VRATI_LISTU_ZAHTEVA);
			tklz.setServer_ObjekatOdgovor(listaZahteva);
			posalji(tklz);
		} else {
			for (Korisnik lp : listaZahteva) {
				if (korId == lp.getIdKorisnika()) {
					for (Korisnik lz : listaKorisnikaVracanje) {
						if (lp.getIdPrimaoca() == lz.getIdKorisnika()) {
							posiljaoc = lz.getIme() + " " + lz.getPrezime() + " " + lz.getKorisnickoIme();
							Zahtev z = new Zahtev(posiljaoc, lp.getIdKorisnika());
							listaZahtevaPrava.add(z);
						}
					}
					Zahtev z = new Zahtev(posiljaoc, lp.getIdPrimaoca());
					// Korisnik k = new Korisnik(lp.getIdKorisnika(), lp.getIdPrimaoca());

					listaZahtevaPrava.add(z);
				} else if (korId == lp.getIdPrimaoca()) {
					for (Korisnik lz : listaKorisnikaVracanje) {
						if (lp.getIdKorisnika() == lz.getIdKorisnika()) {
							String posiljaoc = lz.getIme() + " " + lz.getPrezime() + " " + lz.getKorisnickoIme();
							Zahtev z = new Zahtev(posiljaoc, lp.getIdKorisnika());
							listaZahtevaPrava.add(z);
						}
					}

				}
			}
			tklz.setOperacija(Konstante.VRATI_LISTU_ZAHTEVA);
			tklz.setServer_ObjekatOdgovor(listaZahtevaPrava);
			posalji(tklz);
		}
	}

	public void vratiListuPrijatelja(int id) {
		String red = "";
		int korId = id;
		System.out.println("id korisnika:  " + korId);
		listaPrijateljstava = Kontroler.vratiInstancu().vratiSpisakPrijateljstava();
		if (listaPrijateljstava.isEmpty()) {
			tklp.setOperacija(Konstante.VRATI_LISTU_PRIJATELJA);
			tklp.setServer_ObjekatOdgovor(listaPrijateljaPrava);
		} else {
			List<Integer> listaIdPrijatelja = new ArrayList<>();
			for (Korisnik lp : listaPrijateljstava) {
				// if(korId==lp.getIdKorisnika() || (korId==lp.getIdPrimaoca())) {

				if (korId == lp.getIdKorisnika()) {
					int idPrijatelja = lp.getIdPrimaoca();
					listaIdPrijatelja.add(idPrijatelja);
				} else if (korId == lp.getIdPrimaoca()) {
					int idPrijatelja1 = lp.getIdKorisnika();
					listaIdPrijatelja.add(idPrijatelja1);
				}
			}
			if (!listaIdPrijatelja.isEmpty()) {
				red += "(" + listaIdPrijatelja.get(0);
				System.out.println("prijatelji" + red);
				for (int i = 1; i < listaIdPrijatelja.size(); i++) {
					red += "," + listaIdPrijatelja.get(i);
				}
				red += ")";
				System.out.println(red);
				Korisnik kRed = new Korisnik(red);
				System.out.println("Idevi prijatelja su " + red);

				listaPrijateljaPrava = Kontroler.vratiInstancu().vratiPrijatelje(kRed);
			}else if(listaIdPrijatelja.isEmpty()) {
				Korisnik kRed= new Korisnik();
				listaPrijateljaPrava= new ArrayList<>();
			}
			tklp.setOperacija(Konstante.VRATI_LISTU_PRIJATELJA);
			tklp.setServer_ObjekatOdgovor(listaPrijateljaPrava);
			posalji(tklp);
		}

	}

	private void vratiListuPoruka(int idK) {
		Poruka p = new Poruka(idK, idK);
		List<String> listaPoruka = new ArrayList<>();
		// hm.put("objekat", listaPoruka);

		for (Korisnik lpp : listaPrijateljaPrava) {
			String imePrijatelja = lpp.getKorisnickoIme();
			List<String> listaPorukaPojed = new ArrayList<>();
			int idPrijatelja = lpp.getIdKorisnika();
			Korisnik k = new Korisnik(idKorisnika4, idPrijatelja);
			listaPorukaPojedID = Kontroler.vratiInstancu().vratiSvePoruke(k);			
			for (Poruka lpp1 : listaPorukaPojedID) {
				if (lpp1.getIdPosiljaoca() == idPrijatelja) {
					String porukaPoj = imePrijatelja + "  : " + lpp1.getP();
					System.out.println(imePrijatelja + " kaze " + lpp1.getP());
					listaPorukaPojed.add(porukaPoj);
				} else {
					String porukaPoj1 = korisnickoIme4 + "   : " + lpp1.getP();
					System.out.println(korisnickoIme4 + " kaze : " + lpp1.getP());
					listaPorukaPojed.add(porukaPoj1);
				}
			}
			hm.put(lpp.getIdKorisnika(), listaPorukaPojed);
		}
		tkPoruke.setOperacija(Konstante.VRATI_LISTU_PORUKA);
		tkPoruke.setServer_ObjekatOdgovor(hm);
		posalji(tkPoruke);

	}
	
	private void vratiListuGrupnihPoruka() {
		listaGrupnihPoruka= Kontroler.vratiInstancu().vratiListuGrupnihPoruka();
		listaKorisnikGrupa=Kontroler.vratiInstancu().vratiListuKorisnikGrupa(idKorisnika4);
		TransferKlasa tkgp= new TransferKlasa();
		for(Grupa lmg:listaKorisnikGrupa) {
			int idGrupe=lmg.getIdGrupe();
			int idKoris=idKorisnika4;
			int idUlaska=lmg.getIdUlaska();
			
			System.out.println("id grupe je "+idGrupe);
			List<String> listaGrupnigPorukaString= new ArrayList<>();
			for(Grupna_Poruka gp:listaGrupnihPoruka) {
				if(idGrupe==gp.getIdGrupe()) {
					if(gp.getIdGrupnaPoruka()>idUlaska) {
					String porukaG=gp.getIme()+"  : "+gp.getPoruka();
					System.out.println(gp.getIme()+"  : "+gp.getPoruka());
					listaGrupnigPorukaString.add(porukaG);				
				}
				}
			}
			hmgp.put(idGrupe, listaGrupnigPorukaString);
		}
		tkgp.setOperacija(Konstante.VRATI_LISTU_GRUPNIH_PORUKA);
		tkgp.setServer_ObjekatOdgovor(hmgp);
		posalji(tkgp);
		
	}
	
	public void posalji(TransferKlasa tk) {
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(soket.getOutputStream());
			oos.writeObject(tk);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public int getiDKorisnikNitObradaZahteva() {
		return iDKorisnikNitObradaZahteva;
	}

	public void setiDKorisnikNitObradaZahteva(int iDKorisnikNitObradaZahteva) {
		this.iDKorisnikNitObradaZahteva = iDKorisnikNitObradaZahteva;
	}

	public int getPrimalacPoruke() {
		return primalacPoruke;
	}

	public void setPrimalacPoruke(int primalacPoruke) {
		this.primalacPoruke = primalacPoruke;
	}

	
	
	public int getIdPosiljaocaPor() {
		return idPosiljaocaPor;
	}

	public void setIdPosiljaocaPor(int idPosiljaocaPor) {
		this.idPosiljaocaPor = idPosiljaocaPor;
	}

	public int getIdKorisnika4() {
		return idKorisnika4;
	}

	public void setIdKorisnika4(int idKorisnika4) {
		this.idKorisnika4 = idKorisnika4;
	}

	public int getIdPrimaocaPor() {
		return idPrimaocaPor;
	}

	public void setIdPrimaocaPor(int idPrimaocaPor) {
		this.idPrimaocaPor = idPrimaocaPor;
	}

	public int getIdPosiljaocaPrih() {
		return idPosiljaocaPrih;
	}

	public void setIdPosiljaocaPrih(int idPosiljaocaPrih) {
		this.idPosiljaocaPrih = idPosiljaocaPrih;
	}

	private void setKorZahtev(Korisnik korZahtev) {
		this.korZahtev = korZahtev;
	}

	public Korisnik getKorZahtev() {
		return korZahtev;
	}

	public Socket getSoket() {
		return soket;
	}

	public void setSoket(Socket soket) {
		this.soket = soket;
	}

	public String getKorImeNitiObradeZahteva() {
		return korImeNitiObradeZahteva;
	}

	public void setKorImeNitiObradeZahteva(String korImeNitiObradeZahteva) {
		this.korImeNitiObradeZahteva = korImeNitiObradeZahteva;
	}

	public String getKorImePrimaoca() {
		return korImePrimaoca;
	}

	public void setKorImePrimaoca(String korImePrimaoca) {
		this.korImePrimaoca = korImePrimaoca;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	

}
