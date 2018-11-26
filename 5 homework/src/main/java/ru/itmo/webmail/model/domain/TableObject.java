package ru.itmo.webmail.model.domain;

import java.util.Date;

public interface TableObject {
    public long getId();
    public void setId(long id);
    public Date getCreationTime();
    public void setCreationTime(Date creationTime);
}
