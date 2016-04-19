package com.mangopay.teamcity.runscope;

import jetbrains.buildServer.serverSide.STestRun;
import jetbrains.buildServer.web.openapi.PagePlaces;
import jetbrains.buildServer.web.openapi.PlaceId;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import jetbrains.buildServer.web.openapi.SimplePageExtension;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RunscopeTestExtension extends SimplePageExtension {
    private static final String RUNSCOPE_TEST_LINK_ATTRIBUTE = "runscopeTestLink";
    private static final Pattern FULL_LOG_PATTERN;

    static{
        final String pattern = RunscopeConstants.LOG_SEE_FULL_LOG.replaceAll("%s", "(?<url>.*)");
        FULL_LOG_PATTERN = Pattern.compile(pattern);
    }

    public RunscopeTestExtension(@NotNull final PagePlaces pagePlaces, @NotNull final PluginDescriptor pluginDescriptor){
        super(pagePlaces, PlaceId.TEST_DETAILS_BLOCK, RunscopeConstants.PLUGIN_ID, pluginDescriptor.getPluginResourcesPath("runscopeTestDetail.jsp"));
        register();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean isAvailable(@NotNull final HttpServletRequest request) {
        final Object testRuns = request.getAttribute("testRuns");
        if(testRuns == null) return false;

        final Collection<? extends STestRun> runs = (Collection<? extends STestRun>) testRuns;
        if(runs.isEmpty()) return false;

        //finding test details
        for (final STestRun run : runs) {
            if(run == null) continue;
            final Matcher matcher = FULL_LOG_PATTERN.matcher(run.getFullText());
            if (!matcher.find()) continue;

            request.setAttribute(RUNSCOPE_TEST_LINK_ATTRIBUTE, matcher.group("url"));
            return true;
        }

        return false;
    }

    @Override
    public void fillModel(@NotNull final Map<String, Object> model, @NotNull final HttpServletRequest request) {
        final Object url = request.getAttribute(RUNSCOPE_TEST_LINK_ATTRIBUTE);
        model.put("url", url);
    }
}
