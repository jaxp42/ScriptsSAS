/**
    Script para cambiar el nombre de un tipo de tarea.
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

//Tipos de tarea que van a cambiar
def nombreTipoTarea = "Análisis y Monitorización"
def tiposRegistro = "11400, 11301, 11304, 11303, 11306"

/*****************************************************************************************
                            
*****************************************************************************************/

def listaProyectos = projectManager.getProjectObjects();
def log = [];

for(proyecto in listaProyectos){
    def tiposTarea = servicioTipoTarea.obtenDeProyecto(proyecto.getId());
    for(tipoTarea in tiposTarea){
        if(tipoTarea.getTipoTarea() == nombreTipoTarea){
            def tipoTareaBean = new TipoTareaBean();
            tipoTareaBean.setId(tipoTarea.getId());
            tipoTareaBean.setIdProyecto(proyecto.getId());
            tipoTareaBean.setArea(tipoTarea.getArea());
            tipoTareaBean.setSubTipo("")
            tipoTareaBean.setTipoTarea(tipoTarea.getTipoTarea());
            tipoTareaBean.setTipoRegistro(tiposRegistro); 
            tipoTareaBean.setAplicaATodos(tipoTarea.getAplicaATodos());
            tipoTareaBean.setDeshabilitado(tipoTarea.getDeshabilitado());

            try{
                servicioTipoTarea.actualiza(tipoTareaBean);
            }catch(Exception e){
                log.push("Error actualizando " + proyecto.getId())
            }
        }
    }
}

return log;