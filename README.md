# aws-lambda-java-testing-utils
This library contains some useful classes for testing AWS Lambda Java components.

## AwsLambdaRunner
This class will start a HTTP server which forwards messages to a Lambda component. 
Currently it can only forward messages to components which implement `com.amazonaws.services.lambda.runtime.RequestHandler`.
By default, it will start the server on port 8080. It can be changed by setting system property `port`.

### Usage - Gradle
The simplest usage would be to add a task to your Gradle build file as follows:

```groovy

configurations {
    lambdaTestJars
}

dependencies {
  compile 'com.amazonaws:aws-lambda-java-core:1.0.0' // Contains the RequestHandler class
  lambdaTestJars "uk.co.bbc.pcs.common:aws-lambda-java-testing-utils:1-SNAPSHOT"
}

/**
 * Starts server which forwards the body of POST requests on "/" to the AWS Lambda class.
 */
task runLambda(dependsOn: 'classes', type: JavaExec) {
  main = 'uk.co.bbc.pcs.common.lambda.AwsLambdaRunner'
  args = ['<Full class name of your RequestHandler>',
          '<Full class name of the event>']
    classpath=configurations.cucumberTestJars
    classpath+=[sourceSets.test.runtimeClasspath]
}
```

The server can be started using `gradle runLambda`.

### Usage - Java

The server can also be started programmatically as follows:

```java
RequestHandler myRequestHandler<EventClass, ResponseClass> = // Initialize your RequestHandler

// Start server
AwsLambdaRunner.startServer(myRequestHandler, EventClass.class);

// Stop server
AwsLambdaRunner.stopServer();
```

## MockContext

This class is an implementation of `com.amazonaws.services.lambda.runtime.Context` for use in unit tests. 
See [here](http://docs.aws.amazon.com/lambda/latest/dg/java-context-object.html) for more details.

It is instantiated using the MockContextBuilder:

```java
LambdaLogger logger = // Create a logger
Context context = new MockContextBuilder("my-lambda-function-name", logger)
                          .createMockContext();
                          
// If no logger is specified, by default it will log using System.out::println
Context context = new MockContextBuilder("my-lambda-function-name")
                          .createMockContext();               

```

## AwsLambdaEventsObjectMapper

This is an extension of [ObjectMapper](https://fasterxml.github.io/jackson-databind/javadoc/2.5/com/fasterxml/jackson/databind/ObjectMapper.html)
which can serialize and deserialize Lambda events. 
Currently it will only handle `com.amazonaws.services.lambda.runtime.events.DynamodbEvent`.
