<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Monitor-[APICLOJURE]</title>

    <asset:link rel="icon" href="favicon.ico" type="image/x-ico" />
</head>
<body>
    <content tag="nav">
        <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Application Status <span class="caret"></span></a>
            <ul class="dropdown-menu">
                <li><a href="#">Entorno: ${grails.util.Environment.current.name}</a></li>
                <li><a href="#">Perfil de la aplicación: ${grailsApplication.config.grails?.profile}</a></li>
                <li><a href="#">version de la aplicación:
                    <g:meta name="info.app.version"/></a>
                </li>
                <li role="separator" class="divider"></li>
                <li><a href="#">Grails version:
                    <g:meta name="info.app.grailsVersion"/></a>
                </li>
                <li><a href="#">Groovy version: ${GroovySystem.getVersion()}</a></li>
                <li><a href="#">JVM version: ${System.getProperty('java.version')}</a></li>
                <li role="separator" class="divider"></li>
                <li><a href="#">Reloading active: ${grails.util.Environment.reloadingAgentEnabled}</a></li>
            </ul>
        </li>
        <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Artefacts <span class="caret"></span></a>
            <ul class="dropdown-menu">
                <li><a href="#">Controladores: ${grailsApplication.controllerClasses.size()}</a></li>
                <li><a href="#">Dominios: ${grailsApplication.domainClasses.size()}</a></li>
                <li><a href="#">Servicios: ${grailsApplication.serviceClasses.size()}</a></li>
                <li><a href="#">Libreria de etiquetas</a>: ${grailsApplication.tagLibClasses.size()}</a></li>
            </ul>
        </li>
        <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Installed Plugins <span class="caret"></span></a>
            <ul class="dropdown-menu">
                <g:each var="plugin" in="${applicationContext.getBean('pluginManager').allPlugins}">
                    <li><a href="#">${plugin.name} - ${plugin.version}</a></li>
                </g:each>
            </ul>
        </li>
    </content>

    <div class="svg" role="presentation">
        <div class="grails-logo-container">
            <asset:image src="grails-cupsonly-logo-white.svg" class="grails-logo"/>
        </div>
    </div>

    <div id="content" role="main">
        <section class="row colset-2-its">
            <h1>Bienvenido Monitor de eventos</h1>

            <p>
                CONTROLADORES [REDIRIGIENDO A INDICE]:
            </p>

            <div id="controllers" role="navigation">
                <h2>Available Controllers:</h2>
                <ul>
                    <g:each var="c" in="${grailsApplication.controllerClasses.sort { it.fullName } }">
                        <li class="controller">
                            <g:link controller="${c.logicalPropertyName}">${c.fullName}</g:link>
                        </li>
                    </g:each>
                </ul>
            </div>
        </section>
    </div>
    <table style="border:1 px solid black" >
		<th style="border:1 px solid black" ><h2><span class="label label-default">Pulse para dar inicio a la aplicación</span></h2></th>
	<div id ="derivacion" style="font-family: Arial, Helvetica, sans-serif;font-size:20px;text-align:center;text-shadow: 2px 2px green; background-color: rgba(255, 0, 0, 0.3)">
				
			<g:link controller="publicador" action="derivar_Iniciar"><input type="button" value="INICIAR APLICACION" class="btn btn-primary" /></g:link>
					
	</div>
	</table
</body>
</html>
