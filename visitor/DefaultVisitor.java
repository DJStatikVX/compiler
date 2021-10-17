/**
 * @generated VGen (for ANTLR) 1.7.2
 */

package visitor;

import ast.*;
import java.util.*;

/*
DefaultVisitor. Implementación base del visitor para ser derivada por nuevos visitor.
	No modificar esta clase. Para crear nuevos visitor usar el fichero "_PlantillaParaVisitors.txt".
	DefaultVisitor ofrece una implementación por defecto de cada nodo que se limita a visitar los nodos hijos.
*/
public class DefaultVisitor implements Visitor {

	//	class Programa { List<Definicion> definiciones; }
	public Object visit(Programa node, Object param) {
		visitChildren(node.getDefiniciones(), param);
		return null;
	}

	//	class TipoInt {  }
	public Object visit(TipoInt node, Object param) {
		return null;
	}

	//	class TipoFloat {  }
	public Object visit(TipoFloat node, Object param) {
		return null;
	}

	//	class TipoChar {  }
	public Object visit(TipoChar node, Object param) {
		return null;
	}

	//	class TipoVoid {  }
	public Object visit(TipoVoid node, Object param) {
		return null;
	}

	//	class TipoArray { Tipo tipo;  String dimensiones; }
	public Object visit(TipoArray node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		return null;
	}

	//	class TipoStruct { String nombre; }
	public Object visit(TipoStruct node, Object param) {
		return null;
	}

	//	class DefVariable { Tipo tipo;  String nombre;  String ambito; }
	public Object visit(DefVariable node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		return null;
	}

	//	class DefAtributo { Tipo tipo;  String nombre; }
	public Object visit(DefAtributo node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		return null;
	}

	//	class DefStruct { String nombre;  List<DefAtributo> atributos; }
	public Object visit(DefStruct node, Object param) {
		visitChildren(node.getAtributos(), param);
		return null;
	}

	//	class DefFuncion { String nombre;  List<Parametro> parametros;  Tipo retorno;  List<DefVariable> declaraciones;  List<Sentencia> sentencias; }
	public Object visit(DefFuncion node, Object param) {
		visitChildren(node.getParametros(), param);
		if (node.getRetorno() != null)
			node.getRetorno().accept(this, param);
		visitChildren(node.getDeclaraciones(), param);
		visitChildren(node.getSentencias(), param);
		return null;
	}

	//	class Inicializacion { Tipo tipo;  String nombre;  String ambito;  Expresion valor; }
	public Object visit(Inicializacion node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		if (node.getValor() != null)
			node.getValor().accept(this, param);
		return null;
	}

	//	class Parametro { Tipo tipo;  String nombre; }
	public Object visit(Parametro node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		return null;
	}

	//	class LlamadaFuncionExpresion { String nombre;  List<Expresion> args; }
	public Object visit(LlamadaFuncionExpresion node, Object param) {
		visitChildren(node.getArgs(), param);
		return null;
	}

	//	class Asignacion { Expresion variable;  Expresion valor; }
	public Object visit(Asignacion node, Object param) {
		if (node.getVariable() != null)
			node.getVariable().accept(this, param);
		if (node.getValor() != null)
			node.getValor().accept(this, param);
		return null;
	}

	//	class Read { Expresion variable; }
	public Object visit(Read node, Object param) {
		if (node.getVariable() != null)
			node.getVariable().accept(this, param);
		return null;
	}

	//	class If { Expresion condicion;  List<Sentencia> cierto;  List<Sentencia> falso; }
	public Object visit(If node, Object param) {
		if (node.getCondicion() != null)
			node.getCondicion().accept(this, param);
		visitChildren(node.getCierto(), param);
		visitChildren(node.getFalso(), param);
		return null;
	}

	//	class While { Expresion condicion;  List<Sentencia> sentencias; }
	public Object visit(While node, Object param) {
		if (node.getCondicion() != null)
			node.getCondicion().accept(this, param);
		visitChildren(node.getSentencias(), param);
		return null;
	}

	//	class Print { Expresion expresion; }
	public Object visit(Print node, Object param) {
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		return null;
	}

	//	class Printsp { Expresion expresion; }
	public Object visit(Printsp node, Object param) {
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		return null;
	}

	//	class Println { Expresion expresion; }
	public Object visit(Println node, Object param) {
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		return null;
	}

	//	class LlamadaFuncionSentencia { String nombre;  List<Expresion> args; }
	public Object visit(LlamadaFuncionSentencia node, Object param) {
		visitChildren(node.getArgs(), param);
		return null;
	}

	//	class Return { Expresion expresion; }
	public Object visit(Return node, Object param) {
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		return null;
	}

	//	class ReturnVacio {  }
	public Object visit(ReturnVacio node, Object param) {
		return null;
	}

	//	class ExpresionAritmetica { Expresion izquierda;  String operador;  Expresion derecha; }
	public Object visit(ExpresionAritmetica node, Object param) {
		if (node.getIzquierda() != null)
			node.getIzquierda().accept(this, param);
		if (node.getDerecha() != null)
			node.getDerecha().accept(this, param);
		return null;
	}

	//	class ExpresionBooleana { Expresion izquierda;  String operador;  Expresion derecha; }
	public Object visit(ExpresionBooleana node, Object param) {
		if (node.getIzquierda() != null)
			node.getIzquierda().accept(this, param);
		if (node.getDerecha() != null)
			node.getDerecha().accept(this, param);
		return null;
	}

	//	class ExpresionComparativa { Expresion izquierda;  String operador;  Expresion derecha; }
	public Object visit(ExpresionComparativa node, Object param) {
		if (node.getIzquierda() != null)
			node.getIzquierda().accept(this, param);
		if (node.getDerecha() != null)
			node.getDerecha().accept(this, param);
		return null;
	}

	//	class NegacionBooleana { Expresion expresion; }
	public Object visit(NegacionBooleana node, Object param) {
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		return null;
	}

	//	class AccesoArray { Expresion izquierda;  Expresion derecha; }
	public Object visit(AccesoArray node, Object param) {
		if (node.getIzquierda() != null)
			node.getIzquierda().accept(this, param);
		if (node.getDerecha() != null)
			node.getDerecha().accept(this, param);
		return null;
	}

	//	class AccesoStruct { Expresion expresion;  String campo; }
	public Object visit(AccesoStruct node, Object param) {
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		return null;
	}

	//	class Variable { String name; }
	public Object visit(Variable node, Object param) {
		return null;
	}

	//	class Cast { Tipo tipo;  Expresion expresion; }
	public Object visit(Cast node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		return null;
	}

	//	class ConstanteInt { String valor; }
	public Object visit(ConstanteInt node, Object param) {
		return null;
	}

	//	class ConstanteFloat { String valor; }
	public Object visit(ConstanteFloat node, Object param) {
		return null;
	}

	//	class ConstanteChar { String valor; }
	public Object visit(ConstanteChar node, Object param) {
		return null;
	}

    // Método auxiliar -----------------------------
    protected void visitChildren(List<? extends AST> children, Object param) {
        if (children != null)
            for (AST child : children)
                child.accept(this, param);
    }
}
