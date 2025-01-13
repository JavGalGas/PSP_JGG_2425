const fs = require("fs");

fs.readFile("../../../../PSP_JGG_2425/NodeJS/prueba.txt", "utf-8", (error, data) => {
    error ? console.log(error) : console.log(data);
});

console.log("FIN");