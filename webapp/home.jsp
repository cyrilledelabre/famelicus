<!--
	Objetivo: JSP para imprimir a lista de todos os contatos do banco de dados.
			  Foi implementando usando JavaLibs.

	Autor: Bruno Fraga
	Data: 18/02/2015
 -->
 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@page import="ufrj.cos.famelicus.servidor.model.PontoAlimentacao" %>
<%@page import="ufrj.cos.famelicus.servidor.model.PontoAlimentacaoDao" %>
<%@page import="ufrj.cos.famelicus.servidor.model.ConnectionFactory" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.sql.Connection" %>
<%@page import="java.util.*" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script src="js/jquery-1.11.3.min.js"></script>
		
		<!-- Linking CSS documents -->
		<link type="text/css" rel="stylesheet" href="css/bootstrap.css"/>
		<link href="fonts/glyphicons-halflings-regular.svg"/>
		
		
		<title>Famelicus - Painel de Controle</title>
	</head>
	<body>
	
	<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">Famelicus - Servidor</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
    
      <ul class="nav navbar-nav navbar-right">
        <li><a href="painel">Painel de Controle</a></li>
        <li><a href=configuracoes>Configuracoes</a></li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
</body>
			
			<div class = "container center-block text-right">
				<a href="adicionaPa.html">
					<button type="button" class="btn btn-default" >
						<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
  						Novo cadastro
					</button>
				</a>
			</div>
		
			<div class = "container center-block">
				<div class = "col-xs-12 col-sm-10 col-md-10 ">
					<table class = "table table-striped">
						<thead class="text-center">
							<th>Nome</th>
							<th>Latitude</th>
							<th>Longitude</th> 
							<th>Funcionamento </th>
							<th>Situação da Fila</th>
							<th></th>
						</thead>
					
						<c:forEach var="pa" items="${listaPa}">
						<tr>
							<form>
							<td name="nome" class="editable">${pa.value.nome}</td>
							<td name="lat" class="editable">${pa.value.localizacao.lat}</td>
							<td name="lng" class="editable">${pa.value.localizacao.lng}</td>
							<td>${pa.value.funcionamento}</td>					
							<td>${pa.value.situacaoDaFila}</td>
							<td>
								<div class="button-group">
									<div class="button-group-up">
										<a href ="#">
											<font class="up-trigger glyphicon glyphicon-pencil" ></font>
										</a>
										<a href ="servidor?&logic=RemovePaLogica&id=${pa.value.id}">
											<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
										</a>
									</div>
								
									<div class="button-group-down">
										<font class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></font>
										<button class = "glyphicon glyphicon-floppy-disk" action="/atualizaPa" type='submit' value = ""/>
										</button>
										<!-- </a> -->						
										<a href ="#">
											<font class="down-trigger glyphicon glyphicon-remove" aria-hidden="true" color="bdbdbd"></font>
										</a>
									</div>
								</div>
							</td>
							</form>
						</tr>
						</c:forEach> 
					</table>
				</div>
			</div>
		
		<script src="js/table-button-animation.js"></script>
	</body>
</html>