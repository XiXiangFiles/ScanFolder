const http=require('http');
const fs = require('fs');
http.createServer(function(req,res){
	let check=0;
	fs.readdirSync("/home/pi/Desktop/test").forEach(function(file){
		let id=file.split('.');
		id=id[0].split('-');
		id='/'+id[0];

		if(req.url===id){
			check++;
		}	
	});
	if(check >= 2){
		let url=req.url.split('/');
		res.writeHead(200, {'Content-Type': 'text/html'});
		res.write("finish:"+url[1]);
		res.end();

	}
}).listen(8080);

