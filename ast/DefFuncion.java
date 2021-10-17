/**
 * @generated VGen (for ANTLR) 1.7.2
 */

package ast;

import java.util.*;
import org.antlr.v4.runtime.*;

import visitor.*;

//	defFuncion:definicion -> nombre:String  parametros:parametro*  retorno:tipo  declaraciones:defVariable*  sentencias:sentencia*

public class DefFuncion extends AbstractDefinicion {

	public DefFuncion(String nombre, List<Parametro> parametros, Tipo retorno, List<DefVariable> declaraciones, List<Sentencia> sentencias) {
		this.nombre = nombre;
		this.parametros = parametros;
		this.retorno = retorno;
		this.declaraciones = declaraciones;
		this.sentencias = sentencias;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(parametros, retorno, declaraciones, sentencias);
	}

	public DefFuncion(Object nombre, Object parametros, Object retorno, Object declaraciones, Object sentencias) {
		this.nombre = (nombre instanceof Token) ? ((Token)nombre).getText() : (String) nombre;
		this.parametros = this.<Parametro>getAstFromContexts(parametros);
		this.retorno = (Tipo) getAST(retorno);
		this.declaraciones = this.<DefVariable>getAstFromContexts(declaraciones);
		this.sentencias = this.<Sentencia>getAstFromContexts(sentencias);

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(nombre, parametros, retorno, declaraciones, sentencias);
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Parametro> getParametros() {
		return parametros;
	}
	public void setParametros(List<Parametro> parametros) {
		this.parametros = parametros;
	}

	public Tipo getRetorno() {
		return retorno;
	}
	public void setRetorno(Tipo retorno) {
		this.retorno = retorno;
	}

	public List<DefVariable> getDeclaraciones() {
		return declaraciones;
	}
	public void setDeclaraciones(List<DefVariable> declaraciones) {
		this.declaraciones = declaraciones;
	}

	public List<Sentencia> getSentencias() {
		return sentencias;
	}
	public void setSentencias(List<Sentencia> sentencias) {
		this.sentencias = sentencias;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private String nombre;
	private List<Parametro> parametros;
	private Tipo retorno;
	private List<DefVariable> declaraciones;
	private List<Sentencia> sentencias;

	public String toString() {
       return "{nombre:" + getNombre() + ", parametros:" + getParametros() + ", retorno:" + getRetorno() + ", declaraciones:" + getDeclaraciones() + ", sentencias:" + getSentencias() + "}";
   }
}
