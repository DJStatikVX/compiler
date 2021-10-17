/**
 * @generated VGen (for ANTLR) 1.7.2
 */

package ast;

import java.util.*;
import visitor.*;

//	programa -> definiciones:definicion*

public class Programa extends AbstractAST  {

	public Programa(List<Definicion> definiciones) {
		this.definiciones = definiciones;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(definiciones);
	}

	public Programa(Object definiciones) {
		this.definiciones = this.<Definicion>getAstFromContexts(definiciones);

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(definiciones);
	}

	public List<Definicion> getDefiniciones() {
		return definiciones;
	}
	public void setDefiniciones(List<Definicion> definiciones) {
		this.definiciones = definiciones;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private List<Definicion> definiciones;

	public String toString() {
       return "{definiciones:" + getDefiniciones() + "}";
   }
}
