package ufrj.cos.famelicus.servidor.controller.servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ufrj.cos.famelicus.servidor.engine.Servidor;
import ufrj.cos.famelicus.servidor.model.PontoAlimentacao;


@WebServlet("/painel")
public class ListaPaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Servidor servidor = Servidor.getInstance();

	
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		
		HashMap<Integer, PontoAlimentacao> listaPa= servidor.getListaPa();
		//guardando lista_contatos no request
		request.setAttribute("listaPa", listaPa);
		
		RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
		rd.forward(request, response);
	}
}

//package ufrj.cos.famelicus.servidor.controller.servlet;
//
//import java.io.IOException;
//import java.sql.Connection;
//import java.util.List;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import ufrj.cos.famelicus.servidor.model.ConnectionFactory;
//import ufrj.cos.famelicus.servidor.model.PontoAlimentacao;
//import ufrj.cos.famelicus.servidor.model.PontoAlimentacaoDao;
//
//
//@WebServlet("/painel")
//public class ListaPaServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//	
//	protected void service(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//	
//		//abrindo conexao
//		Connection conexao = new ConnectionFactory().getConnection();
//		
//		//montando a lista de contatos
//		List<PontoAlimentacao> listaPa = new PontoAlimentacaoDao(conexao).getLista();
//		
//		//guardando lista_contatos no request
//		request.setAttribute("listaPa", listaPa);
//		
//		RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
//		rd.forward(request, response);
//	}
//}
