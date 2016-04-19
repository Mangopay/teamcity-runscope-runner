package com.mangopay.teamcity.runscope.server;

import com.mangopay.teamcity.common.RunscopeConstants;
import jetbrains.buildServer.serverSide.InvalidProperty;
import jetbrains.buildServer.serverSide.PropertiesProcessor;
import jetbrains.buildServer.util.PropertiesUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;

class RunscopeRunTypePropertiesProcessor implements PropertiesProcessor {

    @Override
    public Collection<InvalidProperty> process(final Map<String, String> map) {
        final Collection<InvalidProperty> result = new ArrayList<InvalidProperty>();

        final String apiKey = map.get(RunscopeConstants.SETTINGS_APIKEY);
        final String bucket = map.get(RunscopeConstants.SETTINGS_BUCKET);
        final String variables = map.get(RunscopeConstants.SETTINGS_VARIABLES);

        if(PropertiesUtil.isEmptyOrNull(apiKey)) {
            result.add(new InvalidProperty(RunscopeConstants.SETTINGS_APIKEY, "Api key must be specified"));
        }

        if(PropertiesUtil.isEmptyOrNull(bucket)) {
            result.add(new InvalidProperty(RunscopeConstants.SETTINGS_BUCKET, "Bucket must be specified"));
        }

        if(!PropertiesUtil.isEmptyOrNull(variables)) {
            final Matcher matcher = RunscopeConstants.SETTINGS_VARIABLES_PARSER.matcher(variables);
            if(!matcher.matches()) result.add(new InvalidProperty(RunscopeConstants.SETTINGS_VARIABLES, "Specified value is not valid"));
        }

        return result;
    }
}