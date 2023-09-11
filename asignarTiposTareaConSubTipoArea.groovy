/**
Script para renombrar todas las subtareas y añadirles un subtipo. 
*/

import com.onresolve.scriptrunner.runner.customisers.WithPlugin
import com.onresolve.scriptrunner.runner.customisers.PluginModule
import org.sas.cges.jira.app.service.ServicioTipoTarea
import org.sas.cges.jira.app.bean.TipoTareaBean

@WithPlugin("org.sas.cges.jira.app.sas-utilities")

@PluginModule
ServicioTipoTarea servicioTipoTarea

def tiposDeTarea = servicioTipoTarea.getAll();
def logger = [];

for(tipoTarea in tiposDeTarea){
    if(tipoTarea.getTipoTarea().contains("–")){

        def tipoYSubtipo = tipoTarea.getTipoTarea().split('–');
        logger.add(tipoYSubtipo);

        def tipoTareaBean = new TipoTareaBean();
        tipoTareaBean.setId(tipoTarea.getId());
        tipoTareaBean.setIdProyecto(tipoTarea.getIdProyecto());
        tipoTareaBean.setTipoTarea(tipoYSubtipo.get(0));
        tipoTareaBean.setArea(tipoTarea.getArea());
        tipoTareaBean.setSubTipo(tipoYSubtipo.get(1));
        tipoTareaBean.setTipoRegistro(tipoTarea.getTipoRegistro());
        tipoTareaBean.setNombreTipoRegistro(tipoTarea.getNombreTipoRegistro());
        tipoTareaBean.setAplicaATodos(tipoTarea.getAplicaATodos());
        tipoTareaBean.setDeshabilitado(tipoTarea.getDeshabilitado());

        servicioTipoTarea.actualiza(tipoTareaBean);
    }
}

return logger;