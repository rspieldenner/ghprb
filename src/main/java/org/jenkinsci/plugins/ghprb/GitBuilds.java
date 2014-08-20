package org.jenkinsci.plugins.ghprb;

import hudson.model.AbstractBuild;

/**
 * Created by rspieldenner on 8/20/14.
 */
public interface GitBuilds {
    String build(GitPullRequest pr);

    void onStarted(AbstractBuild build);

    void onCompleted(AbstractBuild build);
}
