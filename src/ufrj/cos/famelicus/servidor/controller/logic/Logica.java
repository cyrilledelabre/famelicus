//Objetivo: Interface para usar os metodos de qualquer objeto do tipo "Logica".
//A interface "Logica" se refere a qualquer logica de negocio que se-
//ra ativada a partir do metodo "executa".
//
//Autor: Bruno Fraga
//Data: 18/02/2015


package ufrj.cos.famelicus.servidor.controller.logic;

import javax.servlet.http.*;

public interface Logica {
	String executa(HttpServletRequest request, HttpServletResponse response)
			throws Exception;
}