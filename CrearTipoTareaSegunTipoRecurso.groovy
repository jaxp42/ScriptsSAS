/**
Script para crear una lista de tipos de tareas a todos los proyectos que tengan los tipos de recursos indicados.
*/

import com.atlassian.jira.component.ComponentAccessor
import com.onresolve.scriptrunner.runner.customisers.WithPlugin
import com.onresolve.scriptrunner.runner.customisers.PluginModule
import org.sas.cges.jira.app.service.ServicioTipoTarea
import org.sas.cges.jira.app.service.ServicioPropiedadDeProyecto
import org.sas.cges.jira.app.bean.TipoTareaBean

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
def TIPO_RECURSO = "gestion proyectos"

def areas = "sistemas,puesto usuario" //nombres separados por coma

def tiposRegistro = "11301, 11304, 11303, 11306, 11400, 11600" //ids separados por coma

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
/***********************************************************************
                            FIN CONFIGURACIÓN
***********************************************************************/

def log = ""

def proyectos = servicioPropiedadDeProyecto.obtenerIdProyectosContengaValorPropiedad(KEY_TIPO_RECURSO, TIPO_RECURSO);

for(idProyecto in proyectos){
    log += "<details>"
    log += "<summary>Editanto/Creando tipos de tarea del proyecto: " + idProyecto + "</summary>";

    for(tipoTarea in listaTiposTarea){
        def busquedaTipoTarea = servicioTipoTarea.obtenDeProyectoPorNombre(idProyecto, tipoTarea);

        if(busquedaTipoTarea.isEmpty()){
            log += "<p>Creando: " + tipoTarea + "</p>";

            def tipoTareaBean = new TipoTareaBean();
            tipoTareaBean.setIdProyecto(idProyecto);
            tipoTareaBean.setArea(areas);
            tipoTareaBean.setTipoTarea(tipoTarea);
            tipoTareaBean.setTipoRegistro(tiposRegistro); 
            tipoTareaBean.setAplicaATodos(false);
            tipoTareaBean.setDeshabilitado(false);
            
            servicioTipoTarea.crea(tipoTareaBean);
        }
        else{
            log += "<p>"
            log += "Editando: " + tipoTarea + " con id: " + busquedaTipoTarea.get(0).getId();

            try{
                def tipoTareaBean = new TipoTareaBean();
                tipoTareaBean.setId(busquedaTipoTarea.get(0).getId())
                tipoTareaBean.setIdProyecto(idProyecto);
                tipoTareaBean.setArea(areas);
                tipoTareaBean.setTipoTarea(tipoTarea);
                tipoTareaBean.setSubTipo(busquedaTipoTarea.get(0).getSubTipo())
                tipoTareaBean.setTipoRegistro(tiposRegistro); 
                tipoTareaBean.setAplicaATodos(busquedaTipoTarea.get(0).getAplicaATodos());
                tipoTareaBean.setDeshabilitado(busquedaTipoTarea.get(0).getDeshabilitado());

                servicioTipoTarea.actualiza(tipoTareaBean);
            }
            catch(Exception e){
				log += " - ERROR"
            }
            
            log += "</p>"
        }
    }

    log += "</details>"
}

return log;
