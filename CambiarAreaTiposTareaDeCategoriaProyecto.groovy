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
def categoria = 10908;

//Areas que van a cambiar
def areaAntigua = "gestión,proyectos,i+d+i";
def areaNueva = "gestión,implantación/proyectos,i+d+i";

/*****************************************************************************************
                            
*****************************************************************************************/

def listaProyectos = projectManager.getProjectObjectsFromProjectCategory(categoria);
def log = [];

for(proyecto in listaProyectos){
    try{
        def tiposTarea = servicioTipoTarea.obtenDeProyecto(proyecto.getId());
        for(tipoTarea in tiposTarea){
            if(tipoTarea.getArea().contains(areaAntigua)){
                def tipoTareaBean = new TipoTareaBean();
                tipoTareaBean.setId(tipoTarea.getId());
                tipoTareaBean.setIdProyecto(proyecto.getId());
                tipoTareaBean.setArea(tipoTarea.getArea().replace(areaAntigua, areaNueva));
                tipoTareaBean.setSubTipo(tipoTarea.getSubTipo())
                tipoTareaBean.setTipoTarea(tipoTarea.getTipoTarea());
                tipoTareaBean.setTipoRegistro(tipoTarea.getTipoRegistro()); 
                tipoTareaBean.setAplicaATodos(tipoTarea.getAplicaATodos());
                tipoTareaBean.setDeshabilitado(tipoTarea.getDeshabilitado());

                servicioTipoTarea.actualiza(tipoTareaBean);
            }
        }
    }
    catch(Exception e){
        log.push("No se ha podido actualizar en el proyecto: " + proyecto.getKey());
    }
}

return log;