# ENRE

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

ENRE (ENtity Relationship Extractor ) is a tool for extraction of code entity dependencies(relationships) from source code. The resolved dependency types include:

| Dependency Type | Description |
| ------ | ------ |
| Import | a File imports a Package, Module, etc. |
| Implement | A Class implements an Interface. |
| Extend | A Class inherits a Class. |
| Call | A function/Method calls A Function/method. |
| Use  | A Function/Method uses or reads a Variable. |
| Set | A Function/Method uses or reads a Varibale. |
| Parameter | A Funciton/Method has a Class type parameter. |
| Return | A Function/Method returns a Class type value. |


# Features
ENRE supports analyzing source code written in [*Python*](https://www.python.org/), [*Go*](https://golang.org/). 

# Usage
###  1)Prepare the executable jar
The released jar of ENRE is named as **ENRE-v2.0.jar**.
###  2) Set up Java environment 
To execute ENRE-v2.0.jar, you should set up JAVA envionment. Please referer to [Set up JAVA environment](https://docs.oracle.com/javase/7/docs/webnotes/install/). 
### 3) cmd usage
Now, everthing is already prepared well. Let's use ENRE to analyze source code. 
The usage command is:
```sh
java -jar <executable> <lang> <dir> <include-dir> <project-name> [--from-type] [stub-dir]
```
- \<executable>. The executable jar package of ENRE.
- \<lang>. The language of source code that will be analyzed. It can be **python** or **golang**.
- \<dir>. The path of the source code that will be analyzed.
- \<include-dir>. The **github url** of source code. It only works when analyzing golang projects. Set it "**null**" when analyzing python projects.
- \<project-name>. A short alias name of the anayzed source code project.  
- [--from-type]. Optional argument to add dependencies deduced from type infomation.
- [stub-dir]. Python stub file directory, used with --from-type option.

#### Example I:
Use ENRE to analyze a demo project "**fire**" written in *Python*: 
```sh
#in linux platform 
$java -jar ENRE-v2.0.jar  python  demo-projects/fire   null  fire   
```
```sh
#in windows platform
$java -jar ENRE-v2.0.jar  python  demo-projects\fire   null  fire 
```

After analysis, ENRE finally outputs the resovled entities and dependencies in **JSON**, **XML**, **DOT** files in new-generated **fire-out/** directory.

#### Example II:
Use ENRE to analyze a demo project "**beego**" written in  *Go*:
```sh
#in linux platform 
$java -jar ENRE-v2.0.jar  golang  demo-projects/beego   github.com/astaxie/beego  beego  
```
```sh
#in windows platform
$java -jar ENRE-v2.0.jar  golang  demo-projects\beego   github.com/astaxie/beego  beego
```
After analysis, ENRE finally outputs the resovled entities and dependencies in **JSON**, **XML**, **DOT** files in new-generated **beego-out/** directory.


[**ENRE video introduction**] (https://www.youtube.com/watch?v=BfXp5bb1yqc&t=43s)

#### Example III
Use ENRE to analyze a demo project **django** written in *python* with **type** infomation.
```sh
#in windows platform
$java -jar ENRE-v2.0.jar  python  demo-projects\django   null  django demo-projects\django-stubs
```
With the regular outputs like Example I and II, ENRE will generate type infomation in  django-type-info/ directory in csv format, and use it to generate the dependencies to JSON file named django-deps-from-type.json.

License
----

**MIT**
