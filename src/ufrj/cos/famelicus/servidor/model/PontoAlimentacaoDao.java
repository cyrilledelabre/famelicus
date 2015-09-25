//Objetivo: Classe para acessar os dados no banco de dados a partir
//de um argumento do tipo PontoAlimentacao.
//
//Autor: Bruno Fraga
//Data: 11/02/2015
//
//Design Pattern: Data Access Object (DAO)

package ufrj.cos.famelicus.servidor.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.HashMap;

import ufrj.cos.famelicus.servidor.engine.MyTimer;
import ufrj.cos.famelicus.servidor.engine.Servidor;
import ufrj.cos.famelicus.servidor.model.metadata.SituacaoDoPA;

//TABELA
//id => int autoincrement index
//nome => varchar(255)
//lat => float
//lng => float


public class PontoAlimentacaoDao extends PontoAlimentacao{
	private static final long serialVersionUID = 1L;
	private static final String NOME_TABELA ="Pontos_de_Alimentacao";	
	
	//a conexao com o banco de dados
	private Connection connection;

	public PontoAlimentacaoDao (Connection conexao_dabase_contatos) {
		this.connection = conexao_dabase_contatos;
	}

	
	//so o administrador pode adiciona um PA
	public void adiciona(PontoAlimentacao pa){
		String querry = "insert into " + NOME_TABELA +        // comando em mysql para inserir informacoes na tabela 
       " (nome, lat, lng) " +  // fields a serem preenchidos pelos
       "values (?, ?, ?)";                        // valores (ainda nao preenchidos)  

		try {
			//prepared statement para insercao
			PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(querry);

			//seta os valores
			stmt.setString(1, pa.getNome());
			stmt.setFloat(2, pa.getLocalizacao().getLat());
			stmt.setFloat(3, pa.getLocalizacao().getLng());
			//stmt.setInt(4, pa.getSituacao().funcionamento.getValue());
			//stmt.setInt(5, pa.getSituacao().situacaoDaFila.getValue());
			
	
			stmt.execute();
			stmt.close();   
			actualizaServidor();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public HashMap<Integer,PontoAlimentacao> getLista() {
		try {
			HashMap<Integer,PontoAlimentacao> listaPa = new HashMap<Integer,PontoAlimentacao>();
			PreparedStatement stmt = (PreparedStatement) this.connection.prepareStatement("select * from " + NOME_TABELA);
			ResultSet rs = stmt.executeQuery();

			//itera no ResultSet
			while (rs.next()){
				// criando o objeto PontoAlimentacao
				PontoAlimentacao pa = new PontoAlimentacao();
								
				pa.setId(rs.getInt("id"));
				pa.setNome(rs.getString("nome"));
				pa.setLocalizacao(rs.getFloat("lat"), rs.getFloat("lng"));
				
				//default values on init;
				pa.setSituacao(SituacaoDoPA.getDefault());
				//get a default value ???
				pa.setUltimaActualizacao(MyTimer.getCurrentTime());
				// adicionando o objeto a lista
				listaPa.put(pa.getId(), pa);				
			}

			rs.close();
			stmt.close();
			return listaPa;

		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			//rs.close();          ---> PQ NAO POSSO COLCOAR ESSE CARA AQUI???
			//stmt.close();        ---> PQ NAO POSSO COLCOAR ESSE CARA AQUI???		
			try {this.connection.close();} 
			catch (SQLException e) {throw new RuntimeException (e);}
		}
		//TODO handle error 
		return null;  //         ---> PQ NAO POSSO COLCOAR contatos_list CARA AQUI???        
}
	//so o administrador pode remove ese estado
	public void remove(PontoAlimentacao pa) {
		try {
			PreparedStatement stmt = connection.prepareStatement("delete from " 
					+ NOME_TABELA + " where id=?");

			stmt.setLong(1, pa.getId());
			stmt.execute();
			stmt.close();
			actualizaServidor();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	//so o administrador pode actualizar ese estado
	public void atualiza(PontoAlimentacao pa) {
		String sql = "update "+NOME_TABELA+" set"
				+ " nome=?, lat=?, lng=?"
				+ "where id=?";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, pa.getNome());
			stmt.setString(2, pa.getLocalizacao().getLat().toString());
			stmt.setString(3, pa.getLocalizacao().getLng().toString());
			stmt.setLong(4, pa.getId());		
			
			stmt.execute();
			stmt.close();
			
			actualizaServidor();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
	}
	
	private void actualizaServidor()
	{
		Servidor servidor = Servidor.getInstance(); 
		servidor.atualiza();
	}
	
}
