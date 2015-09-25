package ufrj.cos.famelicus.servidor.engine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;

import ufrj.cos.famelicus.servidor.engine.MyTimer;
import ufrj.cos.famelicus.servidor.model.ConnectionFactory;
import ufrj.cos.famelicus.servidor.model.PontoAlimentacao;
import ufrj.cos.famelicus.servidor.model.PontoAlimentacaoDao;
import ufrj.cos.famelicus.servidor.model.Voto;
import ufrj.cos.famelicus.servidor.model.metadata.ResponseHandler;
import ufrj.cos.famelicus.servidor.model.metadata.SituacaoDoPA;

/**
*
* @author cyrilledelabre
*/

public class Servidor {
    private static Servidor INSTANCE = null;
    
    //singleton design pattern....
    public static Servidor getInstance() {
		if (INSTANCE == null) {
			// Thread Safe. Might be costly operation in some case
			synchronized (Servidor.class) {
				if (INSTANCE == null) {
					INSTANCE = new Servidor();
				}
			}
		} else{
			INSTANCE.fetch();
		}
		return INSTANCE;
    }
    
    private static final String NOME_TABELA ="Configuracao";
	
	private int versao = 1; //TODO do something better : should store the versão in the database
    private int tempoVotacao = 5; // 5 minutes
    private int horaAbertura = 8; // we just need the hour so only int
    private int horaFechamento = 18; // we just need the hour so only int
    
    public HashMap<Integer, PontoAlimentacao> pontosAlimentacoes;
    private MyTimer myTimer; 
    public DeterminadorDeSituacao determinadorDeSituacao;
    
    
    


    
    //timer and versao maybe in the database
//    private static ConfiguracoesDoServidor config = null;
//    private static int TIMER = 5000 * 60 ; // 5 minutes
//    private static int OPENING_HOUR = 8 ; // we just need the hour so only int
//    private static int CLOSING_HOUR = 18 ; // we just need the hour so only int


        
    private Servidor() {
    	pontosAlimentacoes = new HashMap<Integer, PontoAlimentacao>();
		Connection conexao = new ConnectionFactory().getConnection();
		pontosAlimentacoes = new PontoAlimentacaoDao(conexao).getLista();
		
		fetch();
		long tempoVotacaoMillis = tempoVotacao*60*1000;
		this.myTimer = MyTimer.getInstance(determinadorDeSituacao, tempoVotacaoMillis, horaAbertura, horaFechamento);
		this.determinadorDeSituacao = new DeterminadorDeSituacao(this);
         
        
    }
    
    public HashMap<Integer, PontoAlimentacao> retornarTudo(){
        return pontosAlimentacoes;
    }
    public HashMap<Integer, WrapperSituacao> retornarSituacao()
    {
    	  HashMap<Integer,WrapperSituacao> situaçao = new HashMap<Integer,WrapperSituacao>(); 
    	  for(Entry<Integer, PontoAlimentacao> entry : pontosAlimentacoes.entrySet()) {
    		  PontoAlimentacao PA = entry.getValue();
        	  //to show everything we want, we just need to create a Wrapper with the content we want to send
              situaçao.put(PA.getId(), new WrapperSituacao(PA.getNome(), PA.getUltimaActualizacao(),PA.getSituacao()));
          }
          return situaçao;
    }
    public ResponseHandler.AdicionarVoto addicionarVoto(Voto voto){
        int idPa = voto.idPA;
        PontoAlimentacao Pa;
    	Pa = pontosAlimentacoes.get(idPa);
       if(Pa == null){
			return ResponseHandler.AdicionarVoto.IdPa;
		}
        if(!voto.situacao.isSituacaoDaFilaValidForUser()){
        	return ResponseHandler.AdicionarVoto.EstadoSituacaoDaFila;
        }
        if(!voto.situacao.isFuncionamentoValidForUser()){
        	return ResponseHandler.AdicionarVoto.EstadoFuncionamento;
        }
		Pa.addVoto(voto);
        
        return ResponseHandler.AdicionarVoto.Successo;
    }
    
    
    public HashMap<Integer, PontoAlimentacao> getListaPa(){
    	Connection conexao = new ConnectionFactory().getConnection();
		pontosAlimentacoes = new PontoAlimentacaoDao(conexao).getLista();
		return pontosAlimentacoes;
    }
    
    public int getVersao(){return this.versao;}
    public boolean areWeOpen(){return this.myTimer.openingHours();}
    public boolean isSameVersao(int versao){return this.versao == versao;}
    
    public String getUltimaActualizacao(){return this.myTimer.getUltimaActualizacao();}
    private void updateVersao(){
    	atualizaBanco();
    }
    
    public void atualiza(){
		Connection conexao = new ConnectionFactory().getConnection();
		pontosAlimentacoes = new PontoAlimentacaoDao(conexao).getLista();
		updateVersao();
		//should do something like; 
		//new ParamsDao(conexao).updateVersao(this.versao);
    }
    
	@SuppressWarnings("unused")
	private class WrapperSituacao
	{
		String nome, ultimaActualizacao;
		SituacaoDoPA situacao;
		public WrapperSituacao(String n, String u, SituacaoDoPA s)
		{
			ultimaActualizacao = u;
			nome = n; 
			situacao = s;
		}
	}
	
    
    //getters
	public int getTempoVotacao() {return this.tempoVotacao;}
	public int getHoraAbertura() {
		return this.horaAbertura;
	}
	public int getHoraFechamento() {
		return this.horaFechamento;
	}
	
	//setters
	public void setTempoVotacao(int tempoVotacao) {
		this.tempoVotacao = tempoVotacao;
		if (myTimer != null){
			long tempoVotacaoMillis = tempoVotacao*60*1000;
			this.myTimer.setVotingTimeInterval(tempoVotacaoMillis);;
		}
	}
	public void setHoraAbertura(int horaAbertura) {
		this.horaAbertura = horaAbertura;
		if (myTimer != null){
			myTimer.setOpeningHour(horaAbertura);
		}
		
	}
	public void setHoraFechamento(int horaFechamento) {
		this.horaFechamento = horaFechamento;
		if (myTimer != null){
			myTimer.setClosingHour(horaFechamento);
		}
	}
	private void setVersao(int v){versao = v;}

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
		Connection con = new ConnectionFactory().getConnection();

		
		try {
			PreparedStatement stmt = (PreparedStatement) con.prepareStatement("select * from " + NOME_TABELA);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			this.setVersao(rs.getInt("versao"));
			this.setTempoVotacao(rs.getInt("tempo_votacao"));
			this.setHoraAbertura(rs.getInt("hora_abertura"));
			this.setHoraFechamento(rs.getInt("hora_fechamento"));
			System.out.println(rs.getInt("versao"));
			System.out.println(versao);
			
			rs.close();
			stmt.close();
		}catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("ALOOOOOU??");
		}finally {
			System.out.println("ta dando rium?");
			try {con.close();} 
			catch (SQLException e) {throw new RuntimeException (e);}
		}
	}

		//so o administrador pode actualizar ese estado
		public void atualizaBanco() {
			Connection conexao = new ConnectionFactory().getConnection();
			this.versao++;
			String sql = "update "+NOME_TABELA+" set"
					+ " versao=?, tempo_votacao=?, hora_abertura=?, hora_fechamento=?";
//					+ "where id=?";
			try {
				PreparedStatement stmt = conexao.prepareStatement(sql);
				
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


