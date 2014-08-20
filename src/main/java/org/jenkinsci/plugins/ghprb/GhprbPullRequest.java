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
public interface GhprbPullRequest {

    public void init(Ghprb helper, GhprbRepository repo);

    /**
     * Checks this Pull Request representation against a GitHub version of the Pull Request, and triggers
     * a build if necessary.
     *
     * @param pr
     */
    public void check(GHPullRequest pr);

    public void check(GHIssueComment comment);

    public boolean isWhiteListedTargetBranch();

    public int getId();

    public String getHead();

    public boolean isMergeable();

    public String getTarget();

    public String getSource();

    public String getAuthorEmail();

    public String getTitle();

    /**
     * Returns the URL to the Github Pull Request.
     *
     * @return the Github Pull Request URL
     */
    public URL getUrl();
}
