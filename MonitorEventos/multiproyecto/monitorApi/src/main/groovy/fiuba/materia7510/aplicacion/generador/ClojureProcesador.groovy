package fiuba.materia7510.aplicacion.generador

import clojure.java.api.Clojure
import clojure.lang.IFn


class ClojureProcesador extends Procesador {
	/**************MUY IMPORTANTE*******************/
	/**
	 * Cuando aparece un error de este estilo:
	 * Exception in thread "main" java.lang.IllegalStateException: 
	 * Attempting to call unbound fn: #'motor/saludar...
	 * 
	 * Lo que sucede es que en tiempo de ejecucion (runtime),
	 * el namespace que me interesa no esta disponible, 
	 * por ejemplo, motor del archivo motor.clj;
	 * 
	 * por lo tanto, para ello el procedimiento es,
	 * REQUERIR (require) ese namespace (o libreria de Clojure),
	 * para que estE disponible.
	 * 
	 * (por esa razon, funcionaba en grails console y luego dejo de
	 * funcionar en unitTests). Por esa razon funcionaba solamente,
	 * las operaciones aritmeticas, que dependen de clojure.core,
	 * y no de los propios (caso de namespace motor).
	 * Se agrega el siguiente comentario adicional encontrado 
	 * en Internet( adaptado al proyecto):
	 * ClojureProcesador class does not automatically import
	 * and compile the Clojure code that it depends on. 
	 * In this case, we are calling a Clojure function saludar 
	 * from motor.saludar(), and this code is not loaded in the runtime.
	 * 
	 * So to load the required Clojure code, 
	 * we need to invoke "funcion require" to use our new class. 
	 * Here we use the clojure.java.api package to require our 
	 * motor namespace.
	 * 
	 * IFn require = Clojure.var("clojure.core","require");
	 * require.invoke(Clojure.read("motor"));
	 * ****/
	/********************************************/
	
	static final String libreriafuncionEstandard 	= "clojure.core"
	
	/*******************************************************/
	static void imprimir (Object... args){
		if (args.length() > 1)
			println args.join(",")
	}
	static void imprimir (Object arg){
		println arg
	}
	
	/*******************************************************/
	//para cargar otros namespaces de la libreria de clojure (por ejemplo:(:require [clojure.set])
	static final String require_funcion			= "require"
	
	static IFn requerir_libreria (String libreria_Estandard,String require_fn, String libreria_requerida){
		 Clojure.var(libreria_Estandard, require_fn)?.invoke(Clojure.read(libreria_requerida))	 
	}
	
	static IFn cargarClojureNamespace (String ns){
		//carga el namespace, retorna null si falla
		Clojure.read(ns)
	}
	
	/*******************************************************/
	static IFn obtenerFuncionEstandard (String libreria_Estandard, String operador){
		 Clojure.var(libreria_Estandard, operador);	 
	}
	
	static IFn obtenerFuncionEstandard (String operador){
		 Clojure.var(libreriafuncionEstandard, operador);	 
	}
	/**
	static IFn obtenerFuncion (String ns_de_clojure, String funcion_de_ns){
		 Clojure.var(ns_de_clojure, funcion_de_ns);}
	*
	*/
	
	static IFn obtenerFuncion (IFn ns_de_clojure, String funcion_de_ns){
		 Clojure.var(ns_de_clojure, funcion_de_ns);
		 
	}
	/**************Cantidad de parametros predefinido*************/
	/****OBJECT INCLUYE NUMBER, No incluye STRING******/
	static Object invocar(IFn fn){								
			fn?fn.invoke():null
		}
	//esto sirve para pojo tambien. con MotorPOJO.clj
	static Object invocar(IFn fn, Object arg){	
		imprimir (arg)					
			(fn && arg)?fn.invoke(arg):null
		}
	
	
	/**PRINCIPAL PARA PROCESAMIENTO **/
	static Object invocar(IFn fn, Object arg, String arg2){						
			
			(fn && arg && arg2 )?fn.invoke(arg, Clojure.read(arg2)):null
		}
	
	static Object invocar(IFn fn, Object arg, Object arg2){						
			
			
			(fn && arg && arg2 )?fn.invoke(arg, arg2):null
		}	
	static Object invocar(IFn fn, Object arg, Object arg2, Object arg3){
						
			(fn && arg && arg2 && arg3 )?fn.invoke(arg, arg2, arg3):null
		}
	static Object invocar(IFn fn, Object arg, Object arg2, Object arg3,Object arg4){						
			(fn && arg && arg2 && arg3 && arg4)?fn.invoke(arg, arg2, arg3, arg4):null
		}		
		
	/*************Para Strings, cantidad de parametros predefinidos**/
	
	static Object invocar(IFn fn, String arg){						
			(fn && arg )?fn.invoke(Clojure.read(arg)):null
		}
	static Object invocar(IFn fn, String arg, String arg2){
							
			(fn && arg && arg2 )?fn.invoke(Clojure.read(arg), Clojure.read(arg2)):null
		}
	static Object invocar(IFn fn, String arg, String arg2, String arg3){						
			(fn && arg && arg2 && arg3 )?fn.invoke(Clojure.read(arg), Clojure.read(arg2),Clojure.read(arg3)):null
		}
	static Object invocar(IFn fn, String arg, String arg2, String arg3,String arg4){						
			(fn && arg && arg2 && arg3 && arg4)?fn.invoke(Clojure.read(arg), Clojure.read(arg2),Clojure.read(arg3), Clojure.read(arg4)):null
		}			

	/*******************Multiparametros VARARGS***************************/	
	//no utiles.
	/*********************************************************************/
		//ejemplo (map + [1 2 3]): funcion_Estandard= map ;fn = +; argumento = [1 2 3]
		//para funciones estandards definido.
	
	static Object invocarFuncionOrdenSuperior (String argumento, String funcion_Superior, String funcion_valor){
					
		obtenerFuncionEstandard(funcion_Superior)?.invoke(obtenerFuncionEstandard(funcion_valor), Clojure.read(argumento))
	}
	
	static Object invocarFuncionOrdenSuperior (String argumento, String funcion_Estandard, IFn fn){
					
		obtenerFuncionEstandard(funcion_Estandard)?.invoke(fn, Clojure.read(argumento))
	}
	
}

/**
	* NOTA: este falla por no poder castear.
	* No puede convertir Object to Number.
	* Tampoco Number to Number ??? Por eso signatures con long, int.
	* IMPORTANTE: Sirve para un array de elementos: ARIDAD 1(UNO) para CLOJURE
	* 
	* 
	static Object invocar(IFn fn, Object... args){	

		(fn && args && (args.length() != 0))?fn.invoke(args):fn.invoke()
	}
	* *******/

	/**	
	 * 
	static Object invocar(IFn fn, String... args){						
			(fn && args && (args.length() != 0))?fn.invoke(args):fn.invoke()
		}
	static Object invocar(IFn fn, Number... args){						
			(fn && args && (args.length() != 0))?fn.invoke(args):fn.invoke()
		}
	static Object invocar(IFn fn, long... args){						
			(fn && args && (args.length() != 0))?fn.invoke(args):fn.invoke()
		}
	static Object invocar(IFn fn, int... args){						
			(fn && args && (args.length() != 0))?fn.invoke(args):fn.invoke()
		}
	*/	

	/**
	 * Metodo standarizado
	 * public void callIfExists(String namespace, String name, Object... args) {
		require.invoke(namespace);
		IFn method = Clojure.var(namespace, name);
		if (method != null) {
			if (args != null && args.length != 0) {
				method.invoke(args);
			} else {
				method.invoke();
			}
		}
	}
	 */
 /* INFORMACION UTIL:
 * *public static IFn var(Object qualifiedName)
	*Returns the var associated with qualifiedName.
	*Parameters:
	*qualifiedName - a String or clojure.lang.Symbol
	*Returns:
	*a clojure.lang.IFn
	*  
 * */
 /*
  * public static IFn var(Object ns,
      Object name)
	Returns an IFn associated with the namespace and name.
	Parameters:
	ns - a String or clojure.lang.Symbol
	name - a String or clojure.lang.Symbol
	Returns:
	a clojure.lang.IFn
* */
/*
 * public static Object read(String s)
Read one object from the String s. Reads data in the edn format.
Parameters:
s - a String
Returns:
an Object, or nil.
 * */



/*INFORMACION DE INTERES INTEROPERABILIDAD CON CLOJURE
 * 
 * 
 * Clojure interop from Java.
 * The clojure.java.api package provides a minimal interface
 * to bootstrap Clojure access from other JVM languages. 
 * It does this by providing:
 * 
 * The ability to use Clojure's namespaces to locate an arbitrary var,
 * returning the var's clojure.lang.IFn interface.
 * 
 * A convenience method read for reading data using Clojure's edn reader.
 * 
 * IFns provide complete access to Clojure's APIs.
 * You can also access any other library written in Clojure,
 *  after adding either its source or compiled form to the classpath.
 * 
 * 
 * The public Java API for Clojure 
 * consists of the following classes and interfaces:
 * 
 * clojure.java.api.Clojure
 * clojure.lang.IFn
 * 
 * All other Java classes should be treated as implementation details,
 * and applications should avoid relying on them.
 * 
 * To lookup and call a Clojure function:

	IFn plus = Clojure.var("clojure.core", "+");
	plus.invoke(1, 2);

* Functions in clojure.core are automatically loaded.
*  Other namespaces can be loaded via require:

	IFn require = Clojure.var("clojure.core", "require");
	require.invoke(Clojure.read("clojure.set"));
* IFns can be passed to higher order functions,
*  e.g. the example below passes plus to read:

	IFn map = Clojure.var("clojure.core", "map");
	IFn inc = Clojure.var("clojure.core", "inc");
	map.invoke(inc, Clojure.read("[1 2 3]"));
* Most IFns in Clojure refer to functions.
* A few, however, refer to non-function data values.
* To access these, use deref instead of fn:

	IFn printLength = Clojure.var("clojure.core", "*print-length*");
	IFn deref = Clojure.var("clojure.core", "deref");
	deref.invoke(printLength);
********************************************************/
