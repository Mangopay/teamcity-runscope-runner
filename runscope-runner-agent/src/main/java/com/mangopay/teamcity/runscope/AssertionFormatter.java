package com.mangopay.teamcity.runscope;

import com.intellij.openapi.util.text.StringUtil;
import com.mangopay.teamcity.runscope.model.AssertionStatus;
import com.mangopay.teamcity.runscope.model.RequestAssertion;

import java.util.HashMap;
import java.util.Map;

class AssertionFormatter {

    private static final Map<String, String> sourceMap = new HashMap<String, String>() {
        {
            put(AssertionConstants.SOURCE_JSON, "body.");
            put(AssertionConstants.SOURCE_XML, "body.");
            put(AssertionConstants.SOURCE_HEADER, "header.");
            put(AssertionConstants.SOURCE_SIZE, "Response (bytes)");
            put(AssertionConstants.SOURCE_TIME, "Response time (ms)");
            put(AssertionConstants.SOURCE_STATUS, "Status");
        }
    };

    private static final Map<String, String> okMap = new HashMap<String, String>() {
        {
            put(AssertionConstants.COMPARISON_EQUALS, "'%s' was equal to '%s'");
            put(AssertionConstants.COMPARISON_NOT_EQUALS, "'%s' was not equal to '%s'");
            put(AssertionConstants.COMPARISON_EMPTY, "was empty");
            put(AssertionConstants.COMPARISON_NOT_EMPTY, "was not empty");
            put(AssertionConstants.COMPARISON_GTE, "'%s' was greater than or equal to %s");
            put(AssertionConstants.COMPARISON_GT, "'%s' was greater than %s");
            put(AssertionConstants.COMPARISON_LTE, "'%s' was lower than or equal to %s");
            put(AssertionConstants.COMPARISON_LT, "'%s' was lower than %s");
            put(AssertionConstants.COMPARISON_EQUALS_NUMBER, "'%s' was equal to the number %s");
            put(AssertionConstants.COMPARISON_CONTAINS, "'%s' contained '%s'");
            put(AssertionConstants.COMPARISON_NOT_CONTAINS, "'%s' did not contained '%s'");
            put(AssertionConstants.COMPARISON_HAS_KEY, "'%s' had the key '%s'");
            put(AssertionConstants.COMPARISON_HAS_VALUE, "'%s' had the value '%s'");
            put(AssertionConstants.COMPARISON_IS_NULL, "was null");
        }
    };

    private static final Map<String, String> koMap = new HashMap<String, String>() {
        {
            put(AssertionConstants.COMPARISON_EQUALS, "'%s' was not equal to '%s'");
            put(AssertionConstants.COMPARISON_NOT_EQUALS, "'%s' was equal to '%s'");
            put(AssertionConstants.COMPARISON_EMPTY, "was not empty");
            put(AssertionConstants.COMPARISON_NOT_EMPTY, "was empty");
            put(AssertionConstants.COMPARISON_GTE, "'%s' was lower than %s");
            put(AssertionConstants.COMPARISON_GT, "'%s' was lower than or equal to %s");
            put(AssertionConstants.COMPARISON_LTE, "'%s' was greater than %s");
            put(AssertionConstants.COMPARISON_LT, "'%s' was greater than or equal to %s");
            put(AssertionConstants.COMPARISON_EQUALS_NUMBER, "'%s' was not equal to the number %s");
            put(AssertionConstants.COMPARISON_CONTAINS, "'%s' did not contain '%s'");
            put(AssertionConstants.COMPARISON_NOT_CONTAINS, "'%s' contained '%s'");
            put(AssertionConstants.COMPARISON_HAS_KEY, "'%s' did not have the key '%s'");
            put(AssertionConstants.COMPARISON_HAS_VALUE, "'%s' did not have the value '%s'");
            put(AssertionConstants.COMPARISON_IS_NULL, "was not null");
        }
    };

    public final String format(final RequestAssertion assertion) {
        final StringBuilder sb = new StringBuilder();
        formatStatus(assertion, sb);
        formatSource(assertion, sb);
        formatComparison(assertion, sb);
        formatError(assertion, sb);

        return sb.toString();
    }

    protected void formatStatus(final RequestAssertion assertion, final StringBuilder sb) {
        if(assertion.getResult() == AssertionStatus.PASSED) {
            sb.append("OK : ");
        }
        else sb.append("KO : ");
    }

    protected void formatSource(final RequestAssertion assertion, final StringBuilder sb) {
        final String source = assertion.getSource();
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
        final Map<String, String> map;

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
}
