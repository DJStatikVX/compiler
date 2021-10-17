/**
 * @generated VGen (for ANTLR) 1.7.2
 */

package ast;

import org.antlr.v4.runtime.*;

import visitor.*;

//	defVariable:definicion -> tipo:tipo  nombre:String

public class DefVariable extends AbstractDefinicion {
	
	private int direccion;
	
	public void setDireccion(int direccion) {
		this.direccion = direccion;
	}
	
	public int getDireccion() {
		return direccion;
	}

	public DefVariable(Tipo tipo, String nombre, String ambito) {
		this.tipo = tipo;
		this.nombre = nombre;
		this.ambito = ambito;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(tipo);
	}

	public DefVariable(Object tipo, Object nombre, Object ambito) {
		this.tipo = (Tipo) getAST(tipo);
		this.nombre = (nombre instanceof Token) ? ((Token)nombre).getText() : (String) nombre;
		this.ambito = (ambito instanceof Token) ? ((Token)ambito).getText() : (String) ambito;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(tipo, ambito);
	}

	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getAmbito() {
		return ambito;
	}

	public void setAmbito(String ambito) {
		this.ambito = ambito;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Tipo tipo;
	private String nombre;
	private String ambito;

	public String toString() {
       return "{tipo:" + getTipo() + ", nombre:" + getNombre() + "}";
   }
}
