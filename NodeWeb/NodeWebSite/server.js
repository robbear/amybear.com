//
// Module dependencies
//
var express = require('express'),
    cons = require('consolidate'),
    connect = require('connect'),
    router = require('./routes/router');

var app = module.exports = express();

//
// Configuration
//
app.configure(function() {
    app.set('view engine', 'html');
    app.use(express.bodyParser());
    app.use(express.methodOverride());
    app.use(express.logger({ format: ':response-time ms - :date - :req[x-real-ip] - :method :url :user-agent / :referrer' }));
    app.use(express.static(__dirname + '/public'));
    app.use(app.router);
});

app.configure('development', function() {
    app.use(express.errorHandler({ dumpExceptions: true, showStack: true }));
});

app.configure('production', function() {
    app.use(express.errorHandler());
});

//
// Map http://www.host.com/foo/bar to http://host.com/foo/bar
//
app.get('*', function(req, res, next) {
    if(req.host.match('www.')) {
        var index = req.host.indexOf('www.');
        var newHost = req.host.substring(index + 4);
        var protocol = (req.header('X-Forwarded-Protocol') == 'https') ? 'https://' : 'http://';
        var newUrl = protocol + newHost + req.url;
        res.writeHead(301, { 'Location': newUrl, 'Expires': (new Date).toGMTString() });
        res.end();
    }
    else {
        return next();
    }
});

//
// Routes
//
app.get('/', router.index);

// 404 errors
app.get('*', router.handle404);

app.listen(process.env.PORT || 1994);
console.log("amybearman.com server listening on port %s in %s mode", process.env.PORT || 1994, app.settings.env);
console.log("Using connect %s, Express %s", connect.version, express.version);