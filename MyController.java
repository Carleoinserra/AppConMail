package com.example.demo;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.mail.MessagingException;

@Controller
public class MyController {
	
	/*
	 * Creiamo un oggetto di tipo dipJdbcTemplate
	 */
	dipJdbcTemplate d1;
	ArrayList<dip> lista = new ArrayList<>();
	/*
	 * Questo oggetto lo iniettiamo nel controller tramite costruttore
	 */
	
	@Autowired
	EmailService emailservice;
	
	public MyController(dipJdbcTemplate d1) {
		this.d1 = d1;
	}
	
	
	@GetMapping("/form")
	public String getForm() {
		//d1.delete("Bianchi");
		
		
		return "form";
		
	}
	/*
	 * Il metodo submit riceve i dati dal form 
	 */
	@PostMapping("/submit")
	public String getDip(@RequestParam("nome") String nome,
			@RequestParam("mansione") String mansione,
			@RequestParam("stipendio") String stipendio) {
		// chiamiamo il metodo insertDip su d1 e li passiamo i dati ottenuti dal form
		int stipendioD = Integer.parseInt(stipendio);
		int rows = d1.insertDip(nome, mansione, stipendioD);
		System.out.println("Sono cambiate n: " + rows + "righe");
		
		
		
		
		return "form";
	}
	
	@GetMapping("/")
	public String listaDip(Model m1) {
		
		lista = d1.getLista();
		
		m1.addAttribute("lista", lista);
		
		
		return "lista";
	}
	
	int somma = 0;
	
	@PostMapping("/buy")
	public String buy(Model m1,
	@RequestParam("num") ArrayList<Integer> num) throws MessagingException
	{
		lista = d1.getLista();
		ArrayList <String> nomi = new ArrayList <>();
		
		
		
		for (int i = 0; i < num.size(); i++ ) {
			
			if (num.get(i) != 0)
			{
				d1.updateDip(num.get(i), lista.get(i).nome);
				
				somma += num.get(i) * lista.get(i).stipendio;
				nomi.add(lista.get(i).nome);
			
			}
			}
		
		m1.addAttribute("nomi", nomi);
		m1.addAttribute("somma", somma);
		
		
		
		
		return "recap";
		
		
		
	}
	@ResponseBody
	@PostMapping("/conferma")
	public String conferma(
			@RequestParam("mail") String mail) throws MessagingException {
		
		emailservice.sendEmailWithImage(mail, "acquisto dipendenti", ("Somma da pagare " + somma));
		
		
		
		return "Acquisto effettuato correttamente";
	}

}
