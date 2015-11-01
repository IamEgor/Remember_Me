package yegor_gruk.example.com.rememberme.DataBase;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "DatabaseModel")
public class DatabaseModel {

    public static final String TABLE_NAME = "DatabaseModel";

    public static final String ID = "_id";
    public static final String TIME = "repTime";
    public static final String IS_ACTIVE = "isActive";

    @DatabaseField(generatedId = true, columnName = ID)
    private Long id;

    @DatabaseField(columnName = TIME)
    private long repTime;

    @DatabaseField(columnName = IS_ACTIVE)
    private boolean isActive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getRepTime() {
        return repTime;
    }

    public void setRepTime(long repTime) {
        this.repTime = repTime;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "DatabaseModel{" +
                "id=" + id +
                ", repTime=" + repTime +
                ", isActive=" + isActive +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DatabaseModel that = (DatabaseModel) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
