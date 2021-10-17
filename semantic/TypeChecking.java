/**
 * Tutorial de Diseño de Lenguajes de Programación
 * @author Raúl Izquierdo
 */

package semantic;

import ast.*;
import main.ErrorManager;
import visitor.DefaultVisitor;

public class TypeChecking extends DefaultVisitor {

    public TypeChecking(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    // # ----------------------------------------------------------
    /*
    * Poner aquí los visit.
    *
    * Si se ha usado VGen, solo hay que copiarlos de la clase 'visitor/_PlantillaParaVisitors.txt'.
    */
    
	//	class Inicializacion { Tipo tipo;  String nombre;  String ambito;  Expresion valor; }
	public Object visit(Inicializacion node, Object param) {
		super.visit(node, param);

		// Solo es posible inicializar variables locales de tipos simples
    	predicado(esTipoSimple(node.getTipo()), 
    			"El par�metro " + node.getNombre() 
    			+ " debe ser de tipo primitivo.", node);
    	
    	// El tipo de la variable local a inicializar y la expresi�n debe ser el mismo
    	predicado(mismoTipo(node.getTipo(), node.getValor().getTipo()),
    			"La expresi�n a asignar no es del mismo tipo.", node);
    	
    	// Si la expresi�n es una llamada a funci�n, comprobar que no sea void
    	if (node.getValor() instanceof LlamadaFuncionExpresion)
    		predicado(!esTipoVoid(node.getValor().getTipo()), 
    				"No se puede inicializar una variable con una funci�n tipo void.",
    				node);

		return null;
	}
    
    public Object visit(Parametro node, Object param) {
    	super.visit(node, param);
    	
    	predicado(esTipoSimple(node.getTipo()), 
    			"El par�metro " + node.getNombre() 
    			+ " debe ser de tipo primitivo.", node);
    	    	
    	return null;
    }

    public Object visit(DefFuncion node, Object param) {
    	for (Sentencia sent : node.getSentencias())
    		sent.setFuncion(node);
    	
    	super.visit(node, param);
  	
    	predicado(esTipoSimple(node.getRetorno()) || esTipoVoid(node.getRetorno()), 
    			"El retorno de la funci�n " + node.getNombre() 
    			+ " debe ser simple.", node);
    	
    	return null;
    }
        
    public Object visit(LlamadaFuncionExpresion node, Object param) {
    	super.visit(node, param);
    	
    	predicado(node.getArgs().size() 
    			== node.getDefinicion().getParametros().size(), 
    			"El n�mero de argumentos de la llamada a " + node.getNombre() 
    			+ " no coincide con el n�mero de parametros de su definici�n.", 
    			node);
    	
    	for (int i = 0; i < node.getArgs().size(); i++) {
    		try {
    			Expresion arg = node.getArgs().get(i);
    			Parametro parametro = node.getDefinicion().getParametros().get(i);
    			predicado(arg.getTipo().getClass() == parametro.getTipo().getClass(),
    					"El tipo de un argumento no coincide "
    					+ "con el de la definici�n de la funci�n.", node);
    		} catch (IndexOutOfBoundsException ex) {
    			break;
    		}
    	}
    	
    	node.setTipo(node.getDefinicion().getRetorno());
    	node.setModificable(false);
    	    	
    	return null;
    }
    
    public Object visit(ExpresionAritmetica node, Object param) {
    	super.visit(node, param);
    	
    	predicado(esTipoNumerico(node.getIzquierda().getTipo()),
    			"Los tipos de los operadores deben ser num�ricos.", node);
    	predicado(mismoTipo(node.getIzquierda(), node.getDerecha()),
    			"Los operandos deben ser del mismo tipo.", node);
    	
    	node.setTipo(node.getIzquierda().getTipo());
    	node.setModificable(false);

    	return null;
    }
    
    public Object visit(ExpresionBooleana node, Object param) {
    	super.visit(node, param);
    	
    	predicado(node.getIzquierda().getTipo().getClass() == TipoInt.class,
    			"Los operandos deben ser de tipo entero.", node);
    	predicado(mismoTipo(node.getIzquierda(), node.getDerecha()),
    			"Los operandos deben ser del mismo tipo.", node);
    	
    	node.setTipo(new TipoInt());
    	node.setModificable(false);

    	return null;
    }
    
    public Object visit(ExpresionComparativa node, Object param) {
    	super.visit(node, param);
    	
    	predicado(esTipoNumerico(node.getIzquierda().getTipo()),
    			"Los operandos deben ser de tipo num�rico.", node);
    	predicado(mismoTipo(node.getIzquierda(), node.getDerecha()),
    			"Los operandos deben ser del mismo tipo.", node);
    	
    	node.setTipo(new TipoInt());
    	node.setModificable(false);

    	return null;
    }
    
    public Object visit(NegacionBooleana node, Object param) {
    	super.visit(node, param);
    	
    	predicado(node.getExpresion().getTipo().getClass() == TipoInt.class,
    			"La expresi�n a negar debe ser de tipo entero.", node);
    	
    	node.setTipo(new TipoInt());
    	node.setModificable(false);

    	return null;
    }
    
    public Object visit(AccesoArray node, Object param) {
    	super.visit(node, param);
    	
    	predicado(node.getIzquierda().getTipo().getClass() == TipoArray.class,
    			"La variable accedida debe ser de tipo array.", node);
    	predicado(node.getDerecha().getTipo().getClass() == TipoInt.class,
    			"El �ndice accedido debe ser de tipo entero.", node);
    	
    	if (node.getIzquierda().getTipo().getClass() == TipoArray.class) {
    		node.setTipo(((TipoArray) node.getIzquierda().getTipo()).getTipo());
    	} else {
    		node.setTipo(new TipoVoid());
    	}
    	
    	node.setModificable(true);

    	return null;
    }
    
    public Object visit(AccesoStruct node, Object param) {
    	super.visit(node, param);
    	
    	predicado(node.getExpresion().getTipo().getClass() == TipoStruct.class,
    			"La variable accedida debe ser de tipo struct.", node);
    	
    	if (node.getExpresion().getTipo().getClass() == TipoStruct.class) { 
	    	predicado(existeCampo((TipoStruct) node.getExpresion().getTipo(), 
	    			node.getCampo()), "El campo " + node.getCampo() 
	    			+ " no est� definido.", node);
		    node.setTipo(buscarTipoCampo(
		    		(TipoStruct) node.getExpresion().getTipo(), node.getCampo()));
    	} else {
    		node.setTipo(new TipoVoid());
    	}
    	
	    node.setModificable(true);

    	return null;
    }
    
    public Object visit(Variable node, Object param) {
    	super.visit(node, param);
    	
    	node.setTipo(node.getDefinicion().getTipo());
    	node.setModificable(true);

    	return null;
    }
    
    public Object visit(Cast node, Object param) {
    	super.visit(node, param);
    	
    	predicado(esTipoSimple(node.getTipo()), 
    			"El tipo destino de la conversi�n debe ser simple.", node);
    	predicado(esTipoSimple(node.getExpresion().getTipo()), 
    			"El tipo de la expresi�n a convertir debe ser simple.", node);
    	predicado(!mismoTipo(node.getTipo(), node.getExpresion().getTipo()), 
    			"La conversi�n debe realizarse a un tipo distinto.", node);
    	
    	node.setTipo(node.getTipo());
    	node.setModificable(false);

    	return null;
    }
    
    public Object visit(ConstanteInt node, Object param) {
    	super.visit(node, param);
    	
    	node.setTipo(new TipoInt());
    	node.setModificable(false);
    	    	
    	return null;
    }
    
    public Object visit(ConstanteFloat node, Object param) {
    	super.visit(node, param);
    	
    	node.setTipo(new TipoFloat());
    	node.setModificable(false);
    	    	
    	return null;
    }
    
    public Object visit(ConstanteChar node, Object param) {
    	super.visit(node, param);
    	
    	node.setTipo(new TipoChar());
    	node.setModificable(false);
    	    	
    	return null;
    }
    
    public Object visit(Asignacion node, Object param) {
    	super.visit(node, param);
    	
    	predicado(node.getVariable().isModificable(), 
    			"El valor de la izquierda en una asignaci�n "
    			+ "debe ser modificable.", node);
    	predicado(mismoTipo(node.getVariable(), node.getValor()), 
    			"No es posible asignar un valor de distinto tipo.", node);
    	predicado(esTipoSimple(node.getVariable().getTipo()), 
    			"El tipo del valor de la izquierda de una asignaci�n "
    			+ "debe ser simple.", node);
    	
    	return null;
    }
    
    public Object visit(Read node, Object param) {
    	super.visit(node, param);
    	
    	predicado(esTipoSimple(node.getVariable().getTipo()),
    			"La variable en un read debe ser un tipo simple.", node);
    	predicado(node.getVariable().isModificable(), 
    			"La variable en un read debe ser modificable.", node);
    	
    	return null;
    }
    
    public Object visit(If node, Object param) {
    	for (Sentencia sent : node.getCierto())
    		sent.setFuncion(node.getFuncion());
    	
    	for (Sentencia sent : node.getFalso())
    		sent.setFuncion(node.getFuncion());
    	
    	super.visit(node, param);
    	
    	predicado(node.getCondicion().getTipo().getClass() == TipoInt.class,
    			"La condici�n debe ser de tipo entero", node);
    	   	
    	return null;
    }
    
    public Object visit(While node, Object param) {
    	for (Sentencia sent : node.getSentencias())
    		sent.setFuncion(node.getFuncion());
    	
    	super.visit(node, param);
    	
    	predicado(node.getCondicion().getTipo().getClass() == TipoInt.class,
    			"La condici�n debe ser de tipo entero", node);
    	
    	return null;
    }
    
    public Object visit(Print node, Object param) {
    	super.visit(node, param);
    	
    	if (node.getExpresion() != null && node.getExpresion().getTipo() != null) {
	    	predicado(esTipoSimple(node.getExpresion().getTipo()),
	    			"La expresi�n a imprimir debe ser de tipo primitivo.", node);
    	}
    	
    	return null;
    }
    
    public Object visit(Printsp node, Object param) {
    	super.visit(node, param);
    	
    	if (node.getExpresion() != null && node.getExpresion().getTipo() != null) {
	    	predicado(esTipoSimple(node.getExpresion().getTipo()),
	    			"La expresi�n a imprimir debe ser de tipo primitivo.", node);
    	}
    	
    	return null;
    }
    
    public Object visit(Println node, Object param) {
    	super.visit(node, param);
    	
    	if (node.getExpresion() != null && node.getExpresion().getTipo() != null) {
	    	predicado(esTipoSimple(node.getExpresion().getTipo()),
	    			"La expresi�n a imprimir debe ser de tipo primitivo.", node);
    	}
    	
    	return null;
    }
    
    public Object visit(LlamadaFuncionSentencia node, Object param) {
    	super.visit(node, param);
    	
    	predicado(node.getArgs().size() 
    			== node.getDefinicion().getParametros().size(), 
    			"El n�mero de argumentos de la llamada a " + node.getNombre() 
    			+ " no coincide con el n�mero de parametros de su definici�n.", 
    			node);
    	
    	for (int i = 0; i < node.getArgs().size(); i++) {
    		try {
    			Expresion arg = node.getArgs().get(i);
    			Parametro parametro = node.getDefinicion().getParametros().get(i);
    			predicado(arg.getTipo().getClass() == parametro.getTipo().getClass(),
    					"El tipo de un argumento no coincide "
    					+ "con el de la definici�n de la funci�n.", node);
    		} catch (IndexOutOfBoundsException ex) {
    			break;
    		}
    	}
    	    	
    	return null;
    }
       
    public Object visit(Return node, Object param) {
    	super.visit(node, param);
    	
    	predicado(esTipoSimple(node.getExpresion().getTipo()),
    			"El valor retornado debe ser de tipo simple.", node);
    	
    	if (esTipoVoid(node.getFuncion().getRetorno())) {
        	predicado(node.getFuncion().getRetorno().getClass() 
        			== node.getExpresion().getTipo().getClass(), 
        			"El return no debe tener expresi�n en funciones void.", 
        			node);
    	} else {
        	predicado(node.getFuncion().getRetorno().getClass() 
        			== node.getExpresion().getTipo().getClass(), 
        			"El tipo del valor retornado no coincide "
        			+ "con el de la definici�n de la funci�n.", node);
    	}
   	
    	return null;
    }
    
    public Object visit(ReturnVacio node, Object param) {
    	super.visit(node, param);
    	
    	predicado(esTipoVoid(node.getFuncion().getRetorno()),
    			"Se debe retornar un valor en una funci�n con retorno.", 
    			node);
    	
    	return null;
    }
         
    // ...
    // ...
    // ...

    // # ----------------------------------------------------------
    // Métodos auxiliares recomendados (opcionales) -------------

    /**
     * predicado. Método auxiliar para implementar los predicados. Borrar si no se quiere usar.
     *
     * Ejemplos de uso (suponiendo que existe un método "esPrimitivo(expr)"):
     *
     *      1. predicado(esPrimitivo(expr.tipo), "La expresión debe ser de un tipo primitivo", expr.getStart());
     *      2. predicado(esPrimitivo(expr.tipo), "La expresión debe ser de un tipo primitivo", expr); // Se asume getStart()
     *      3. predicado(esPrimitivo(expr.tipo), "La expresión debe ser de un tipo primitivo");
     *
     * NOTA: El método getStart() (ejemplo 1) indica la linea/columna del fichero fuente donde estaba el nodo
     * (y así poder dar información más detallada de la posición del error). Si se usa VGen, dicho método
     * habrá sido generado en todos los nodos del AST.
     * No es obligatorio llamar a getStart() (ejemplo 2), ya que si se pasa el nodo, se usará por defecto dicha
     * posición.
     * Si no se quiere imprimir la posición del fichero, se puede omitir el tercer argumento (ejemplo 3).
     *
     * @param condition     Debe cumplirse para que no se produzca un error
     * @param errorMessage  Se imprime si no se cumple la condición
     * @param posicionError Fila y columna del fichero donde se ha producido el error.
     */
    
    private Tipo buscarTipoCampo(TipoStruct struct, String campo) {
    	for (DefAtributo atr : struct.getDefinicion().getAtributos()) {
    		if (atr.getNombre().equals(campo)) {
    			return atr.getTipo();
    		}
    	}
    	
    	return null;
    }
    
    private boolean existeCampo(TipoStruct struct, String campo) {
    	for (DefAtributo atr : struct.getDefinicion().getAtributos()) {
    		if (atr.getNombre().equals(campo)) {
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    private boolean esTipoVoid(Tipo tipo) {
    	return (tipo.getClass() == TipoVoid.class);
    }
    
    private boolean esTipoSimple(Tipo tipo) {
    	return (tipo.getClass() == TipoInt.class 
    		 || tipo.getClass() == TipoFloat.class
    		 || tipo.getClass() == TipoChar.class);
    }
    
    private boolean esTipoNumerico(Tipo tipo) {
    	return (tipo.getClass() == TipoInt.class 
    		 || tipo.getClass() == TipoFloat.class);
    }
    
    private boolean mismoTipo(Expresion a, Expresion b) {
    	return (a.getTipo().getClass() == b.getTipo().getClass());
    }
    
    private boolean mismoTipo(Tipo a, Tipo b) {
    	return (a.getClass() == b.getClass());
    }

    private void predicado(boolean condition, String errorMessage, AST node) {
        predicado(condition, errorMessage, node.getStart());
    }

    private void predicado(boolean condition, String errorMessage, Position position) {
        if (!condition)
            errorManager.notify("Type Checking", errorMessage, position);
    }

    private void predicado(boolean condition, String errorMessage) {
        predicado(condition, errorMessage, (Position) null);
    }

    private ErrorManager errorManager;
}
