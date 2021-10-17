/**
 * @generated VGen (for ANTLR) 1.7.2
 */

package ast;

import visitor.*;

//	tipoInt:tipo -> 

public class TipoInt extends AbstractTipo {

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}


	public String toString() {
       return "{TipoInt}";
   }


	@Override
	public int getTamano() {
		return 2;
	}


	@Override
	public char getSufijo() {
		// TODO Auto-generated method stub
		return 'i';
	}


	@Override
	public String getNombreMAPL() {
		// TODO Auto-generated method stub
		return "int";
	}
}
