function getContentType(ext) {
    switch (ext) {
        case '.html':
            return 'text/html';
        case '.css':
            return 'text/css';
        case '.jpg':
            return 'image/jpeg';
        default:
            return 'application/octet-stream';
    }
}
module.exports = {
    getContent : getContentType
};