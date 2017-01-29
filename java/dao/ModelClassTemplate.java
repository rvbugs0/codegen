<template>{"key":"properties", "template" :"private #dataType# #propertyArgName#;"}</template>
<template>{"key":"setFunction", "template" :"public void set#propertyName#(#dataType# #propertyArgName#){ this.#propertyArgName#=#propertyArgName#;}"}</template>
<template>{"key":"getFunction", "template" :"public #dataType# get#propertyName#(){return this.#propertyArgName#;}"}</template>


<head>
package #package#;
import #package#.interfaces.*;
public class #name# implements #name#Interface
{
public boolean equals(Object object)
{ 
if(!(object instanceof #name#Interface))
{
return false;
}
#name#Interface m#name#Interface;
m#name#Interface=(#name#Interface)object;
return this.code==m#name#Interface.getCode();
}

</head>
<footer>
}
</footer>