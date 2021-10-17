/**
 * @generated VGen (for ANTLR) 1.7.2
 */

package ast;

public abstract class AbstractSentencia extends AbstractAST implements Sentencia {
	
	public void setFuncion(DefFuncion funcion) {
		this.funcion = funcion;
	}
	
	public DefFuncion getFuncion() {
		return funcion;
	}
	
	private DefFuncion funcion;

}
