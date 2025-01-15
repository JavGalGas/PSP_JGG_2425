const http = require('http');
const fs = require('fs');
const path = require('path');
const myModule = require('./modules/my_module.js');

const server = http.createServer((request, response) => {
    let filePath = '.' + request.url;
    if (filePath === './') {
        filePath = './index.html';
    } else if (filePath === './pagina1') {
        filePath = './pagina1.html';
    } else if (filePath === './pagina2') {
        filePath = './pagina2.html'
    }

    const extname = String(path.extname(filePath)).toLowerCase();
    const contentType = myModule.getContent(extname);

    fs.readFile(filePath, (error, content) => {
        if (error) {
            if (error.code === 'ENOENT') {
                response.writeHead(404, { 'Content-Type': 'text/html' });
                response.end('<h1>404 Not Found</h1>', 'utf-8');
            } else {
                response.writeHead(500);
                response.end('Sorry, check with the site admin for error: ' + error.code + ' ..\n');
            }
        } else {
            response.writeHead(200, { 'Content-Type': contentType });
            response.end(content, 'utf-8');
        }
    });
});

server.listen(80, () => {
    console.log('Server running at: localhost');
});