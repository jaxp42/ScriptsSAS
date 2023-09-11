import com.atlassian.jira.component.ComponentAccessor
import com.onresolve.scriptrunner.db.DatabaseUtil
import com.atlassian.jira.bc.user.search.UserSearchService
import com.atlassian.jira.bc.user.UserService
import org.sas.cges.jira.app.service.ServicioPropiedadDeProyecto
import com.onresolve.scriptrunner.runner.customisers.WithPlugin
import com.onresolve.scriptrunner.runner.customisers.PluginModule

@WithPlugin("org.sas.cges.jira.app.sas-utilities")

@PluginModule
ServicioPropiedadDeProyecto servicioPropiedadDeProyecto

def userManager = ComponentAccessor.userManager
def userService = ComponentAccessor.getComponent(UserService)

def usernames = ["AguilarLuisM98F"] //nombres de usuarios a desactivar
def results = ""

for(username in usernames){
    def user = userManager.getUserByName(username)

    results += user.getName()
    // Buscar el usuario correspondiente en base de datos, su JIRAUSER, esto lo podemos hacer con la siguiente consulta: select * from APP_USER where LOWER_USER_NAME like '%username en minusculas%';


    // Realizar la siguiente búsqueda SQL, siendo USERNAME el JIRAUSER correspondiente, por precaución, realizarla también con el username normal, en caso de que ambos sean distintos para asegurar, select * from ao_0da69f_propiedad_proyecto WHERE property_value like '%username%';
    return servicioPropiedadDeProyecto.obtenerIdProyectosSegunValorPropiedad("", username)

    // Vemos que proyectos son los afectados, es decir, proyectos en los que este usuario esté informado en alguna propiedad. Podemos comprobarlo con la siguiente JQL: select * from PROJECT where ID in (13927, 13400); (Ejemplo de lista de proyectos afectados, en la lista iría cada uno de los IDs que nos aparecieron en la búsqueda anterior, esto nos daría los datos de los proyectos afectados)


    // Nos vamos a JIRA, y los modificamos todos, de manera que el usuario no aparezca en ninguna de las propiedades, esto incluye cualquier propiedad del tipo "Responsable de X", si no nos dijeran nada, habría que preguntar a Olimpia quién es el nuevo usuario que toma cargo de esa responsabilidad.


    // Comprobamos en BDD que el usuario deshabilitado ya no está en ninguna de las propiedades


    // Debemos comprar tambien en BDD que el usuario no está siendo usado como usuario genérico por algún "Miembro de proyecto", para comprobarlo, ejecutamos la siguiente consulta SQL: select * from AO_0DA69F_MIEMBRO_PROYECTO where usuario_generico like '%username%', donde username es el DMSAS o nombre de usuario del usuario a dar de baja


    // Los resultados que obtengamos, serán los miembros de proyecto que hay que deshabilitar (no eliminar, para que así se quede siempre el histórico) en el proyecto correspondiente (hay una columna que representa el ID del proyecto)


    // Si tuviera registros abiertos o en vuelo, habría que ver qué usuario se hace cargo de ellos, comentarlo con Olimpia si es el caso, y no se ha otorgado información al respecto. (Por ejemplo, no se ha dicho nada y el usuario tiene tareas en resolución o paralizadas, o lo que fuera). Una vez se sepa qué hacer con los registros, reprocesarlos.


    // Una vez hecho esto, nos vamos a la administración de usuarios, buscamos al usuario a deshabilitar, y editamos, el check de activo lo dejamos vacío, y guardamos. Si nos dijera que es líder de algún proyecto (no debería ya que hemos modificado antes las propiedades, pero puede quedarse por algún error humano un líder mal informado) comentarlo con Olimpia si no se sabe o poner como jefe al responsable de producto (o proyecto si es de GP) actual.
}

return results