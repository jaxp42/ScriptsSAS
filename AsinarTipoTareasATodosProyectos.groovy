/**
    Script para asignar una lista de tipos de tareas a todos los proyectos.
    ¡¡¡ESTE SCRIPT AFECTA A TODOS LOS PROYECTOS. DEPENDIENDO DEL NÚMERO DE TAREAS PARA AÑADIR PUEDE TARDAR BASTANTE TIEMPO.!!!
*/

/**

Script para asignar una lista de tipos de tarea a un proyecto.

*/

import com.atlassian.jira.component.ComponentAccessor
import com.onresolve.scriptrunner.runner.customisers.WithPlugin
import com.onresolve.scriptrunner.runner.customisers.PluginModule
import org.sas.cges.jira.app.service.ServicioTipoTarea
import org.sas.cges.jira.app.bean.TipoTareaBean

@WithPlugin("org.sas.cges.jira.app.sas-utilities")

@PluginModule
ServicioTipoTarea servicioTipoTarea

def projectManager = ComponentAccessor.projectManager

//Tipos de registro que se van a asignar a los tipos de tarea
def tiposRegistro = [
    "12100", //Tarea soporte provincial
    "12700" //Subtarea soporte provincial
]

//Lista de los tipos de tarea que se van a crear en el proyecto
def listaTiposTarea = [
    "Redes e interconexiones→ Análisis de Tráfico y Monitorización",
    "Redes e interconexiones→ Conectividad local/centros",
    "Redes e interconexiones→ Acondicionamiento de armario",
    "Redes e interconexiones→ Diseño y consulta técnica",
    "Redes e interconexiones→ Gestión de red y segmentación",
    "Redes e interconexiones→ Configuración de electrónica",
    "Redes e interconexiones→ Documentación e informes",
    "Redes e interconexiones→ Direccionamiento IP",
    "Redes e interconexiones→ Otros",
    "Infraestructura TI y Software→ Revisión CPD",
    "Infraestructura TI y Software→ Infraestructura hiperconvergencia",
    "Infraestructura TI y Software→ Infraestructura virtualización",
    "Infraestructura TI y Software→ Servidores Windows/Linux",
    "Infraestructura TI y Software→ Almacenamiento",
    "Infraestructura TI y Software→ Copias de Seguridad",
    "Infraestructura TI y Software→ Documentación e informes",
    "Infraestructura TI y Software→ Bases de Datos",
    "Infraestructura TI y Software→  Middleware",
    "Infraestructura TI y Software→ Soporte puesta en producción/migración",
    "Infraestructura TI y Software→ Directorio Activo y servicios asociados",
    "Infraestructura TI y Software→ Otros",
    "Seguridad TI→ Antivirus",
    "Seguridad TI→ Análisis forense",
    "Seguridad TI→ Securización de entorno",
    "Seguridad TI→ Documentación e Informes",
    "Seguridad TI→ Firewall",
    "Seguridad TI→ Otros",
    "Instalación hardware -> Nuevo periférico",
    "Instalación hardware -> Nuevo puesto PC/portátil",
    "Instalación hardware -> Nuevo puesto TL",
    "Instalación software → Software base/corporativo",
    "Instalación software → Actualización SO",
    "Instalación software → Despliegue Altiris",
    "Inventario -> Inventario de Almacén",
    "Inventario -> Inventario de Equipos",
    "Inventario -> Inventario de Software",
    "LAN/WAN -> Adecentamiento Armario",
    "LAN/WAN -> Parcheo Puntos de Red",
    "LAN/WAN -> Revisión punto de red",
    "LAN/WAN -> Soporte migración Datos",
    "Mantenimiento preventivo -> Acondicionamiento de puesto de trabajo",
    "Mantenimiento preventivo -> Mantenimiento preventivo",
    "Mantenimiento preventivo -> Mantenimiento preventivo puesto",
    "Maquetación -> Maquetación / Configuración Tablets",
    "Maquetación -> Maquetación de Equipo",
    "Maquetación -> Maquetación de TL",
    "Pilotaje -> Pilotaje",
    "Pruebas -> Pruebas",
    "Pruebas -> Verificación Puesto",
    "Pruebas -> Verificación periférico",
    "Pruebas -> Verificación software",
    "Soporte -> Soporte proveedores externos",
    "Soporte -> Soporte a videoconferencia",
    "Sustitución -> Sustitución / Instalación Periférico",
    "Sustitucion -> Sustitución / Instalación Equipo",
    "Sustitucion -> Sustitución / Instalación TL",
    "Taller -> Reparación impresoras",
    "Tareas Almacén -> recepción de equipamiento",
    "Tareas Almacén -> Adecentamiento Almacen / Taller",
    "Tareas Almacen -> Retirada de equipamiento",
    "Tareas Almacen -> Tareas de Almacén",
    "Traslado -> Traslado equipamiento informático",
    "Telefonía -> Instalación teléfono",
    "Telefonía -> Comprobación teléfono",
    "Gestión AV → Gestión de clientes de la consola SEP",
    "Gestión AV → Eliminar malware",
    "Salvar información PC",
    "Migrar información de un PC a otro"
]

def projects = projectManager.getProjectObjects();
def log = [];

for(project in projects){
    try{
        for(tarea in listaTiposTarea){
            def tipoTareaBean = new TipoTareaBean();
            tipoTareaBean.setIdProyecto(project.getId());
            tipoTareaBean.setTipoTarea(tarea);
            tipoTareaBean.setTipoRegistro("12100, 12700");
            tipoTareaBean.setAplicaATodos(false);
            tipoTareaBean.setDeshabilitado(false);
            
            servicioTipoTarea.crea(tipoTareaBean);
        }
    }
    catch(Exception e){
        log.push("No se ha podido eliminar los datos del proyecto " + project.getKey());
    }

}

return log;
