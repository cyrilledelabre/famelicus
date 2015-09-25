package ufrj.cos.famelicus.servidor.model.metadata;

import javax.ws.rs.core.Response;

public class ResponseHandler {
    public static enum  AdicionarVoto{
        Successo (1),
        EstadoFuncionamento (2),
        EstadoSituacaoDaFila (3),
        IdPa (4),
        Versao(5);
        
        private final int value;
        
        AdicionarVoto(int value){this.value = value;}
        public int getValue(){return value;}
        public Response response(){
        	Response response;
            switch (getValue()) {
    		case 1: //Successo
    			response =  Response.status(200).entity("adicionar Voto sucesso").build();
    			break;
    		case 2: //EstadoFuncionamento
    			response =  Response.status(400).entity("Erro: Estado Funcionamento incorreto").build();
    			break;
    		case 3: //EstadoSituacaoDaFila
    			response =  Response.status(400).entity("Erro: Estado SituacaoDaFila incorreta").build();
    			break;	
    		case 4: //IdPa
    			response =  Response.status(400).entity("Erro: IdPa incorreto").build();
    			break;
    		case 5: //Versao
    			response =  Response.status(400).entity("Erro: Versao incorreta").build();
    			break;
    		default:
    			response = Response.status(404).entity("Erro: Nao encontrado").build();
    			break;
        }
        	
        	return response;
        }
    }
    
    
    public static enum  DeterminadorDeSituacao{
        Successo (1),
        NenhumPACadastrado (2);

        private final int value;
        DeterminadorDeSituacao(int value){this.value = value;}
        public int getValue(){return value;}
        public Response response(String message){
        	
        	Response response;
            switch (getValue()) {
    		case 1: //Successo
    			response =  Response.status(200).entity(message).build();
    			break;
    		case 2: //Nenhum PA Cadastrado
    			response =  Response.status(500).entity(message).build();
    			break;
    		default:
    			response = Response.status(404).entity("Erro: Nao encontrado").build();
    			break;
        }
        	
        	return response;
        }
    }
}
