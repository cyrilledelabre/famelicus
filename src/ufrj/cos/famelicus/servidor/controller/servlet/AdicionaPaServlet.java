//Objetivo: Servlet para adicionar no banco dadaos um contato, cujas informa-
//coes foram passadas através da form em adiciona-contato.jsp.
//As informacoes do contato foram encaminhadas para serem impressas
//em outra jsp (contato-adicionado.jsp).
//
//Autor: Bruno Fraga
//Data: 17/02/2015

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
import ufrj.cos.famelicus.servidor.model.metadata.SituacaoDoPA;

@WebServlet("/adicionaPa")
//TODO: fazer teste de valores validos (nao null, por exemplo) 
//TODO: fazer teste de valores repetidos (no DAO)

//TODO: design by contracts 
public class AdicionaPaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//buscando os parametros no request
		String nome = request.getParameter("nome");
		Float lat = Float.parseFloat(request.getParameter("lat"));
		Float lng = Float.parseFloat(request.getParameter("lng"));
		

		// montando um objeto Contato
		PontoAlimentacao pa = new PontoAlimentacao();
		pa.setNome(nome);
		pa.setLocalizacao(lat, lng);
		pa.setSituacao(SituacaoDoPA.getDefault());
			
		Connection conexao = new ConnectionFactory().getConnection(); 

		// salvando o contato na conexao aberta
		PontoAlimentacaoDao dao = new PontoAlimentacaoDao(conexao);
		dao.adiciona(pa);
		try {
			conexao.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		RequestDispatcher rd = request.getRequestDispatcher("painel");
		rd.forward(request, response);
	}
}