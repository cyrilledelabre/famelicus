package ufrj.cos.famelicus.servidor.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.io.Serializable;

public class ConfiguracoesDoServidor implements Serializable{
	private static ConfiguracoesDoServidor INSTANCE = null;
	private static final String NOME_TABELA ="Configuracao";
	private static final long serialVersionUID = -7788619177798333712L;
	private Connection connection;
	
	private int versao = 1; //TODO do something better : should store the versão in the database
    private int tempoVotacao = 5000 * 60; // 5 minutes
    private int horaAbertura = 8; // we just need the hour so only int
    private int horaFechamento = 18; // we just need the hour so only int
    
    //getters
    public Connection getConnection() {return INSTANCE.connection;}
	public int getTempoVotacao() {return INSTANCE.tempoVotacao;}
	public int getHoraAbertura() {
		return INSTANCE.horaAbertura;
	}
	public int getHoraFechamento() {
		return INSTANCE.horaFechamento;
	}
	public int getVersao() {
		return INSTANCE.versao;
	}
	
	//setters
	public void setConnection(Connection connection) {
		INSTANCE.connection = connection;
	}
	public void setTempoVotacao(int tempoVotacao) {
		INSTANCE.tempoVotacao = tempoVotacao;
	}
	public void setHoraAbertura(int horaAbertura) {
		INSTANCE.horaAbertura = horaAbertura;
	}
	public void setHoraFechamento(int horaFechamento) {
		INSTANCE.horaFechamento = horaFechamento;
	}
	public void setVersao(int versao) {
		INSTANCE.versao = versao;
	}

	
	public static ConfiguracoesDoServidor getInstance(Connection conexao) {
		if (INSTANCE == null) {
			// Thread Safe. Might be costly operation in some case
			synchronized (ConfiguracoesDoServidor.class) {
				if (INSTANCE == null) {
					INSTANCE = new ConfiguracoesDoServidor(conexao);
					INSTANCE.fetch();
				}
			}
		}
		return INSTANCE;
    }
    
	private ConfiguracoesDoServidor (Connection conexao_dabase_contatos) {
		this.connection = conexao_dabase_contatos;
	}
		
/*
	private void adiciona(PontoAlimentacao pa){
		String querry = "insert into " + NOME_TABELA +
				" (nome, lat, lng) " +
				"values (?, ?, ?)";  

		try {
			//prepared statement para insercao
			PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(querry);

			//seta os valores
			stmt.setString(1, pa.getNome());
			stmt.setFloat(2, pa.getLocalizacao().getLat());
			stmt.setFloat(3, pa.getLocalizacao().getLng());
			
			stmt.execute();
			stmt.close();   
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
*/
	
	public void fetch() {
		try {
			PreparedStatement stmt = (PreparedStatement) this.connection.prepareStatement("select * from " + NOME_TABELA);
			ResultSet rs = stmt.executeQuery();
			
			this.setVersao(rs.getInt("versao"));
			this.setTempoVotacao(rs.getInt("tempo_votacao"));
			this.setHoraAbertura(rs.getInt("hora_abertura"));
			this.setHoraFechamento(rs.getInt("hora_fechamento"));
			
			rs.close();
			stmt.close();
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			try {this.connection.close();} 
			catch (SQLException e) {throw new RuntimeException (e);}
		}
	}

		//so o administrador pode actualizar ese estado
		public void atualiza() {
			this.versao++;
			String sql = "update "+NOME_TABELA+" set"
					+ " versao=?, tempo_votacao=?, hora_abertura=?, hora_fechamento=?";
//					+ "where id=?";
			try {
				PreparedStatement stmt = connection.prepareStatement(sql);
				
				stmt.setInt(1, this.getVersao());
				stmt.setInt(2, this.getTempoVotacao());
				stmt.setInt(3, this.getHoraAbertura());
				stmt.setInt(4, this.getHoraFechamento());		
				
				stmt.execute();
				stmt.close();
				
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}	
		}
}
