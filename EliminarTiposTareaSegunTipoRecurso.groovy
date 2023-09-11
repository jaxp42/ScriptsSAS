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

//Lista de los tipos de recuros
def listaTiposRecurso = [
    "gestion proyectos",
    "gestion proyectos negocio"
]

//Lista de los tipos de tarea que se van a eliminar de todos los proyectos
def listaTiposTarea = [
    "Subida a PRO",
    "LINEAS TRANSFORMACIÓN",
    "Tareas de sistemas",
    "Tarea del piloto", 
    "Gestión",
    "Servicio de mejora continua",
    "Centralizado",
    "OTRAS",
    "IMPLANTACIONES",
    "Desarrollo funcionalidades",
    "CONFLUENCE",
    "Gestión de Proyecto",
    "Tarea al proveedor",
    "SERVICIOS CGES",
    "GESTOR NOTIFICACIONES",
    "Servicio de testing funcional",
    "Servicio de Verificación de Sprint",
    "MTI-SSHH",
    "Fase 1",
    "MI GESTOR SERVICIOS",
    "Servicio de verificación de la instalabilidad (FARO)",
    "ASTERISK",
    "Servicio de testing de accesibilidad",
    "Producto",
    "NUEVA WEB TÉCNICA",
    "Agile",
    "Técnica",
    "Servicio de gestión de entornos",
    "OCS",
    "Servicio de testing temprano",
    "MI CENTRO SERVICIOS",
    "OTI",
    "Integración Continua",
    "Fase 2",
    "Servicio de Soporte",
    "JIRA",
    "Distribuido",
    "Hospital",
    "Servicio de Usabilidad"
]

//Project Keys de los proyectos excluidos de eliminar sus tipos de tareas
def listaProyectosExcluidos = [
    "M360",
    "TRANSFOR",
    "COORDINA",
    "ALWIFIADA",
    "STICGES",
    "HUGESVACA"
]

/***********************************************************************
                            FIN CONFIGURACIÓN
***********************************************************************/

def log = []


for(tipoRecurso in listaTiposRecurso){
    eliminarTiposDeTareaDeTipoRecurso(KEY_TIPO_RECURSO, tipoRecurso, servicioPropiedadDeProyecto, servicioTipoTarea, projectManager, listaTiposTarea, listaProyectosExcluidos, log);
    return log;
}

def eliminarTiposDeTareaDeTipoRecurso(keyTipoRecurso, tipoRecurso, servicioPropiedadDeProyecto, servicioTipoTarea, projectManager, listaTiposTarea, listaProyectosExcluidos, log){
    def listaIdProyectos = servicioPropiedadDeProyecto.obtenerIdProyectosContengaValorPropiedad(keyTipoRecurso, tipoRecurso);

    for(idProyecto in listaIdProyectos){
        eliminarTiposDeTareaDeProyecto(idProyecto, projectManager, listaTiposTarea, listaProyectosExcluidos, servicioTipoTarea, log);
    }
} 

def eliminarTiposDeTareaDeProyecto(idProyecto, projectManager, listaTiposTarea, listaProyectosExcluidos, servicioTipoTarea, log){
    def proyecto = projectManager.getProjectObj(idProyecto);

    if(proyecto != null && !listaProyectosExcluidos.contains(proyecto.getKey())){
        def tiposTareaDeProyecto = servicioTipoTarea.obtenDeProyecto(idProyecto);

        for(tipoTarea in tiposTareaDeProyecto){
            if(listaTiposTarea.contains(tipoTarea.getTipoTarea())){
                servicioTipoTarea.borraPorId(tipoTarea.getId());
            }
        }
        log.push("Eliminadas tareas proyecto - " + proyecto.getKey());
    }
}

