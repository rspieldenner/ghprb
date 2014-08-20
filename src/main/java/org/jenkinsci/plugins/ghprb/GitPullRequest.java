package org.jenkinsci.plugins.ghprb;

import org.kohsuke.github.GHIssueComment;
import org.kohsuke.github.GHPullRequest;

import java.net.URL;

/**
 * Maintains state about a Pull Request for a particular Jenkins job.  This is what understands the current state
 * of a PR for a particular job.
 *
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
public interface GitPullRequest {

    void init(Ghprb helper, GitRepository repo);

    /**
     * Checks this Pull Request representation against a GitHub version of the Pull Request, and triggers
     * a build if necessary.
     *
     * @param pr
     */
    void check(GHPullRequest pr);

    void check(GHIssueComment comment);

    boolean isWhiteListedTargetBranch();

    int getId();

    String getHead();

    boolean isMergeable();

    String getTarget();

    String getSource();

    String getAuthorEmail();

    String getTitle();

    /**
     * Returns the URL to the Github Pull Request.
     *
     * @return the Github Pull Request URL
     */
    URL getUrl();
}
