//Objetivo: FIltro para medir o tempo de execucao de TODAS as requisicoes do
//projeto.
//
//Autor: Bruno Fraga
//Data: 20/02/2015

package ufrj.cos.famelicus.servidor.filtros;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;


@WebFilter("/*")
public class FiltroTeste implements Filter{
	public void doFilter (ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		long  tempoInicial = System.currentTimeMillis();

		chain.doFilter(request, response);

		long tempoFinal = System.currentTimeMillis();

		//pega a URI requisitada
		String uri = ((HttpServletRequest)request).getRequestURI();
		//pega o nome da classe - do servlet, mais especificamente - que foi usado
//		String parametros = ((HttpServletRequest)request).getParameter("logica");
		

		System.out.println("Tempo de requisicao de "
				+ uri 
				+ " demorou (ms): " + (tempoFinal - tempoInicial));
	}

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}


}
