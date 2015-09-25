<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Configurações do Servidor</title>
<script src="js/jquery-1.11.3.min.js"></script>
		
		<!-- Linking CSS documents -->
		<link type="text/css" rel="stylesheet" href="css/bootstrap.css"/>
		<link href="fonts/glyphicons-halflings-regular.svg"/>

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


<ul>
	<li>Versão: ${config.versao}</li>
	<li>Horário de abertura: ${config.horaAbertura}h</li>
	<li>Horário de fechamento: ${config.horaFechamento}h</li>
	<li>Intervalo de tempo para votação: ${config.tempoVotacao} min</li>

</ul>

</body>
</html>