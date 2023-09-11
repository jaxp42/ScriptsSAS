/**
Script para asignar un área a un tipo de tarea según su tipo de registro
*/

import com.onresolve.scriptrunner.runner.customisers.WithPlugin
import com.onresolve.scriptrunner.runner.customisers.PluginModule
import org.sas.cges.jira.app.service.ServicioTipoTarea


@WithPlugin("org.sas.cges.jira.app.sas-utilities")

@PluginModule
ServicioTipoTarea servicioTipoTarea

def area = "seguridad";

//Introducir id de los tipos de registro a los que se les quiere asignar el tipo de tarea
def tiposRegistro = [
    "12200", //tarea seguridad
    "12201" //subtarea seguridad
];

def listaTiposTarea = servicioTipoTarea.getAll();
def listaTareasActualizar = [];

for(tipoTarea in listaTiposTarea){
    def updateTarea = false;
    for(tipoRegistro in tipoTarea.getTipoRegistro().split(",")){
        if(tiposRegistro.contains(tipoRegistro)){
            tipoTarea.setArea(area);
            updateTarea = true;
        }

        if(updateTarea){
            listaTareasActualizar.push(tipoTarea);
        }
    }
}

servicioTipoTarea.updateList(listaTareasActualizar);
return;