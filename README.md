# mongodb-java-multithreaded-loadtest

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

To run up to 10 threads in 10 steps
 mvn exec:java -Dexec.mainClass=app.ApplicationMain  -Dexec.args="--maxThreads 10 --inserts 10" 


Configuration for database can be found in `Configuration.java`