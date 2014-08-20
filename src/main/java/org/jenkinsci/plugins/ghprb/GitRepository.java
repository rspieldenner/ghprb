package org.jenkinsci.plugins.ghprb;

import hudson.model.AbstractBuild;
import org.kohsuke.github.GHCommitState;
import org.kohsuke.github.GHEventPayload;
import org.kohsuke.github.GHPullRequest;

import java.io.IOException;

/**
 * Interface for various Git Repositories
 */
public interface GitRepository {
    void init();

    void check();

    void createCommitStatus(AbstractBuild<?, ?> build, GHCommitState state, String message, int id);

    void createCommitStatus(String sha1, GHCommitState state, String url, String message, int id);

    String getName();

    void addComment(int id, String comment);

    void closePullRequest(int id);

    boolean createHook();

    GHPullRequest getPullRequest(int id) throws IOException;

    void onIssueCommentHook(GHEventPayload.IssueComment issueComment) throws IOException;

    void onPullRequestHook(GHEventPayload.PullRequest pr);
}
