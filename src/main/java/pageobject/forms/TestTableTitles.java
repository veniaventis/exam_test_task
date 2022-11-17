package pageobject.forms;

public enum TestTableTitles {
    TEST_NAME("1"),
    TEST_METHOD("2"),
    LATEST_TEST_RESULT("3"),
    LATEST_TEST_START_TIME("4"),
    LATEST_TEST_END_TIME("5"),
    LATEST_TEST_DURATION("6"),
    HISTORY("7");

    private final String title;
    TestTableTitles(String title) {
        this.title = title;
    }

    public String getTitle(){return title;}
}
