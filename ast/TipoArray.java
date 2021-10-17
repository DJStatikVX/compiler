/**
 * @generated VGen (for ANTLR) 1.7.2
 */

package ast;

import org.antlr.v4.runtime.*;

import visitor.*;

//	tipoArray:tipo -> tipo:tipo  dimensiones:String

public class TipoArray extends AbstractTipo {

	public TipoArray(Tipo tipo, String dimensiones) {
		this.tipo = tipo;
		this.dimensiones = dimensiones;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(tipo);
	}

	public TipoArray(Object tipo, Object dimensiones) {
		this.tipo = (Tipo) getAST(tipo);
		this.dimensiones = (dimensiones instanceof Token) ? ((Token)dimensiones).getText() : (String) dimensiones;

       // Lo siguiente se puede borrar si no se quiere la posicion en el fichero.
       // Obtiene la linea/columna a partir de las de los hijos.
       setPositions(tipo, dimensiones);
	}

	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public String getDimensiones() {
		return dimensiones;
	}
	public void setDimensiones(String dimensiones) {
		this.dimensiones = dimensiones;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Tipo tipo;
	private String dimensiones;

	public String toString() {
       return "{tipo:" + getTipo() + ", dimensiones:" + getDimensiones() + "}";
   }

	@Override
	public int getTamano() {
		return tipo.getTamano() * Integer.parseInt(dimensiones);
	}

	@Override
	public char getSufijo() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getNombreMAPL() {
		return String.format("%s * %s", dimensiones, tipo.getNombreMAPL());
	}
}
