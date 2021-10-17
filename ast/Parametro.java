/**
 * @generated VGen (for ANTLR) 1.7.2
 */

package ast;

import org.antlr.v4.runtime.*;

import visitor.*;

//	parametro -> tipo:tipo  nombre:String

public class Parametro extends DefVariable  {

	public Parametro(Tipo tipo, String nombre) {
		super(tipo, nombre, "param");
	}

	public Parametro(Object tipo, Object nombre) {
		super(tipo, nombre, "param");
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	public String toString() {
       return "{tipo:" + getTipo() + ", nombre:" + getNombre() + "}";
   }
}
