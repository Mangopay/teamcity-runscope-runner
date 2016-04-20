package com.mangopay.teamcity.common;

import java.util.regex.Pattern;

public interface RunscopeConstants {
    String SETTINGS_APIKEY = "runscope.apikey";
    String SETTINGS_BUCKET = "runscope.bucket";
    String SETTINGS_TESTS = "runscope.tests";
    String SETTINGS_ENVIRONMENT = "runscope.environment";
    String SETTINGS_VARIABLES = "runscope.variables";
    String SETTINGS_PARALLEL = "runscope.parallel";

    String RUNNER_DISPLAY_NAME = "Runscope";
    String RUNNER_DESCRIPTION = "Runscope tests runner";
    String RUNNER_TYPE = "Runscope";

    String LOG_SEE_FULL_LOG = "See full log at : %s";

    String BASE_URL = "https://api.runscope.com";

    String CLIENT_ENVIRONMENT = "runscope_environment";
    String CLIENT_COUNT = "count";

    String PLUGIN_ID = "runscope";

    String RUNSCOPE_VAR_PREFIX = "runscope.vars.";

    Pattern SETTINGS_VARIABLES_PARSER = Pattern.compile("^(?<key>[^=]+)(=(?<value>.+))?$", Pattern.MULTILINE);
    Pattern MULTI_PARAMETER_SPLIT = Pattern.compile("[\n, ]");
}
