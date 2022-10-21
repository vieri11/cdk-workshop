package com.myorg;

import software.amazon.awscdk.Stage;
import software.constructs.Construct;
import software.amazon.awscdk.StageProps;

public class WorkshopPipelineStage extends Stage {
    public WorkshopPipelineStage(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public WorkshopPipelineStage(final Construct scope, final String id, final StageProps props) {
        super(scope, id, props);

        new CdkWorkshopStack(this, "WebService");
    }
}