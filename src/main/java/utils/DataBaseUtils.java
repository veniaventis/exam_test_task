package utils;

import aquality.selenium.core.logging.Logger;
import constants.DataBaseConstant;
import models.ProjectTest;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;

public class DataBaseUtils {
    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement preparedStatement;

    public static void connect() {
        try {
            Logger.getInstance().debug("Connecting to the mySQL database");
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            connection = DriverManager.getConnection(ConfigUtil.getSettingsData("dbUrl"), ConfigUtil.getConfidentialData("dbLogin"), ConfigUtil.getConfidentialData("dbPassword"));
            connection.setAutoCommit(false);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
        } catch (Exception ex) {
            Logger.getInstance().debug("Couldn't connect to the database",ex);
        }
    }

    public static void insertImageAttach(String contentPath, int testId){
        connect();
        try(FileInputStream fis = new FileInputStream(contentPath)){
            Logger.getInstance().debug("Sending img attachment to database");
            preparedStatement = connection.prepareStatement(DataBaseConstant.INSERT_IMG_ATTACH_QUERY);
            preparedStatement.setBinaryStream(1,fis);
            preparedStatement.setString(2,"image/png");
            preparedStatement.setInt(3,testId);
            preparedStatement.executeLargeUpdate();
            preparedStatement.close();
            connection.commit();
        } catch (SQLException | FileNotFoundException ex) {
            Logger.getInstance().debug("Attachment cannot be send",ex);
        } catch (IOException e) {
            e.printStackTrace();
        }
        disconnect();
    }

    public static void insertLog(String contentPath, int testId){
        connect();
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader reader = null;
        try{
            Logger.getInstance().debug("Sending log attachment to database");
            fis = new FileInputStream(contentPath);
            isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            reader = new BufferedReader(isr);
            preparedStatement = connection.prepareStatement(DataBaseConstant.INSERT_LOG_QUERY);
            preparedStatement.setCharacterStream(1,reader);
            preparedStatement.setBoolean(2,false);
            preparedStatement.setInt(3,testId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.commit();
        } catch (SQLException | FileNotFoundException ex) {
            Logger.getInstance().debug("Attachment cannot be send",ex);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        disconnect();
    }

    public static void insertTest(ProjectTest test){
        connect();
        ResultSet resultSet = null;
        try {
            Logger.getInstance().debug("Sending test to database");
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
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        disconnect();
        insertImageAttach(test.getScreenshotPath(),test.getId());
        insertLog(test.getLogPath(), test.getId());
    }

    public static String[][] selectTable(String sqlScript){
        connect();
        Logger.getInstance().debug("Getting result set from database");
        String[][] resultArray = null;
        ResultSet resultSet = null;
        try {
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
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        disconnect();
        return resultArray;
    }

    public static void disconnect() {
        try {
            Logger.getInstance().debug("Disconnecting from database");
            connection.close();
        } catch (SQLException ex) {
            Logger.getInstance().debug("Connection cannot be disconnected",ex);
        }
    }
}
