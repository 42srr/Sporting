package kr.sporting.team.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;
@EntityListeners(AuditingEntityListener.class)

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "sporting_team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "team_name", nullable = false)
    @NotBlank
    private String teamName;

    @Column(name = "address", nullable = false)
    @NotBlank
    private String address;

    @Transient
    @Column(name = "records", nullable = false)
    private List<Object> records;

    @Transient
//    @ManyToMany
//    @JoinColumn(name = "member_id", referencedColumnName = "id") // member 구현 후 foreign key로 대체
    private Object member; // 'Object'는 나중에 Member로 변경

    @Column(name = "team_size", nullable = false)
    private Integer teamSize;

}