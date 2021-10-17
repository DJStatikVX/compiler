/**
 * @generated VGen (for ANTLR) 1.7.2
 */

package ast;

import org.antlr.v4.runtime.*;

import visitor.*;

//	tipoStruct:tipo -> nombre:String

public class TipoStruct extends AbstractTipo {
	
	private DefStruct definicion;
	
	public DefStruct getDefinicion() {
		return definicion;
	}
	
	public void setDefinicion(DefStruct definicion) {
		this.definicion = definicion;
	}

	public TipoStruct(String nombre) {
		this.nombre = nombre;
	}

	public TipoStruct(Object nombre) {
		this.nombre = (nombre instanceof Token) ? ((Token)nombre).getText() : (String) nombre;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(nombre);
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private String nombre;

	public String toString() {
       return "{nombre:" + getNombre() + "}";
   }

	@Override
	public int getTamano() {
		int total = 0;
		
		for (DefAtributo child : definicion.getAtributos()) {
			total += child.getTipo().getTamano();
		}
		
		return total;
	}

	@Override
	public char getSufijo() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getNombreMAPL() {
		return nombre;
	}
}
