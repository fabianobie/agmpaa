/**
 * PAA - Projeto de Analise e Algoritmos
 * Universidade Estadual do Ceará - UECE
 * Alunos: Fabiano Tavares e Diego Sá
 * Prof. Marcos Negreiro
 *
 */
package br.uece.comp.paa.estruturas;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

/**
 * @author Fabiano Tavares (fabiano.bie@gmail.com)
 * 
 */
public class HeapFibonacci<T> {

	//numeros de nohs da heap
	private int numNoh;
	
	//o minimo da heap de fibonacci
	private HeapFibonacciNoh<T> minNoh;

	public HeapFibonacci() {
		reset();
	}

	/**
	 * Insere novo valor a Heap de Fibonacci
	 * 
	 * @param chave
	 * @param info
	 */
	public void inserir(double chave, T info) {
		HeapFibonacciNoh<T> noh = new HeapFibonacciNoh<T>(info, chave);
		colocar(noh);
		this.numNoh++;
	}
	
	private void colocar(HeapFibonacciNoh<T> noh) {
		if (this.minNoh != null) {
			// faz a ligação do novo elemento a lista de raizes
			concatMin(noh);
			// se menor vira o novo minimo
			if (noh.getChave() < this.minNoh.getChave()) {
				this.minNoh = noh;
			}
		} else {
			this.minNoh = noh;
		}
	}

	/**
	 * Concatenacao do elemento a lista de raizes [O<=>O<=>O]
	 * 
	 * @param noh
	 */
	private void concatMin(HeapFibonacciNoh<T> noh) {
		noh.setDireito(this.minNoh);
		noh.setEsquerdo(this.minNoh.getEsquerdo());
		this.minNoh.setEsquerdo(noh);
		noh.getEsquerdo().setDireito(noh);
	}

	public void reset() {
		minNoh = null;
		numNoh = 0;
	}

	public boolean isVazio() {
		return minNoh == null;
	}

	public void deleta(HeapFibonacciNoh<T> noh) {
		diminuirChave(noh, Double.NEGATIVE_INFINITY);
		extrairMin();
	}

	/**
	 * Remove o Menor Valor
	 * 
	 * @return
	 */
	public HeapFibonacciNoh<T> extrairMin() {

		HeapFibonacciNoh<T> min = this.minNoh;

		if (min != null) {
			int numFilho = min.getGrau();
			HeapFibonacciNoh<T> nohFilho = min.getFilho();
			HeapFibonacciNoh<T> tempRight;

			// para cada filho do minimo
			while (numFilho > 0) {
				tempRight = nohFilho.getDireito();

				// remove filho da lista de filho
				nohFilho.getEsquerdo().setDireito(nohFilho.getDireito());
				nohFilho.getDireito().setEsquerdo(nohFilho.getEsquerdo());

				// adciona o filho a lista raiz da heap
				concatMin(nohFilho);

				// filho perde o pai
				nohFilho.setPai(null);
				nohFilho = tempRight;
				numFilho--;
			}

			// remove o minimo da lista
			min.getEsquerdo().setDireito(min.getDireito());
			min.getDireito().setEsquerdo(min.getEsquerdo());

			if (min == min.getDireito()) {
				this.minNoh = null;
			} else {
				this.minNoh = min.getDireito();
				consolidar();
			}

			this.numNoh--;
		}

		return min;
	}

	protected void consolidar() {
		int x = 0, arrTam = this.numNoh + 1;
		ArrayList<HeapFibonacciNoh<T>> arrGraus = new ArrayList<HeapFibonacciNoh<T>>(
				arrTam);
		// inicia array de graus
		while (x < arrTam) {
			arrGraus.add(null);
			x++;
		}
		int numRaizes = 0;
		HeapFibonacciNoh<T> noh = this.minNoh;
		// conta o numero de raizes
		if (noh != null) {
			do {
				numRaizes++;
				noh = noh.getDireito();

			} while (noh != this.minNoh);
		}

		// para cada noh raiz
		while (numRaizes > 0) {
			int grau = noh.getGrau();
			HeapFibonacciNoh<T> prox = noh.getDireito();
			// se ha outro do mesmo grau
			while (arrGraus.get(grau) != null) {
				// outro no com o mesmo grau de noh
				HeapFibonacciNoh<T> outroNoh = arrGraus.get(grau);

				// de acordo com o valor da chave
				if (noh.getChave() > outroNoh.getChave()) {
					HeapFibonacciNoh<T> aux = outroNoh;
					outroNoh = noh;
					noh = aux;
				}
				// Eliminamos outroNoh da lista de nohs raizes e tornamos filho
				ligar(outroNoh, noh);
				arrGraus.set(grau, null);
				grau++;
			}
			// salvar noh pra depois encontrar outro de mesmo grau
			arrGraus.set(grau, noh);
			noh = prox;
			numRaizes--;
		}
		//minNoh recebe nulo e retirado da lista
		this.minNoh = null;
		//limpeza e reconstrução da lista
		for (int i = 0; i < arrTam; i++){
			HeapFibonacciNoh<T> nohAux  = arrGraus.get(i); 
			if (arrGraus.get(i) != null){
				if (this.minNoh != null){
					//removemos o noh da lista
					nohAux.setEsquerdo(nohAux);
					nohAux.setDireito(nohAux);
					colocar(nohAux);
				} else {
					this.minNoh = nohAux;
				}
			}
		}
	}

	/**
	 * @param outroNoh
	 * @param noh
	 */
	private void ligar(HeapFibonacciNoh<T> outroNoh, HeapFibonacciNoh<T> noh) {
		// remove outroNoh da lista
		outroNoh.getEsquerdo().setDireito(outroNoh.getDireito());
		outroNoh.getDireito().setEsquerdo(outroNoh.getEsquerdo());
		// faz o outroNoh virar filho de noh
		outroNoh.setPai(noh);
		if (noh.getFilho() == null) {
			noh.setFilho(outroNoh);
			outroNoh.setDireito(outroNoh);
			outroNoh.setDireito(outroNoh);
		} else {
			outroNoh.setEsquerdo(noh.getFilho());
			outroNoh.setDireito(noh.getFilho().getDireito());
			noh.getFilho().setDireito(outroNoh);
			outroNoh.getDireito().setEsquerdo(outroNoh);
		}
		// aumenta o grau do noh, pois aumentou o numero de filhos
		noh.setGrau(noh.getGrau() + 1);
		// marca ele como falso
		outroNoh.setMarcado(false);
	}

	public void diminuirChave(HeapFibonacciNoh<T> noh, double chave) {
		if (chave > noh.getChave()) {
			System.out.println("ERRO: Valor de chave muito pequeno");
		} else {
			noh.setChave(chave);
			HeapFibonacciNoh<T> pai = noh.getPai();

			if ((pai != null) && (noh.getChave() < pai.getChave())) {
				cortar(noh, pai);
				cortarCascata(pai);
			}

			if (noh.getChave() < minNoh.getChave()) {
				minNoh = noh;
			}
		}
	}

	/**
	 * @param noh
	 * @param pai
	 */
	private void cortar(HeapFibonacciNoh<T> nohFilho, HeapFibonacciNoh<T> pai) {
	     // remove nohFilho da lista de de filhos do pai
        nohFilho.getEsquerdo().setDireito(nohFilho.getDireito());
        nohFilho.getDireito().setEsquerdo(nohFilho.getEsquerdo());
        pai.setGrau(pai.getGrau() - 1);

        //reinicia o o filho do pai se necessario
        if (pai.getFilho() == nohFilho) {
            pai.setFilho(nohFilho.getDireito());
        }

        if (pai.getGrau() == 0) {
            pai.setFilho(null);
        }

        //adiciona filho a lista raiz
        nohFilho.setEsquerdo(this.minNoh);
        nohFilho.setDireito(this.minNoh.getDireito());
        this.minNoh.setDireito(nohFilho);
        nohFilho.getDireito().setEsquerdo(nohFilho);

        //filho perde o pai
        nohFilho.setPai(null);

        //filho eh desmarcado caso marcado
        nohFilho.setMarcado(false);
	}
	
	
	/**
	 * @param pai
	 */
	private void cortarCascata(HeapFibonacciNoh<T> nohFilho) {
	     HeapFibonacciNoh<T> pai = nohFilho.getPai();

	        //se ha um filho
	        if (pai != null) {
	            // se o noh naum esta marcado , marcar
	            if (!nohFilho.isMarcado()) {
	                nohFilho.setMarcado(true);
	            } else {
	                //se marcado corte do pai
	                cortar(nohFilho, pai);
	                //corte de seu pai tambem
	                cortarCascata(pai);
	            }
	        }
	}
	
	/***
	 * Teste
	 */
	
	   public static void main(String args[]){
	        HeapFibonacci<Integer> heap = new HeapFibonacci<Integer>();

	        Random generator = new Random(12823423);
	        for(int i = 0; i < 1000; i++){
	                int temp = generator.nextInt(50000);
	                heap.inserir(new Integer(temp), temp);
	                //System.out.println(heap.toGraphViz());
	        }

	                heap.consolidar();
	                
	                //System.out.println(heap.toGraphViz());
	                //ArrayList<String> dot = new ArrayList<String>();
	                //while(!heap.isEmpty()){
	                  //      heap.removeMin();
	                  //      dot.add(heap.toGraphViz());
	                //}


	        System.out.println("Acabou !");

	    }


	   	/**
	   	 * Gerar aquivo para o formato GraphViz
	   	 * Modelo baseado de codigo modelado no Site do GraphViz
	   	 * @return
	   	 */
/*	    public String toGraphViz(){
	        if (this.minNoh == null) {
	            return "FibonacciHeap=[]";
	        }

	        // create a new stack and put root on it
	        Stack<HeapFibonacciNoh<T>> stack = new Stack<HeapFibonacciNoh<T>>();
	        stack.push(this.minNoh);
	        ArrayList<HeapFibonacciNoh<T>> list = new ArrayList<HeapFibonacciNoh<T>>();
	        ArrayList<HeapFibonacciNoh<T>> rank = new ArrayList<HeapFibonacciNoh<T>>();

	        StringBuffer buf = new StringBuffer(512);
	        buf.append("digraph G{\n");
	        buf.append("ranksep=1.5;\n");

	        // do a simple breadth-first traversal on the tree
	        while (!stack.empty()) {
	            HeapFibonacciNoh<T> curr = stack.pop();
	            if(!list.contains(curr)){
	                list.add(curr);

	                HeapFibonacciNoh<T> tempRankCurr = curr;
	                buf.append("{ rank = same; ");
	                while(!rank.contains(tempRankCurr)){
	                        rank.add(tempRankCurr);
	                        buf.append(tempRankCurr.getPK() + "; ");
	                        tempRankCurr = tempRankCurr.getRight();

	                }
	                buf.append("}\n");
	                buf.append(curr.toGraphViz() + "\n");

	                if(curr.getParent() != null && curr.getParent() != curr && !list.contains(curr.getParent())){
	                        buf.append(curr.getPK() + " -> " + curr.getParent().getPK() + ";\n");
	                        buf.append(curr.getParent().getPK() + " -> " + curr.getPK() + ";\n");
	                }
	                if(curr.getRight() != null && curr.getRight() != curr && !list.contains(curr.getRight())){
	                        buf.append(curr.getPK() + " -> " + curr.getRight().getPK() + ";\n");
	                        buf.append(curr.getRight().getPK() + " -> " + curr.getPK() + ";\n");
	                }
	                if(curr.getLeft() != null && curr.getLeft() != curr && curr.getLeft() != curr.getRight() && !list.contains(curr.getLeft())){
	                        buf.append(curr.getPK() + " -> " + curr.getLeft().getPK() + ";\n");
	                        buf.append(curr.getLeft().getPK() + " -> " + curr.getPK() + ";\n");
	                }
	                if(curr.getChild() != null && curr.getChild() != curr && !list.contains(curr.getChild())){
	                        buf.append(curr.getPK() + " -> " + curr.getChild().getPK() + ";\n");
	                        buf.append(curr.getChild().getPK() + " -> " + curr.getPK() + ";\n");
	                }
	            }

	            if (curr.getChild() != null) {
	                stack.push(curr.getChild());
	            }

	            HeapFibonacciNoh<T> start = curr;
	            curr = curr.getRight();

	            while (curr != start) {
	                if(!list.contains(curr)){
	                        list.add(curr);
	                        buf.append(curr.toGraphViz() + "\n");

	                    if(curr.getParent() != null && curr.getParent() != curr && !list.contains(curr.getParent())){
	                        buf.append(curr.getPK() + " -> " + curr.getParent().getPK() + ";\n");
	                        buf.append(curr.getParent().getPK() + " -> " + curr.getPK() + ";\n");
	                    }
	                    if(curr.getRight() != null && curr.getRight() != curr && !list.contains(curr.getRight())){
	                        buf.append(curr.getPK() + " -> " + curr.getRight().getPK() + ";\n");
	                        buf.append(curr.getRight().getPK() + " -> " + curr.getPK() + ";\n");
	                    }
	                    if(curr.getLeft() != null && curr.getLeft() != curr && curr.getLeft() != curr.getRight() && !list.contains(curr.getLeft())){
	                        buf.append(curr.getPK() + " -> " + curr.getLeft().getPK() + ";\n");
	                        buf.append(curr.getLeft().getPK() + " -> " + curr.getPK() + ";\n");
	                    }
	                    if(curr.getChild() != null && curr.getChild() != curr && !list.contains(curr.getChild())){
	                        buf.append(curr.getPK() + " -> " + curr.getChild().getPK() + ";\n");
	                        buf.append(curr.getChild().getPK() + " -> " + curr.getPK() + ";\n");
	                    }
	                }

	                if (curr.getChild() != null) {
	                    stack.push(curr.getChild());
	                }

	                curr = curr.getRight();
	            }
	        }

	        buf.append('}');

	        return buf.toString();
	    }
	}
*/
	
	
	
}
