package com.example.demo;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class dipJdbcTemplate {
	// richimamo un oggetto JdbcTemplate
	private JdbcTemplate jdbcTemplateObject;
	
	
	/*
	 * Andiamo a iniettare questo oggetto nella classe dipJdbcTemplate
	 * tramite il metodo set
	 */
	@Autowired
    public void setJdbcTemplateObject(JdbcTemplate jdbcTemplateObject) {
        this.jdbcTemplateObject = jdbcTemplateObject;
    }
	
	
	public int insertDip(String nome, String mansione, int stipendio) {
		/*
		 * con inserDip andiamo a inserire un nuovo dipedente passando i parametri ottenuti
		 * alla query tramite un preapred statement
		 */
		String query = "INSERT INTO dipendenti (nome,  mansione, stipendio) VALUES (?, ?, ?)";
		return jdbcTemplateObject.update(query, nome, mansione, stipendio);
		
	}
	
	public int delete(String nome) {
		
		String query = "DELETE FROM dipendenti WHERE nome = ?";
		return jdbcTemplateObject.update(query, nome);
	}
	
	public ArrayList <dip> getLista(){
		// seleziona tutti i record da eventi
		String query = "SELECT * FROM dipendenti";
		
		// il metodo esegue la query e come secondo parametro crea un result set extractor
		 return jdbcTemplateObject.query(query, new ResultSetExtractor<ArrayList<dip>>() {
            // l'oggetto resultSetExtractor ha il metodo extractData che deve essere obbligatoriamente implementato
			@Override
			public ArrayList<dip> extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				// creiamo un arraylist di prodotto che ci servirà come valore di ritorno del metodo
				ArrayList <dip> listaD = new ArrayList<>();
				
				// andiamo a iterare il resulta set
				while (rs.next()) {
					
					dip d1 = new dip();
					// con i risultati del result set abbiamo instanziato un oggetto prodotto e lo abbiamo
					// aggiunto alla lista
					d1.setNome(rs.getString("nome"));
					d1.setMansione(rs.getString("mansione"));
					d1.setStipendio(rs.getInt("stipendio"));
					
					listaD.add(d1);
					
				}
				
				return listaD;
			}
		
	});

}
	
	public int updateDip(int num, String nome) {
        String query = "UPDATE dipendenti SET prodAcquistati = prodAcquistati + ? WHERE nome = ?";
        return jdbcTemplateObject.update(query, num, nome);
    }




}

