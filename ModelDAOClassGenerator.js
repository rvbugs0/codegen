var fs = require("fs");
var readline = require("readline").createInterface(process.stdin,process.stdout);

function Entity()
{
	this.name = "";
	this.package = "";
	this.key = "";
	this.keyDataType = "";
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
					 	

					});
				 	// console.log(thisEntity);
				 	readline.question("key : ",function(k)
				 	{

				 		readline.close();
				 		thisEntity.key=k;
				 		thisEntity.properties.forEach(function(da)
				 		{
				 			if(da.propertyName ===k)
				 			{
				 				thisEntity.keyDataType=da.dataType;
				 			}
				 		});
				 		generateCode(thisEntity);
				 	});


				});
			});
	});

function capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}


function generateCode(classObject)
{
	var templateContents = fs.readFileSync("./java/dao/ModelDAOClassTemplate.java","UTF-8");

	newFileContent = "";

	var head = templateContents.match(/<head>[\s\S]*<\/head>/)[0];
	head = head.replace("<head>","");
	head = head.replace("</head>","");
	head = head.replace(/#package#/g,classObject.package);
	head = head.replace(/#name#/g,classObject.name);

	newFileContent+=head;	


	var body = templateContents.match(/<body>[\s\S]*<\/body>/)[0];
	body = body.replace("<body>","");
	body = body.replace("</body>","");
	body = body.replace(/#key#/g,classObject.key);
	body = body.replace(/#name#/g,classObject.name);
	body = body.replace(/#keyFirstLetterUpperCase#/g,capitalizeFirstLetter(classObject.key));
	body = body.replace(/#keyDataTypeFirstLetterUpperCase#/g,capitalizeFirstLetter(classObject.keyDataType));
	body = body.replace(/#keyDataType#/g,classObject.keyDataType);


	var repeat = body.match(/<repeat\b[^>]*>([\s\S]*?)<\/repeat>/g);

	console.log("repeat : "+repeat.length);
	while(repeat.length>0)
	{
			toAppend = "";

			var newContent = repeat[0];
			newContent = newContent.replace("<repeat>","");
			newContent = newContent.replace("</repeat>","");
			newContent=newContent.trim();
			var x=1;

			appendChar="";
			if(newContent.charAt(newContent.length-1)==';')
			{
				appendChar="\n";
			}
			classObject.properties.forEach(function(data){

				temp = newContent;
				temp = temp.replace(/#index#/g,x);
				temp = temp.replace(/#keyFirstLetterUpperCase#/,capitalizeFirstLetter(classObject.key));
				temp = temp.replace(/#name#/g,classObject.name);
				temp = temp.replace(/#dataType#/g,data.dataType);
				temp = temp.replace(/#propertyName#/g,data.propertyName);
				temp = temp.replace(/#dataTypeFirstLetterUpperCase#/g,capitalizeFirstLetter(data.dataType));
				temp = temp.replace(/#propertyNameFirstLetterUppercase#/g,capitalizeFirstLetter(data.propertyName));
				toAppend+=temp.trim();
				toAppend+=appendChar;
				x+=1;
			});
			toAppend = toAppend.substring(0,toAppend.length-1);
			//only first occurence
			body = body.replace(/<repeat\b[^>]*>([\s\S]*?)<\/repeat>/,toAppend);
			repeat.shift();
	}


	newFileContent+=body;
	fs.writeFile("./gen/"+classObject.name+"DAO.java",newFileContent,function(err)
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






