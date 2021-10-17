package visitor;

import ast.*;

public class MyVisitor extends DefaultVisitor {

	public Object visit(TipoInt node, Object param) {
		System.out.print("int");
		return null;
	}
	
	public Object visit(TipoFloat node, Object param) {
		System.out.print("float");
		return null;
	}
	
	public Object visit(TipoChar node, Object param) {
		System.out.print("char");
		return null;
	}
	
	public Object visit(TipoArray node, Object param) {
		System.out.print(String.format("[%s]", node.getDimensiones()));
		
		if (node.getTipo() != null) {
			System.out.print(" ");
			node.getTipo().accept(this, param);
		}

		return null;
	}
	
	public Object visit(TipoStruct node, Object param) {
		System.out.print(node.getNombre());
		return null;
	}
	
	public Object visit(DefVariable node, Object param) {
		System.out.print(String.format("var %s", node.getNombre()));

		if (node.getTipo() != null) {
			System.out.print(":");
			node.getTipo().accept(this, param);
		}

		System.out.println(";");
		
		return null;
	}
	
	public Object visit(DefAtributo node, Object param) {
		System.out.print(node.getNombre());

		if (node.getTipo() != null) {
			System.out.print(":");
			node.getTipo().accept(this, param);
		}
		
		System.out.println(";");

		return null;
	}
	
	public Object visit(DefStruct node, Object param) {
		System.out.println(String.format("struct %s {", node.getNombre()));

		if (node.getAtributos() != null) {
			for (DefAtributo child : node.getAtributos()) {
				System.out.print("  ");
				child.accept(this, param);
			}
		}
		
		System.out.println("};");

		return null;
	}
		
	public Object visit(DefFuncion node, Object param) {
		System.out.print(node.getNombre() + "(");

		if (node.getParametros() != null) {
			for (int i = 0; i < node.getParametros().size(); i++) {
				if (i > 0) {
					System.out.print(", ");
				}
				
				node.getParametros().get(i).accept(this, param);
			}
		}
		
		System.out.print(")");

		if (!(node.getRetorno() instanceof TipoVoid)) {
			System.out.print(":");
			node.getRetorno().accept(this, param);
		}
		
		System.out.println(" {");

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
		
		System.out.println("}");

		return null;
	}
	
	public Object visit(Parametro node, Object param) {
		System.out.print(node.getNombre());

		if (node.getTipo() != null) {
			System.out.print(":");
			node.getTipo().accept(this, param);
		}

		return null;
	}
		
	public Object visit(LlamadaFuncionExpresion node, Object param) {
		System.out.print(node.getNombre() + "(");

		if (node.getArgs() != null) {
			for (int i = 0; i < node.getArgs().size(); i++) {
				if (i > 0) {
					System.out.print(", ");
				}
				
				node.getArgs().get(i).accept(this, param);
			}
		}
		
		System.out.print(")");
		
		return null;
	}
	
	public Object visit(Asignacion node, Object param) {
		if (node.getVariable() != null)
			node.getVariable().accept(this, param);
		
		System.out.print(" = ");

		if (node.getValor() != null)
			node.getValor().accept(this, param);
		
		System.out.println(";");

		return null;
	}
	
	public Object visit(Read node, Object param) {
		System.out.print("read ");

		if (node.getVariable() != null)
			node.getVariable().accept(this, param);
		
		System.out.println(";");

		return null;
	}
	
	public Object visit(If node, Object param) {
		System.out.print("if (");

		if (node.getCondicion() != null)
			node.getCondicion().accept(this, param);
		
		System.out.println(") {");

		if (node.getCierto() != null) {
			for (Sentencia child : node.getCierto()) {
				System.out.print("  ");
				child.accept(this, param);
			}
		}
		
		if (node.getFalso() != null && node.getFalso().size() > 0) {
			System.out.println("} else {");
			for (Sentencia child : node.getFalso()) {
				System.out.print("  ");
				child.accept(this, param);
			}
		}
		
		System.out.println("}");

		return null;
	}
	
	public Object visit(While node, Object param) {
		System.out.print("while (");

		if (node.getCondicion() != null)
			node.getCondicion().accept(this, param);
		
		System.out.println(") {");

		if (node.getSentencias() != null) {
			for (Sentencia child : node.getSentencias()) {
				System.out.print("\t");
				child.accept(this, param);
			}
		}
		
		System.out.println("}");

		return null;
	}
	
	public Object visit(Print node, Object param) {
		System.out.print("print ");

		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		
		System.out.println(";");

		return null;
	}
	
	public Object visit(Printsp node, Object param) {
		System.out.print("printsp ");

		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		
		System.out.println(";");

		return null;
	}
	
	public Object visit(Println node, Object param) {
		System.out.print("println ");

		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		
		System.out.println(";");

		return null;
	}
	
	public Object visit(LlamadaFuncionSentencia node, Object param) {
		System.out.print(node.getNombre() + "(");

		if (node.getArgs() != null) {
			for (int i = 0; i < node.getArgs().size(); i++) {
				if (i > 0) {
					System.out.print(", ");
				}
				
				node.getArgs().get(i).accept(this, param);
			}
		}
		
		System.out.println(");");

		return null;
	}
	
	public Object visit(Return node, Object param) {
		System.out.print("return ");

		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		
		System.out.println(";");

		return null;
	}
	
	public Object visit(ReturnVacio node, Object param) {
		System.out.println("return;");
		return null;
	}
	
	public Object visit(ExpresionAritmetica node, Object param) {
		if (node.getIzquierda() != null)
			node.getIzquierda().accept(this, param);
		
		if (node.getOperador() != null)
			System.out.print(" " + node.getOperador() + " ");

		if (node.getDerecha() != null)
			node.getDerecha().accept(this, param);

		return null;
	}
	
	public Object visit(ExpresionBooleana node, Object param) {
		if (node.getIzquierda() != null)
			node.getIzquierda().accept(this, param);
		
		if (node.getOperador() != null)
			System.out.print(" " + node.getOperador() + " ");

		if (node.getDerecha() != null)
			node.getDerecha().accept(this, param);

		return null;
	}
	
	public Object visit(ExpresionComparativa node, Object param) {
		if (node.getIzquierda() != null)
			node.getIzquierda().accept(this, param);
		
		if (node.getOperador() != null)
			System.out.print(" " + node.getOperador() + " ");

		if (node.getDerecha() != null)
			node.getDerecha().accept(this, param);

		return null;
	}
	
	public Object visit(NegacionBooleana node, Object param) {
		System.out.print("!(");
		
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
				
		System.out.println(")");

		return null;
	}
	
	public Object visit(AccesoArray node, Object param) {
		if (node.getIzquierda() != null)
			node.getIzquierda().accept(this, param);
		
		System.out.print("[");

		if (node.getDerecha() != null)
			node.getDerecha().accept(this, param);
		
		System.out.print("]");

		return null;
	}
	
	public Object visit(AccesoStruct node, Object param) {
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		
		if (node.getCampo() != null) {
			System.out.print("." + node.getCampo());
		}

		return null;
	}
	
	public Object visit(Variable node, Object param) {
		System.out.print(node.getName());
		return null;
	}
	
	public Object visit(Cast node, Object param) {
		System.out.print("<");

		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		
		System.out.print(">(");

		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		
		System.out.print(")");

		return null;
	}
	
	public Object visit(ConstanteInt node, Object param) {
		System.out.print(node.getValor());
		return null;
	}
	
	public Object visit(ConstanteFloat node, Object param) {
		System.out.print(node.getValor());
		return null;
	}
	
	public Object visit(ConstanteChar node, Object param) {
		System.out.print(node.getValor());
		return null;
	}

}