package com.mangopay.teamcity.runscope.common;

import java.util.regex.Pattern;

public interface RunscopeConstants {
    String SETTINGS_TOKEN = "runscope.token";
    String SETTINGS_BUCKET = "runscope.bucket";
    String SETTINGS_TESTS = "runscope.tests.included";
    String SETTINGS_EXCLUDED_TESTS = "runscope.tests.excluded";
    String SETTINGS_ENVIRONMENT = "runscope.environment";
    String SETTINGS_VARIABLES = "runscope.variables";
    String SETTINGS_PARALLEL = "runscope.parallel.enabled";
    String SETTINGS_PARALLEL_COUNT = "runscope.parallel.count";

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
