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

## Basic Feature
ENRE supports analyzing source code written in [*Python*](https://www.python.org/), [*Go*](https://golang.org/). 

## Advanced Feature 
**New Update!**

ENRE has integrated type inference technique and type stub files (one of type hint practices) to enhance the extraction of **Possible Dependencies** in Python code.

**Possible Dependencies** are the syntactic dependencies indiscernible in source code due to the lack of explicit type references, in contrast with  **Explicit Dependencies**.

You can learn more about this fresh feature and experience it in current version **[ENRE-v2.0](https://github.com/jinwuxia/ENRE/tree/v2.0)**. 


# Requirement

## 1) Set up Java environment 

To execute ENRE-type2.0.jar, you should set up JAVA envionment. Please referer to [Set up JAVA environment](https://docs.oracle.com/javase/7/docs/webnotes/install/). 

## 2) Set up Python environment

ENRE contains Python scripts located in *TypeExtractor* for *--from-type* option. 
You should install [Python](https://www.python.org/) in your local environment, at least **Python 3.8** version. 

*TypeExtractor* also requires several third-party tools listed in *requirements.txt*. 
You can install them by running the following command.

```sh
pip install -r requirements.txt
``` 

Why needs at least **Python 3.8**? The Python code in ENRE use Python [ast library](https://docs.python.org/3/library/ast.html) to find locations of objects, and some attributes (i.e., *end_lineno*,*end_col_offset*) of *ast.node* are only supported in Python3.8 and later. 

# Usage
## 1) Prepare the executable jar
The released jar of ENRE is named as **ENRE-type2.0.jar**.

## 2) Command
Now, everthing is already prepared well. Let's use ENRE to analyze source code. 
The usage command is:
```sh
java -jar <executable> <lang> <dir> <include-dir> <project-name> [--from-type] [stub-dir]
```
- <executable>. The executable jar package of ENRE.
- <lang>. The language of source code that will be analyzed. It can be **python** or **golang**.
- <dir>. The path of the source code that will be analyzed.
- <include-dir>. The **github url** of source code. It only works when analyzing golang projects. Set it "**null**" when analyzing python projects.
- <project-name>. A short alias name of the anayzed source code project.  
- [--from-type]. Optional argument to add dependencies deduced from type infomation.
- [stub-dir]. Python stub file directory, used with --from-type option.

### Example I
Use ENRE to analyze a demo project "**fire**" written in *Python*: 
```sh
#in linux platform 
$java -jar ENRE-type2.0.jar  python  demo-projects/fire   null  fire   
```
```sh
#in windows platform
$java -jar ENRE-type2.0.jar  python  demo-projects\fire   null  fire 
```

After analysis, ENRE finally outputs the resovled entities and dependencies in **JSON**, **XML**, **DOT** files in new-generated **fire-out/** directory.

### Example II
Use ENRE to analyze a demo project "**beego**" written in  *Go*:
```sh
#in linux platform 
$java -jar ENRE-type2.0.jar  golang  demo-projects/beego   github.com/astaxie/beego  beego  
```
```sh
#in windows platform
$java -jar ENRE-type2.0.jar  golang  demo-projects\beego   github.com/astaxie/beego  beego
```
After analysis, ENRE finally outputs the resovled entities and dependencies in **JSON**, **XML**, **DOT** files in new-generated **beego-out/** directory.


[**ENRE video introduction**] (https://www.youtube.com/watch?v=BfXp5bb1yqc&t=43s)

### Example III
Use ENRE to analyze a demo project **django** written in *python* with **type stub** infomation.
```sh
#in windows platform
$java -jar ENRE-type2.0.jar  python  demo-projects\django   null  django demo-projects\django-stubs
```
Besides outputting similar files to the *Example I* and *Example II*, this command will generate type infomation into  *django-type-info.csv* and thus possible dependencies into *deps-from-type.json*.


# References

You can reference the following papers if you use ENRE or feel interested in knowing more about it.


    @inproceedings{2020ase-jin,
        title={Exploring the Architectural Impact of Possible Dependencies in Python Software},
		
        author={Jin, Wuxia and Cai, Yuanfang and Kazman, Rick and Zhang, Gang and Zheng, Qinghua and Liu, Ting},
        booktitle={2020 35th IEEE/ACM International Conference on Automated Software Engineering (ASE)},
        pages={1--13},
        year={2020},
        organization={IEEE}
        }

    @inproceedings{2019icse-jin,
      title={ENRE: a tool framework for extensible eNtity relation extraction},
	  
      author={Jin, Wuxia and Cai, Yuanfang and Kazman, Rick and Zheng, Qinghua and Cui, Di and Liu, Ting},
      booktitle={Proceedings of the 41st International Conference on Software Engineering: Companion Proceedings},
      pages={67--70},
      year={2019},
      organization={IEEE Press}
    }


[**ENRE Introduction Video **](https://www.youtube.com/watch?v=BfXp5bb1yqc&t=43s)


License
----

**MIT**
