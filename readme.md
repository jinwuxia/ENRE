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
<font color=blue>New Update!</font>

ENRE has integrated type inference technique and type stub files (one of type hint practices) to enhance the extraction of **Possible Dependencies** in Python code.

**Possible Dependencies** are the syntactic dependencies indiscernible in source code due to the lack of explicit type references, in contrast with  **Explicit Dependencies**.

You can learn more about this fresh feature and experience it by diving into the **[ENRE-v2.0](https://github.com/jinwuxia/ENRE/tree/v2.0)**. 


# Usage
###  1)Prepare the executable jar
The released jar of ENRE is named as **ENRE-v1.0.jar**.
###  2) Set up Java environment 
To execute ENRE-v1.0.jar, you should set up JAVA envionment. Please referer to [Set up JAVA environment](https://docs.oracle.com/javase/7/docs/webnotes/install/). 
### 3) cmd usage
Now, everthing is already prepared well. Let's use ENRE to analyze source code. 
The usage command is:
```sh
java -jar <executable> <lang> <dir> <include-dir> <project-name>
```
- \<executable>. The executable jar package of ENRE.
- \<lang>. The language of source code that will be analyzed. It can be **python** or **golang**.
- \<dir>. The path of the source code that will be analyzed.
- \<include-dir>. The **github url** of source code. It only works when analyzing golang projects. Set it "**null**" when analyzing python projects.
- \<project-name>. A short alias name of the anayzed source code project.  

#### Example I:
Use ENRE to analyze a demo project "**fire**" written in *Python*: 
```sh
#in linux platform 
$java -jar ENRE-v1.0.jar  python  demo-projects/fire   null  fire   
```
```sh
#in windows platform
$java -jar ENRE-v1.0.jar  python  demo-projects\fire   null  fire 
```

After analysis, ENRE finally outputs the resovled entities and dependencies in **JSON**, **XML**, **DOT** files in new-generated **fire-out/** directory.

#### Example II:
Use ENRE to analyze a demo project "**beego**" written in  *Go*:
```sh
#in linux platform 
$java -jar ENRE-v1.0.jar  golang  demo-projects/beego   github.com/astaxie/beego  beego  
```
```sh
#in windows platform
$java -jar ENRE-v1.0.jar  golang  demo-projects\beego   github.com/astaxie/beego  beego
```
After analysis, ENRE finally outputs the resovled entities and dependencies in **JSON**, **XML**, **DOT** files in new-generated **beego-out/** directory.


# References

You can reference the following papers if you use ENRE or want to learn more about it.


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




[**ENRE video introduction**] (https://www.youtube.com/watch?v=BfXp5bb1yqc&t=43s)


License
----

**MIT**
