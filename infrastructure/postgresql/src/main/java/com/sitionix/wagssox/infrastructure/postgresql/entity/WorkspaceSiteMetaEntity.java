package com.sitionix.wagssox.infrastructure.postgresql.entity;

import com.sitionix.wagssox.domain.WorkspaceSiteMetaStatus;
import com.sitionix.wagssox.domain.WorkspaceSiteMetaType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "workspace_site_meta",
        indexes = {
                @Index(name = "idx_workspace_site_meta_owner_updated", columnList = "owner_user_id,updated_at")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceSiteMetaEntity {

    @Id
    @Column(name = "site_id", nullable = false)
    private UUID siteId;

    @Column(name = "owner_user_id", nullable = false)
    private Long ownerUserId;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private WorkspaceSiteMetaStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private WorkspaceSiteMetaType type;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}
