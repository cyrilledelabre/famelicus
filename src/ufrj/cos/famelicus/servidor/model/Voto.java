/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ufrj.cos.famelicus.servidor.model;

import java.io.Serializable;

import ufrj.cos.famelicus.servidor.model.metadata.SituacaoDoPA;

/**
 *
 * @author cyrilledelabre
 */
public class Voto implements Serializable{
	private static final long serialVersionUID = 1L;
	public String hora; 
    public int idPA;
    public SituacaoDoPA situacao;
    
    
    public void setSituacao(SituacaoDoPA s){this.situacao = s;}
    public String getHora(){return this.hora;}
    public void setHora(String h){this.hora = h;}
    public int getidPA(){return this.idPA;}
    public void setidPA(int id){this.idPA = id;}
    public SituacaoDoPA getSituacao(){return this.situacao;}
    
}
