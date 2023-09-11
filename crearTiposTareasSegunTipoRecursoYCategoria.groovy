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
def TIPO_RECURSO = "gestion proyectos negocio"
def CATEGORIA = "PROYECTOS ÁREA SOLUCIONES CORPORATIVAS Y SOCIEDAD DIGITAL"

def areas = "gestión" //nombres separados por coma

def tiposRegistro = "11301, 11304, 11303, 11306" //ids separados por coma

//Lista de los tipos de tarea que se van a eliminar de todos los proyectos
def listaTiposTareas = [Decision: ["Directiva", "Operativa"], Hito: ["Directivo", "Operativo"], Riesgo: ["Directivo", "Operativo"]]

/***********************************************************************
                            FIN CONFIGURACIÓN
***********************************************************************/

def log = new StringBuilder();

def proyectos = servicioPropiedadDeProyecto.obtenerIdProyectosContengaValorPropiedad(KEY_TIPO_RECURSO, TIPO_RECURSO)

log.append(proyectos.size)

for(idProyecto in proyectos){
    try{
        def proyecto = projectManager.getProjectObj(idProyecto)
        def categoriaProyecto = proyecto.getProjectCategory()

        if(CATEGORIA.isBlank() || CATEGORIA.equals(categoriaProyecto.getName())){
            log.append("<p>")
            log.append("Agregando tareas al proyecto: " + idProyecto)
            log.append("</p>")

            listaTiposTareas.eachWithIndex{key, value, index ->
                for(subtipo in value){
                    def tipoTareaBean = new TipoTareaBean()

                    tipoTareaBean.setIdProyecto(idProyecto)
                    tipoTareaBean.setArea(areas)
                    tipoTareaBean.setTipoTarea(key)
                    tipoTareaBean.setSubTipo(subtipo)
                    tipoTareaBean.setTipoRegistro(tiposRegistro)
                    tipoTareaBean.setAplicaATodos(false)
                    tipoTareaBean.setDeshabilitado(false)
                    
                    servicioTipoTarea.crea(tipoTareaBean)
                }
            }
        }
    }catch(Exception e){
        log.append('<p style="color: red">')
        log.append('Error con el proyecto: ' + idProyecto)
        log.append(e)
        log.append("</p>")
    }
}

return log;

