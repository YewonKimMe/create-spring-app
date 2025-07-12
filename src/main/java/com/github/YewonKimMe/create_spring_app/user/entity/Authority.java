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
@Table(name = "USER_ROLE")
public class Authority {

    @Id
    @GeneratedValue(generator = "snowflake")
    @SnowflakeGenerated
    @Column(name = "ROLE_ID")
    private Long id;

    @Column(name = "ROLE_NAME", nullable = false)
    private String role;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;
}
