/**
 * @generated VGen (for ANTLR) 1.7.2
 */

package ast;

import visitor.*;

//	returnVacio:sentencia -> 

public class ReturnVacio extends AbstractSentencia {

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}


	public String toString() {
       return "{ReturnVacio}";
   }
}
