package org.jenkinsci.plugins.ghprb;

import hudson.model.AbstractBuild;
import hudson.model.Cause;
import hudson.model.queue.QueueTaskFuture;
import org.kohsuke.github.GHCommitState;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class GhprbBuilds implements GitBuilds {
    protected final GhprbTrigger trigger;
    protected final GitRepository repo;

    public GhprbBuilds(GhprbTrigger trigger, GitRepository repo) {
        this.trigger = trigger;
        this.repo = repo;
    }

    protected abstract Logger getLogger();
    public abstract void onCompleted(AbstractBuild build);

    public String build(GitPullRequest pr) {
        StringBuilder sb = new StringBuilder();
        if (cancelBuild(pr.getId())) {
            sb.append("Previous build stopped.");
        }

        if (pr.isMergeable()) {
            sb.append(" Merged build triggered.");
        } else {
            sb.append(" Build triggered.");
        }

        GhprbCause cause = new GhprbCause(pr.getHead(), pr.getId(), pr.isMergeable(), pr.getTarget(), pr.getSource(), pr.getAuthorEmail(), pr.getTitle(), pr.getUrl());

        QueueTaskFuture<?> build = trigger.startJob(cause, repo);
        if (build == null) {
            getLogger().log(Level.SEVERE, "Job did not start");
        }
        return sb.toString();
    }

    private boolean cancelBuild(int id) {
        return false;
    }

    protected GhprbCause getCause(AbstractBuild build) {
        Cause cause = build.getCause(GhprbCause.class);
        if (cause == null || (!(cause instanceof GhprbCause))) return null;
        return (GhprbCause) cause;
    }

    public void onStarted(AbstractBuild build) {
        GhprbCause c = getCause(build);
        if (c == null) {
            return;
        }

        repo.createCommitStatus(build, GHCommitState.PENDING, (c.isMerged() ? "Merged build started." : "Build started."), c.getPullID());
        try {
            build.setDescription("<a title=\"" + c.getTitle() + "\" href=\"" + c.getUrl() + "\">PR #" + c.getPullID() + "</a>: " + c.getAbbreviatedTitle());
        } catch (IOException ex) {
            getLogger().log(Level.SEVERE, "Can't update build description", ex);
        }
    }
}
