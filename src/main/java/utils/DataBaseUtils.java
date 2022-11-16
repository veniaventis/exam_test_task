package utils;

import aquality.selenium.core.logging.Logger;
import constants.DataBaseConstant;
import lombok.SneakyThrows;
import models.ProjectTest;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;

public class DataBaseUtils {
    private static PreparedStatement preparedStatement;


    @SneakyThrows
    public static void setUpDriver(){
        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
    }

    public static void insertImageAttach(String contentPath, int testId){
        setUpDriver();
        try(FileInputStream fis = new FileInputStream(contentPath);
            Connection connection = DriverManager.getConnection(ConfigUtil.getSettingsData("dbUrl"), ConfigUtil.getConfidentialData("dbLogin"), ConfigUtil.getConfidentialData("dbPassword"));) {
            Logger.getInstance().debug("Sending img attachment to database");
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(DataBaseConstant.INSERT_IMG_ATTACH_QUERY);
            preparedStatement.setBinaryStream(1,fis);
            preparedStatement.setString(2,"image/png");
            preparedStatement.setInt(3,testId);
            preparedStatement.executeLargeUpdate();
            preparedStatement.close();
            connection.commit();
        } catch (SQLException | IOException ex) {
            Logger.getInstance().debug("Attachment cannot be send",ex);
        }
    }

    public static void insertLog(String contentPath, int testId){
        setUpDriver();
        try(FileInputStream fis = new FileInputStream(contentPath);
        InputStreamReader isr  = new InputStreamReader(fis, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(isr);
            Connection connection = DriverManager.getConnection(ConfigUtil.getSettingsData("dbUrl"), ConfigUtil.getConfidentialData("dbLogin"), ConfigUtil.getConfidentialData("dbPassword"))
        ){
            Logger.getInstance().debug("Sending log attachment to database");
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(DataBaseConstant.INSERT_LOG_QUERY);
            preparedStatement.setCharacterStream(1,reader);
            preparedStatement.setBoolean(2,false);
            preparedStatement.setInt(3,testId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.commit();
        } catch (SQLException | IOException ex) {
            Logger.getInstance().debug("Attachment cannot be send",ex);
        }
    }

    public static void insertTest(ProjectTest test){
        setUpDriver();
        ResultSet resultSet;
        try (Connection connection = DriverManager.getConnection(ConfigUtil.getSettingsData("dbUrl"), ConfigUtil.getConfidentialData("dbLogin"), ConfigUtil.getConfidentialData("dbPassword"));
             Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)){
            Logger.getInstance().debug("Sending test to database");
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(DataBaseConstant.INSERT_TEST_QUERY);
            preparedStatement.setString(1,test.getName());
            preparedStatement.setInt(2,test.getStatusId());
            preparedStatement.setString(3,test.getMethodName());
            preparedStatement.setInt(4,test.getProjectId());
            preparedStatement.setInt(5,test.getSessionId());
            preparedStatement.setTimestamp(6,test.getStartTime());
            preparedStatement.setTimestamp(7,test.getEndTime());
            preparedStatement.setString(8,test.getEnv());
            preparedStatement.setString(9,test.getBrowser());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.commit();
            resultSet = statement.executeQuery(String.format(DataBaseConstant.SELECT_ADDED_TEST,
                    test.getName(), test.getStatusId(), test.getMethodName(), test.getSessionId()));
            resultSet.next();
            test.setId(resultSet.getInt("id"));
        } catch (SQLException ex) {
            Logger.getInstance().debug("Test cannot be send",ex);
        }
        insertImageAttach(test.getScreenshotPath(),test.getId());
        insertLog(test.getLogPath(), test.getId());
    }

    public static String[][] selectTable(String sqlScript){
        Logger.getInstance().debug("Getting result set from database");
        setUpDriver();
        String[][] resultArray = null;
        ResultSet resultSet;
        try(Connection connection = DriverManager.getConnection(ConfigUtil.getSettingsData("dbUrl"), ConfigUtil.getConfidentialData("dbLogin"), ConfigUtil.getConfidentialData("dbPassword"));
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY)) {
            resultSet = statement.executeQuery(sqlScript);
            int columnAmount = resultSet.getMetaData().getColumnCount();
            resultSet.last();
            resultArray = new String[resultSet.getRow()][columnAmount];
            resultSet.beforeFirst();
            while (resultSet.next()){
                for (int i = 1; i <= columnAmount; i++) {
                    resultArray[resultSet.getRow()-1][i-1] = resultSet.getString(i);
                }
            }
        } catch (SQLException ex) {
            Logger.getInstance().debug("The result cannot be obtained",ex);
        }
        return resultArray;
    }
}
