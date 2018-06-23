package fiuba.materia7510.aplicacion.generador

/*Contenedorpara los datos recibidos por el procesador*/
public class CLojurePojo {

 private Object resultado = null
 
 public void setInicializarProceso(Object o)
 {resultado = o}
 public void setProcesarDatos(Object o)
 {resultado = o}
 public void setProcess_data_dropping_signals(Object o)
 {resultado = o}		
  public void setConsultar_Contador(Object o)
  {resultado = o}
  public void setAgregar_reglas_procesar_datos_emitir_sennales(Object o)
  {resultado = o}	
}
/*
 * 
 * class MyService {
    def grailsResourceLocator

    myMethod() {
        def fileIn = grailsResourceLocator.findResourceForURI('/txt/lexicon.txt').file
    }
}
 * 
 *tree
.
├── build.gradle
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── java-clojure.iml
├── settings.gradle
└── src
    └── main
        ├── clojure
        │   └── io
        │       └── advantageous
        ├── java
        │   └── io
        │       └── advantageous
        │           └── clojureint
        │               ├── IHello.java
        │               └── Main.java
        └── resources
            └── io
                └── advantageous
                    └── clojureint
                        └── main.clj
 
 * 
 * 
 * package io.advantageous.clojureint;

public interface IHello {

    void sayHello(String name);
    
}
* 
* main.clj Defining instance of IHello in Clojure

(ns io.advantageous.clojureint
    (:require
        [clojure.pprint :as pprint]
    )
    (:import [io.advantageous.clojureint IHello])
    (:gen-class)
)

(defn hello "hello" [] (println "hello"))


(defn HelloClass
  "hello func"
  []
  (reify
    IHello
    (sayHello [this name] (println "hello " name))))
Invoking from Java. Main.java

package io.advantageous.clojureint;


import clojure.lang.IFn;
import clojure.java.api.Clojure;

public class Main {

    public static void main(final String... args) throws Exception {

         Import clojure core. 
        final IFn require = Clojure.var("clojure.core", "require");
        require.invoke(Clojure.read("io.advantageous.clojureint.main"));

        Invoke Clojure hello function. 
        final IFn helloFunc = Clojure.var("io.advantageous.clojureint", "hello");
        helloFunc.invoke();


        Create instance of IHello from Clojure "class" and use it 
        final IFn helloClass = Clojure.var("io.advantageous.clojureint", "HelloClass");
        final IHello helloInstance = (IHello) helloClass.invoke();

        helloInstance.sayHello("Justin");
    }

}
 * */
 
 /**
  * 
  * Just to clarify it is sufficient to write this.class.getClassLoader().getResourceAsStream("my_xsl.xsl") if my_xsl.xsl is located at src/main/resource/my_xsl.xsl */
 
 /*
  * Static Client Files
If the files should be accessible both from the server and the client (browser), you should put them into the web-app directory in the root of your project (along with the grails-app and src folders). This is the location for the static client side files that don't need to be processed by the Grails Asset Pipeline and hence are not placed in the grails-app\assets folder.

You'll need to have the Grails ResourceLocator injected into your controller or service to gain access to these files:

class MyController {

    ResourceLocator grailsResourceLocator // injected during initialization

    def action() {

        // ...

        def resource = grailsResourceLocator.findResourceForURI('/static.json')
        def path = resource.file.path // absolute file path
        def inputStream = resource.inputStream // input stream for the file

       // ...
    }

    // ...
}
Make sure you always pass in the absolute URI of the path (starting with a /), or the file won't be found.

Server-Only Files
For the files that are specific to the server and shouldn't be accessible from the browser, you should put them in the grails-app/conf folder. Any files placed in that folder will be copied to the root of your application during build. This means they will be accessible as regular Java resources:

def resource = this.class.classLoader.getResource('conf.json')
def path = resource.file // absolute file path
return resource.openStream() // input stream for the file
Notice that there's no leading / in this case.

Alternatively, you can also put these files into src/main/resources folder, if you don't want to pollute your conf folder with files that are not really configuration files. The code to access them will still be the same. Thanks to Gregor for bringing this option to my attention.*/
