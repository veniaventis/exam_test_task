package queries;

import models.ProjectTest;

import java.sql.Timestamp;

public class DataBaseRequest {
    public ProjectTest sendNewTest(int id) {
        return new ProjectTest(this.getClass().getSimpleName(),
                Thread.currentThread().getStackTrace()[1].getMethodName(), id,
                new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
    }
}
