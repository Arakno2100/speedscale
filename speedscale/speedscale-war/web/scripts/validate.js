const nameOrLastnamePattern = /^[A-Za-z]+$/;
const emailPattern = /^\S+@\S+\.\S+$/;
const phonePattern = /^([0-9]{10})$/;
const addressPattern = /^(Via|Corso|Piazza|Viale|Largo|Borgo)\s+[A-Za-zàèéìòùÀÈÉÌÒÙ]+\s*([A-Za-zàèéìòùÀÈÉÌÒÙ]*\s*)*$/;
const capPattern = /^([0-9]{5})$/
const provPattern = /^[A-Z]{2}$/

const nameErrorMessage = "Il nome deve avere solo lettere";
const lastnameErrorMessage = "Il cognome deve avere solo lettere";
const cittaError = "La citt&agrave deve avere solo lettere"
const nazioneError = "La nazione deve avere solo lettere"
const emailErrorMessage = "La mail dovrebbe essere del tipo username@domain.ext";
const numberErrorMessage = "Un numero valido ha 10 cifre";
const addressError = "Indirizzo non valido deve Iniziare con Via/Corso... ";
const capError = "Un cap è fatto solo di 5 cifre";
const provError = "Un provincia &egrave formata solo da due lettere maiuscole come NA";
let count = 1;

function validateFormElem(formElem, pattern, span, message) {
    if(formElem.value.match(pattern)){
        formElem.classList.remove("error");
        span.style.color = "black";
        span.innerHTML = "";
        return true;
    }
    formElem.classList.add("error");
    span.innerHTML = message;
    span.style.color = "red";
    return false;
}
function validateAllAddress(str , btn){
    let valid =  validateAddress(str , btn) && validateCitta(str ,btn) && validateProv(str, btn) && validateCap(str, btn) && validateNaz(str,btn);
    const butt = document.getElementById(btn);
    butt.disabled = !valid;
}
function validateReg(str, btn){
    let valid = validateNome(str) && validateCognome(str) && validateMail(str);
    const butt = document.getElementById(btn);
    butt.disabled = !valid;
    console.log("Festa");
}
function  validateAllName(str  , btn){
    let valid = validateNome(str) && validateCognome(str) && validatePhone(str);
    const butt = document.getElementById(btn);
    butt.disabled = !valid;
}
function validateNome(str) {
    let valid = true;
    let form = document.getElementById(str);

    let spanName = document.getElementById("errorName");
    if (!validateFormElem(form.nome, nameOrLastnamePattern, spanName, nameErrorMessage)) {
        valid = false;
    }
    return valid;
}
function validateCognome(str) {
    let valid = true;
    let form = document.getElementById(str);

    let spanLastname = document.getElementById("errorLastname");
    if (!validateFormElem(form.cognome, nameOrLastnamePattern, spanLastname, lastnameErrorMessage)) {
        valid = false;
    }
    return valid;
}
function validateMail(str) {
    let valid = true;
    let form = document.getElementById(str);

    let spanEmail = document.getElementById("errorEmail");
    if (!validateFormElem(form.email, emailPattern, spanEmail, emailErrorMessage)){
        valid = false;
    }
    return valid;
}
function validatePhone(str) {
    let valid = true;
    let form = document.getElementById(str);
    let spanPhone = document.getElementById("errorPhone");
    let phoneElement = document.getElementById("phone");
    let phoneValue = phoneElement.value.toString();

    // Verifica se il campo del telefono è vuoto o se non passa la validazione del pattern
    if (phoneValue!=="" && phoneValue !== "null" && !validateFormElem(phoneElement, phonePattern, spanPhone, numberErrorMessage)) {
        valid = false;
    }
    return valid;
}
function validateAddress(str) {
    let valid = true;
    let form = document.getElementById(str);

    let spanName = document.getElementById("errorAddress");
    if (!validateFormElem(form.via, addressPattern, spanName, addressError)) {
        valid = false;
    }
    return valid;
}
function validateCitta(str) {
    let valid = true;
    let form = document.getElementById(str);

    let spanName = document.getElementById("errorCitta");
    if (!validateFormElem(form.citta, nameOrLastnamePattern, spanName, cittaError)) {
        valid = false;
    }
    return valid;
}
function validateProv(str) {
    let valid = true;
    let form = document.getElementById(str);


    let spanName = document.getElementById("errorProv");
    if (!validateFormElem(form.provincia, provPattern, spanName, provError)) {
        valid = false;
    }

    return valid;
}
function validateCap(str) {
    let valid = true;
    let form = document.getElementById(str);

    let spanName = document.getElementById("errorCap");
    if (!validateFormElem(form.cap, capPattern, spanName, capError)) {
        valid = false;

    }

    return valid;
}
function validateNaz(str) {
    let valid = true;
    let form = document.getElementById(str);


    let spanName = document.getElementById("errorNaz");
    if (!validateFormElem(form.nazione, nameOrLastnamePattern, spanName, nazioneError)) {
        valid = false;

    }
    return valid;

}
function  validatePrice(id , spanName){


    const input = document.getElementById(id).value;
    if(Number.parseFloat(input) <=  0)
        document.getElementById(spanName).innerHTML = "Inserisci un numero maggiore di 0"
    else
        document.getElementById(spanName).innerHTML = ""


    return Number.parseFloat(input) <=  0;

}
function validateQuantPrice(id1 , id2 , btn )
{

    const input1 = document.getElementById(id1).value;
    const input2 = document.getElementById(id2).value;
    const butt = document.getElementById(btn)
    let val = Number.parseFloat(input1) <=  0 || Number.parseFloat(input2) <=  0;
    butt.disabled = val;


}
function removeSpecial (){
    document.getElementById("nome").value = escapeHtml(   document.getElementById("nome").value );
    document.getElementById("descrizione").value = escapeHtml(   document.getElementById("descrizione").value );
    document.getElementById("isbn").value = escapeHtml(   document.getElementById("isbn").value );
    document.getElementById("prezzo").value = escapeHtml(   document.getElementById("prezzo").value );
    document.getElementById("quantita").value = escapeHtml(   document.getElementById("quantita").value );

}
function escapeHtml(str) {
    const entityMap = {
        '&': '&amp;',
        '<': '&lt;',
        '>': '&gt;',
        '"': '&quot;',
        "'": '&#39;',
        '/': '&#x2F;',
        '`': '&#x60;',
        '=': '&#x3D;'
    };

    return String(str).replace(/[&<>"'`=\/]/g, function (s) {
        return entityMap[s];
    });
}

