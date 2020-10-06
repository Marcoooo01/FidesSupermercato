package com.fides.supermercato.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fides.supermercato.dao.CarrelloDAO;
import com.fides.supermercato.dao.PersonaDAO;
import com.fides.supermercato.dao.ProdottoDAO;
import com.fides.supermercato.dao.TipologiaDAO;
import com.fides.supermercato.model.CarrelloDBO;
import com.fides.supermercato.model.PersonaDBO;
import com.fides.supermercato.model.ProdottoDBO;

@Component
public class PersonaService {

	Scanner scan = new Scanner(System.in);
	
	@Autowired
	CarrelloDBO carrello;
	@Autowired
	PersonaDBO persona;
	
	@Autowired
	CarrelloDAO carrelloDAO;
	@Autowired
	PersonaDAO personaDAO;
	@Autowired
	ProdottoDAO prodottoDAO;
	@Autowired
	TipologiaDAO tipologiaDAO;
	
	@Autowired
	CarrelloService carrelloService;

	//PROFILAZIONE CON ID INSERITO DALLA PERSONA
	public PersonaDBO getPersona(Integer idInserito) throws SQLException {
		persona = personaDAO.findById(idInserito);
		return persona;
	}

	//INTERFACCIA PER GESTIRE LE AZIONI CHE PUO' FARE LA PERSONA DI TIPO CLIENTE
	public void azioniCliente(CarrelloDBO carrello) throws SQLException{
		int a; //FLAG CHE CONTROLLA IL DO WHILE
		carrelloService.mettiInCarrello(carrello);
		do {
			System.out.println("Cosa vuoi fare? \n"
					+ "1. Inserisci prodotto. \n"
					+ "2. Rimuovi prodotto. \n"
					+ "3. Visualizza prodotti in carrello.");
			int scelta = scan.nextInt();
			switch(scelta) {
			case 1:
				carrelloService.mettiInCarrello(carrello);
				break;
			case 2:
				carrelloService.togliDaCarrello(carrello);
				break;
			case 3:
				prodottoDAO.findProdottiInCarrello(carrello.getId());  //CONTROLLO DEI PRODOTTI DOVE L' ID_Carrello
				break;												   //E' UGUALE A QUELLO TROVATO (getCarrelloFree)
			default:
				System.out.println("Scelta non disponibile!");
			}
			System.out.println("Vuoi continuare? 1:SI / 2:NO");
			a = scan.nextInt();
		}while(a == 1);
	}

	public void azioniProprietario() throws SQLException{
		Scanner scanner = new Scanner(System.in);
		System.out.println("Seleziona un carrello:");
		int idCarrello = scan.nextInt();
		int d;
		do {
			tipologiaDAO.findTipi();
			System.out.println("Quale tipo vuoi prendere?");
			String tipo = scanner.nextLine();
			for(int i = 0; i < 2; i++) {
				prodottoDAO.findByTipoInScaffale(tipo);
				System.out.println("Seleziona un prodotto:");
				int idProdotto = scan.nextInt();
				prodottoDAO.inserisciProdottoInCarrello(idProdotto, idCarrello);
			}
			prodottoDAO.findProdottiInCarrello(idCarrello);
			System.out.println("Vuoi selezionare altri prodotti? 1:SI / 2:NO");
			d = scan.nextInt();
		}while(d == 1);
	}

	public void azioniLadro() throws SQLException{
		Scanner scanner = new Scanner(System.in);
		System.out.println("Selezione un carrello:");
		Integer idCarrello = scan.nextInt();
		int d;
		do {
			System.out.println("Cosa vuoi fare? \n"
					+ "1. Rubare prodotto. \n"
					+ "2. Sostituire prodotti.");
			int scelta = scan.nextInt();
			switch(scelta) {
			case 1:
				List<ProdottoDBO> spesatipoalimentare = new ArrayList<ProdottoDBO>();
				spesatipoalimentare = prodottoDAO.findByTipoInCarrello(idCarrello, "Alimentari");
				if(spesatipoalimentare.isEmpty()) {
					System.out.println("Non puoi rubare");
					break;
				}else {
					prodottoDAO.findProdottiInCarrello(idCarrello);	
					System.out.println("Seleziona un prodotto da rubare:");
					int idProdotto = scan.nextInt();
					prodottoDAO.togliProdottoDaCarrello(idProdotto);
				}break;
			case 2: 
				List<ProdottoDBO> spesatipoconsumo = new ArrayList<ProdottoDBO>();
				spesatipoconsumo = prodottoDAO.findByTipoInCarrello(idCarrello,"Consumo");
				if(spesatipoconsumo.isEmpty() || spesatipoconsumo.size() < 2 ) {
					System.out.println("Non puoi sostituire");
					break;
				}else {
					for(ProdottoDBO item : spesatipoconsumo) {
						System.out.println(item.toStringDisp());
					}
					for(int i = 0; i < 2; i++) {
						System.out.println("Seleziona un prodotto:");
						int idProdotto = scan.nextInt();
						prodottoDAO.togliProdottoDaCarrello(idProdotto);
					}
					String tipo = "Alimentari";
					for(int i = 0; i < 2; i++) {
						prodottoDAO.findByTipoInScaffale(tipo);
						System.out.println("Seleziona un prodotto:");
						int idProdotto = scan.nextInt();
						prodottoDAO.inserisciProdottoInCarrello(idProdotto, idCarrello);
					}
					prodottoDAO.findProdottiInCarrello(idCarrello);	
				}break;
			default:
				System.out.println("Scelta non disponibile!");
			}
			System.out.println("Vuoi continuare? 1:SI / 2:NO");
			d = scan.nextInt();
		}while(d == 1);
	}
}