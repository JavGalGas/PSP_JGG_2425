const http = require('http');
const { URL } = require('url');
const path = require('path');
const fs = require('fs');
const myModule = require('./modules/my_module.js');
const colorConvert = require('color-convert'); // Requiere: npm install color-convert


function hexToRgb(hex) {
    const r = parseInt(hex.slice(1, 3), 16);
    const g = parseInt(hex.slice(3, 5), 16);
    const b = parseInt(hex.slice(5, 7), 16);
    return `rgb(${r}, ${g}, ${b})`;
}

function rgbLuminance(r, g, b) {
    return (0.2126 * r + 0.7152 * g + 0.0722 * b) / 255;
}

function nombreA_Rgb(nombreColor) {
    const tempElement = document.createElement('div');
    tempElement.style.color = nombreColor;
    document.body.appendChild(tempElement);
    const colorRgb = window.getComputedStyle(tempElement).color;
    document.body.removeChild(tempElement);
    return colorRgb;
}

function calcularContraste(colorFondo, colorLetra) {
    let rgbFondo, rgbLetra;

    if (colorFondo.startsWith('#')) {
        rgbFondo = hexToRgb(colorFondo);
    } else if (colorFondo.startsWith('rgb')) {
        rgbFondo = colorFondo;
    } else {
        try {
          rgbFondo = colorConvert.keyword.rgb(colorFondo);
          rgbFondo = `rgb(${rgbFondo[0]}, ${rgbFondo[1]}, ${rgbFondo[2]})`;
        } catch (error) {
          console.error(`Error converting background color: ${colorFondo}`);
          rgbFondo = 'rgb(255, 255, 255)'; // Valor por defecto en caso de error
        }
    }

    if (colorLetra.startsWith('#')) {
        rgbLetra = hexToRgb(colorLetra);
    } else if (colorLetra.startsWith('rgb')) {
        rgbLetra = colorLetra;
    } else {
      try {
        rgbLetra = colorConvert.keyword.rgb(colorLetra);
        rgbLetra = `rgb(${rgbLetra[0]}, ${rgbLetra[1]}, ${rgbLetra[2]})`;
      } catch (error) {
        console.error(`Error converting text color: ${colorLetra}`);
        rgbLetra = 'rgb(0, 0, 0)'; // Valor por defecto en caso de error
      }
    }

    const [rf, gf, bf] = rgbFondo.replace(/[^\d,]/g, '').split(',').map(Number);
    const [rl, gl, bl] = rgbLetra.replace(/[^\d,]/g, '').split(',').map(Number);

    const fondoLuminancia = rgbLuminance(rf, gf, bf);
    const letraLuminancia = rgbLuminance(rl, gl, bl);

    const masClaro = Math.max(fondoLuminancia, letraLuminancia);
    const masOscuro = Math.min(fondoLuminancia, letraLuminancia);

    return (masClaro + 0.05) / (masOscuro + 0.05);
}

function ajustarColorLetra(colorFondo) {
    const umbralContraste = 4.5;
    const colorLetra = calcularContraste(colorFondo, 'rgb(0, 0, 0)') >= umbralContraste ? 'rgb(0, 0, 0)' : 'rgb(255, 255, 255)';
    return colorLetra;
}

const server = http.createServer((request, response) => {
    const url_parts = new URL(request.url, `http://${request.headers.host}`);
    console.log(url_parts);

    let filePath = '.' + url_parts.pathname;
    console.log(filePath);
    if (filePath === './index') {
        console.log('entra en index');
        switch (url_parts.searchParams.get('lang') || 'es') {
            case 'en':
                filePath = './enindex.html';
                break;
            default:
                filePath = './index.html';
        }
    } else if (filePath === './pagina1') {
        console.log('entra en pagina 1');
        switch (url_parts.searchParams.get('lang') || 'es') {
            case 'en':
                filePath = './enpagina1.html';
                break;
            default:
                filePath = './pagina1.html';
        }
    } else if (filePath === './pagina2') {
        console.log('entra en pagina 2');
        switch (url_parts.searchParams.get('lang') || 'es') {
            case 'en':
                filePath = './enpagina2.html';
                break;
            default:
                filePath = './pagina2.html';
        }
    }
    console.log(filePath);

    const extname = String(path.extname(filePath)).toLowerCase();
    console.log(extname);
    const contentType = myModule.getContent(extname);
    console.log(contentType);

    fs.readFile(filePath, (error, content) => {
        if (error) {
            console.log(error);
            if (error.code === 'ENOENT') {
                response.writeHead(404, { 'Content-Type': 'text/html' });
                response.end('<h1>404 Not Found</h1>', 'utf-8');
            } else {
                response.writeHead(500);
                response.end('Sorry, check with the site admin for error: ' + error.code + ' ..\n');
            }
        } else {
            const colorFondo = url_parts.searchParams.get('color') || 'white';
            const colorFondoRGB = colorFondo.startsWith('#') ? hexToRgb(colorFondo) : (colorFondo.startsWith('rgb') ? colorFondo : nombreA_Rgb(colorFondo));
            const nuevoColorLetra = ajustarColorLetra(colorFondoRGB);

            const modifiedContent = content.toString('utf-8').replace('</head>', `
                <style>
                    body { 
                        background-color: ${colorFondo};
                        color: ${nuevoColorLetra};
                    }
                </style>
            </head>`);

            response.writeHead(200, { 'Content-Type': contentType });
            response.end(modifiedContent, 'utf-8');
        }
    });
});

server.listen(4000, () => {
    console.log(`Server running at: ${server.address().port}`);
});