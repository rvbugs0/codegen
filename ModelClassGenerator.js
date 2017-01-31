var fs = require("fs");
var readline = require("readline").createInterface(process.stdin,process.stdout);

function Entity()
{
	this.name = "";
	this.package = "";
	this.properties = [];
}

thisEntity =new  Entity();





readline.question("Package Name (absolute path) :",function(pkg)
	{

		thisEntity.package = pkg.trim();
		readline.question("Entity Name :",function(ent)
			{
				thisEntity.name = ent;
				readline.question("Properties propertyName:dataType(comma separated) : ",function(props){
					 props.split(",").forEach(function(data){
					 	var ob= {};
					 	ob["propertyName"]=data.split(":")[0].trim();
					 	ob["dataType"]=data.split(":")[1].trim();
					 	thisEntity.properties.push(ob);
					 	readline.close();

					});
				 	// console.log(thisEntity);
				 	generateCode(thisEntity);
				});
			});
	});

function capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}


function generateCode(classObject)
{
	var templateContents = fs.readFileSync("./java/dao/ModelClassTemplate.java","UTF-8");
	

	var definitions = [];

	templateContents.match(/<template>.*<\/template>/g).forEach(function(data)
		{

			data = data.replace("<template>","");
			data = data.replace("</template>","");
			// console.log("Data" +data);
			var o = JSON.parse(data);
			definitions.push(o.template);

		});
	// console.log("definitions :"+definitions);

	var head = templateContents.match(/<head>[\s\S]*<\/head>/)[0];
	head = head.replace("<head>","");
	head = head.replace("</head>","");
	head = head.replace(/#package#/g,classObject.package);
	head = head.replace(/#name#/g,classObject.name);


	var body = "";

	definitions.forEach(function(data){

		classObject.properties.forEach(function(d){

			var func = data;
			func = func.replace(/#propertyName#/g,capitalizeFirstLetter(d.propertyName));
			func = func.replace(/#propertyArgName#/g,d.propertyName);
			func = func.replace(/#dataType#/g,d.dataType);
			func+="\n";
			body+=func;
		});

	});

	var footer = templateContents.match(/<footer>[\s\S]*<\/footer>/)[0];
	footer = footer.replace("<footer>","");
	footer = footer.replace("</footer>","");

	var newFileContent = "";
	newFileContent+=head;
	newFileContent+=body;
	newFileContent+=footer;
	fs.writeFile("./gen/"+classObject.name+".java",newFileContent,function(err)
		{
			if(err)
			{
				console.log(err);				
			}
			else
			{
				console.log("Done !");
			}
		});

}






