package fiuba.materia7510.aplicacion.generador

import fiuba.materia7510.aplicacion.utilidad.Impresor
import fiuba.materia7510.aplicacion.utilidad.Constantes
import fiuba.materia7510.aplicacion.MotorService
import fiuba.materia7510.aplicacion.generador.Estadio

class ReglaController {

    static scaffold = Regla
	def seleccionarRegla(){
		
		Impresor.instance.consola( "[Seleccionando Regla]...[parametros:${params}]",true)
		
		def encontrado_regla_maestra 	= null
		def regla_sel					= null
		regla_sel						= Regla.get(params.regla) //params retorna el id
		encontrado_regla_maestra		= Regla.findByNombre(Constantes.REGLA_PRIMERA)
		
		Impresor.instance.consola( "[REGLA PREXISTENTE:${encontrado_regla_maestra}]",true)
		Impresor.instance.consola( "[REGLA SELECCIONADA:${regla_sel}]",true)
				
		if(encontrado_regla_maestra && regla_sel){
			
			
			encontrado_regla_maestra.regla = regla_sel.regla
		    
		    if(!encontrado_regla_maestra.hasErrors()){
				encontrado_regla_maestra.save(flush:true)
				Impresor.instance.consola( "[REGLA SELECCIONADA]...[${Regla.findByNombre(Constantes.REGLA_PRIMERA)}]",true)
				
				configurarEstadoInicialMotor(encontrado_regla_maestra)
			
			}
		}
			
		redirect (controller:"publicador", action:"iniciar")
	}
	
	
	def configurarEstadoInicialMotor(Regla reglamento){
		
		Estadio estadio_motor_principal= Estadio.findByNombre(Constantes.NOMBRE_ESTADIO_MOTOR_PRODUCCION)
		
		Impresor.instance.consola( "[ESTADIO PRINCIPAL MOTOR :${estadio_motor_principal}]",true)
		
		
		def estad = MotorService.inicializar_procesador(reglamento.regla)
		
		Impresor.instance.consola( "[ESTADIO PRINCIPAL MOTOR- NUEVA REGLAMENTACION :${estad}]",true)
		
		if (estad){ 
			
			estadio_motor_principal.estadio = estad
			if(!estadio_motor_principal.hasErrors()){
				estadio_motor_principal.save(flush:true)
				
				Impresor.instance.consola("[ESTADIO MOTOR ACTUALIZADO] ${Estadio.findByNombre(Constantes.NOMBRE_ESTADIO_MOTOR_PRODUCCION)}",true)
		
				}
		}
	}
		
		
	def derivar_InicioMonitor(){
		redirect (controller:"publicador", action:"iniciar")
	}
}
