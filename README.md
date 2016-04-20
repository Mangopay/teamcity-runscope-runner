This plugin allows TeamCity builds to trigger and parse Runscope tests.

# Installing
Download the plugin from the [Mangopay Github release page](https://github.com/Mangopay/teamcity-runscope-runner/releases/latest).  
Install the plugin [as usual](https://confluence.jetbrains.com/display/TCD9/Installing+Additional+Plugins).

# Using
The plugin comes with a runner. To use that runner, add a new build step in your build configuration.

## Define runscope variables
You can provide a set of initial variables that will be passed on Runscope along with each test. To do so, you have two choices :
* Fully specify the variable : ```variablename=value```.
* Specify only the variable name : ```variablename```.

Each variable without value specified will take its value from ```runscope.vars.{variablename}``` build parameter.
That can be usefull if you want to be able to run custom build or pass Runscope variable from one build step to another (see below)

## Capture runscope variables
While running, each variable that is successfully defined by Runscope (```variable.result == pass```) will be added as a TeamCity build parameter available to the currently running build.
Those variables are prefixed with ```runscope.vars.```.  
When defining build configuration, you can pass Runscope variable from one build step to another :
* During a build step, Runscope successfully defines ```userId``` variable to ```451```.
* Runscope runner adds a build parameter ```runscope.vars.userId``` equals to ```451```.
* On a second Runscope runner step, you set ```Initial variables``` parameter to ```userId```.
* All tests triggered from that build step will include a Runscope initial variable ```userId``` having the value ```451```.

## Parallel tests running
The plugin allows you to run tests in parallel. Doing so, the build log tree will be all messed-up due a knwown [TeamCity bug](https://youtrack.jetbrains.com/issue/TW-8249).
Only the build log is messed up, not tests reports. If you do not care about the build log, you should definitly enable parallel tests running to reduce your build time.

## Known issues
Runscope does not provide an API endpoint to cancel a running test. Thus if your TeamCity build is interrupted (shutdown, cancel, etc), your tests are **still running** on Runscope side !

# Building
To build the plugin :
1. Install the java SDK. Version 8u77 was used to develop this plugin.
2. Install Maven.
3. Set ```JAVA_HOME``` and ```M2_HOME``` variables.
4. Add ```bin``` folder of each installation to your ```PATH```.
5. Go to the plugin root directory and run ```mvn package```.

The plugin will be packaged to the ```target``` folder.
