package com.mangopay.teamcity.agent;

interface AssertionConstants {
    String SOURCE_JSON = "json";
    String SOURCE_XML = "xml";
    String SOURCE_HEADER = "response_header";
    String SOURCE_SIZE = "response_size_bytes";
    String SOURCE_TIME = "response_time_ms";
    String SOURCE_STATUS = "status_code";

    String COMPARISON_EQUALS = "equals";
    String COMPARISON_NOT_EQUALS = "does_not_equal";
    String COMPARISON_EMPTY = "is_empty";
    String COMPARISON_NOT_EMPTY = "is_not_empty";
    String COMPARISON_GTE = "greater_than_or_equal";
    String COMPARISON_GT = "greater_than";
    String COMPARISON_LTE = "less_than_or_equal";
    String COMPARISON_LT = "less_than";
    String COMPARISON_EQUALS_NUMBER = "equals_number";
    String COMPARISON_CONTAINS = "contains";
    String COMPARISON_NOT_CONTAINS = "does_not_contain";
    String COMPARISON_HAS_KEY = "has_key";
    String COMPARISON_HAS_VALUE = "has_value";
    String COMPARISON_IS_NULL = "is_null";
}
