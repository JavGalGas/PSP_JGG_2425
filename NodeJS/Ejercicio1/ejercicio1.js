const http = require('http');
const { URL } = require('url');
const path = require('path');
const fs = require('fs');
const myModule = require('./modules/my_module.js');

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
            const color = url_parts.searchParams.get('color') || 'white';
            const modifiedContent = content.toString('utf-8').replace('</head>', `<style>body { background-color: ${color}; }</style></head>`);
            response.writeHead(200, { 'Content-Type': contentType });
            response.end(modifiedContent, 'utf-8');
        }
    });
});

server.listen(8080, () => {
    console.log(`Server running at: ${server.address().port}`);
});