package spring.security.conquer.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "RESOURCES")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@EntityListeners(value = { AuditingEntityListener.class })
@Builder
@AllArgsConstructor
public class Resources implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resource_id")
    private Long id;
    @Column
    private String resourceName;
    @Column
    private String httpMethod;
    @Column
    private int orderNum;
    @Column
    private String resourceType;

    @ManyToMany
    @JoinTable(name = "role_resources", joinColumns = {
            @JoinColumn(name = "resource_id")
    }, inverseJoinColumns = @JoinColumn(name = "role_id"))
    @ToString.Exclude
    private Set<Role> roleSet = new HashSet<>();
}
