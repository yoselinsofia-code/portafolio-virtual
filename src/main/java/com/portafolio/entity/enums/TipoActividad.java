package com.portafolio.entity.enums;

/**
 * Tipos posibles de una actividad academica dentro de una semana.
 */
public enum TipoActividad {
    EJERCICIO("Ejercicio"),
    TAREA("Tarea"),
    PROYECTO("Proyecto"),
    INVESTIGACION("Investigacion"),
    PRACTICA("Practica"),
    EXAMEN("Examen"),
    EVIDENCIA("Evidencia");

    private final String etiqueta;

    TipoActividad(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }
}
