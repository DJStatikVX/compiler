/**
 * @generated VGen (for ANTLR) 1.7.2
 */

package ast;

import visitor.*;

//	tipoVoid:tipo -> 

public class TipoVoid extends AbstractTipo {

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}


	public String toString() {
       return "{TipoVoid}";
   }


	@Override
	public int getTamano() {
		return 0;
	}


	@Override
	public char getSufijo() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public String getNombreMAPL() {
		// TODO Auto-generated method stub
		return null;
	}
}
