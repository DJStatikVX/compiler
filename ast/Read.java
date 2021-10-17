/**
 * @generated VGen (for ANTLR) 1.7.2
 */

package ast;

import org.antlr.v4.runtime.*;

import visitor.*;

//	read:sentencia -> variable:expresion

public class Read extends AbstractSentencia {

	public Read(Expresion variable) {
		this.variable = variable;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(variable);
	}

	public Read(Object variable) {
		this.variable = (Expresion) getAST(variable);

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(variable);
	}

	public Expresion getVariable() {
		return variable;
	}
	public void setVariable(Expresion variable) {
		this.variable = variable;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion variable;

	public String toString() {
       return "{variable:" + getVariable() + "}";
   }
}
