package models;

import aquality.selenium.browser.AqualityServices;
import constants.CommonConstant;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ProjectTest {
    private int id;
    private String name;
    private int statusId;
    private String methodName;
    private int projectId;
    private int sessionId;
    private Timestamp startTime;
    private Timestamp endTime;
    private String env;
    private String browser;
    private String screenshotPath;
    private String logPath;

    public ProjectTest(String name, String methodName, int projectId, Timestamp startTime, Timestamp endTime) {
        this.name = name;
        this.methodName = methodName;
        this.projectId = projectId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.env = System.getenv("OS");
        this.browser = AqualityServices.getBrowser().getBrowserName().toString();
    }
}

