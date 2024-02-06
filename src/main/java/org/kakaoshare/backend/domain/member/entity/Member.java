package org.kakaoshare.backend.domain.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Getter;
import org.kakaoshare.backend.domain.base.entity.BaseTimeEntity;
import org.kakaoshare.backend.domain.orders.entity.Orders;
import org.kakaoshare.backend.domain.funding.entity.Funding;


@Entity
@Getter
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, length = 20)
    private String phoneNumber;

    @Column
    private String kakaoId;

    @Column
    private String kakaoUuid;

    @OneToMany(mappedBy = "member")
    private List<Orders> orders;

    @OneToMany(mappedBy = "member")
    private List<Funding> funding;

}
