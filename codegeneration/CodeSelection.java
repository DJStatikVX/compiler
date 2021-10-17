/**
 * Tutorial de DiseÃ±o de Lenguajes de ProgramaciÃ³n
 * @author RaÃºl Izquierdo
 */

package codegeneration;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import ast.*;
import visitor.DefaultVisitor;

public class CodeSelection extends DefaultVisitor {
	
	private Map<String, String> instruccion = new HashMap<String, String>();
	private Map<String, Integer> charEspeciales = new HashMap<String, Integer>();
	private int numeroIfs = 0;
	private int numeroWhiles = 0;
	
	enum FuncionCodigo {
	    DIRECCION, VALOR
	}

    public CodeSelection(Writer writer, String sourceFile) {
        this.writer = new PrintWriter(writer);
        this.sourceFile = sourceFile;
        
        instruccion.put("+", "add");
        instruccion.put("-", "sub");
        instruccion.put("*", "mul");
        instruccion.put("/", "div");
        instruccion.put("&&", "and");
        instruccion.put("||", "or");
        instruccion.put("!", "not");
        instruccion.put(">", "gt");
        instruccion.put("<", "lt");
        instruccion.put(">=", "ge");
        instruccion.put("<=", "le");
        instruccion.put("==", "eq");
        instruccion.put("!=", "ne");
        
        charEspeciales.put("\'\\n\'", 10);
    }

    // # ----------------------------------------------------------
    /*
     * Poner aquÃ­ los visit.
     *
     * Si se ha usado VGen, solo hay que copiarlos de la clase 'visitor/_PlantillaParaVisitors.txt'.
     */

    // Ejemplo:
    //
    // public Object visit(Program node, Object param) {
    //      out("#source \"" + sourceFile + "\"");
    //      out("call main");
    //      out("halt");
    //
    //      super.visit(node, param); // Recorrer los hijos
    //      return null;
    // }
    
	//	class Programa { List<Definicion> definiciones; }
	public Object visit(Programa node, Object param) {
		out("#source \"" + sourceFile + "\"");
		
		out("call main");
		out("halt");

		if (node.getDefiniciones() != null)
			for (Definicion child : node.getDefiniciones())
				child.accept(this, param);
				
		return null;
	}
	
	//	class Inicializacion { Tipo tipo;  String nombre;  String ambito;  Expresion valor; }
	public Object visit(Inicializacion node, Object param) {
		line(node.getStart());
		
		// Colocamos la dirección de la nueva variable local
		out("pusha BP");
		out(String.format("pushi %d", node.getDireccion()));
		out("subi");

		// Colocamos en la pila el valor a asignar, que es válido
		if (node.getValor() != null)
			node.getValor().accept(this, FuncionCodigo.VALOR);
		
		// Almacenamos en memoria
		out("store", node.getTipo());

		return null;
	}
	
	//	class DefVariable { Tipo tipo;  String nombre; }
	public Object visit(DefVariable node, Object param) {
		if (node.getAmbito().equals("global"))
			out("#GLOBAL " + node.getNombre() + ":" + node.getTipo().getNombreMAPL());

		return null;
	}
	
	//	class DefStruct { String nombre;  List<DefAtributo> atributos; }
	public Object visit(DefStruct node, Object param) {

		out(String.format("#type %s: {", node.getNombre()));

		if (node.getAtributos() != null)
			for (DefAtributo child : node.getAtributos())
				child.accept(this, param);
		
		out("}");

		return null;
	}
	
	//	class DefFuncion { String nombre;  List<Parametro> parametros;  Tipo retorno;  List<DefVariable> declaraciones;  List<Sentencia> sentencias; }
	public Object visit(DefFuncion node, Object param) {
		out("#func " + node.getNombre());

		if (node.getParametros() != null)
			for (Parametro child : node.getParametros())
				out(String.format("#param %s:%s", 
						child.getNombre(), child.getTipo().getNombreMAPL()));

		if (node.getRetorno() != null && !(node.getRetorno() instanceof TipoVoid))
			out("#ret " + node.getRetorno().getNombreMAPL());
		else
			out("#ret void");

		// Metadatos de locales
		if (node.getDeclaraciones() != null)
			for (DefVariable child : node.getDeclaraciones())
				out(String.format("#local %s:%s", 
						child.getNombre(), child.getTipo().getNombreMAPL()));
		
		int tamañoRetorno = 0;
		int tamañoLocales = 0;
		int tamañoParametros = 0;
		
		// Calculamos tamaño de retorno
		tamañoRetorno = node.getRetorno().getTamano();
		
		// Calculamos tamaño de variables locales
		for (DefVariable d : node.getDeclaraciones())
			tamañoLocales += d.getTipo().getTamano();
		
		// Calculamos tamaño de parametros
		for (DefVariable p : node.getParametros())
			tamañoParametros += p.getTipo().getTamano();
		
		out(node.getNombre() + ":");
		out("enter " + tamañoLocales);
		
		// Generamos código de inicializaciones
		if (node.getDeclaraciones() != null)
			for (DefVariable child : node.getDeclaraciones()) {
				// Si es una inicialización, generamos su código
				if (child instanceof Inicializacion)
					child.accept(this, param);
			}
		
		if (node.getSentencias() != null)
			for (Sentencia child : node.getSentencias())
				child.accept(this, param);
		
		// Retorno
		if (node.getRetorno() != null && node.getRetorno() instanceof TipoVoid)
			out(String.format("ret %d, %d, %d", 
				tamañoRetorno, tamañoLocales, tamañoParametros));

		return null;
	}
	
	//	class DefAtributo { Tipo tipo;  String nombre; }
	public Object visit(DefAtributo node, Object param) {
		if (node.getTipo() != null)
			out(String.format("\t%s:%s", node.getNombre(), 
					node.getTipo().getNombreMAPL()));

		return null;
	}
	
	//	class Print { Expresion expresion; }
	public Object visit(Print node, Object param) {
		line(node);

		if (node.getExpresion() != null) {
			node.getExpresion().accept(this, FuncionCodigo.VALOR);
			out("out", node.getExpresion().getTipo());
		}
		
		return null;
	}
	
	//	class Println { Expresion expresion; }
	public Object visit(Println node, Object param) {
		line(node);

		if (node.getExpresion() != null) {
			node.getExpresion().accept(this, FuncionCodigo.VALOR);
			out("out", node.getExpresion().getTipo());
		}
		
		// Salto de línea
		out("pushb 10");
		out("outb");

		return null;
	}
	
	//	class Printsp { Expresion expresion; }
	public Object visit(Printsp node, Object param) {
		line(node);

		if (node.getExpresion() != null) {
			node.getExpresion().accept(this, FuncionCodigo.VALOR);
			out("out", node.getExpresion().getTipo());
		}
		
		// Salto de línea
		out("pushb 32");
		out("outb");

		return null;
	}
	
	//	class Read { Expresion variable; }
	public Object visit(Read node, Object param) {
		line(node);

		if (node.getVariable() != null) {
			node.getVariable().accept(this, FuncionCodigo.DIRECCION);
			out("in", node.getVariable().getTipo());
			out("store", node.getVariable().getTipo());
		}
		
		return null;
	}
	
	//	class Asignacion { Expresion variable;  Expresion valor; }
	public Object visit(Asignacion node, Object param) {
		line(node);

		if (node.getVariable() != null)
			node.getVariable().accept(this, FuncionCodigo.DIRECCION);

		if (node.getValor() != null)
			node.getValor().accept(this, FuncionCodigo.VALOR);
		
		out("store", node.getVariable().getTipo());

		return null;
	}
	
	//	class If { Expresion condicion;  List<Sentencia> cierto;  List<Sentencia> falso; }
	public Object visit(If node, Object param) {
		line(node.getStart());

		if (node.getCondicion() != null)
			node.getCondicion().accept(this, param);
		
		int numIf = numeroIfs++;
		
		out("jz else" + numIf);

		if (node.getCierto() != null)
			for (Sentencia child : node.getCierto())
				child.accept(this, param);
		
		if (!node.getFalso().isEmpty()) {
			out("jmp finElse" + numIf);
		}
		
		out("else" + numIf + ":");

		if (node.getFalso() != null && !node.getFalso().isEmpty()) {
			for (Sentencia child : node.getFalso())
				child.accept(this, param);
			out("finElse" + numIf + ":");
		}
		
		return null;
	}
	
	//	class While { Expresion condicion;  List<Sentencia> sentencias; }
	public Object visit(While node, Object param) {
		line(node.getStart());
		out("inicioWhile" + numeroWhiles + ":");
		
		int numWhile = numeroWhiles++;
		
		if (node.getCondicion() != null)
			node.getCondicion().accept(this, param);
		
		out("jz finWhile" + numWhile);

		if (node.getSentencias() != null)
			for (Sentencia child : node.getSentencias())
				child.accept(this, param);
		
		out("jmp inicioWhile" + numWhile);
		
		out("finWhile" + numWhile + ":");
		
		return null;
	}
	
	//	class Variable { String name; }
	public Object visit(Variable node, Object param) {
		if ((FuncionCodigo) param == FuncionCodigo.VALOR) {
			visit(node, FuncionCodigo.DIRECCION);
			out("load", node.getTipo());
		} else {
			assert(param == FuncionCodigo.DIRECCION);
			if (!node.getDefinicion().getAmbito().equals("global")) {
				out("pusha BP");
				out("push " + node.getDefinicion().getDireccion());
				if (!node.getDefinicion().getAmbito().equals("local"))
					out("addi");
				else
					out("subi");
			} else {
				out("pusha " + node.getDefinicion().getDireccion());
			}
		}
		
		return null;
	}
	
	//	class ExpresionAritmetica { Expresion izquierda;  String operador;  Expresion derecha; }
	public Object visit(ExpresionAritmetica node, Object param) {
		assert(param == FuncionCodigo.VALOR);

		if (node.getIzquierda() != null)
			node.getIzquierda().accept(this, FuncionCodigo.VALOR);

		if (node.getDerecha() != null)
			node.getDerecha().accept(this, FuncionCodigo.VALOR);
		
		out(instruccion.get(node.getOperador()), node.getTipo());

		return null;
	}
	
	//	class ExpresionBooleana { Expresion izquierda;  String operador;  Expresion derecha; }
	public Object visit(ExpresionBooleana node, Object param) {
		assert(param == FuncionCodigo.VALOR);

		if (node.getIzquierda() != null)
			node.getIzquierda().accept(this, FuncionCodigo.VALOR);

		if (node.getDerecha() != null)
			node.getDerecha().accept(this, FuncionCodigo.VALOR);
		
		out(instruccion.get(node.getOperador()));

		return null;
	}
	
	//	class ExpresionComparativa { Expresion izquierda;  String operador;  Expresion derecha; }
	public Object visit(ExpresionComparativa node, Object param) {
		assert(param == FuncionCodigo.VALOR);

		if (node.getIzquierda() != null)
			node.getIzquierda().accept(this, FuncionCodigo.VALOR);

		if (node.getDerecha() != null)
			node.getDerecha().accept(this, FuncionCodigo.VALOR);
		
		out(instruccion.get(node.getOperador()), node.getIzquierda().getTipo());

		return null;
	}
	
	//	class NegacionBooleana { Expresion expresion; }
	public Object visit(NegacionBooleana node, Object param) {
		assert(param == FuncionCodigo.VALOR);

		if (node.getExpresion() != null)
			node.getExpresion().accept(this, FuncionCodigo.VALOR);
		
		out(instruccion.get("!"));

		return null;
	}
	
	//	class AccesoArray { Expresion izquierda;  Expresion derecha; }
	public Object visit(AccesoArray node, Object param) {
		if (node.getIzquierda() != null)
			node.getIzquierda().accept(this, FuncionCodigo.DIRECCION);

		if (node.getDerecha() != null)
			node.getDerecha().accept(this, FuncionCodigo.VALOR);
		
		if (node.getTipo() instanceof TipoArray)
			out("pushi " + ((TipoArray) node.getTipo()).getTipo().getTamano());
		else
			out("pushi " + node.getTipo().getTamano());
				
		out("muli");
		out("addi");
		
		if ((FuncionCodigo) param == FuncionCodigo.VALOR) {
			out("load", node.getTipo());
		}

		return null;
	}
	
	//	class AccesoStruct { Expresion expresion;  String campo; }
	public Object visit(AccesoStruct node, Object param) {
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, FuncionCodigo.DIRECCION);
		
		out("push " + obtenerDireccionCampo(node));
		out("add");
		
		if ((FuncionCodigo) param == FuncionCodigo.VALOR) {
			out("load", node.getTipo());
		}

		return null;
	}
	
	//	class Cast { Tipo tipo;  Expresion expresion; }
	public Object visit(Cast node, Object param) {
		if (node.getTipo() != null && node.getExpresion() != null) {
			node.getExpresion().accept(this, FuncionCodigo.VALOR);
			String tipoOrigen = Character.toString(node.getExpresion()
					.getTipo().getSufijo());
			String tipoDestino = Character.toString(node.getTipo().getSufijo());
			out(String.format("%s2%s", tipoOrigen, tipoDestino));
		}

		return null;
	}
	
	//	class LlamadaFuncionSentencia { String nombre;  List<Argumento> args; }
	public Object visit(LlamadaFuncionSentencia node, Object param) {
		line(node);
		
		if (node.getArgs() != null)
			for (Expresion child : node.getArgs())
				child.accept(this, FuncionCodigo.VALOR);
		
		out("call " + node.getNombre());
		
		if (!(node.getDefinicion().getRetorno() instanceof TipoVoid)) {
			out("pop", node.getDefinicion().getRetorno());
		}

		return null;
	}
	
	//	class LlamadaFuncionExpresion { String nombre;  List<Argumento> args; }
	public Object visit(LlamadaFuncionExpresion node, Object param) {		
		if (node.getArgs() != null)
			for (Expresion child : node.getArgs())
				child.accept(this, FuncionCodigo.VALOR);
		
		out("call " + node.getNombre());
		
		return null;
	}
	
	//	class Return { Expresion expresion; }
	public Object visit(Return node, Object param) {
		int tamañoRetorno = 0;
		int tamañoLocales = 0;
		int tamañoParametros = 0;
		
		// Calculamos tamaño de retorno
		tamañoRetorno = node.getFuncion().getRetorno().getTamano();
		
		// Calculamos tamaño de variables locales
		for (DefVariable d : node.getFuncion().getDeclaraciones())
			tamañoLocales += d.getTipo().getTamano();
		
		// Calculamos tamaño de parametros
		for (DefVariable p : node.getFuncion().getParametros())
			tamañoParametros += p.getTipo().getTamano();

		if (node.getExpresion() != null) {
			line(node);
			node.getExpresion().accept(this, FuncionCodigo.VALOR);
		}
		
		out(String.format("ret %d, %d, %d", 
				tamañoRetorno, tamañoLocales, tamañoParametros));

		return null;
	}
	
	//	class ReturnVacio {  }
	public Object visit(ReturnVacio node, Object param) {
		int tamañoLocales = 0;
		int tamañoParametros = 0;
				
		// Calculamos tamaño de variables locales
		for (DefVariable d : node.getFuncion().getDeclaraciones())
			tamañoLocales += d.getTipo().getTamano();
		
		// Calculamos tamaño de parametros
		for (DefVariable p : node.getFuncion().getParametros())
			tamañoParametros += p.getTipo().getTamano();
		
		out(String.format("ret 0, %d, %d", tamañoLocales, tamañoParametros));

		return null;
	}
	
	//	class ConstanteInt { String valor; }
	public Object visit(ConstanteInt node, Object param) {
		assert(param == FuncionCodigo.VALOR);
		out("pushi " + node.getValor());
		return null;
	}

	//	class ConstanteFloat { String valor; }
	public Object visit(ConstanteFloat node, Object param) {
		assert(param == FuncionCodigo.VALOR);
		out("pushf " + node.getValor());
		return null;
	}

	//	class ConstanteChar { String valor; }
	public Object visit(ConstanteChar node, Object param) {
		assert(param == FuncionCodigo.VALOR);
		
		int valor;
		if (charEspeciales.containsKey(node.getValor()))
			valor = charEspeciales.get(node.getValor());
		else
			valor = (int) node.getValor().charAt(1);
		
		out("pushb " + valor);
		
		return null;
	}

    // # ----------------------------------------------------------
    // MÃ©todos auxiliares recomendados (opcionales) -------------

    // Imprime una lÃ­nea en el fichero de salida
    private void out(String instruction) {
        writer.println(instruction);
    }
    
    private void out(String instruction, Tipo tipo) {
        writer.println(instruction + tipo.getSufijo());
    }

    private void line(AST node) {
        line(node.getEnd());
    }

    private void line(Position pos) {
        if (pos != null)
            out("\n#line " + pos.getLine());
        else
            System.out.println("#line no generado. Se ha pasado una Position null. Falta información en el AST");
    }
    
    private int obtenerDireccionCampo(AccesoStruct node) {
    	String campo = node.getCampo();
    	
    	for (DefAtributo a : ((TipoStruct) node.getExpresion().getTipo())
    			.getDefinicion().getAtributos()) {
    		if (a.getNombre().equals(campo))
    			return a.getDireccion();
    	}
    	
    	return -1;
    }

    private PrintWriter writer;
    private String sourceFile;
}
