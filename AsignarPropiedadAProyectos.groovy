import com.atlassian.jira.component.ComponentAccessor
import com.onresolve.scriptrunner.runner.customisers.WithPlugin
import com.onresolve.scriptrunner.runner.customisers.PluginModule
import org.sas.cges.jira.app.service.ServicioPropiedadDeProyecto
import org.sas.cges.jira.app.bean.TipoTareaBean

@WithPlugin("org.sas.cges.jira.app.sas-utilities")

@PluginModule
ServicioPropiedadDeProyecto servicioPropiedadDeProyecto

def projectManager = ComponentAccessor.projectManager;


def listaProyectos = [
"TRIA",
"URGCE"
]

def propiedad = "CLIENTE"
def valor = "SUBDIRECCIÓN DE ACCESIBILIDAD Y CONTINUIDAD ASISTENCIAL"

def log = [];

for(key in listaProyectos){
    try{
        def project = projectManager.getProjectObjByKey(key)
        servicioPropiedadDeProyecto.crearOActualizar(project.getId(), propiedad, valor)
    }catch(Exception e){
        log.push("Fallo: " + key)
    }
}

return log;
