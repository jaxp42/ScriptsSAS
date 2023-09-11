import com.onresolve.scriptrunner.runner.customisers.PluginModule
import com.onresolve.scriptrunner.runner.customisers.WithPlugin
import org.sas.cges.jira.app.rest.ConfluenceInteractionsRest
import com.atlassian.jira.project.Project


@WithPlugin("org.sas.cges.jira.app.sas-utilities")
   
@PluginModule
ConfluenceInteractionsRest confluRest


def platformKey = "PLAT1052"

def spaceResponse = confluRest.setupSpaceForProject(platformKey)

if(spaceResponse.status == 200){
    log.info "Created space successfully"     
} else {
    log.error "Could not create space, status: ${spaceResponse.status} entity: ${spaceResponse.entity}"
}