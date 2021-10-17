grammar Grammar;
import Lexicon;

@parser::header {
	import ast.*;
}

start returns[Programa ast]
	: definiciones { $ast = new Programa($definiciones.lista); }
	;

definiciones returns[List<Definicion> lista = new ArrayList<Definicion>();] 
	: (definicion { $lista.add($definicion.ast); })*
	;

definicion returns[Definicion ast]
	: def_variable  { $ast = $def_variable.ast; }
	| def_atributo  { $ast = $def_atributo.ast; }
	| def_funcion 	{ $ast = $def_funcion.ast; 	}
	| def_struct 	{ $ast = $def_struct.ast; 	}
	;
	
def_variable returns[DefVariable ast]
	: 'var' IDENT ':' tipo ';' { $ast = new DefVariable($tipo.ast, $IDENT, "global"); }
	;

def_atributo returns[DefAtributo ast]
	: IDENT ':' tipo ';' { $ast = new DefAtributo($tipo.ast, $IDENT); }
	;
			
def_parametro returns[Parametro ast]
	: IDENT ':' tipo { $ast = new Parametro($tipo.ast, $IDENT); }
	;
			
parametros returns[List<Parametro> lista = new ArrayList<Parametro>();]
	: (IDENT ':' tipo { $lista.add(new Parametro($tipo.ast, $IDENT)); }
		(',' IDENT ':' tipo { $lista.add(new Parametro($tipo.ast, $IDENT)); })*
	)?
	;
			
def_funcion returns[DefFuncion ast]
	: IDENT '(' parametros ')' tipo_retorno '{' declaraciones sentencias '}'
		{ $ast = new DefFuncion($IDENT, $parametros.lista, $tipo_retorno.ast, $declaraciones.lista, $sentencias.lista); }
	|
	IDENT '(' parametros ')' '{' declaraciones sentencias '}' 
		{ $ast = new DefFuncion($IDENT, $parametros.lista, new TipoVoid(), $declaraciones.lista, $sentencias.lista); }
	;
				
def_struct returns[DefStruct ast]
	: 'struct' IDENT '{' atributos '}' ';' { $ast = new DefStruct($IDENT, $atributos.lista); }
	;
			
atributos returns[List<DefAtributo> lista = new ArrayList<DefAtributo>();]
	: (def_atributo { $lista.add($def_atributo.ast); })* 
	;
			
tipo returns[Tipo ast]
	: 'int'							{ $ast = new TipoInt(); 		 					}
	| 'float'						{ $ast = new TipoFloat(); 		 					}
	| 'char'						{ $ast = new TipoChar();		 					}
	| IDENT							{ $ast = new TipoStruct($IDENT); 					}
	| '[' INT_CONSTANT ']' tipo		{ $ast = new TipoArray($tipo.ast, $INT_CONSTANT); 	}
	;
			
tipo_retorno returns[Tipo ast]
	: ':' tipo	{ $ast = $tipo.ast; }
	;
			
declaraciones returns[List<DefVariable> lista = new ArrayList<DefVariable>();] 
	: (declaracion { $lista.add($declaracion.ast); })*
	;
	
declaracion returns[DefVariable ast]
	: 'var' IDENT ':' tipo '=' expresion ';' { $ast = new Inicializacion($tipo.ast, $IDENT, "local", $expresion.ast); }
	| 'var' IDENT ':' tipo ';' { $ast = new DefVariable($tipo.ast, $IDENT, "local"); }
	;	

sentencias returns[List<Sentencia> lista = new ArrayList<Sentencia>();]
	: (sentencia { $lista.add($sentencia.ast); })*
	;

sentencia returns[Sentencia ast]
	: e1=expresion '=' e2=expresion ';'				{ $ast = new Asignacion($e1.ast, $e2.ast); }
	| 'print' expresion ';'							{ $ast = new Print($expresion.ast); }
	| 'print' ';'									{ $ast = new Print(null); }
	| 'println' expresion ';'						{ $ast = new Println($expresion.ast); }
	| 'println' ';'									{ $ast = new Println(null); }
	| 'printsp' expresion ';'						{ $ast = new Printsp($expresion.ast); }
	| 'printsp' ';'									{ $ast = new Printsp(null); }
	| t='return' ';'								{ $ast = new ReturnVacio(); $ast.setPositions($t); }
	| 'return' expresion ';'						{ $ast = new Return($expresion.ast); }
	| 'read' expresion ';'							{ $ast = new Read($expresion.ast); }
	| 'while' '(' expresion ')' '{' sentencias '}'	{ $ast = new While($expresion.ast, $sentencias.lista); }
	| sentencia_if									{ $ast = $sentencia_if.ast; }
	| IDENT '(' argumentos ')' ';'					{ $ast = new LlamadaFuncionSentencia($IDENT, $argumentos.lista); }
	;
			
sentencia_if returns[If ast] 
	: 
	'if' '(' expresion ')' '{' sentencias '}'
		{ $ast = new If($expresion.ast, $sentencias.lista, new ArrayList<Sentencia>()); }		
	| 'if' '(' expresion ')' '{' sentencias '}' sentencia_else 
		{ $ast = new If($expresion.ast, $sentencias.lista, $sentencia_else.lista); }
	;
			
sentencia_else returns[List<Sentencia> lista = new ArrayList<Sentencia>();] 
	: 'else' '{' sentencias '}' { $lista = $sentencias.lista; }
	;
			
argumentos returns[List<Expresion> lista = new ArrayList<Expresion>();]
	: (arg1=expresion { $lista.add($arg1.ast); }
		(',' arg2=expresion { $lista.add($arg2.ast); })*
	)?
	;
									
expresion returns[Expresion ast]
	: INT_CONSTANT 									 { $ast = new ConstanteInt($INT_CONSTANT); 							}
	| REAL_CONSTANT									 { $ast = new ConstanteFloat($REAL_CONSTANT);						}
	| CHAR_CONSTANT									 { $ast = new ConstanteChar($CHAR_CONSTANT); 						}
	| IDENT 										 { $ast = new Variable($IDENT); 									}
	| e1=expresion '.' IDENT				 		 { $ast = new AccesoStruct($e1.ast, $IDENT); 						}
	| e1=expresion '[' e2=expresion ']' 			 { $ast = new AccesoArray($e1.ast, $e2.ast); 						}
	| e1=expresion operador=('*'|'/') e2=expresion	 { $ast = new ExpresionAritmetica($e1.ast, $operador, $e2.ast);		}
	| e1=expresion operador=('+'|'-') e2=expresion 	 { $ast = new ExpresionAritmetica($e1.ast, $operador, $e2.ast);		}
	| e1=expresion operador=('<='|'>=') e2=expresion { $ast = new ExpresionComparativa($e1.ast, $operador, $e2.ast);	}
	| e1=expresion operador=('<'|'>') e2=expresion	 { $ast = new ExpresionComparativa($e1.ast, $operador, $e2.ast);	}
	| e1=expresion operador=('=='|'!=') e2=expresion { $ast = new ExpresionComparativa($e1.ast, $operador, $e2.ast);	}
	| e1=expresion operador=('&&'|'||') e2=expresion { $ast = new ExpresionBooleana($e1.ast, $operador, $e2.ast);		}
	| '!' expresion									 { $ast = new NegacionBooleana($expresion.ast); 					}		 	
	| '(' expresion ')'								 { $ast = $expresion.ast; 											}
	| IDENT '(' argumentos ')'						 { $ast = new LlamadaFuncionExpresion($IDENT, $argumentos.lista); 	} 
	| '<' tipo '>' '(' expresion ')'				 { $ast = new Cast($tipo.ast, $expresion.ast); 						}
	;