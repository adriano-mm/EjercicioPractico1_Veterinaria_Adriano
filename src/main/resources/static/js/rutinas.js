
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
                "/tipoServicio/eliminar";
            document.getElementById("mensajeEliminar").textContent =
                "¿Deseas eliminar el tipo de servicio " + nombre + "?";
        });
    }
});