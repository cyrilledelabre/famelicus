package ufrj.cos.famelicus.servidor.controller.logic;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ufrj.cos.famelicus.servidor.model.ConnectionFactory;
import ufrj.cos.famelicus.servidor.model.PontoAlimentacao;
import ufrj.cos.famelicus.servidor.model.PontoAlimentacaoDao;

public class RemovePaLogica implements Logica{

	public String executa(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int id = Integer.parseInt(request.getParameter("id"));
		//this is wrong.... 
		PontoAlimentacao pa = new PontoAlimentacao();
		pa.setId(id);
	
		Connection conexao = new ConnectionFactory().getConnection();
		PontoAlimentacaoDao dao = new PontoAlimentacaoDao(conexao);
		dao.remove(pa);
		//this pa will be always null .........
		System.out.println("Ponto de alimentação " + pa.getNome() + " excluido");
	
		return "painel";
	}
}