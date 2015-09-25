//Objetivo: Essa servlet controlara o fluxo de nossa aplicacao.
//
//Autor: Bruno Fraga
//Data: 18/02/2015

package estudo.mvc.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ufrj.cos.famelicus.servidor.controller.logic.*;

@WebServlet("/servidor")
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException{
		
		// pegando o nome da classe que iremos usar
		String parametro = request.getParameter("logic");
		System.out.println(parametro);
		String nomeDaClasse = "ufrj.cos.famelicus.servidor.controller.logic." + parametro;
		
		try {
			// o metodo forName retorna uma Class, cujo nome e "nome_da_classe"
			Class classe = Class.forName(nomeDaClasse);
			
			// o metodo newInstance eh o contrututor do objeto do tipo Class;
			// dai, fazemos um polimorfismo para que a variavel business_logic
			// seja do tipo correto (Logica);
			Logica businessLogic = (Logica) classe.newInstance();
			
			// pegamos a String que se refere a qual pagina o request e o 
			// response serao despachados.
			String pagepath = businessLogic.executa(request, response);
			
			request.getRequestDispatcher(pagepath).forward(request, response);
			
		} catch (Exception e) {
			throw new ServletException("A logica de negocios causou uma Exception", e);
		}
	}
}