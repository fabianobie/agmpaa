/**
 * PAA - Projeto de Analise e Algoritmos
 * Universidade Estadual do Ceará - UECE
 * Alunos: Fabiano Tavares e Diego Sá
 * Prof. Marcos Negreiro
 *
 */
package br.uece.comp.paa.estruturas;

/**
 * @author Fabiano Tavares (fabiano.bie@gmail.com)
 *
 */
public class HeapFibonacciNoh<T>{
  
	/*
	 * No Raiz
	 */
    private HeapFibonacciNoh<T> pai;
    
    /*
     * Um dos filhos
     */
    private HeapFibonacciNoh<T> filho;

    /*
     * Irmao direito
     */
    private HeapFibonacciNoh<T> direito;
    
    /*
     * irmao esquerdo
     */
    private HeapFibonacciNoh<T> esquerdo;
    

	/*
	 * informação a ser armazenada
	 */
    private T info;
    
    /*
     * Informa se ele perdeu o filho desde a ultima vez q ele foi feito filho
     */
    private boolean marcado;
    
    /*
     * Chave de Identificação
     */
    private double chave;

    /*
     * quantos filhos possui
     */
    private int grau;



    /**
     * Construtor da Heap
     * @param info
     * @param chave
     */
    public  HeapFibonacciNoh(T info, double chave)
    {
        this.info = info;
        this.chave = chave;
        direito = this;
        esquerdo = this;
        grau = 0;
        marcado = false;
    }

    
    /**
     * @return
     */
    public HeapFibonacciNoh<T> getPai() {
		return pai;
	}


	/**
	 * @param pai
	 */
	public void setPai(HeapFibonacciNoh<T> pai) {
		this.pai = pai;
	}


	/**
	 * @return
	 */
	public HeapFibonacciNoh<T> getFilho() {
		return filho;
	}


	public void setFilho(HeapFibonacciNoh<T> filho) {
		this.filho = filho;
	}


	public HeapFibonacciNoh<T> getDireito() {
		return direito;
	}


	public void setDireito(HeapFibonacciNoh<T> direito) {
		this.direito = direito;
	}


	public HeapFibonacciNoh<T> getEsquerdo() {
		return esquerdo;
	}


	public void setEsquerdo(HeapFibonacciNoh<T> esquerdo) {
		this.esquerdo = esquerdo;
	}


	public T getInfo() {
		return info;
	}


	public void setInfo(T info) {
		this.info = info;
	}


	public boolean isMarcado() {
		return marcado;
	}


	public void setMarcado(boolean marcado) {
		this.marcado = marcado;
	}


	public double getChave() {
		return chave;
	}


	public void setChave(double chave) {
		this.chave = chave;
	}


	public int getGrau() {
		return grau;
	}


	public void setGrau(int grau) {
		this.grau = grau;
	}



	/**
	 * Metodo de impressão de apenas um Noh da Heap
	 * @see java.lang.Object#toString()
	 */
	public String toString()
    {
            StringBuffer buf = new StringBuffer();
            buf.append("Noh = [ ");

            if (pai != null) {
                buf.append(Double.toString(pai.chave));
            } else {
                buf.append("---");
            }

            buf.append(", chave = ");
            buf.append(Double.toString(chave));
            buf.append(", grau = ");
            buf.append(Integer.toString(grau));
            buf.append(", direito = ");

            if (direito != null) {
                buf.append(Double.toString(direito.chave));
            } else {
                buf.append("---");
            }

            buf.append(", esquerdo = ");

            if (esquerdo != null) {
                buf.append(Double.toString(esquerdo.chave));
            } else {
                buf.append("---");
            }

            buf.append(", filho = ");

            if (filho != null) {
                buf.append(Double.toString(filho.chave));
            } else {
                buf.append("---");
            }

            buf.append(']');

            return buf.toString();
    }
	
}

