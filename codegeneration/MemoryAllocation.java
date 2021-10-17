/**
 * Tutorial de Diseño de Lenguajes de Programación
 * @author Raúl Izquierdo
 */

package codegeneration;

import ast.*;
import visitor.*;

/**
 * Clase encargada de asignar direcciones a las variables.
 */
public class MemoryAllocation extends DefaultVisitor {

    /*
    * Poner aquí los visit.
    *
    * Si se ha usado VGen, solo hay que copiarlos de la clase 'visitor/_PlantillaParaVisitors.txt'.
    */
	
	private int memoriaLibre = 0;
	
	//	class DefVariable { Tipo tipo;  String nombre; }
	public Object visit(DefVariable node, Object param) {

		// super.visit(node, param);

		if (node.getTipo() != null) {
			node.getTipo().accept(this, param);
			if (node.getAmbito().equals("global")) {
				node.setDireccion(memoriaLibre);
				memoriaLibre += node.getTipo().getTamano();
			}
		}
		
		return null;
	}
		
	//	class DefFuncion { String nombre;  List<Parametro> parametros;  Tipo retorno;  List<DefVariable> declaraciones;  List<Sentencia> sentencias; }
	public Object visit(DefFuncion node, Object param) {
		// super.visit(node, param);
		int direccion = 4; // variables locales comienzan por BP + 4

		if (node.getParametros() != null) {
			for (Parametro child : node.getParametros())
				child.accept(this, param);
			
			// Asignamos direcciones
			for (int i = node.getParametros().size() - 1; i >= 0; i--) {
				DefVariable child = (DefVariable) node.getParametros().get(i);
				child.setDireccion(direccion);
				direccion += child.getTipo().getTamano();
			}
		}

		if (node.getRetorno() != null)
			node.getRetorno().accept(this, param);

		if (node.getDeclaraciones() != null) {
			// Asignamos direcciones
			direccion = 0;
			for (DefVariable child : node.getDeclaraciones()) {
				// Restamos a partir de BP
				child.setDireccion(child.getTipo().getTamano() + direccion);
				direccion = child.getDireccion();
				child.accept(this, param);
			}
		}

		if (node.getSentencias() != null)
			for (Sentencia child : node.getSentencias())
				child.accept(this, param);

		return null;
	}
	
	//	class DefStruct { String nombre;  List<DefAtributo> atributos; }
	public Object visit(DefStruct node, Object param) {
		// super.visit(node, param);
		int direccion = 0;

		if (node.getAtributos() != null) {
			for (DefAtributo child : node.getAtributos()) {
				child.setDireccion(direccion);
				direccion += child.getTipo().getTamano();
				child.accept(this, param);
			}
		}

		return null;
	}

}
