package it.polito.tdp.artsmia.db;

import it.polito.tdp.artsmia.model.ArtObject;

public class Adiacenza {
private ArtObject id1;
private ArtObject id2;
private int peso;
public Adiacenza(ArtObject id1, ArtObject id2, int peso) {
	super();
	this.id1 = id1;
	this.id2 = id2;
	this.peso = peso;
}
public ArtObject getId1() {
	return id1;
}
public ArtObject getId2() {
	return id2;
}
public int getPeso() {
	return peso;
}





}
