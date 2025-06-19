package web.model.entity;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;

public class AuditListener {

    @PrePersist
    public void onPrePersist() {  }

    @PreUpdate
    public void onPreUpdate() { }

    @PreRemove
    public void onPreRemove() {  }

}
