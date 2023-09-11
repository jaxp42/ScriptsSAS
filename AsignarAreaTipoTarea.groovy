/**
	Script para asignar una categoría a tipos de tareas.
*/

import com.onresolve.scriptrunner.runner.customisers.WithPlugin
import com.onresolve.scriptrunner.runner.customisers.PluginModule
import org.sas.cges.jira.app.service.ServicioTipoTarea


@WithPlugin("org.sas.cges.jira.app.sas-utilities")

@PluginModule
ServicioTipoTarea servicioTipoTarea


//Especificar nombre de la categoría
def area = "sistemas" 

//Especificar los tipos de tarea a las que se les va a asignar dicha categoria
def tareasArea = [ 
    "Redes e interconexiones→ Análisis de Tráfico y Monitorización",
    "Redes e interconexiones→ Conectividad local/centros",
    "Redes e interconexiones→ Acondicionamiento de armario",
    "Redes e interconexiones→ Diseño y consulta técnica",
    "Redes e interconexiones→ Gestión de red y segmentación",
    "Redes e interconexiones→ Configuración de electrónica",
    "Redes e interconexiones→ Documentación e informes",
    "Redes e interconexiones→ Direccionamiento IP",
    "Redes e interconexiones→ Instalación/sustitución",
    "Redes e interconexiones – Soporte terceros",
    "Redes e interconexiones→ Otros",
    "Redes e interconexiones – Inventario",
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
    "Infraestructura TI y Software -> Inventario",
    "Infraestructura TI y Software→ Otros",
    "Seguridad TI→ Antivirus",
    "Seguridad TI→ Análisis forense",
    "Seguridad TI→ Securización de entorno",
    "Seguridad TI→ Documentación e Informes",
    "Seguridad TI→ Firewall",
    "Seguridad TI→ Otros",
    "Soporte PU",
    "Infraestructura",
    "Revisión de equipo de usuario"
]


//El siguiente bloque son las tareas de "puesto usuario"

def tareasArea = [
    "Auditorias Almacenes Caché",
    "Calidad del dato",
    "Documentación",
    "Instalación hardware -> Nuevo periférico",
    "Instalación hardware -> Nuevo puesto PC/portátil",
    "Instalacion hardware->Nuevo puesto PC",
    "Instalación hardware -> Nuevo puesto TL",
    "Sustitucion->Sustitución / Instalación de PC",
    "Instalación software → Software base/corporativo",
    "Instalación software → Actualización SO",
    "Instalación software → Despliegue Altiris",
    "Instalacion Software->Instalación de software",
    "Instalacion Software->Actualización de licencias",
    "Inventario -> Inventario de Almacén",
    "Inventario -> Inventario de Equipos",
    "Inventario -> Inventario de Software",
    "LAN/WAN -> Adecentamiento Armario",
    "LAN/WAN -> Parcheo Puntos de Red",
    "LAN/WAN -> Revisión punto de red",
    "LAN/WAN -> Soporte migración Datos",
    "Logística de transporte",
    "Mantenimiento preventivo -> Acondicionamiento de puesto de trabajo",
    "Mantenimiento preventivo -> Mantenimiento preventivo",
    "Mantenimiento preventivo -> Mantenimiento preventivo puesto",
    "Mantenimiento preventivo-> Adecentamiento de puesto de trabajo",
    "Maquetación -> Maquetación / Configuración Tablets",
    "Maquetación -> Maquetación de Equipo",
    "Maquetación -> Maquetación de TL",
    "Maquetación->Maquetación de Portátil",
    "Pilotaje -> Pilotaje",
    "Pruebas -> Pruebas",
    "Pruebas -> Verificación Puesto",
    "Pruebas -> Verificación periférico",
    "Pruebas -> Verificación software",
    "Soporte -> Soporte proveedores externos",
    "Soporte -> Soporte a videoconferencia",
    "Soporte – AT2",
    "Sustitución -> Sustitución / Instalación Periférico",
    "Sustitucion -> Sustitución / Instalación Equipo",
    "Sustitucion -> Sustitución / Instalación TL",
    "Sustitucion->Sustitución / Instalación Portátil",
    "Sustitucion->Sustitución / Instalación Impresora",
    "Taller -> Reparación impresoras",
    "Taller – Reparación PC-TL-Monitor",
    "Taller – Reparación otros",
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
    "Migrar información de un PC a otro",
    "Administración->Tareas administrativas",
    "Comunicaciones",
    "SPU-SAS",
    "Configuración de Software",
    "Actualizacion->Mejora Portátil",
    "Servidores",
    "Actualizacion->Actualizar SO",
    "Maquetación->Maquetación de PC",
    "Actualizacion->Mejora PC",
    "Traslado de equipo de usuario",
    "Instalación de equipo de usuario",
    "Telefonía"
]

def tareasArea = [
    "Respuesta a incidentes",
    "Certificación ENS de los SI",
    "Monitorización y Prevención de Incidentes",
    "Gestión de la Seguridad de la Información",
    "Asesoría y Consultoría",
    "Auditoría",
    "Evaluación de Seguridad de Proyectos",
    "Operación de la Seguridad"
];

def tareasArea = [
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
    "Evaluación de Seguridad de Proyectos",
    "Proveedor",
    "Tarea Migración",
    "Tarea camino crítico"
]


def listaTiposTarea = servicioTipoTarea.getAll()
def listaTiposTareaActualizar = []

if((area != null && area != "") && !tareasArea.isEmpty()){
    for(tipoTarea in listaTiposTarea){
        if(tareasArea.contains(tipoTarea.getTipoTarea())){
            tipoTarea.setArea(area)
            listaTiposTareaActualizar.push(tipoTarea)
        }
    }

    servicioTipoTarea.updateList(listaTiposTareaActualizar)
}
else {
    return "Nombre de categoría o lista de tipos de tarea tienen valores no válidos"
}

return;
