package com.devopsbuddy.backend.persistence.repositories;

import com.devopsbuddy.backend.persistence.domain.backend.Plan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by larris on 05/09/16.
 */
@Repository
public interface PlanRepository extends CrudRepository<Plan,Integer> {
}
