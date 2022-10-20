package com.myorg;

import io.github.cdklabs.dynamotableviewer.TableViewer;
import software.amazon.awscdk.services.apigateway.LambdaRestApi;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.Runtime;
import software.constructs.Construct;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;

public class CdkWorkshopStack extends Stack {
    public CdkWorkshopStack(final Construct parent, final String id) {
        this(parent, id, null);
    }

    public CdkWorkshopStack(final Construct parent, final String id, final StackProps props) {
        super(parent, id, props);

        // Defines a new lambda resource
        final Function hello = Function.Builder.create(this, "HelloHandler")
                .runtime(Runtime.NODEJS_14_X)    // execution environment
                .code(Code.fromAsset("lambda"))  // code loaded from the "lambda" directory
                .handler("hello.handler")        // file is "hello", function is "handler"
                .build();

        // Defines our hitcounter resource
        final HitCounter helloWithCounter = new HitCounter(this, "HelloHitCounter", HitCounterProps.builder()
                .downstream(hello)
                .build());

        // Defines API Gateway REST API resource backed by hello function 'hello.js'
        // API Gateway exposes a public HTTP endpoint that anyone on the internet can hit with
        // an http client such as curl or a web browser
        LambdaRestApi.Builder.create(this, "Endpoint")
                .handler(helloWithCounter.getHandler())
                .build();

        // Defines a viewer for the HitCounts table
        TableViewer.Builder.create(this, "ViewerHitCount")
                .title("Hello Hits")
                .table(helloWithCounter.getTable())
                .sortBy("hits")
                .build();

    }
}
