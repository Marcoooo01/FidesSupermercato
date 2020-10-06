package com.fides.supermercato.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fides.supermercato.dao.CarrelloDAO;
import com.fides.supermercato.dao.ProdottoDAO;
import com.fides.supermercato.model.CarrelloDBO;
import com.fides.supermercato.model.ProdottoDBO;

@Component
public class CarrelloService {

	Scanner scan = new Scanner(System.in);
	
	@Autowired
	ProdottoDAO prodottoDAO;
	@Autowired
	CarrelloDAO carrelloDAO;

	//METODO CHE DATO UN ID PERSONA TROVA IL PRIMO CARRELLO LIBERO E LO ASSOCIA AL CLIENTE

	public CarrelloDBO getCarrelloFree(int idPersona) throws SQLException{
		CarrelloDBO c = carrelloDAO.findAllVoid().get(0);
		c.setId_persona(idPersona);
		carrelloDAO.update(c);
		return c;
	}

	//METODO CHE MOSTRA LA LISTA DI CARRELLI DOVE ID_PERSONA NON E' NULL
	public void getCarrelloOccupato() throws SQLException{
		List<CarrelloDBO> listc = carrelloDAO.findAllNotVoid();
		for(CarrelloDBO item: listc) {
			System.out.println(item.toString());
		}
	}

	public void mettiInCarrello(CarrelloDBO carrello) throws SQLException{
		prodottoDAO.findProdottiDisp(); //CONTROLLO DEI PRODOTTI DOVE L' ID_Carrello E' NULL
		System.out.println("Seleziona prodotto:");
		int idProdotto = scan.nextInt();
		prodottoDAO.inserisciProdottoInCarrello(idProdotto, carrello.getId());
	}

	public void togliDaCarrello(CarrelloDBO carrello) throws SQLException{
		prodottoDAO.findProdottiInCarrello(carrello.getId()); //CONTROLLO DEI PRODOTTI DOVE L' ID_Carrello 
		System.out.println("Seleziona prodotto:");			  //E' UGUALE A QUELLO TROVATO (getCarrelloFree)
		int idProdotto = scan.nextInt();
		prodottoDAO.togliProdottoDaCarrello(idProdotto);
	}

	public boolean contenutoCarrello (CarrelloDBO c) throws SQLException{
		List<ProdottoDBO> list = carrelloDAO.prodotti(c.getId());
		if (list.isEmpty()) {
			System.out.println("il carrello è vuoto");
			return false;
		}
		System.out.println("il contenuto del carrello è:"); 
		for(ProdottoDBO p : list) {
			System.out.println(p.toStringDisp());
		}
		System.out.println("Scegli un prodotto");
		return true;
	}	

	public void findCarrelli() throws SQLException{
		List<CarrelloDBO> listc = carrelloDAO.findAllVoid();
		for(CarrelloDBO c : listc) {
			if(contenutoCarrello(c)) {
				System.out.println(c.toString());
			}
		} 
	}
}	