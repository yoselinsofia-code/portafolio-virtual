package com.portafolio.entity.enums;

/**
 * Estado de publicacion de una semana o actividad.
 * ACTIVO/PUBLICADO -> visible en el portafolio publico.
 * INACTIVO/PENDIENTE -> solo visible en el dashboard administrativo.
 */
public enum EstadoRegistro {
    PUBLICADO("Publicado"),
    PENDIENTE("Pendiente");

    private final String etiqueta;

    EstadoRegistro(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }
}
