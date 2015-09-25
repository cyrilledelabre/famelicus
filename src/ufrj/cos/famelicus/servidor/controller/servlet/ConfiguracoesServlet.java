package ufrj.cos.famelicus.servidor.controller.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ufrj.cos.famelicus.servidor.engine.Servidor;
//import ufrj.cos.famelicus.servidor.model.ConfiguracoesDoServidor;


@WebServlet("/configuracoes")
public class ConfiguracoesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Servidor servidor = Servidor.getInstance();

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		Servidor config = Servidor.getInstance();
		request.setAttribute("config", config);
		
		RequestDispatcher rd = request.getRequestDispatcher("configuracoes.jsp");
		rd.forward(request, response);
	}
}
