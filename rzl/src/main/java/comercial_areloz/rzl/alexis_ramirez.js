function Hello() {
    var mensaje="Hola Mundo";
    console.log(mensaje);
}

function Saludo(invitado) {
    var mensaje="Hola, bienvenido " + invitado;
    console.log(mensaje);
}

function calculo (operacion, num1, num2) {
    var a = Number(num1);
    var b = Number(num2);
    if (isNaN(a) || isNaN(b)) {
        console.log("Error: num1 y num2 deben ser números");
        return;
    }

    if (operacion === "suma") {
        var resultado = a + b;
        console.log("El resultado de la suma es: " + resultado);
    } else if (operacion === "resta") {
        var resultado = a - b;
        console.log("El resultado de la resta es: " + resultado);
    } else {
        console.log("Operación no reconocida: " + operacion);
    }
}

if (typeof module !== 'undefined' && module.exports) {
    module.exports = { Hello: Hello, Saludo: Saludo, calculo: calculo };
}

if (typeof require !== 'undefined' && require.main === module) {
    var args = process.argv.slice(2);

    if (args.length === 0) {
        Hello();
        Saludo('Usuario');
        calculo('suma', 4, 3);
        Despedir('Usuario');
    } else {
        var cmd = args[0];
        if (cmd === 'hello' || cmd === 'hola') {
            Hello();
        } else if (cmd === 'saludo' && args[1]) {
            Saludo(args[1]);
        } else if (cmd === 'calculo' && args.length >= 4) {
            calculo(args[1], args[2], args[3]);
        } else {
            console.log('Comando no reconocido o argumentos insuficientes.');
            console.log('Ejemplos:');
            console.log('  node alexis_ramirez.js');
            console.log('  node alexis_ramirez.js saludo Ana');
            console.log('  node alexis_ramirez.js calculo suma 5 2');
        }
    }
}
function Despedir(invitado) {
    var mensaje="Adiós, hasta luego " + invitado;
    console.log(mensaje);
}