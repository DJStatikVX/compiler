/**
 * @generated VGen (for ANTLR) 1.7.2
 */

package ast;

import org.antlr.v4.runtime.*;

import visitor.*;

//	inicializacion -> tipo:tipo  nombre:String  ambito:String  valor:expresion

public class Inicializacion extends DefVariable  {

	public Inicializacion(Tipo tipo, String nombre, String ambito, Expresion valor) {
		super(tipo, nombre, ambito);
		this.valor = valor;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(tipo, valor);
	}

	public Inicializacion(Object tipo, Object nombre, Object ambito, Object valor) {
		super(tipo, nombre, ambito);
		this.valor = (Expresion) getAST(valor);

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(tipo, nombre, ambito, valor);
	}

	public Expresion getValor() {
		return valor;
	}
	public void setValor(Expresion valor) {
		this.valor = valor;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion valor;

	public String toString() {
       return "{tipo:" + getTipo() + ", nombre:" + getNombre() + ", ambito:" + getAmbito() + ", valor:" + getValor() + "}";
   }
}
