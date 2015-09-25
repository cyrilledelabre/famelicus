/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ufrj.cos.famelicus.servidor.model;

import ufrj.cos.famelicus.servidor.engine.MyTimer;
import ufrj.cos.famelicus.servidor.model.metadata.GeoPt;
import ufrj.cos.famelicus.servidor.model.metadata.SituacaoDoPA;
import ufrj.cos.famelicus.servidor.model.metadata.SituacaoDoPA.Funcionamento;
import ufrj.cos.famelicus.servidor.model.metadata.SituacaoDoPA.SituacaoDaFila;

//import com.famelicus.servidor.engine.Servidor;
//import com.famelicus.servidor.resources.GeoPt;
//import com.famelicus.servidor.resources.MyTimer;
//import com.famelicus.servidor.resources.SituacaoDoRestaurante;



import java.io.Serializable;
import java.util.ArrayList;

//import org.slf4j.LoggerFactory;

/**
 * @author cyrilledelabre
 */
public class PontoAlimentacao  implements Serializable{
	private static final long serialVersionUID = -7788619177798333712L;

	
    private int id;
    private String nome;
    private GeoPt localizacao;
    private SituacaoDoPA situacao;
    private transient String ultimaActualizacao; 
    private transient ArrayList<Voto> votos;
    
    public PontoAlimentacao() {
        //get the default SituacaoDoPA for init;
        this.situacao = SituacaoDoPA.getDefault();
    	
        //create empty array of Votos
        this.votos = new ArrayList<Voto>();
        //criando localizacao default
        Float defaultXY = (float) 0;
        this.localizacao = new GeoPt(defaultXY, defaultXY);
    }
    
    
    //only for test purposes
    public PontoAlimentacao(int id, String nome, GeoPt localizacao) {
    	setId(id);
    	setNome(nome);
    	setLocalizacao(localizacao);
        this.situacao = SituacaoDoPA.getDefault();
        this.ultimaActualizacao = MyTimer.getCurrentTime();
        this.votos = new ArrayList<Voto>();   
    }
    //only for test purpose
    public PontoAlimentacao(int id, String nome, GeoPt localizacao, int situacaoFila, int funcionamento ) {
    	setId(id);
    	setNome(nome);
    	setLocalizacao(localizacao);
        this.ultimaActualizacao = MyTimer.getCurrentTime();
    	this.situacao = new SituacaoDoPA(situacaoFila, funcionamento);
        this.votos = new ArrayList<Voto>();   
    }
    
    
    public void setId(int id) {this.id = id;}
    public void setNome(String nome) {
    	
    	this.nome = nome;
    }
    public void setLocalizacao(GeoPt localizacao) {this.localizacao = localizacao;}
    public void setLocalizacao(Float lat, Float lng) {this.localizacao.set(lat, lng);}
    public void setSituacao(SituacaoDoPA s){this.situacao = s;}
    public void setSituacao(int situacaoFila, int funcionamento){
    	this.situacao.set(situacaoFila, funcionamento);
    }
    public void setUltimaActualizacao(String u){ this.ultimaActualizacao = u;}
    public ArrayList<Voto> obterVotos(){return this.votos;}
    
    //getters
    public int getId() {return id;}
    public String getNome() {return nome;}
    public GeoPt getLocalizacao() {return localizacao;}
    public SituacaoDoPA getSituacao(){return situacao;}
    public Funcionamento getFuncionamento(){return situacao.funcionamento;}
    public SituacaoDaFila getSituacaoDaFila(){return situacao.situacaoDaFila;}
    public int getSituacaoDaFilaInt(){return situacao.situacaoDaFila.getValue();}
    public int getFuncionamentoInt(){return situacao.funcionamento.getValue();}
    public String getUltimaActualizacao(){return ultimaActualizacao;}
    
    
    
    
    public void updateSituaçaoDaFila(SituacaoDaFila s){
    	this.situacao.situacaoDaFila = s;
    }
    public void updateFuncionamento(Funcionamento f) {
    	this.situacao.funcionamento =f;
    }
    
    public void addVoto(Voto v) {
    	//ugly but easy : 
    	//should return boolean : success | error and send it back to the Response
        if(v.idPA != this.id) {
        	System.out.println("addVoto PA error: idPa "+v.idPA +"!= this "+ this.id); 
        }
        votos.add(v);   
    }
    
    public void limpiaVotos() {
        if(this.votos != null){
            this.votos.clear();
        }
    }    
    
    
   

}

