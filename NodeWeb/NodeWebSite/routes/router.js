var cons = require('consolidate'),
    hogan = require('hogan.js'),
    fs = require('fs'),
    engineName = 'hogan';

exports.index = function(req, res) {
    sendOutputHtml(req, res, 'views/index_header.html', 'views/index_body.html',
        {
            "PageTitle": "Amy Bearman"
        });
}

exports.handle404 = function(req, res) {
    res.status(404);
    if(req.accepts('html')) {
        sendOutputHtml(req, res, 'views/404_header.html', 'views/404_body.html',
            {
                "PageTitle": "Not found"
            });
        return;
    }

    if(req.accepts('json')) {
        res.send({ error: 'Not found' });
        return;
    }

    res.type('txt').send('Not found');
}

//
// Long operation test. Uncomment to allow.
// Use this test to verify that a long operation does not block
// other routes.
//
/*
exports.timeouttest = function(req, res) {
    setTimeout(function() {
        sendOutputHtml(req, res, 'views/index_header.html', 'views/index_body.html',
            {
                "PageTitle": "Timeout Test"
            })},
            10000);        
}
*/

function sendOutputHtml(req, res, headerContentPath, bodyContentPath, propertyBag) {	
	// For testing purposes
	propertyBag.dummyProperty = 'dummy';
	
    fs.readFile('views/layout.html', 'utf-8', function(err, layoutString) {
        var template = hogan.compile(layoutString.toString('utf-8'));

        fs.readFile(headerContentPath, 'utf-8', function(err, headerContentString) {
            fs.readFile(bodyContentPath, 'utf-8', function(err, bodyContentString) {
                var partials = { "HeaderContent": headerContentString, "BodyContent": bodyContentString };
                var outputHtml = template.render(propertyBag, partials);

                res.send(outputHtml);
            });
        });
    });
}
