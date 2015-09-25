package ufrj.cos.famelicus.servidor.model.metadata;

import com.google.gson.annotations.SerializedName;


public class SituacaoDoPA {   
    public SituacaoDaFila situacaoDaFila;
    public Funcionamento funcionamento;
    
    public SituacaoDoPA(SituacaoDaFila s, Funcionamento f) {
        situacaoDaFila = s;
        funcionamento = f;
    }
    
    public SituacaoDoPA(int situacaoFila, int funcionamentoRestaurante){
        set(situacaoFila, funcionamentoRestaurante);
    }
    
    public boolean isSituacaoDaFilaValidForUser()
    {
    	return (situacaoDaFila != SituacaoDaFila.NaoConhecido);
    }

    public boolean isFuncionamentoValidForUser()
    {
    	return (funcionamento != Funcionamento.NaoConhecido);
    }
    
    
    public void set(int situacaoFila, int funcionamentoRestaurante){
    switch (situacaoFila) {
		case 0:
			situacaoDaFila = SituacaoDaFila.Vazia;
			break;
 
		case 1:
			situacaoDaFila = SituacaoDaFila.FilaPequena;
			break;

		case 2:
			situacaoDaFila = SituacaoDaFila.FilaMedia;
			break;
			
		case 3:
			situacaoDaFila = SituacaoDaFila.FilaGrande;
			break;	
 
		case 4:
			situacaoDaFila = SituacaoDaFila.Lotado;
			break;
			
		default:
			situacaoDaFila = SituacaoDaFila.NaoConhecido;
			break;
    }
    
    switch (funcionamentoRestaurante) {
		case 0:
			funcionamento = Funcionamento.Fechado;
			break;
	
		case 1:
			funcionamento = Funcionamento.Aberto;
			break;
			
		default:
			funcionamento = Funcionamento.NaoConhecido;
			break;
	    }
}

	public static SituacaoDoPA getDefault() {
        return new SituacaoDoPA(SituacaoDaFila.NaoConhecido, Funcionamento.NaoConhecido);
    }
	
	

    public static enum  SituacaoDaFila{
    	@SerializedName("-1")
        NaoConhecido (-1),
    	@SerializedName("0")
        Vazia (0),
    	@SerializedName("1")
        FilaPequena (1),
    	@SerializedName("2")
        FilaMedia (2),
    	@SerializedName("3")
        FilaGrande (3),
    	@SerializedName("4")
        Lotado (4);
        
        private final int value;
        
        SituacaoDaFila(int value){this.value = value;}
        public int getValue(){return value;}
    }

    public static enum Funcionamento {
    	@SerializedName("-1")
        NaoConhecido (-1),
    	@SerializedName("0")
        Fechado (0),
    	@SerializedName("1")
        Aberto (1); 
        
        private final int value;
     
        Funcionamento(int value){this.value = value;}
        public int getValue(){return value;}
    }
    
}
