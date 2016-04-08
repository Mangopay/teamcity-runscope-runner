package com.mangopay.teamcity.runscope;

import com.intellij.openapi.util.text.StringUtil;
import com.mangopay.teamcity.runscope.model.AssertionStatus;
import com.mangopay.teamcity.runscope.model.RequestAssertion;

import java.util.HashMap;
import java.util.Map;

class AssertionFormatter {

    private static final String SOURCE_JSON = "json";
    private static final String SOURCE_XML = "xml";
    private static final String SOURCE_HEADER = "response_header";
    private static final String SOURCE_SIZE = "response_size_bytes";
    private static final String SOURCE_TIME = "response_time_ms";
    private static final String SOURCE_STATUS = "status_code";

    private static final String COMPARISON_EQUALS = "equals";
    private static final String COMPARISON_NOT_EQUALS = "does_not_equal";
    private static final String COMPARISON_EMPTY = "is_empty";
    private static final String COMPARISON_NOT_EMPTY = "is_not_empty";
    private static final String COMPARISON_GTE = "greater_than_or_equal";
    private static final String COMPARISON_GT = "greater_than";
    private static final String COMPARISON_LTE = "less_than_or_equal";
    private static final String COMPARISON_LT = "less_than";
    private static final String COMPARISON_EQUALS_NUMBER = "equals_number";
    private static final String COMPARISON_CONTAINS = "contains";
    private static final String COMPARISON_NOT_CONTAINS = "does_not_contain";
    private static final String COMPARISON_HAS_KEY = "has_key";
    private static final String COMPARISON_HAS_VALUE = "has_value";
    private static final String COMPARISON_IS_NULL = "is_null";

    private static final Map<String, String> sourceMap = new HashMap<String, String>() {
        {
            put(SOURCE_JSON, "body.");
            put(SOURCE_XML, "body.");
            put(SOURCE_HEADER, "header.");
            put(SOURCE_SIZE, "Response (bytes)");
            put(SOURCE_TIME, "Response time (ms)");
            put(SOURCE_STATUS, "Status");
        }
    };

    private static final Map<String, String> okMap = new HashMap<String, String>() {
        {
            put(COMPARISON_EQUALS, "%s was equal to %s");
            put(COMPARISON_NOT_EQUALS, "%s was not equal to %s");
            put(COMPARISON_EMPTY, "was empty");
            put(COMPARISON_NOT_EMPTY, "was not empty");
            put(COMPARISON_GTE, "%s was greater than or equal to %s");
            put(COMPARISON_GT, "%s was greater than %s");
            put(COMPARISON_LTE, "%s was lower than or equal to %s");
            put(COMPARISON_LT, "%s was lower than %s");
            put(COMPARISON_EQUALS_NUMBER, "%s was equal to the number %s");
            put(COMPARISON_CONTAINS, "%s contained %s");
            put(COMPARISON_NOT_CONTAINS, "%s did not contained %s");
            put(COMPARISON_HAS_KEY, "%s had the key %s");
            put(COMPARISON_HAS_VALUE, "%s had the value %s");
            put(COMPARISON_IS_NULL, "was null");
        }
    };

    private static final Map<String, String> koMap = new HashMap<String, String>() {
        {
            put(COMPARISON_EQUALS, "%s was not equal to %s");
            put(COMPARISON_NOT_EQUALS, "%s was equal to %s");
            put(COMPARISON_EMPTY, "was not empty");
            put(COMPARISON_NOT_EMPTY, "was empty");
            put(COMPARISON_GTE, "%s was lower than %s");
            put(COMPARISON_GT, "%s was lower than or equal to %s");
            put(COMPARISON_LTE, "%s was greater than %s");
            put(COMPARISON_LT, "%s was greater than or equal to %s");
            put(COMPARISON_EQUALS_NUMBER, "%s was not equal to the number %s");
            put(COMPARISON_CONTAINS, "%s did not contain %s");
            put(COMPARISON_NOT_CONTAINS, "%s contained %s");
            put(COMPARISON_HAS_KEY, "%s did not have the key %s");
            put(COMPARISON_HAS_VALUE, "%s did not have the value %s");
            put(COMPARISON_IS_NULL, "was not null");
        }
    };

    public final void format(final RequestAssertion assertion, final StringBuilder sb) {
        formatStatus(assertion, sb);
        formatSource(assertion, sb);
        formatComparison(assertion, sb);
        formatError(assertion, sb);
    }

    protected void formatStatus(final RequestAssertion assertion, final StringBuilder sb) {
        if(assertion.getResult() == AssertionStatus.PASSED) {
            sb.append("OK : ");
        }
        else sb.append("KO : ");
    }

    protected void formatSource(RequestAssertion assertion, StringBuilder sb) {
        String source = assertion.getSource();
        if(!sourceMap.containsKey(source)) {
            sb.append(source);
        }
        else {
            sb.append(sourceMap.get(source));
        }

        if(!StringUtil.isEmptyOrSpaces(assertion.getProperty())) {
            sb.append(assertion.getProperty());
        }
        sb.append(" : ");
    }

    protected void formatError(final RequestAssertion assertion, final StringBuilder sb)
    {
        if(!StringUtil.isEmptyOrSpaces(assertion.getError())) {
            sb.append(" - ");
            sb.append(assertion.getError());
        }
    }

    protected void formatComparison(final RequestAssertion assertion, final StringBuilder sb) {
        Map<String, String> map;

        if(assertion.getResult() == AssertionStatus.PASSED) map = okMap;
        else map = koMap;

        if(!map.containsKey(assertion.getComparison())) {
            sb.append(assertion.getActualValue());
            sb.append(" ");
            sb.append(assertion.getComparison());
            sb.append(" ");
            sb.append(assertion.getTargetValue());
        }
        else {
            String comparison = String.format(map.get(assertion.getComparison()), assertion.getActualValue(), assertion.getTargetValue());
            sb.append(comparison);
        }
    }

    private boolean isUnaryComparison(final RequestAssertion assertion) {
        String comparison = assertion.getComparison();

        boolean unary = COMPARISON_IS_NULL.equals(comparison) ||
                COMPARISON_EMPTY.equals(comparison) ||
                COMPARISON_NOT_EMPTY.equals(comparison);

        return !unary;
    }
}
