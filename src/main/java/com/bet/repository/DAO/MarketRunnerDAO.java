//package com.bet.repository.DAO;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Join;
//import javax.persistence.criteria.Predicate;
//import javax.persistence.criteria.Root;
//
//import org.springframework.data.jpa.domain.Specification;
//
//import com.bet.domain.Market4bet;
//import com.bet.domain.Market4bet_;
//import com.bet.domain.MarketRunner4bet;
//import com.bet.domain.MarketRunner4bet_;
//import com.bet.domain.Runner4bet;
//import com.bet.domain.Runner4bet_;
//import com.bet.domain.enums.MarketType;
//import com.bet.domain.enums.RunnerType;
//
//public class MarketRunnerDAO {
//
//	public static Specification<MarketRunner4bet> findByMarketAndRunnerType(final MarketType marketType,
//			final RunnerType runnerType) {
//		return new Specification<MarketRunner4bet>() {
//
//			private static final long serialVersionUID = 6450301142148045789L;
//
//			@Override
//			public Predicate toPredicate(Root<MarketRunner4bet> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
//
//				Join<MarketRunner4bet, Market4bet> joinMarket = root.join(MarketRunner4bet_.market);
//
//				Join<MarketRunner4bet, Runner4bet> joinRunner = root.join(MarketRunner4bet_.runner);
//
//				List<Predicate> predicates = new ArrayList<Predicate>();
//
//				if (marketType != null) {
//					predicates.add(builder.and(builder.equal(joinMarket.get(Market4bet_.marketType), marketType)));
//				}
//				if (runnerType != null) {
//					predicates.add(builder.and(builder.equal(joinRunner.get(Runner4bet_.runnerType), runnerType)));
//				}
//
//				Predicate[] predicatesArray = new Predicate[predicates.size()];
//				return builder.and(predicates.toArray(predicatesArray));
//			}
//		};
//	}
//}
