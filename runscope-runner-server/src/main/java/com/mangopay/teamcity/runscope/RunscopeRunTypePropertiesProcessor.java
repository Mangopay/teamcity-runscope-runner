package com.mangopay.teamcity.runscope;

import jetbrains.buildServer.serverSide.InvalidProperty;
import jetbrains.buildServer.serverSide.PropertiesProcessor;
import jetbrains.buildServer.util.PropertiesUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

class RunscopeRunTypePropertiesProcessor implements PropertiesProcessor {

    @Override
    public Collection<InvalidProperty> process(Map<String, String> properties) {
        List<InvalidProperty> result = new ArrayList<InvalidProperty>();

        final String apiKey = properties.get(RunscopeConstants.SETTINGS_APIKEY);
        final String bucket = properties.get(RunscopeConstants.SETTINGS_BUCKET);

        if(PropertiesUtil.isEmptyOrNull((apiKey))) {
            result.add(new InvalidProperty(RunscopeConstants.SETTINGS_APIKEY, "Api key must be specified"));
        }

        if(PropertiesUtil.isEmptyOrNull((bucket))) {
            result.add(new InvalidProperty(RunscopeConstants.SETTINGS_BUCKET, "Bucket must be specified"));
        }

        return result;
    }
}