package fiuba.materia7510.aplicacion.generador

import clojure.java.api.Clojure
import clojure.lang.IFn


/* *public static IFn var(Object qualifiedName)
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

class ClojureProcesador extends Procesador {
	
	
	static String fuentefuncionEstandard = "clojure.core"
	
	static IFn obtenerFuncion (String operador){
		 Clojure.var(fuentefuncionEstandard, operador);
		 
	}
	//esto sirve para pojo tambien. con MotorPOJO.clj
	static Object invocar(IFn fn, Object arg){						
			(fn && arg && (arg.length != 0))?fn.invoke(arg):fn.invoke()
		}
	static Object invocar(IFn fn, Number arg){						
			(fn && arg )?fn.invoke(arg):fn.invoke()
		}
	static Object invocar(IFn fn, String arg){						
			(fn && arg && (arg.length() != 0))?fn.invoke(arg):fn.invoke()
		}
	
	static Object invocar(IFn fn, Object arg1,Object arg2 ){						
			(fn && arg1 && arg2 && (arg1.length != 0) && (arg2.length != 0))?fn.invoke(arg1,arg2):fn.invoke()
		}
	static Object invocar(IFn fn, Number arg1,Number arg2 ){						
			(fn && arg1 && arg2 )?fn.invoke(arg1,arg2):fn.invoke()
		}
	static Object invocar(IFn fn, String arg1,String arg2 ){						
			(fn && arg1 && arg2 && (arg1.length() != 0) && (arg2.length() != 0))?fn.invoke(arg1,arg2):fn.invoke()
		}
		
	static Object invocar(IFn fn, Object... args){						
			(fn && args && (args.length != 0))?fn.invoke(args):fn.invoke()
		}
	
	static IFn cargarClojureNamespace (String ns){
		//carga el namespace, retorna null si falla
		
	(ns.length() > 0)?Clojure.var(fuentefuncionEstandard, "require")?.invoke(Clojure.read(ns)):null
		
}
		
	static Object invocarFuncionOrdenSuperior (String argumento, String operador, IFn fn){
					
		fn?obtenerFuncion(operador)?.invoke(fn, Clojure.read(argumento)):null
	}
	
	static Object invocar(String ns, String nombreFuncion, Object... args) {
    
		IFn funcion = cargarClojureNamespace(ns)?Clojure.var(ns, nombreFuncion):null
			
		if (funcion && args && (args.length != 0)) {
				funcion.invoke(args);
			} else {
				funcion.invoke();
			}
		}


}
 
 
 

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
