/**
 * @generated VGen (for ANTLR) 1.7.2
 */

package ast;

import visitor.*;

//	tipoFloat:tipo -> 

public class TipoFloat extends AbstractTipo {

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}


	public String toString() {
       return "{TipoFloat}";
   }


	@Override
	public int getTamano() {
		return 4;
	}


	@Override
	public char getSufijo() {
		// TODO Auto-generated method stub
		return 'f';
	}


	@Override
	public String getNombreMAPL() {
		// TODO Auto-generated method stub
		return "float";
	}
}
