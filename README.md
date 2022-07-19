## mongodb-java-multithreaded-loadtest

A Java client that performs insertions into a MongoDB database using a configurable number of threads.


**Usage:**
``` 
Options category 'threads':
  --deltaThreads [-d] (an integer; default: "2")
    How many threads to start in each iteration.
  --inserts [-i] (an integer; default: "1000")
    Number of docs to be inserted per thread.
  --maxThreads [-x] (an integer; default: "100")
    Maximum number of threads to spin
  --sleep [-s] (an integer; default: "500")
    Milliseconds to sleep between iterations.
  --startThreads [-t] (an integer; default: "0")
    Starting number of threads for this process
```


**Example:**

To run up to 10 threads in 10 steps:

```
 mvn exec:java -Dexec.mainClass=app.ApplicationMain  -Dexec.args="--maxThreads 10 --inserts 10" 
```

Configuration for database currently must be changed in `Configuration.java`.


**Tips**

To create an initial collection to test, use [mgeneratejs](https://github.com/rueckstiess/mgeneratejs) and then `mongoimport`. 


```
$ mgeneratejs template.json -n 500000 > test.collection.json
$ mongoimport --host mongo.test.com --port 27017 -d test -c collection test.collection.json 
```

This code is released as-is, and is not supported by MongoDB.

