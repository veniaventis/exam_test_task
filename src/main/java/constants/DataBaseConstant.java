package constants;

public final class DataBaseConstant {

    public static final String SELECT_TESTS_LIST_QUERY = "SELECT t.name as test_name FROM project as p CROSS JOIN test as t ON p.id = t.project_id WHERE p.name='%s' ORDER BY start_time DESC";
    public static final String INSERT_TEST_QUERY = "INSERT INTO test (name,status_id,method_name,project_id,session_id,start_time,end_time,env,browser) VALUES (?,?,?,?,?,?,?,?,?)";
    public static final String INSERT_LOG_QUERY = "INSERT INTO log (content,is_exception,test_id) VALUES (?,?,?)";
    public static final String INSERT_IMG_ATTACH_QUERY = "INSERT INTO attachment (content,content_type,test_id) VALUES (?,?,?)";
    public static final String SELECT_ADDED_TEST = "SELECT * FROM test WHERE name='%s' and status_id=%s and method_name='%s' and session_id=%s";


}
