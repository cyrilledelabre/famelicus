package ufrj.cos.famelicus.servidor.engine;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;

import ufrj.cos.famelicus.servidor.model.PontoAlimentacao;
import ufrj.cos.famelicus.servidor.model.Voto;
import ufrj.cos.famelicus.servidor.model.metadata.ResponseHandler;
import ufrj.cos.famelicus.servidor.model.metadata.SituacaoDoPA;
import ufrj.cos.famelicus.servidor.model.metadata.SituacaoDoPA.Funcionamento;
import ufrj.cos.famelicus.servidor.model.metadata.SituacaoDoPA.SituacaoDaFila;

import javax.ws.rs.core.Response;

/**
 *
 * @author cyrilledelabre
 */
public class DeterminadorDeSituacao {
    public Servidor servidor;
    public DeterminadorDeSituacao(Servidor servidor)
    {
        this.servidor = servidor;
    }
    
    
    public void reset(){
    	
        Map<Integer, PontoAlimentacao> pontosAlimentacoes = servidor.pontosAlimentacoes;

    	for(Entry<Integer, PontoAlimentacao> entryPa : pontosAlimentacoes.entrySet()) {
  		  entryPa.getValue().setSituacao(SituacaoDoPA.getDefault());
    	}
    }
    
    public Response determinar(){    	
       System.out.println("determinar !"); 
       String responseString ="";
       Map<Integer, PontoAlimentacao> pontosAlimentacoes = servidor.pontosAlimentacoes;
       if(pontosAlimentacoes.isEmpty())
       {
    	   return ResponseHandler.DeterminadorDeSituacao.NenhumPACadastrado.response("Nenhum PA Cadastrado");
       }
       
 	   for(Entry<Integer, PontoAlimentacao> entryPa : pontosAlimentacoes.entrySet()) {
		  PontoAlimentacao PA = entryPa.getValue();
    	   	//getting the list of votos
    	   ArrayList<Voto> tempVotos = PA.obterVotos();
            if(!tempVotos.isEmpty()){
            	//there is Votos for this PA;
                Map<SituacaoDaFila, Integer> situacaoDaFilaCounter = new EnumMap<SituacaoDaFila, Integer>(SituacaoDaFila.class);
                Map<Funcionamento, Integer>  funcionamentoCounter = new EnumMap<Funcionamento, Integer>(Funcionamento.class);
         
                for (Voto v : tempVotos) {
                	
                    Integer situacaoDaFilaValue = situacaoDaFilaCounter.get(v.situacao.situacaoDaFila);
                    Integer funcionamentoValue = funcionamentoCounter.get(v.situacao.funcionamento);
              
                    //if situacaoDaFilaCounter doesn't have yet a voto of current type : v.situacao.situacaoDaFila
                    //then create one and add 1 to the actual counter 
                    //else just add 1 to situacaoDaFilaValue;
                    if (situacaoDaFilaValue == null) {
                    	situacaoDaFilaCounter.put(v.situacao.situacaoDaFila, 1);
                    } else {
                    	situacaoDaFilaValue++;
                    }
                    
                    //doing the same for funcionamentoValue
                    if (funcionamentoValue == null) {
                    	funcionamentoCounter.put(v.situacao.funcionamento, 1);
                    } else {
                    	funcionamentoValue++;
                    }
                }
                
                SituacaoDaFila situaçao = SituacaoDaFila.NaoConhecido;   //default situaçao (KeyValue)
                int best = 0;  //default counter of votos
                //for all the keys value of the situacaoDaFilaCounter (HashMap) 
                //we are looking wish one has the highest value()  
                for(Map.Entry<SituacaoDaFila, Integer> entry : situacaoDaFilaCounter.entrySet()){
                	// <= because the votos are ordered by the oldest date / newer date 
                    if(best <= entry.getValue())
                    {
                        best = entry.getValue();
                        situaçao = entry.getKey();
                    }
                }
                        
                Funcionamento funcionamento = Funcionamento.NaoConhecido;   //default funcionamento (KeyValue)
                best = 0;  //default counter of votos
                //for all the keys value of the funcionamentoCounter (HashMap) 
                //we are looking wish one has the highest value()  
                for(Map.Entry<Funcionamento, Integer> entry : funcionamentoCounter.entrySet()){
                    if(best <= entry.getValue())
                    {
                        best = entry.getValue();
                        funcionamento = entry.getKey();
                    }
                }
                PA.setUltimaActualizacao(MyTimer.getCurrentTime());
                PA.updateFuncionamento(funcionamento);
                PA.updateSituaçaoDaFila(situaçao);
                PA.limpiaVotos();

                
                responseString += "Novo estado para PA : " + PA.getNome() +
                		", situaçao da fila : " + situaçao.toString() +
                		" e funcionamento : " + funcionamento.toString() + "<br>";
                
            }else{
            	responseString += "Nehum voto para PA : " + PA.getNome() +"<br>";
            }
            
        }  
 	  return ResponseHandler.DeterminadorDeSituacao.NenhumPACadastrado.response(responseString);
    }
    
}
