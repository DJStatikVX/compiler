/**
 * @generated VGen (for ANTLR) 1.7.2
 */

package ast;

import java.util.*;
import org.antlr.v4.runtime.*;

import visitor.*;

//	llamadaFuncionSentencia:sentencia -> nombre:String  args:Expresion*

public class LlamadaFuncionSentencia extends AbstractSentencia {
	
	private DefFuncion definicion;
	
	public DefFuncion getDefinicion() {
		return definicion;
	}
	
	public void setDefinicion(DefFuncion definicion) {
		this.definicion = definicion;
	}

	public LlamadaFuncionSentencia(String nombre, List<Expresion> args) {
		this.nombre = nombre;
		this.args = args;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(args);
	}

	public LlamadaFuncionSentencia(Object nombre, Object args) {
		this.nombre = (nombre instanceof Token) ? ((Token)nombre).getText() : (String) nombre;
		this.args = this.<Expresion>getAstFromContexts(args);

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(nombre, args);
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Expresion> getArgs() {
		return args;
	}
	public void setArgs(List<Expresion> args) {
		this.args = args;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private String nombre;
	private List<Expresion> args;

	public String toString() {
       return "{nombre:" + getNombre() + ", args:" + getArgs() + "}";
   }
}
