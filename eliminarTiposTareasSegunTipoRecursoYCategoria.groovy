/**
Script para eliminar una lista de tipos de tareas a todos los proyectos que tengan los tipos de recursos indicados.
*/

import com.atlassian.jira.component.ComponentAccessor
import com.onresolve.scriptrunner.runner.customisers.WithPlugin
import com.onresolve.scriptrunner.runner.customisers.PluginModule
import org.sas.cges.jira.app.service.ServicioTipoTarea
import org.sas.cges.jira.app.service.ServicioPropiedadDeProyecto

@WithPlugin("org.sas.cges.jira.app.sas-utilities")

@PluginModule
ServicioTipoTarea servicioTipoTarea

@PluginModule
ServicioPropiedadDeProyecto servicioPropiedadDeProyecto

def projectManager = ComponentAccessor.projectManager;


/***********************************************************************
                            CONFIGURACIÓN
***********************************************************************/
def KEY_TIPO_RECURSO = "TIPO_RECURSO"
def CATEGORIA = "PROYECTOS ÁREA SOLUCIONES CORPORATIVAS Y SOCIEDAD DIGITAL"

//Lista de los tipos de recuros
def listaTiposRecurso = [
    "gestion proyectos negocio"
]


/***********************************************************************
                            FIN CONFIGURACIÓN
***********************************************************************/

def log = []


for(tipoRecurso in listaTiposRecurso){
    def listaIdProyectos = servicioPropiedadDeProyecto.obtenerIdProyectosContengaValorPropiedad(KEY_TIPO_RECURSO, tipoRecurso);

    for(idProyecto in listaIdProyectos){
        def proyecto = projectManager.getProjectObj(idProyecto)

        if(proyecto != null){
            def categoriaProyecto = proyecto.getProjectCategory()

            if(CATEGORIA.equals(categoriaProyecto.getName())){
                def tiposTareaDeProyecto = servicioTipoTarea.obtenDeProyecto(idProyecto);

                for(tipoTarea in tiposTareaDeProyecto){
                    servicioTipoTarea.borraPorId(tipoTarea.getId());
                }

                log.push("Eliminadas tareas proyecto - " + proyecto.getKey());
            }
        }
    }
}

return log;

