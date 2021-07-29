package com.bol.mancala.constant;

import java.util.Arrays;
import java.util.List;

public class MancalaBoardTestCase {
    public static final List<Integer> TEST_CASE_WINNER_1 =
            Arrays.asList(2, 8, 1, 13, 6, 13, 12, 4, 13, 12, 2, 8, 6, 5, 13, 11, 6, 10, 5, 13, 12, 4);
    public static final String TEST_CASE_WINNER_1_EXPECTED_RESULT_JSON = "testcase/expected-mancalaboarddto-1.json";

    public static final List<Integer> TEST_CASE_WINNER_2 =
            Arrays.asList(9, 1, 8, 2, 10, 1, 9, 2, 8, 3, 9, 4, 8, 6, 9, 1);
    public static final String TEST_CASE_WINNER_2_EXPECTED_RESULT_JSON = "testcase/expected-mancalaboarddto-2.json";

    public static final String TEST_CASE_SOW_JSON_1 = "testcase/sow-mancalaboard-1.json";
    public static final String TEST_CASE_SOW_JSON_2 = "testcase/sow-mancalaboard-2.json";
    public static final String TEST_CASE_SOW_EXPECTED_RESULT_JSON_1 = "testcase/expected-sow-mancalaboard-1.json";
    public static final String TEST_CASE_SOW_EXPECTED_RESULT_JSON_2 = "testcase/expected-sow-mancalaboard-2.json";



}
