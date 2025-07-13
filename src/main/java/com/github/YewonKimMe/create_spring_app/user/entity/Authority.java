package com.github.YewonKimMe.create_spring_app.user.entity;

import com.github.YewonKimMe.create_spring_app.common.utils.id.annotation.SnowflakeGenerated;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "USERS_ROLE")
public class Authority {

    @Id
    @GeneratedValue(generator = "snowflake")
    @SnowflakeGenerated
    @Column(name = "role_id")
    private Long id;

    @Column(name = "role_name", nullable = false)
    private String role;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
