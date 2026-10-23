document.addEventListener("DOMContentLoaded", function () {

    const modalTipo = document.getElementById("tipoServicioModal");

    if (modalTipo) {
        modalTipo.addEventListener("show.bs.modal", function (event) {
            const boton = event.relatedTarget;
            const modo = boton.getAttribute("data-modo");
            document.getElementById("tipoServicioModalTitulo").textContent =
                boton.getAttribute("data-titulo");
            document.getElementById("tipoServicioId").value =
                modo === "editar" ? boton.getAttribute("data-id") : "";
            document.getElementById("tipoServicioNombre").value =
                modo === "editar" ? boton.getAttribute("data-nombre") : "";
            document.getElementById("tipoServicioDescripcion").value =
                modo === "editar"
                    ? boton.getAttribute("data-descripcion") || ""
                    : "";
        });
    }

    const modalEliminar = document.getElementById("confirmarEliminarModal");
    if (modalEliminar) {
        modalEliminar.addEventListener("show.bs.modal", function (event) {
            const boton = event.relatedTarget;
            const id = boton.getAttribute("data-id");
            const nombre = boton.getAttribute("data-nombre");
            document.getElementById("idEliminar").value = id;
            document.getElementById("formEliminar").action =
                boton.getAttribute("data-action") || "/tipoServicio/eliminar";
            document.getElementById("mensajeEliminar").textContent =
                "¿Deseas eliminar el registro: " + nombre + "?";
        });
    }
    
    const modalServicio = document.getElementById("servicioModal");
    if (modalServicio) {
        modalServicio.addEventListener("show.bs.modal", function (event) {
            const boton = event.relatedTarget;
            const modo = boton.getAttribute("data-modo");
            document.getElementById("servicioModalTitulo").textContent =
                boton.getAttribute("data-titulo");
            document.getElementById("servicioId").value =
                modo === "editar" ? boton.getAttribute("data-id") : "";
            document.getElementById("servicioNombre").value =
                modo === "editar" ? boton.getAttribute("data-nombre") : "";
            document.getElementById("servicioPrecio").value =
                modo === "editar" ? boton.getAttribute("data-precio") : "";
            document.getElementById("servicioDuracion").value =
                modo === "editar" ? boton.getAttribute("data-duracion") : "";
            document.getElementById("servicioTipo").value =
                modo === "editar" ? boton.getAttribute("data-tipo-id") : "";
        });
    }
});

