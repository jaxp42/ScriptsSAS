/**
    Script para asignar una lista de tipos de tareas a todos los proyectos de una determinada categoría de proyecto.
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


/*****************************************************************************************
                                    CONFIGURACIÓN
*****************************************************************************************/

//ID de la categoría de los proyectos en los que se van a crear los nuevos tipos de tarea
def categoria = 10908; //ID categoría

//Area de las tareas. ¡¡¡DEJAR EN BLANCO SI LAS TAREAS QUE SE VAN A AÑADIR NO TIENEN UN ÁREA COMÚN!!!
def area = "sistemas"; //Nombres de las areas en 1 string separados por ,

//Tipos de registro de los tipos de tarea que se van a agregar. IMPORTANTE Los proyectos deben pertenecer a los mismo tipos de registros.
def tiposRegistro = "11301, 11304" //ID's de los registros en 1 string separados por ,

//Lista con los tipos de tareas que se van a añadir a los proyectos
def listaTiposTarea = [
    "Análisis y Monitorización"
];

/*****************************************************************************************
                                FIN CONFIGURACIÓN
*****************************************************************************************/

def listaProyectos = projectManager.getProjectObjectsFromProjectCategory(categoria);
def log = [];

for(proyecto in listaProyectos){
    for(tipoTarea in listaTiposTarea){
        def tipoTareaBean = new TipoTareaBean();
        tipoTareaBean.setIdProyecto(proyecto.getId());
        tipoTareaBean.setArea(area);
        tipoTareaBean.setTipoTarea(tipoTarea);
        tipoTareaBean.setTipoRegistro(tiposRegistro); 
        tipoTareaBean.setAplicaATodos(false);
        tipoTareaBean.setDeshabilitado(false);
        try{
        servicioTipoTarea.crea(tipoTareaBean);
        }
        catch(Exception e){
            log.push("No se ha podido crear en el proyecto: " + proyecto.getKey());
        }
    }
}

return log;