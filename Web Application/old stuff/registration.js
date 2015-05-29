/* On blur */
var regexMail       = new RegExp("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$");
var regexPassword   = new RegExp("^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).{4,8}$");
function validateElement(element) {
    switch(element) {
        case 'email':    if(!document.getElementById('emailreg').value.match(regexMail)){
                                document.getElementById('emailRegErr').innerHTML = "Vul een geldig emailadres in.";
                            } else {
                                document.getElementById('emailRegErr').innerHTML = "";
                            }
                            if(document.getElementById('emailrep').value!==document.getElementById('emailreg').value) {
                                document.getElementById('emailrep').className += " falseInput";
                            }    
                            else {
                                document.getElementById('emailrep').className = "form-control";
                            }
        break;
        case passwordreg:   break;
        case passwordrep:   break;
        case surname:       break;
        case lastname:      break;
        case address:       break;
        case country:       break;
        default:alert('moeite');
    }
}

/* On submit */
function validateElements() {
    var validated       = true;
        /* Null check form */
        if(document.getElementById(emailreg).value=='')     validated = false;
        if(document.getElementById(emailrep).value=='')     validated = false;
        if(document.getElementById(passwordreg).value=='')  validated = false;
        if(document.getElementById(passwordrep).value=='')  validated = false;
        if(document.getElementById(surname).value=='')      validated = false;
        if(document.getElementById(lastname).value=='')     validated = false;
        if(document.getElementById(address).value=='')      validated = false;
        if(document.getElementById(country).value=='')      validated = false;
        /* REGEX Check */
        if(!(document.getElementById(emailreg).value.match(regexMail)))          validated = false;
        if(!(document.getElementById(emailrep).value.match(regexMail)))          validated = false;
        if(!(document.getElementById(passwordreg).value.match(regexPassword)))   validated = false;
        if(!(document.getElementById(passwordrep).value.match(regexPassword)))   validated = false;
        /* Response */
    return validated;
    }
