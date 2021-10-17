/**
 * @generated VGen (for ANTLR) 1.7.2
 */

package visitor;

import ast.*;

public interface Visitor {
	public Object visit(Programa node, Object param);
	public Object visit(TipoInt node, Object param);
	public Object visit(TipoFloat node, Object param);
	public Object visit(TipoChar node, Object param);
	public Object visit(TipoVoid node, Object param);
	public Object visit(TipoArray node, Object param);
	public Object visit(TipoStruct node, Object param);
	public Object visit(DefVariable node, Object param);
	public Object visit(DefAtributo node, Object param);
	public Object visit(DefStruct node, Object param);
	public Object visit(DefFuncion node, Object param);
	public Object visit(Inicializacion node, Object param);
	public Object visit(Parametro node, Object param);
	public Object visit(LlamadaFuncionExpresion node, Object param);
	public Object visit(Asignacion node, Object param);
	public Object visit(Read node, Object param);
	public Object visit(If node, Object param);
	public Object visit(While node, Object param);
	public Object visit(Print node, Object param);
	public Object visit(Printsp node, Object param);
	public Object visit(Println node, Object param);
	public Object visit(LlamadaFuncionSentencia node, Object param);
	public Object visit(Return node, Object param);
	public Object visit(ReturnVacio node, Object param);
	public Object visit(ExpresionAritmetica node, Object param);
	public Object visit(ExpresionBooleana node, Object param);
	public Object visit(ExpresionComparativa node, Object param);
	public Object visit(NegacionBooleana node, Object param);
	public Object visit(AccesoArray node, Object param);
	public Object visit(AccesoStruct node, Object param);
	public Object visit(Variable node, Object param);
	public Object visit(Cast node, Object param);
	public Object visit(ConstanteInt node, Object param);
	public Object visit(ConstanteFloat node, Object param);
	public Object visit(ConstanteChar node, Object param);
}
