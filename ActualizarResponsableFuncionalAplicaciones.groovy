import com.atlassian.jira.component.ComponentAccessor
import com.onresolve.scriptrunner.runner.customisers.WithPlugin
import com.onresolve.scriptrunner.runner.customisers.PluginModule
import org.sas.cges.jira.app.service.ServicioPropiedadDeProyecto
import org.sas.cges.jira.app.customfield.CustomFieldManagerSas
import org.sas.cges.jira.app.utils.JQLHelper
import com.atlassian.jira.issue.index.IssueIndexingService
import org.sas.cges.jira.app.utils.Constants
import org.sas.cges.jira.app.utils.PropertyHelper
import com.atlassian.jira.event.type.EventDispatchOption



@WithPlugin("org.sas.cges.jira.app.sas-utilities")

@PluginModule
ServicioPropiedadDeProyecto servicioPropiedadDeProyecto

def scriptLog = []


//components
def projectManager = ComponentAccessor.projectManager
def customFieldManager = ComponentAccessor.getCustomFieldManager()
def userManager = ComponentAccessor.getUserManager()
def issueManager = ComponentAccessor.getIssueManager()
def issueIndexingService = ComponentAccessor.getComponent(IssueIndexingService.class)
def jqlHelper = new JQLHelper(customFieldManager, issueManager);


//needed data
def responsableFuncionalKey = "RESPONSABLE_FUNCIONAL";
def tipoRecurso = "TIPO_RECURSO";
def valorTipoRecurso = "aplicacion";
def responsableFuncionalCF = CustomFieldManagerSas.getCustomFieldObject(Constants.CF_RESPONSABLE_FUNCIONAL);
def procesoaut = userManager.getUserByName(PropertyHelper.getText(Constants.USUARIO_PROCESO_AUTOMATICO));


def proyectos = servicioPropiedadDeProyecto.obtenerIdProyectosContengaValorPropiedad(tipoRecurso, valorTipoRecurso);

for(id in proyectos){
    def proyecto = projectManager.getProjectObj(id);


    if(proyecto != null){
        def responsableFuncional = servicioPropiedadDeProyecto.getValor(id, responsableFuncionalKey);
        def issuesWithOldResponsableFuncional = jqlHelper.obtenerEntidades(procesoaut, 
            "project in (" + proyecto.getKey() + ") and status not in (Closed) and 'Responsable funcional' != " + responsableFuncional);

        scriptLog.add(proyecto.getKey() + ": " + responsableFuncional + "\n");

        for(issue in issuesWithOldResponsableFuncional){
            def mutableIssue = issueManager.getIssueObject(issue.getId());
            def responsableFuncionalUser = userManager.getUserByName(responsableFuncional)
            def cfValue = responsableFuncionalCF.getValue(issue)

            if(cfValue != null && !cfValue.equals(responsableFuncional)){
                mutableIssue.setCustomFieldValue(responsableFuncionalCF, responsableFuncionalUser);
                issueManager.updateIssue(procesoaut, mutableIssue, EventDispatchOption.DO_NOT_DISPATCH, false);
                issueIndexingService.reIndex(issue);
            }
        }
    }
}

return scriptLog;
