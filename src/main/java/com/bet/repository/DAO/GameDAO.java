package com.bet.repository.DAO;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.bet.domain.Competition4bet;
import com.bet.domain.Game4bet;
import com.bet.domain.Game4bet_;
import com.bet.util.DateUtils;

public class GameDAO {

	public static Specification<Game4bet> findAllByCompetitionDate(final Competition4bet competition, final Long date) {
		return new Specification<Game4bet>() {

			private static final long serialVersionUID = 6450301142148045789L;

			@Override
			public Predicate toPredicate(Root<Game4bet> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> predicates = new ArrayList<Predicate>();

				if (competition != null) {
					predicates.add(builder.and(builder.equal(root.get(Game4bet_.competition), competition)));
				}
				if (date != null) {
					predicates.add(builder.and(builder.between(root.get(Game4bet_.date),
							DateUtils.miliTruncateDay(date), DateUtils.miliTruncateDay(DateUtils.addDays(date, 1)))));
				}
				Predicate[] predicatesArray = new Predicate[predicates.size()];
				return builder.and(predicates.toArray(predicatesArray));
			}
		};
	}

	public static Specification<Game4bet> findAllByCompetitionMatchDay(Competition4bet competition, Integer matchDay) {
		return new Specification<Game4bet>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Game4bet> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> predicates = new ArrayList<Predicate>();

				if (competition != null) {
					predicates.add(builder.and(builder.equal(root.get(Game4bet_.competition), competition)));
				}
				if (matchDay != null) {
					predicates.add(builder.and(builder.equal(root.get(Game4bet_.matchDay), matchDay)));
				}
				Predicate[] predicatesArray = new Predicate[predicates.size()];
				return builder.and(predicates.toArray(predicatesArray));
			}

		};
	}

	public static Specification<Game4bet> findNextGames(Competition4bet competition) {
		return new Specification<Game4bet>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Game4bet> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> predicates = new ArrayList<Predicate>();

				if (competition != null) {
					predicates.add(builder.and(builder.equal(root.get(Game4bet_.competition), competition)));
				}
				predicates.add(builder.greaterThanOrEqualTo(root.get(Game4bet_.date),
						DateUtils.miliTruncateDay(Instant.now().toEpochMilli())));

				Predicate[] predicatesArray = new Predicate[predicates.size()];
				query.orderBy(builder.asc(root.get(Game4bet_.date)));
				return builder.and(predicates.toArray(predicatesArray));
			}

		};
	}
}
