/**
Script para asignar tipos de tarea despues de un alta de proyecto
*/

import com.atlassian.jira.component.ComponentAccessor
import com.onresolve.scriptrunner.runner.customisers.WithPlugin
import com.onresolve.scriptrunner.runner.customisers.PluginModule
import org.sas.cges.jira.app.service.ServicioTipoTarea
import org.sas.cges.jira.app.bean.TipoTareaBean


@WithPlugin("org.sas.cges.jira.app.sas-utilities")
@PluginModule

ServicioTipoTarea servicioTipoTarea
def projectManager = ComponentAccessor.projectManager;


/********************************************************************************
                                    CONFIGURACIÓN
********************************************************************************/
//Proyecto donde se van a crear los tipos de tarea
def projectId = 15113; //proyecto ejemplo GRPERSONA
def categoriaProyecto = projectManager.getProjectCategory(projectId).getId();

//Areas
def sistemas = "sistemas";
def puestoUsuario = "puesto usuario";
def seguridad = "seguridad";

/*Si el proyecto es de granada utilizar la segunda opción. Si el proyecto es de sevilla utilizar la segunda opción solo para los tipos de tareas de Riesgo*/
def gestionProyectosIdi = "gestión,proyectos,i+d+i";
def gestionImplantacionProyectosIdi = "gestión,implantación/proyectos,i+d+i";


//Comentar el bloque del entorno que no corresponda.
//PRE
//id's de los tipos de registro según el entorno. 
def idTareaSoporteProvincial = 12100;
def idSubtareaSoporteProvincial = 12700;
def idTareaSeguridad = 12200;
def idSubtareaSeguridad = 12201;
def idTareaGeneral = 11709;
def idSubtareaGeneral = 11800;
//id's de los tipos de categoria
def categoriaGranada = 11294;
def categoriaSevilla = 11287;

//PRO
//id's de los tipos de registro según el entorno. 
def idTareaSoporteProvincial = 11400;
def idSubtareaSoporteProvincial = 11600;
def idTareaSeguridad = 11506;
def idSubtareaSeguridad = 11507;
def idTareaGeneral = 11301;
def idSubtareaGeneral = 11304;
//id's de los tipos de categoria
def categoriaGranada = 10908;
def categoriaSevilla = 10901;


//tipos de tarea del area "sistemas"
def tareasAreaSistemas = [
    "Redes e interconexiones→ Análisis de Tráfico y Monitorización",
    "Redes e interconexiones→ Conectividad local/centros",
    "Redes e interconexiones→ Acondicionamiento de armario",
    "Redes e interconexiones→ Diseño y consulta técnica",
    "Redes e interconexiones→ Gestión de red y segmentación",
    "Redes e interconexiones→ Configuración de electrónica",
    "Redes e interconexiones→ Documentación e informes",
    "Redes e interconexiones→ Direccionamiento IP",
    "Redes e interconexiones→ Instalación/sustitución",
    "Redes e interconexiones → Soporte terceros",
    "Redes e interconexiones→ Otros",
    "Redes e interconexiones → Inventario",
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
    "Infraestructura TI y Software → Inventario",
    "Infraestructura TI y Software→ Otros",
    "Seguridad TI→ Antivirus",
    "Seguridad TI→ Análisis forense",
    "Seguridad TI→ Securización de entorno",
    "Seguridad TI→ Documentación e Informes",
    "Seguridad TI→ Firewall",
    "Seguridad TI→ Otros",
    "Soporte PU"
];

//tipos de tarea del area "puesto usuario"
def tareasAreaPuestoUsuario = [
    "Auditorias Almacenes Caché",
    "Calidad del dato",
    "Documentación",
    "Instalación hardware → Nuevo periférico",
    "Instalación hardware → Nuevo puesto PC/portátil",
    "Instalación hardware → Nuevo puesto TL",
    "Instalación software → Software base/corporativo",
    "Instalación software → Actualización SO",
    "Instalación software → Despliegue Altiris",
    "Inventario → Inventario de Almacén",
    "Inventario → Inventario de Equipos",
    "Inventario → Inventario de Software",
    "LAN/WAN → Adecentamiento Armario",
    "LAN/WAN → Parcheo Puntos de Red",
    "LAN/WAN → Revisión punto de red",
    "LAN/WAN → Soporte migración Datos",
    "Logística de transporte",
    "Mantenimiento preventivo → Acondicionamiento de puesto de trabajo",
    "Mantenimiento preventivo → Mantenimiento preventivo",
    "Mantenimiento preventivo → Mantenimiento preventivo puesto",
    "Maquetación → Maquetación / Configuración Tablets",
    "Maquetación → Maquetación de Equipo",
    "Maquetación → Maquetación de TL",
    "Pilotaje → Pilotaje",
    "Pruebas → Pruebas",
    "Pruebas → Verificación Puesto",
    "Pruebas → Verificación periférico",
    "Pruebas → Verificación software",
    "Soporte → Soporte proveedores externos",
    "Soporte → Soporte a videoconferencia",
    "Soporte → AT2",
    "Sustitución → Sustitución / Instalación Periférico",
    "Sustitucion → Sustitución / Instalación Equipo",
    "Sustitucion → Sustitución / Instalación TL",
    "Taller → Reparación impresoras",
    "Taller → Reparación PC-TL-Monitor",
    "Taller → Reparación otros",
    "Tareas Almacén → recepción de equipamiento",
    "Tareas Almacén → Adecentamiento Almacen / Taller",
    "Tareas Almacen → Retirada de equipamiento",
    "Tareas Almacen → Tareas de Almacén",
    "Traslado → Traslado equipamiento informático",
    "Telefonía → Instalación teléfono",
    "Telefonía → Comprobación teléfono",
    "Gestión AV → Gestión de clientes de la consola SEP",
    "Gestión AV → Eliminar malware",
    "Salvar información PC",
    "Migrar información de un PC a otro"
];

def tareasAreaSeguridad = [
    "Respuesta a incidentes",
    "Certificación ENS de los SI",
    "Monitorización y Prevención de Incidentes",
    "Gestión de la Seguridad de la Información",
    "Asesoría y Consultoría",
    "Auditoría",
    "Evaluación de Seguridad de Proyectos",
    "Operación de la Seguridad",
    "Formación, concienciación y comunicación"
];

def tareasAreaGestion = [
    "Responsable",
    "Subida a PRO",
    "LINEAS TRANSFORMACIÓN",
    "Tareas de sistemas",
    "Tarea del piloto",
    "Tarea Toma de datos",
    "Tarea previa",
    "Gestión",
    "Servicio de mejora continua",
    "Centralizado",
    "OTRAS",
    "IMPLANTACIONES",
    "Desarrollo funcionalidades",
    "Pruebas técnicas",
    "CONFLUENCE",
    "Formación",
    "Gestión de Proyecto",
    "Tarea al proveedor",
    "Requisito",
    "SERVICIOS CGES",
    "GESTOR NOTIFICACIONES",
    "Servicio de testing funcional",
    "Servicio de Verificación de Sprint",
    "MTI-SSHH",
    "Implantación",
    "Tarea Validación",
    "Fase 1",
    "MI GESTOR SERVICIOS",
    "Servicio de verificación de la instalabilidad (FARO)",
    "ASTERISK",
    "Servicio de testing de accesibilidad",
    "Producto",
    "NUEVA WEB TÉCNICA",
    "Tarea Despliegue",
    "Apoyo a Eventos",
    "Tarea transversal",
    "Tarea de Coordinación",
    "Agile",
    "Técnica",
    "Servicio de gestión de entornos",
    "OCS",
    "Servicio de testing temprano",
    "MI CENTRO SERVICIOS",
    "OTI",
    "Integración Continua",
    "Funcional",
    "Riesgo",
    "Fase 2",
    "Servicio de Soporte",
    "JIRA",
    "Distribuido",
    "Integración OTI",
    "Validación Subdirección",
    "Hospital",
    "Servicio de Usabilidad",
    "Proveedor",
    "Tarea Migración",
    "Tarea camino crítico"
];

/********************************************************************************
                                FIN CONFIGURACIÓN
********************************************************************************/



//Se añaden los tipos de tarea del area "sistemas"
for(tarea in tareasAreaSistemas){
    def tipo;
    def subtipo;
    if(tarea.contains("→")){
        def tipoYSubtipo = tarea.split("→");
        tipo = tipoYSubtipo[0].trim();
        subtipo = tipoYSubtipo[1].trim();
    }
    else{
        tipo = tarea;
        subtipo = null;
    }
    def tipoTareaBean = new TipoTareaBean();
    tipoTareaBean.setIdProyecto(projectId);
    tipoTareaBean.setArea(sistemas);
    tipoTareaBean.setTipoTarea(tipo);
    tipoTareaBean.setSubTipo(subtipo);
    tipoTareaBean.setTipoRegistro(idTareaSoporteProvincial + ", " + idSubtareaSoporteProvincial);
    tipoTareaBean.setAplicaATodos(false);
    tipoTareaBean.setDeshabilitado(false);
    servicioTipoTarea.crea(tipoTareaBean);
}

//Se añaden los tipos de tarea del area "puesto usuario"
for(tarea in tareasAreaPuestoUsuario){
    def tipo;
    def subtipo;
    if(tarea.contains("→")){
        def tipoYSubtipo = tarea.split("→");
        tipo = tipoYSubtipo[0].trim();
        subtipo = tipoYSubtipo[1].trim();
    }
    else{
        tipo = tarea;
        subtipo = null;
    }

    def tipoTareaBean = new TipoTareaBean();
    tipoTareaBean.setIdProyecto(projectId);
    tipoTareaBean.setArea(puestoUsuario);
    tipoTareaBean.setTipoTarea(tipo);
    tipoTareaBean.setSubTipo(subtipo);
    tipoTareaBean.setTipoRegistro(idTareaSoporteProvincial + ", " + idSubtareaSoporteProvincial);
    tipoTareaBean.setAplicaATodos(false);
    tipoTareaBean.setDeshabilitado(false);
    servicioTipoTarea.crea(tipoTareaBean);
}

//Se añaden los tipos de tarea del area "seguridad"
for(tarea in tareasAreaSeguridad){
    def tipoTareaBean = new TipoTareaBean();
    tipoTareaBean.setIdProyecto(projectId);
    tipoTareaBean.setArea(seguridad);
    tipoTareaBean.setTipoTarea(tarea);
    tipoTareaBean.setTipoRegistro(idTareaSeguridad + ", " + idSubtareaSeguridad);
    tipoTareaBean.setAplicaATodos(false);
    tipoTareaBean.setDeshabilitado(false);
    servicioTipoTarea.crea(tipoTareaBean);
}

//Se añaden los tipos de tarea del area "gestión"
for(tarea in tareasAreaGestion){
    def tipoTareaBean = new TipoTareaBean();
    tipoTareaBean.setIdProyecto(projectId);
    if(categoriaProyecto == categoriaGranada || 
    (categoriaProyecto == categoriaSevilla && tarea == "Riesgo")){
        tipoTareaBean.setArea(gestionImplantacionProyectosIdi);
    }
    else{
        tipoTareaBean.setArea(gestionProyectosIdi);
    }
    tipoTareaBean.setTipoTarea(tarea);
    tipoTareaBean.setTipoRegistro(idTareaGeneral + ", " + idSubtareaGeneral);
    tipoTareaBean.setAplicaATodos(false);
    tipoTareaBean.setDeshabilitado(false);
    servicioTipoTarea.crea(tipoTareaBean);
}