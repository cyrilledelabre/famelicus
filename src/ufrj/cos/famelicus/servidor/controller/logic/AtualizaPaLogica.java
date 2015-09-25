package ufrj.cos.famelicus.servidor.controller.logic;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ufrj.cos.famelicus.servidor.model.ConnectionFactory;
import ufrj.cos.famelicus.servidor.model.PontoAlimentacao;
import ufrj.cos.famelicus.servidor.model.PontoAlimentacaoDao;

public class AtualizaPaLogica implements Logica{

	public String executa(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int id = Integer.parseInt(request.getParameter("id"));
		String nome = request.getParameter("nome");
//		Float lat = Float.parseFloat(request.getParameter("lat"));
//		Float lng = Float.parseFloat(request.getParameter("lng"));

		
		
		PontoAlimentacao pa = new PontoAlimentacao();
		pa.setId(id);
		pa.setNome(nome);
//		pa.setLocalizacao(lat, lng);
	
		Connection conexao = new ConnectionFactory().getConnection();
		PontoAlimentacaoDao dao = new PontoAlimentacaoDao(conexao);
		dao.atualiza(pa);
		//this pa will be always null .........
		System.out.println("Ponto de alimentação " + pa.getNome() + " foi atualizado.");
	
		return "painel";
	}
}