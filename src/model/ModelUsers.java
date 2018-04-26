package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.apache.commons.codec.digest.DigestUtils;
import com.mysql.jdbc.PreparedStatement;

public class ModelUsers{
	private static String URL = "jdbc:mysql://localhost:3306/Etablissement?useSSL=false";
	private static String LOGIN = "root";
	private static String PASSWORD = "jonk2010";
	private static String requete;
	
	public ModelUsers() throws SQLException{
		DriverManager.registerDriver(new com.mysql.jdbc.Driver ());
	}
	
	public int ajouterUser(String nom, String prenom, String mot_de_passe, int id_t ){

		if(nom.length()!=0 & prenom.length()!=0 & id_t>0 & id_t<40){
			setRequete("INSERT INTO Users(nom, prenom, mot_de_passe, id_tache) " 
						+ "VALUES (\""+nom+"\", \""+ prenom+"\""+", \""+ DigestUtils.md5Hex(mot_de_passe)+"\""
						+", \""+ id_t+"\""+"\")"); 
			try (Connection con = (Connection) DriverManager.getConnection(URL, LOGIN, PASSWORD);
				Statement stmt = (Statement) con.createStatement()){
				stmt.execute(getRequete());
				System.out.println("Vyagenze neza cane gose");
				return 1;
			} catch (SQLException e) {
					System.out.println(getRequete());
					System.out.println("Il ya eu une erreur d'écriture dans la base de données "+e);
					return 0;
					}
		}else{
			return 0;
		}
	}

	public int getTypeUser(String matricule, String mot_de_passe){
		String mdp = DigestUtils.md5Hex(mot_de_passe);
		setRequete("SELECT id_tache FROM Users WHERE id=? AND mot_de_passe =?");
		try (
			Connection con = (Connection) DriverManager.getConnection(URL, LOGIN, PASSWORD);
			PreparedStatement stmt = (PreparedStatement) con.prepareStatement(getRequete())){
			stmt.setString(1, matricule);
			stmt.setString(2, mdp);
			try(ResultSet resultat = stmt.executeQuery()){
				if(resultat.next()){
					int tache = resultat.getInt(1);
					return tache;			
				}else{
					return -1;
				}		
			}
		} catch (SQLException e) {
				System.out.println(getRequete());
				System.out.println("ERREUR\n"+e.toString());
				return -1;
			}
	}
	
	public int guhanaguraUser(String id){
		setRequete("DELETE FROM Users where id = "+id);
		
		try (Connection con = (Connection) DriverManager.getConnection(URL, LOGIN, PASSWORD);
			Statement stmt = (Statement) con.createStatement()){
			stmt.execute(getRequete());
			System.out.println("Vyagenze neza cane gose");
			return 1;
		} catch (SQLException e) {
				System.out.println("Il ya eu une erreur d'écriture dans la base de données "+e);
				return 0;
				}
		}
	
	public ArrayList<String> gusomaNomsUsers(int id_tache){
		setRequete("SELECT nom FROM Users WHERE id_tache ="+id_tache);
		try (
			Connection con = (Connection) DriverManager.getConnection(URL, LOGIN, PASSWORD);
			Statement stmt = (Statement) con.createStatement();
			ResultSet resultat = stmt.executeQuery(getRequete())){
			
			ArrayList<String> listResultat = new ArrayList<String>();
			
			while(resultat.next()) {
				listResultat.add(resultat.getString(1));
			}
			return listResultat;
		} catch (SQLException e) {
				System.out.println("Il ya eu une erreur de lecture dans la base de données "+e);
				return null;
				}
	}
	
	public String getRequete() {
		return requete;
	}

	public void setRequete(String requête) {
		requete = requête;
	}

	public void modifierUser(String id, String nom, String prenom, String mdp, String id_t, String id_fac, String id_depart, String id_niv) {
		if(nom.length()!=0 & prenom.length()!=0){
			setRequete("UPDATE Users SET nom = \""+ nom + "\", prenom = \""+ prenom +"\", mot_de_passe = \""
						+ DigestUtils.md5Hex(mdp)+"\", id_tache = \""+ id_t +"\", id_niveau = \""+ id_niv 
						+"\", id_faculte = \""+ id_fac+"\", id_departement = \""+ id_depart +"\" WHERE id = "+ id);		
						
			try (Connection con = (Connection) DriverManager.getConnection(URL, LOGIN, PASSWORD);
				Statement stmt = (Statement) con.createStatement()){
				stmt.execute(getRequete());
				System.out.println("Vyagenze neza cane gose");
			} catch (SQLException e) {
					System.out.println(getRequete());
					System.out.println("Il ya eu une erreur d'écriture dans la base de données "+e);
					}
		}
	}	
	
	public String getNomUser(String matricule) {
		
		String Requete = "SELECT nom  FROM Users WHERE id = ?";
		try (
			Connection con = (Connection) DriverManager.getConnection(URL, LOGIN, PASSWORD);
			PreparedStatement stmt = (PreparedStatement) con.prepareStatement(Requete)){
			stmt.setString(1, matricule);
			ResultSet resultat = stmt.executeQuery();
			
			if (resultat.next() ) return resultat.getString(1);
			return "inconnu";

			
		} catch (SQLException e) {
			System.out.println(Requete);
			System.out.println("Il ya eu une erreur de lecture dans la base de données "+e);
			return "inconnu";
		}
	}	
	
	public String getPrenomUser(String matricule) {
		
		String Requete = "SELECT Prenom  FROM Users WHERE id = ?";
		try (
			Connection con = (Connection) DriverManager.getConnection(URL, LOGIN, PASSWORD);
			PreparedStatement stmt = (PreparedStatement) con.prepareStatement(Requete)){
			stmt.setString(1, matricule);
			ResultSet resultat = stmt.executeQuery();
			
			if (resultat.next() ) return resultat.getString(1);
			return "inconnu";
			
		} catch (SQLException e) {
			System.out.println(Requete);
			System.out.println("Il ya eu une erreur de lecture dans la base de données "+e);
			return "inconnu";
		}
	}

}