package control;

import java.sql.SQLException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.ModelUsers;

@ManagedBean
@SessionScoped
public class Connexion {
	private String nom;
	private String prenom;
	private String matricule;
	private String mdp;
	
	public Connexion(){
		
	}
	
	public String login() throws SQLException{
		ModelUsers modelUsers = new ModelUsers();
		int type = modelUsers.getTypeUser(matricule, mdp);
		if(type!=-1){
			this.setNom(modelUsers.getNomUser(matricule));
			this.setPrenom(modelUsers.getPrenomUser(matricule));
			return "reponse";
		}else{
			return "loginerror";
		}		
	}
	
	public String getNom() {
		return nom.toUpperCase();
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom.toUpperCase();
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

}
