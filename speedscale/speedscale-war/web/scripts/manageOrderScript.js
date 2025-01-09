function filterTable() {
    // Recupera i valori dei filtri
    var emailFilter = document.getElementById("email-filter").value.toLowerCase();
    var startDateFilter = document.getElementById("start-date-filter").value;
    var endDateFilter = document.getElementById("end-date-filter").value;

    // Recupera tutte le righe della tabella
    var table = document.getElementById("orders-table");
    var tr = table.getElementsByTagName("tr");

    // Itera su tutte le righe della tabella
    for (var i = 1; i < tr.length; i++) {

        // Recupera le celle della riga corrente
        var tdEmail = tr[i].getElementsByTagName("td")[1];
        var tdDate = tr[i].getElementsByTagName("td")[2];

        if (tdEmail || tdDate) {
            var emailValue = tdEmail.textContent || tdEmail.innerText;
            var dateValue = tdDate.textContent || tdDate.innerText;

            // Determina se la riga deve essere mostrata
            var showRow = true;

            if (emailFilter && emailValue.toLowerCase().indexOf(emailFilter) === -1) {
                showRow = false;
            }

            if (startDateFilter && new Date(dateValue) < new Date(startDateFilter)) {
                showRow = false;
            }

            if (endDateFilter && new Date(dateValue) > new Date(endDateFilter)) {
                showRow = false;
            }


            if (showRow) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";

            }
        }
    }
}

function mostraDettagli(idOrdine) {
    var dettagliElement = document.getElementById("dettagli-" + idOrdine);
    if (dettagliElement.style.display === "none") {
        dettagliElement.style.display = "block";
    } else {
        dettagliElement.style.display = "none";
    }
}