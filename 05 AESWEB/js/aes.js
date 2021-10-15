/*
var descifrado = CryptoJS.AES.decrypt(cifrado, password);
*/

/*
document.getElementById("demo0").innerHTML = mensaje;
document.getElementById("demo1").innerHTML = cifrado;
document.getElementById("demo2").innerHTML = descifrado;
document.getElementById("demo3").innerHTML = descifrado.toString(CryptoJS.enc.Utf8);
*/

/*
document.getElementById("formulario").addEventListener("submit", function cifrar(){

    //nos aseguramos de que ningun elemento de nuestro form tenga un valor null
    //(sip, incluyendo los botones)
    var btn1 = document.getElementById("cifrar");
    var btnVal1 = "";
    if (btn1) {
        btnVal1 = btn1.value;
    }

    var btn2 = document.getElementById("descargar");
    var btnVal2 = "";
    if (btn2) {
        btnVal2 = btn2.value;
    }

    var cadena = document.getElementById("cadena");
    let cadenaVal = "";
    if (cadena) {
        cadenaVal = cadena.value;
    }
    
    var password = document.getElementById("password");
    let passwordVal = "";
    if (password) {
        passwordVal = password.value;
    }

    
    var cifrado = document.getElementById("cifrado");
    let cifradoVal = "";
    if (cifrado) {
        cifradoVal = cifrado.value;
    }
    
    //variable de error
    let hasError = false;

    //condiciones para validacion sin los required en el form
    if(cadenaVal == null || cadenaVal.length == 0){
        alert("Error: Ingrese su mensaje a cifrar");
        hasError = true;
        //return false;
    }

    if(passwordVal == null || passwordVal.length == 0){
        alert("Error: Tiene que ingresar una clave válida");
        hasError = true;
        //return false;
    }

    if(!document.querySelector('input[name="option"]:checked')) {
        alert('Error: Seleccione un tipo de cifrado');
        hasError = true;
        //return false;
    }

    if(cifradoVal == null || cifrado.length == 0){
        alert("Para realizar la descarga, debe de haber un mensaje en el área de texto")
    }
    

    //condiciones para la longitud de las claves segun el tipo de cifrado
    if(document.getElementById('option1').checked && passwordVal.length != 16){
        alert("Error: La longitud de la clave debe de ser sólo de 16 caracteres");
        hasError = true;

    }else if(document.getElementById('option2').checked && passwordVal.length != 24){
        alert("Error: La longitud de la clave debe de ser sólo de 24 caracteres");
        hasError = true;

    }else if(document.getElementById('option3').checked && passwordVal.length != 32){
        alert("Error: La longitud de la clave debe de ser sólo de 32 caracteres");
        hasError = true;
    }

});
*/

//una vez hechas las validaciones, a partir de aqui comenzamos a utilizar jquery para el cifrado 

$(function(){
    $("#cifrar").on("click", function(event){
        //asi evitamos que se refresque el formulario
        event.preventDefault();
        var cadena = $("#cadena").val();
        var password = $("#password").val();
        //variable de error
        let hasError = false;

        //condiciones para validacion sin los required en el form
        if(cadena == null || cadena.length == 0){
            alert("Error: Ingrese su mensaje a cifrar");
            hasError = true;
            //return false;
        }

        if(password == null || password.length == 0){
            alert("Error: Tiene que ingresar una clave válida");
            hasError = true;
            //return false;
        }

        if(!document.querySelector('input[name="option"]:checked')) {
            alert('Error: Seleccione un tipo de cifrado');
            hasError = true;
            //return false;
        }

        //condiciones para la longitud de las claves segun el tipo de cifrado
        if(document.getElementById('option1').checked && password.length != 16){
            alert("Error: La longitud de la clave debe de ser sólo de 16 caracteres");
            hasError = true;

        }else if(document.getElementById('option2').checked && password.length != 24){
            alert("Error: La longitud de la clave debe de ser sólo de 24 caracteres");
            hasError = true;

        }else if(document.getElementById('option3').checked && password.length != 32){
            alert("Error: La longitud de la clave debe de ser sólo de 32 caracteres");
            hasError = true;
        }

        //cifrado
        var encriptar = CryptoJS.AES.encrypt(cadena, password).toString();
        //todo se pasa a una cadena para manipular la variable
        $("#cifrado").val(encriptar);
        //impresion en la terminal
        console.log(cadena);
        console.log(password);
        console.log(encriptar);
        console.log(encriptar, password);
        
    });
});

/*
document.querySelector('#descargar').onclick = function descargar (data, fileName){
    const a = document.createElement("a");
    $("#cifrado").val() = data,
        blob = new Blob([data], {type: "text/plain;charset=utf-8"}),
        url = window.URL.createObjectURL(blob);
    a.href = url;
    $("#nombretxt").val() = fileName;
    a.click();
    window.URL.revokeObjectURL(url);
}
*/

$(function(){
    $("#descargar").on("click", function(event){
        event.preventDefault();
        //variables para el contenido y nombre del archivo
        var data = $("#cifrado").val();
        var nombretxt = $("#nombretxt").val();

        var element = document.createElement('a');
        
        element.style.display='none';

        element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(data));

        //si el usuario no le asigna un nombre, entonces se descarga con el nombre 'download-default' xD
        element.setAttribute('download-default', nombretxt);
        document.body.appendChild(element);

        element.click();

        document.body.removeChild(element);
            
    });
});


/*
function descargar(){

    var cadena = document.getElementById("cadena");
    var cadenaVal = "";
    if (cadena) {
        cadenaVal = cadena.value;
    }
    
    var password = document.getElementById("password");
    var passwordVal = "";
    if (password) {
        passwordVal = password.value;
    }

    var cifrado = CryptoJS.AES.encrypt(cadena, password);

    var blob = new Blob([cifrado], {type: "text/plain;charset=utf-8"});
    saveAs(blob, "mensajecifrado.txt");
}
*/

/*
document.querySelector('#descargar').onclick = function descargar (cifrado, fileName){
    const a = document.createElement("a");

    var cadena = document.getElementById("cadena");
    let cadenaVal = "";
    if (cadena) {
        cadenaVal = cadena.value;
    }
    
    var password = document.getElementById("password");
    let passwordVal = "";
    if (password) {
        passwordVal = password.value;
    }

    var cifrado = CryptoJS.AES.encrypt(cadena, password);

    var blob = new Blob([cifrado], {type: "text/plain;charset=utf-8"});
    var url = window.URL.createObjectURL(blob);

    a.href = url;
    a.download = fileName;
    a.click();
    window.URL.revokeObjectURL(url);
}*/