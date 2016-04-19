package com.mangopay.teamcity.runscope;

import com.intellij.openapi.util.text.StringUtil;
import com.mangopay.teamcity.runscope.model.BinaryStatus;
import com.mangopay.teamcity.runscope.model.RequestAssertion;

import java.util.HashMap;
import java.util.Map;

class AssertionFormatter {

    private static final Map<String, String> SOURCE_MAP;
    private static final Map<String, String> OK_MAP;
    private static final Map<String, String> KO_MAP;

    static {
        SOURCE_MAP = new HashMap<String, String>();
        SOURCE_MAP.put(AssertionConstants.SOURCE_JSON, "body.");
        SOURCE_MAP.put(AssertionConstants.SOURCE_XML, "body.");
        SOURCE_MAP.put(AssertionConstants.SOURCE_HEADER, "header.");
        SOURCE_MAP.put(AssertionConstants.SOURCE_SIZE, "Response (bytes)");
        SOURCE_MAP.put(AssertionConstants.SOURCE_TIME, "Response time (ms)");
        SOURCE_MAP.put(AssertionConstants.SOURCE_STATUS, "Status");

        OK_MAP = new HashMap<String, String>();
        OK_MAP.put(AssertionConstants.COMPARISON_EQUALS, "'%s' was equal to '%s'");
        OK_MAP.put(AssertionConstants.COMPARISON_NOT_EQUALS, "'%s' was not equal to '%s'");
        OK_MAP.put(AssertionConstants.COMPARISON_EMPTY, "was empty");
        OK_MAP.put(AssertionConstants.COMPARISON_NOT_EMPTY, "was not empty");
        OK_MAP.put(AssertionConstants.COMPARISON_GTE, "'%s' was greater than or equal to %s");
        OK_MAP.put(AssertionConstants.COMPARISON_GT, "'%s' was greater than %s");
        OK_MAP.put(AssertionConstants.COMPARISON_LTE, "'%s' was lower than or equal to %s");
        OK_MAP.put(AssertionConstants.COMPARISON_LT, "'%s' was lower than %s");
        OK_MAP.put(AssertionConstants.COMPARISON_EQUALS_NUMBER, "'%s' was equal to the number %s");
        OK_MAP.put(AssertionConstants.COMPARISON_CONTAINS, "'%s' contained '%s'");
        OK_MAP.put(AssertionConstants.COMPARISON_NOT_CONTAINS, "'%s' did not contained '%s'");
        OK_MAP.put(AssertionConstants.COMPARISON_HAS_KEY, "'%s' had the key '%s'");
        OK_MAP.put(AssertionConstants.COMPARISON_HAS_VALUE, "'%s' had the value '%s'");
        OK_MAP.put(AssertionConstants.COMPARISON_IS_NULL, "was null");

        KO_MAP = new HashMap<String, String>();
        KO_MAP.put(AssertionConstants.COMPARISON_EQUALS, "'%s' was not equal to '%s'");
        KO_MAP.put(AssertionConstants.COMPARISON_NOT_EQUALS, "'%s' was equal to '%s'");
        KO_MAP.put(AssertionConstants.COMPARISON_EMPTY, "was not empty");
        KO_MAP.put(AssertionConstants.COMPARISON_NOT_EMPTY, "was empty");
        KO_MAP.put(AssertionConstants.COMPARISON_GTE, "'%s' was lower than %s");
        KO_MAP.put(AssertionConstants.COMPARISON_GT, "'%s' was lower than or equal to %s");
        KO_MAP.put(AssertionConstants.COMPARISON_LTE, "'%s' was greater than %s");
        KO_MAP.put(AssertionConstants.COMPARISON_LT, "'%s' was greater than or equal to %s");
        KO_MAP.put(AssertionConstants.COMPARISON_EQUALS_NUMBER, "'%s' was not equal to the number %s");
        KO_MAP.put(AssertionConstants.COMPARISON_CONTAINS, "'%s' did not contain '%s'");
        KO_MAP.put(AssertionConstants.COMPARISON_NOT_CONTAINS, "'%s' contained '%s'");
        KO_MAP.put(AssertionConstants.COMPARISON_HAS_KEY, "'%s' did not have the key '%s'");
        KO_MAP.put(AssertionConstants.COMPARISON_HAS_VALUE, "'%s' did not have the value '%s'");
        KO_MAP.put(AssertionConstants.COMPARISON_IS_NULL, "was not null");
    }

    public String format(final RequestAssertion assertion) {
        final StringBuilder sb = new StringBuilder();
        formatStatus(assertion, sb);
        formatSource(assertion, sb);
        formatComparison(assertion, sb);
        formatError(assertion, sb);

        return sb.toString();
    }

    protected void formatStatus(final RequestAssertion assertion, final StringBuilder sb) {
        if(assertion.getResult() == BinaryStatus.PASSED) {
            sb.append("OK : ");
        }
        else sb.append("KO : ");
    }

    protected void formatSource(final RequestAssertion assertion, final StringBuilder sb) {
        final String source = assertion.getSource();
        if (SOURCE_MAP.containsKey(source)) {
            sb.append(SOURCE_MAP.get(source));
        } else {
            sb.append(source);
        }

        final String property = assertion.getProperty();
        if(!StringUtil.isEmptyOrSpaces(property)) {
            sb.append(property);
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

        if(assertion.getResult() == BinaryStatus.PASSED) map = OK_MAP;
        else map = KO_MAP;

        final String comparison = assertion.getComparison();
        if (map.containsKey(comparison)) {
            final String message = String.format(map.get(comparison), assertion.getActualValue(), assertion.getTargetValue());
            sb.append(message);
        } else {
            sb.append(assertion.getActualValue());
            sb.append(' ');
            sb.append(comparison);
            sb.append(' ');
            sb.append(assertion.getTargetValue());
        }
    }
}
