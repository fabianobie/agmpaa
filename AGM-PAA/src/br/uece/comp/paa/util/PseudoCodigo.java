/**
 * PAA - Projeto de Analise e Algoritmos
 * Universidade Estadual do Ceará - UECE
 * Alunos: Fabiano Tavares e Diego Sá
 * Prof. Marcos Negreiro
 *
 */
package br.uece.comp.paa.util;

/**
 * @author Fabiano Tavares (fabiano.bie@gmail.com)
 *
 */
public class PseudoCodigo {
	
	public static  final String PRIM="procedure PRIM(G: grafo)\n"+
"     Seja T um único vértice x\n"+
"     while (T tem menos de n vértices)\n"+ 
"     begin \n"+
"         Encontre o menor borda conectando T para G-T\n"+ 
"         adicioná-lo ao T\n"+ 
"     end";
	
	public static final String KRUSKAL="procedure KRUSKAL(G: grafo)\n"+
"     Ordenar as arestas de G em ordem crescente de comprimento\n"+ 
"     manter um subgrafo S de G, inicialmente vazia\n"+ 
"     foreach aresta e na ordem de classificação\n"+ 
"	begin\n"+
"\n"+
"                  if os pontos de extremidade e são desligados em S\n"+ 
"                  add e ao S\n"+ 
"	end\n"+
"return S";
	
	public static  final String BORUVKA="procedure BORUVKA(G: grafo)\n"+
"begin\n"+
"     fazer uma lista L de n árvores, cada vértice uma única\n"+ 
"\n"+
"     while (L tem mais de uma árvore)\n"+ 
"	begin\n"+
"         foreach  T em L, a menor margem de ligação T GT\n"+ 
"         adicionar todas as arestas com o MST\n"+ 
"	end\n"+
"    (*causando pares de árvores em L para mesclar*)\n"+
"\n"+
"end";
	
	
	public static final String GENETICO="procedure edge-insertion-mutation(Et: grafo );\n"+
"begin\n"+
"(* Escolhe aresta(i, j) para inserção *)\n"+
"choose i ∈ V randomly;\n"+
"choose j ∈ V / {i} | deg(j) < d ∧ (i, j) ∈ ET randomly;\n"+
"\n"+
"(* Escolhe aresta(i, j) para deleção *)\n"+
"L ← {(k, l) ∈ ET | (k, l) lies on path from i to j};\n"+
"   if deg(i) = d then\n"+
"         (a, b) ← (a, b) ∈ L | a = i ∨ b = i;\n"+
"    else\n"+
"      begin\n"+
"        choose (a, b) ∈ L randomly;\n"+
"        ET ← ET ∪ {(i, j)} / {(a, b)};\n"+
"       end\n"+
" return ET ;\n"+
"end;\n"+
"\n"+
"\n"+
"procedure edge-crossover(E1: grafo, E2: grafo );\n"+
"begin\n"+
"   ET ← E1 ∩ E2 ;\n"+
"   F ← E1 ∪ E2 / ET ;\n"+
"for all edges (i, j) ∈ F in random order do\n"+
"begin\n"+
"      if deg(i) < d and deg(j) < d and not(connected(i, j, ET )) then\n"+
"           ET ← ET ∪ {(i, j)};\n"+
"      if |ET | = |V | − 1 then\n"+
"  return ET ;\n"+
"end;\n"+
"(* Determine todas as aresta não conectadas a Uk : *)\n"+
"    U ← {Uk } with ∀i, j ∈ V, i = j :\n"+
"    i ∈ Uk ∧ connected(i, j, ET ) −→ j ∈ Uk ,\n"+
"    i ∈ Uk ∧ not(connected(i, j, ET )) −→ j ∈ Uk ,\n"+
"    k Uk = V ;\n"+
"(* Conecte randomicamente o grafo filho*)\n"+
"  for all Uk ∈ U / {U1 } in random order do\n"+
"  begin\n"+
"       choose i ∈ U1 | deg(i) < d randomly;\n"+
"       choose j ∈ Uk | deg(j) < d randomly;\n"+
"         ET ← ET ∪ {(i, j)};\n"+
"        U1 ← U1 ∪ Uk ;\n"+
"  end;\n"+
"  return ET ;\n"+
"end;\n"+
"\n"+
"\n"+
"procedure gerarPopulacao(G: Grafos)\n"+
"begin\n"+
"   i=0 : int;\n"+
"   HG ← ∅ : HeapFibonacci;\n"+
"   while ( i <)\n"+
"    begin\n"+
"        HG ← init();\n"+
"        i=i+1;\n"+
"    end;\n"+
"return HG;\n"+
"end\n"+
"\n"+
"procedure init;\n"+
"begin\n"+
"      Et ← ∅;\n"+
"      for all edges (i, j) ∈ E in random order do\n"+
"            if deg(i) < d and deg(j) < d and not(connected(i, j, Et )) then\n"+
"                  Et ← Et ∪ {(i, j)};\n"+
"    if |Et| = |V| − 1 then\n"+
"      return ET ;\n"+
"end;\n"+
"\n"+
"procedure geneticoAGM(G: grafo)\n"+
"begin\n"+
"(*gerações*)\n"+
"   g = 0 ;  i =0; int ;\n"+
"   populacaoGlobal: HeapFibonacci;\n"+
"   populacaoLocal: HeapFibonacci;\n"+
"\n"+
"  populacaoLocal ← gerarPopulacao(G)\n"+
"   while ( i < g)\n"+
"    begin\n"+
"        selecao(populacaoLocal);\n"+
"        reproducao(populacaoLocal)\n"+
"         temp ← extrairMinimo(populacaoLocal);\n"+
"         populacaoGlobal ← temp;\n"+
"        i ← i+1;\n"+
"    end;\n"+
"return extrairMinimo(populacaoGlobal);\n"+
"end";

		}
