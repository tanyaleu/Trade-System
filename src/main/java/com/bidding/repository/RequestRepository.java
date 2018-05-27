package com.bidding.repository;

import com.bidding.domain.Request;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Request entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query("select request from Request request where request.requester.login = ?#{principal.username}")
    List<Request> findByRequesterIsCurrentUser();

    List<Request> findByType(int type);

    @Query("select r from Request r where r.product like ?1 and r.price = ?2 and r.itemsCount = ?3 and r.type = ?4")
    List<Request> findQuery(String product, int price, int itemsCount, int type);

}
