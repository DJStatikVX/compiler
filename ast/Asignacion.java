/**
 * @generated VGen (for ANTLR) 1.7.2
 */

package ast;

import org.antlr.v4.runtime.*;

import visitor.*;

//	asignacion:sentencia -> variable:expresion  valor:expresion

public class Asignacion extends AbstractSentencia {

	public Asignacion(Expresion variable, Expresion valor) {
		this.variable = variable;
		this.valor = valor;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(variable, valor);
	}

	public Asignacion(Object variable, Object valor) {
		this.variable = (Expresion) getAST(variable);
		this.valor = (Expresion) getAST(valor);

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(variable, valor);
	}

	public Expresion getVariable() {
		return variable;
	}
	public void setVariable(Expresion variable) {
		this.variable = variable;
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

	private Expresion variable;
	private Expresion valor;

	public String toString() {
       return "{variable:" + getVariable() + ", valor:" + getValor() + "}";
   }
}
