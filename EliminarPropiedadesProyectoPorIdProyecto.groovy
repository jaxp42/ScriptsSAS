/**
Script para eliminar las propiedades de proyecto de un proyecto indicado por su ID
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

def idProyecto = 15413;
def log = [];

/***********************************************************************
                            FIN CONFIGURACIÓN
***********************************************************************/


def propiedadesProyecto = servicioPropiedadDeProyecto.obtenerAOPorIdProyectoJira(idProyecto)

for(propiedad in propiedadesProyecto){
    log.push("Eliminando: " + propiedad.getPropertyKey());
    servicioPropiedadDeProyecto.borrar(idProyecto, propiedad.getPropertyKey());
}

return log;