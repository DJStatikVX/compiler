/**
 * @generated VGen (for ANTLR) 1.7.2
 */

package ast;

import java.util.*;
import org.antlr.v4.runtime.*;

import visitor.*;

//	defStruct:definicion -> nombre:String  atributos:defAtributo*

public class DefStruct extends AbstractDefinicion {

	public DefStruct(String nombre, List<DefAtributo> atributos) {
		this.nombre = nombre;
		this.atributos = atributos;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(atributos);
	}

	public DefStruct(Object nombre, Object atributos) {
		this.nombre = (nombre instanceof Token) ? ((Token)nombre).getText() : (String) nombre;
		this.atributos = this.<DefAtributo>getAstFromContexts(atributos);

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(nombre, atributos);
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<DefAtributo> getAtributos() {
		return atributos;
	}
	public void setAtributos(List<DefAtributo> atributos) {
		this.atributos = atributos;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private String nombre;
	private List<DefAtributo> atributos;

	public String toString() {
       return "{nombre:" + getNombre() + ", atributos:" + getAtributos() + "}";
   }
}
