# Motor para monitorizar eventos-  segunda parte -

A Clojure template designed to be used as a base for [TP1 in Tecnicas de Diseño](https://github.com/7510-tecnicas-de-disenio/material-clases/blob/master/TPs/2018-1C%20-%20TP-1.1%20-%20Validador%20de%20Reglas.pdf).
## NOTA DE CREACION JAR


Warning: specified :main without including it in :aot. 
Implicit AOT of :main will be removed in Leiningen 3.0.0. 
If you only need AOT for your uberjar, consider adding :aot :all into your
:uberjar profile instead
 Sintaxis factible:
 :main ^skip-aot motor
 :profiles {:uberjar {:aot :all}}


## AOT  Ahead-of-time Compilation and Class Generation
	Clojure compiles all code you load on-the-fly into JVM bytecode,
but sometimes it is advantageous to compile ahead-of-time (AOT).
	Some reasons to use AOT compilation are:

.To deliver your application without source
.To speed up application startup
.To generate named classes for use by Java
.To create an application that does not need runtime bytecode generation and custom classloaders
	The Clojure compilation model preserves as much as possible the dynamic nature of Clojure, in spite of the code-reloading limitations of Java.

	Source and classfile pathing follows Java classpath conventions.
	The target of compile is a namespace
	Each file, fn and gen-class will produce a .class file
	Each file generates a loader class of the same name with "---init" appended.

	The static initializer for a loader class produces the same effects as does loading its source file
	You generally shouldn’t need to use these classes directly, as use, require and load will choose between them and more recent source
	The loader class is generated for each file referenced when a namespace is compiled, when its loader .class file is older than its source.
	A stand-alone gen-class facility is provided to create named classes,
for direct use as Java classes, with facilities for:

.Naming the generated class
.Selecting the superclass
.Specifying any implemented interfaces
.Specifying constructor signatures
.Specifying state
.Declaring additional methods
.Generating static factory methods
.Generating main
.Controlling the mapping to an implementing namespace
.Exposing inherited protected members
.Generating more than one named class from a single file, with implementations in one or more namespaces
.An optional :gen-class directive can be used in the ns declaration to generate a named class corresponding to a namespace. (:gen-class …​), when supplied, defaults to :name corresponding to the ns name, :main true, :impl-ns same as ns, and :init-impl-ns true. All options of gen-class are supported.
.gen-class and the :gen-class directive are ignored when not compiling.
.A stand-alone gen-interface facility is provided for generating named interface classes for direct use as Java interfaces, with facilities for:
.Naming the generated interface
.Specifying any superintefaces
.Declaring the signatures of interface methods 

## Usage

java -jar motoClojure-1.0.0-standalone.jar [args]

## License

Copyright © 2018

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

## Tutorial
https://8thlight.com/blog/colin-jones/2010/11/26/a-leiningen-tutorial.html

## Install leiningen
https://github.com/technomancy/leiningen

## Run Tests
lein test

## Run Repl
lein repl
