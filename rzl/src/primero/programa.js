// programa.js
const readline = require('readline');

// Crear interfaz para leer desde la consola
const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout
});

// Preguntar al usuario
rl.question("Ingresa un número: ", (input) => {
  const num = parseInt(input);

  if (isNaN(num)) {
    console.log("⚠️ Eso no es un número válido.");
  } else if (num % 2 === 0) {
    console.log(`✅ El número ${num} es PAR.`);
  } else {
    console.log(`✅ El número ${num} es IMPAR.`);
  }

  rl.close();
});
