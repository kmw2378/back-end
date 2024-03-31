package org.kakaoshare.backend.domain.funding.repository.query;

import static org.kakaoshare.backend.common.util.sort.SortUtil.MOST_RECENT;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;

import org.kakaoshare.backend.common.util.sort.SortUtil;
import org.kakaoshare.backend.common.util.sort.SortableRepository;
import org.kakaoshare.backend.domain.funding.dto.FundingResponse;
import org.kakaoshare.backend.domain.funding.entity.Funding;
import org.kakaoshare.backend.domain.funding.entity.QFunding;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FundingRepositoryCustomImpl implements FundingRepositoryCustom, SortableRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<FundingResponse> findByProductIdAndMemberId(Long productId, Long memberId) {

        Funding funding = queryFactory
                .selectFrom(QFunding.funding)
                .where(QFunding.funding.product.productId.eq(productId)
                        .and(QFunding.funding.member.memberId.eq(memberId)))
                .fetchOne();

        return Optional.ofNullable(funding).map(FundingResponse::from);
    }

    @Override
    public Optional<Funding> findByIdAndMemberId(Long fundingId, Long memberId) {
        Funding funding = queryFactory
                .selectFrom(QFunding.funding)
                .where(fundingAndMemberPredicate(fundingId, memberId))
                .fetchOne();
        return Optional.ofNullable(funding);
    }

    private BooleanExpression fundingAndMemberPredicate(Long fundingId, Long memberId) {
        return QFunding.funding.fundingId.eq(fundingId)
                .and(QFunding.funding.member.memberId.eq(memberId));
    }

    @Override
    public List<Funding> findAllByMemberId(Long memberId) {
        return queryFactory
                .selectFrom(QFunding.funding)
                .where(QFunding.funding.member.memberId.eq(memberId))
                .fetch();
    }

    @Override
    public Slice<Funding> findFundingByMemberIdWithSlice(Long memberId, Pageable pageable) {
        List<Funding> content = queryFactory
                .selectFrom(QFunding.funding)
                .where(QFunding.funding.member.memberId.eq(memberId))
                .orderBy(SortUtil.from(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1) // 다음 페이지 존재 여부 확인을 위해 1 추가
                .fetch();

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(content.size() - 1);
            hasNext = true;
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }

    @Override
    public OrderSpecifier<?>[] getOrderSpecifiers(Pageable pageable) {
        return Stream.concat(Stream.of(SortUtil.from(pageable)), Stream.of(MOST_RECENT)).toArray(OrderSpecifier[]::new);
    }
}