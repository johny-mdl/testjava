package com.bet.repository.DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.bet.domain.Area4bet;
import com.bet.domain.Area4bet_;
import com.bet.domain.Competition4bet;
import com.bet.domain.Competition4bet_;

public class CompetitionDAO {

	public static Specification<Competition4bet> findByArea(final String name) {
		return new Specification<Competition4bet>() {

			private static final long serialVersionUID = 6450301142148045789L;

			@Override
			public Predicate toPredicate(Root<Competition4bet> competitionRoot, CriteriaQuery<?> query,
					CriteriaBuilder builder) {

				Join<Competition4bet, Area4bet> join = competitionRoot.join(Competition4bet_.area);

				List<Predicate> predicates = new ArrayList<Predicate>();

				if (name != null) {
					predicates.add(builder.and(builder.equal(join.get(Area4bet_.name), name.toLowerCase())));
				}
				Predicate[] predicatesArray = new Predicate[predicates.size()];
				return builder.and(predicates.toArray(predicatesArray));
			}
		};
	}
}
