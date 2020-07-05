package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.Adiacenza;
import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
private	ArtsmiaDAO dao;
private Graph<ArtObject, DefaultWeightedEdge> graph;
private Map<Integer,ArtObject> map;
private 	List<ArtObject> bestSolution;
private int bestPeso;

	public Model() {
		this.dao= new ArtsmiaDAO();
		this.map= new HashMap<Integer, ArtObject>();
		this.dao.listObjects(map);
	}
	
	public List<ArtObject> listVertex() {
		return this.dao.listVertex(map);
	}

	public void creaGrafo() {
		this.graph= new SimpleWeightedGraph<ArtObject, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		List<ArtObject> vertex = this.dao.listVertex(map);
		if(vertex==null)
		{
			System.out.println("Errore lettura vertex");
			return;
		}
	Graphs.addAllVertices(this.graph, vertex);
	List<Adiacenza> edge = this.dao.listEdge(map);
	if(edge==null)
	{
		System.out.println("Errore lettura edge");
		return;
	}
	for(Adiacenza a : edge)
	{
		if(this.graph.containsVertex(a.getId1()) && this.graph.containsVertex(a.getId2()))
		{
			Graphs.addEdge(this.graph, a.getId1(), a.getId2(), a.getPeso());
		}
	}
		
		
	}
	public Set<ArtObject> setV()
	{
		return this.graph.vertexSet();
	}
	
	public Set<DefaultWeightedEdge> setE()
	{
		return this.graph.edgeSet();
	}

	public int componenteConn(int n) {

		ConnectivityInspector<ArtObject, DefaultWeightedEdge> cis = new ConnectivityInspector<ArtObject, DefaultWeightedEdge>(graph);
	return 	cis.connectedSetOf(map.get(n)).size();
		
	}

	public List<ArtObject> cammino(int lun, int source) {
	 this.bestSolution= new ArrayList<>();
	 this.bestPeso=0;
	 List<ArtObject> parziale= new ArrayList<ArtObject>();
	 parziale.add(map.get(source));
	 ricorsivo(parziale,lun,1,map.get(source).getClassification());
		
		return this.bestSolution;

	}

	private void ricorsivo(List<ArtObject> parziale, int lun, int i,String type) {
	if(parziale.size()==lun)
	{
		if(peso(parziale) > bestPeso)
		{
			this.bestSolution= new ArrayList<>(parziale);
			this.bestPeso=peso(parziale);
		}	
	}
	ArtObject last = parziale.get(parziale.size()-1);
	for(ArtObject vicino : Graphs.neighborListOf(this.graph, last))
	{
		if(!parziale.contains(vicino))
		{
			if(vicino.getClassification().equals(type))
			{parziale.add(vicino);
			ricorsivo(parziale,lun,i+1,type);
			parziale.remove(parziale.get(parziale.size()-1));}
		}
	}
	
	}

	private int peso(List<ArtObject> parziale) {
	int peso=0;
	for(int i=1; i< parziale.size();i++)
	{
		peso+=this.graph.getEdgeWeight(this.graph.getEdge(parziale.get(i-1), parziale.get(i)));
	}
		return peso;
	}

	public int getBestPeso() {
		return bestPeso;
	}

	public boolean isInGraph(int n) {
		if(this.graph.vertexSet().contains(map.get(n)))
				return true;
		return false;
	}
}
