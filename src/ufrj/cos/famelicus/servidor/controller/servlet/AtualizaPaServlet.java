package ufrj.cos.famelicus.servidor.controller.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ufrj.cos.famelicus.servidor.model.ConnectionFactory;
import ufrj.cos.famelicus.servidor.model.PontoAlimentacao;
import ufrj.cos.famelicus.servidor.model.PontoAlimentacaoDao;

@WebServlet("/atualizaPa")
public class AtualizaPaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//buscando os parametros no request
		int id = Integer.parseInt(request.getParameter("id"));
		String nome = request.getParameter("nome");
		Float lat = Float.parseFloat(request.getParameter("lat"));
		Float lng = Float.parseFloat(request.getParameter("lng"));
		

		// montando um objeto Contato
		PontoAlimentacao pa = new PontoAlimentacao();
		pa.setId(id);
		pa.setNome(nome);
		pa.setLocalizacao(lat, lng);
			
		Connection conexao = new ConnectionFactory().getConnection(); 

		// salvando o contato na conexao aberta
		PontoAlimentacaoDao dao = new PontoAlimentacaoDao(conexao);
		dao.atualiza(pa);
		try {
			conexao.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		RequestDispatcher rd = request.getRequestDispatcher("painel");
		rd.forward(request, response);
	}
}