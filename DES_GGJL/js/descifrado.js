/*
     Leer y mostrar contenido inmediatamente
*/

 function mostrarContenido(contenido) {
    const elemento = document.getElementById('contenido-archivo');
    elemento.innerHTML = contenido;
  }

function leerArchivo(e) {
  const archivo = e.target.files[0];
  if (!archivo) {
    return;
  }
  const reader = new FileReader();
  reader.onload = function(e) {
    const contenido = e.target.result;
    mostrarContenido(contenido);
  };
  reader.readAsText(archivo);
}

document.querySelector('#archivo-d')
  .addEventListener('change', leerArchivo, false);

  

//inicio del descifrado
document.querySelector('#decode').onclick = function(){
    const data = document.querySelector('#texto-clave').value;
    const name = '.descifrado';
    const buffer = Buffer.from(txt, 'base64')
    descargar(data, name);
    const decrypted = crypto.privateDecrypt(
        {
            key: clave.toString(),
            passphrase: '',
        },
            buffer,
        )
            return decrypted.toString('utf8');
    }

    document.querySelector('#decode').onclick = function descargar (data, fileName){
    const a = document.createElement("a");
    const contenido = data,
        blob = new Blob([contenido], {type: "octet/stream"}),
        url = window.URL.createObjectURL(blob);
    a.href = url;
    a.download = fileName;
    a.click();
    window.URL.revokeObjectURL(url);
}