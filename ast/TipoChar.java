/**
 * @generated VGen (for ANTLR) 1.7.2
 */

package ast;

import visitor.*;

//	tipoChar:tipo -> 

public class TipoChar extends AbstractTipo {

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}


	public String toString() {
       return "{TipoChar}";
   }


	@Override
	public int getTamano() {
		return 1;
	}


	@Override
	public char getSufijo() {
		// TODO Auto-generated method stub
		return 'b';
	}


	@Override
	public String getNombreMAPL() {
		// TODO Auto-generated method stub
		return "char";
	}
}
