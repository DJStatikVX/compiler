/**
 * Tutorial de Diseño de Lenguajes de Programación
 * @author Raúl Izquierdo
 */

package semantic;

import ast.*;
import main.*;
import visitor.*;

public class Identification extends DefaultVisitor {

    public Identification(ErrorManager errorManager) {
        this.errorManager = errorManager;
        this.variables = new ContextMap<String, DefVariable>();
        this.funciones = new ContextMap<String, DefFuncion>();
        this.structs = new ContextMap<String, DefStruct>();
        this.atributos = new ContextMap<String, DefAtributo>();
    }

    // # ----------------------------------------------------------
    /*
    * Poner aquí los visit.
    *
    * Si se ha usado VGen, solo hay que copiarlos de la clase 'visitor/_PlantillaParaVisitors.txt'.
    */

	public Object visit(DefVariable node, Object param) {
		node.getTipo().accept(this, param); // No es realmente necesario
		
		DefVariable definicion = variables.getFromTop(node.getNombre());
		predicado(definicion == null, "Variable ya definida: " 
				+ node.getNombre(), node);
		variables.put(node.getNombre(), node);

		return null;
	}
	
	//	class Inicializacion { Tipo tipo;  String nombre;  String ambito;  Expresion valor; }
	public Object visit(Inicializacion node, Object param) {
		node.getTipo().accept(this, param); // No es realmente necesario
		
		// Visitamos el valor para comprobar que esté definido
		node.getValor().accept(this, param);
		
		DefVariable definicion = variables.getFromTop(node.getNombre());
		predicado(definicion == null, "Variable ya definida: " 
				+ node.getNombre(), node);
		variables.put(node.getNombre(), node);

		return null;
	}
	
	public Object visit(DefAtributo node, Object param) {
		node.getTipo().accept(this, param); // No es realmente necesario
		
		DefAtributo definicion = atributos.getFromTop(node.getNombre());
		predicado(definicion == null, "Atributo ya definido: " 
				+ node.getNombre(), node);
		atributos.put(node.getNombre(), node);
		
		return null;
	}
	
	public Object visit(DefStruct node, Object param) {		
		DefStruct definicion = structs.getFromTop(node.getNombre());
		predicado(definicion == null, "Struct ya definido: " 
				+ node.getNombre(), node);
		structs.put(node.getNombre(), node);
		
		atributos.set();
		
		if (node.getAtributos() != null) {
			for (int i = 0; i < node.getAtributos().size(); i++) {
				node.getAtributos().get(i).accept(this, param);
			}
		}
		
		atributos.reset();
		
		return null;
	}
		
	public Object visit(DefFuncion node, Object param) {
		DefFuncion definicion = funciones.getFromAny(node.getNombre());
		predicado(definicion == null, "Función ya definida: "
				+ node.getNombre(), node);
		funciones.put(node.getNombre(), node);
		
		variables.set();

		if (node.getParametros() != null) {
			for (int i = 0; i < node.getParametros().size(); i++) {			
				node.getParametros().get(i).accept(this, param);
			}
		}
		
		if (node.getRetorno() != null)
			node.getRetorno().accept(this, param);

		if (node.getDeclaraciones() != null) {
			for (int i = 0; i < node.getDeclaraciones().size(); i++) {
				node.getDeclaraciones().get(i).accept(this, param);
			}
		}

		if (node.getSentencias() != null) {
			for (int i = 0; i < node.getSentencias().size(); i++) {
				node.getSentencias().get(i).accept(this, param);
			}
		}
		
		variables.reset();

		return null;
	}
	
	public Object visit(Parametro node, Object param) {
		node.getTipo().accept(this, param); // No es realmente necesario
		
		DefVariable definicion = variables.getFromTop(node.getNombre());
		predicado(definicion == null, "Parámetro repetido: " 
				+ node.getNombre(), node);
		variables.put(node.getNombre(), node);

		return null;
	}
	
	public Object visit(LlamadaFuncionExpresion node, Object param) {
		DefFuncion definicion = funciones.getFromAny(node.getNombre());
		predicado(definicion != null, "Función no definida: "
				+ node.getNombre(), node);
		
		// Visitar hijos
		visitChildren(node.getArgs(), param);
		
		node.setDefinicion(definicion);
		
		return null;
	}
	
	public Object visit(LlamadaFuncionSentencia node, Object param) {
		DefFuncion definicion = funciones.getFromAny(node.getNombre());
		predicado(definicion != null, "Función no definida: "
				+ node.getNombre(), node);
		
		// Visitar hijos
		visitChildren(node.getArgs(), param);
		
		node.setDefinicion(definicion);
		
		return null;
	}
	
	public Object visit(Variable node, Object param) {
		DefVariable definicion = variables.getFromAny(node.getName());
		predicado(definicion != null, "Variable no definida: "
				+ node.getName(), node);
		node.setDefinicion(definicion);
		
		return null;
	}
	
	public Object visit(TipoStruct node, Object param) {
		DefStruct definicion = structs.getFromAny(node.getNombre());
		predicado (definicion != null, "Struct no definido: " 
				+ node.getNombre(), node);
		node.setDefinicion(definicion);
		
		return null;
	}
	
    // ...
    // ...
    // ...

    // # --------------------------------------------------------
    // Métodos auxiliares recomendados (opcionales) -------------

    private void error(String msg, Position position) {
        errorManager.notify("Identification", msg, position);
    }
    
    private void predicado(boolean condition, String errorMessage, AST node) {
    	if (!condition)
    		error(errorMessage, node.getStart());
    }

    private ErrorManager errorManager;
    private ContextMap<String, DefVariable> variables;
    private ContextMap<String, DefFuncion> funciones;
    private ContextMap<String, DefStruct> structs;
    private ContextMap<String, DefAtributo> atributos;
}
