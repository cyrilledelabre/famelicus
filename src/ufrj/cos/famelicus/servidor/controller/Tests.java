package ufrj.cos.famelicus.servidor.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import ufrj.cos.famelicus.servidor.engine.MyTimer;
import ufrj.cos.famelicus.servidor.engine.Servidor;
import ufrj.cos.famelicus.servidor.model.Voto;
import ufrj.cos.famelicus.servidor.model.metadata.ResponseHandler;
import ufrj.cos.famelicus.servidor.model.metadata.SituacaoDoPA;

/*
 * Handles requests for the Servidor service API.
 */
@Path("/test/") 
public class Tests{
	Servidor servidor = Servidor.getInstance();

	@GET @Path("retornarTudo/")
	public String retornarTudo() {
		Gson gson = new Gson();
	  	JsonObject object = new JsonObject();
		
		JsonElement listaPA = gson.toJsonTree(servidor.retornarTudo());
    	object.addProperty("versao", servidor.getVersao());
    	object.addProperty("estado", "aberto");
    	//object.addProperty("ultimaActualizacao", servidor.getUltimaActualizacao());
    	object.add("PAs", listaPA);		
	   	
		return gson.toJson(object);

	}	

    @GET @Path("retornarSituacao/")
	public String retornarSituacao() {
    	
    	Gson gson = new Gson();
       	JsonObject object = new JsonObject();
       JsonElement listaPA = gson.toJsonTree(servidor.retornarSituacao());
    	object.addProperty("versao", servidor.getVersao());
    	object.addProperty("estado", "aberto");
    	object.addProperty("ultimaAtualizacao", servidor.getUltimaActualizacao());
    	object.add("PAs", listaPA);

   	
  
		return gson.toJson(object);
	}
    
    @GET @Path("determinarSituacao/")
    public Response determinarSituacao()
    {
    	Response response;
    	response = servidor.determinadorDeSituacao.determinar();
    	
    	return response;
    }

	@GET @Path("adicionarVoto/")
	public Response adicionarVoto(@QueryParam("v") int versao,
								@QueryParam("id") int id,
								   @QueryParam("funcionamento") int funcionamento,
								   @QueryParam("situacaoDaFila") int situacaoDaFila){ 
		
		Response response;
	 
	 		if(servidor.isSameVersao(versao)){
	 			SituacaoDoPA sPa = new SituacaoDoPA(situacaoDaFila, funcionamento);
				Voto voto = new Voto();
				voto.setidPA(id);		
				voto.setSituacao(sPa);
		        voto.setHora(MyTimer.getCurrentTime());
		        
				ResponseHandler.AdicionarVoto responseHandler = servidor.addicionarVoto(voto);
				response = responseHandler.response();
	 		}else{
	 			//sorry bad versao
	 			response = Response.status(500).entity("Versao incorreta ").build();
	 		}
	 	
		
		return response;
	}
		
}