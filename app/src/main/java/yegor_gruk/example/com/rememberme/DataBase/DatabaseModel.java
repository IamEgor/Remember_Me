package yegor_gruk.example.com.rememberme.DataBase;

/**
 * Created by Egor on 21.10.2015.
 */

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class DatabaseModel {

    public static final String NAME_FIELD_ID = "id";
    public static final String NAME_FIELD_TIME = "repTime";
    public static final String NAME_FIELD_ACTIVE = "isActive";

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField
    private long repTime;

    @DatabaseField
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
