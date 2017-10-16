package br.com.porthal.experimento.enums;

public enum TipoConta  {
	S("Sintética"), A("Analítica");
	
	private final String nomeTipo;
	
	private TipoConta(String nome) { 
		this.nomeTipo = nome;
	}
	
	public String getNomeTipo() { 
		return nomeTipo;
	}
        
        @Override
        public String toString(){
            return getNomeTipo();
        }
}
