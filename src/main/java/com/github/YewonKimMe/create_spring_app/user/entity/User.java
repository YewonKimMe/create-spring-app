package com.github.YewonKimMe.create_spring_app.user.entity;

import com.github.YewonKimMe.create_spring_app.common.utils.id.annotation.SnowflakeGenerated;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(generator = "snowflake")
    @SnowflakeGenerated
    private Long id;

    @Column(name = "user_id", length = 60, nullable = false)
    private String userId;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(name = "second_password", length = 100, nullable = false)
    private String secondPassword;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Integer credit = 0;

    @Column(name = "is_verified", nullable = false)
    private Boolean isVerified = false;

    @Column(name = "is_blocked", nullable = false)
    private Boolean isBlocked = false;

    @Column(name = "is_expired", nullable = false)
    private Boolean isExpired = false;

    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled = false;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Authority> authorities = new HashSet<>();
}
