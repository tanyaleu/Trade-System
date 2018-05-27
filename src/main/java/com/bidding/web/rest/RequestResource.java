package com.bidding.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bidding.domain.Request;

import com.bidding.repository.RequestRepository;
import com.bidding.web.rest.errors.BadRequestAlertException;
import com.bidding.web.rest.util.HeaderUtil;
import com.bidding.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Request.
 */
@RestController
@RequestMapping("/api")
public class RequestResource {

    private final Logger log = LoggerFactory.getLogger(RequestResource.class);

    private static final String ENTITY_NAME = "request";

    private final RequestRepository requestRepository;

    public RequestResource(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    /**
     * POST  /requests : Create a new request.
     *
     * @param request the request to create
     * @return the ResponseEntity with status 201 (Created) and with body the new request, or with status 400 (Bad Request) if the request has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/requests")
    @Timed
    public ResponseEntity<Request> createRequest(@Valid @RequestBody Request request) throws URISyntaxException {
        log.debug("REST request to save Request : {}", request);
        if (request.getId() != null) {
            throw new BadRequestAlertException("A new request cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Request result = requestRepository.save(request);
        return ResponseEntity.created(new URI("/api/requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /requests : Updates an existing request.
     *
     * @param request the request to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated request,
     * or with status 400 (Bad Request) if the request is not valid,
     * or with status 500 (Internal Server Error) if the request couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/requests")
    @Timed
    public ResponseEntity<Request> updateRequest(@Valid @RequestBody Request request) throws URISyntaxException {
        log.debug("REST request to update Request : {}", request);
        if (request.getId() == null) {
            return createRequest(request);
        }
        Request result = requestRepository.save(request);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, request.getId().toString()))
            .body(result);
    }

    /**
     * GET  /requests : get all the requests.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of requests in body
     */
    @GetMapping("/requests")
    @Timed
    public ResponseEntity<List<Request>> getAllRequests(Pageable pageable) {
        log.debug("REST request to get a page of Requests");
        Page<Request> page = requestRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/requests");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /requests/:id : get the "id" request.
     *
     * @param id the id of the request to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the request, or with status 404 (Not Found)
     */
    @GetMapping("/requests/{id}")
    @Timed
    public ResponseEntity<Request> getRequest(@PathVariable Long id) {
        log.debug("REST request to get Request : {}", id);
        Request request = requestRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(request));
    }

    /**
     * DELETE  /requests/:id : delete the "id" request.
     *
     * @param id the id of the request to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/requests/{id}")
    @Timed
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        log.debug("REST request to delete Request : {}", id);
        requestRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @PostMapping("/requests/process")
    @Timed
    public ResponseEntity<Void> processRequests() {
        log.debug("REST request to process Request : {}");
        List<Request> sellRequests =  requestRepository.findByType(Request.SELL);
        List<Request> allBuyRequests =  requestRepository.findByType(Request.BUY);
        System.out.println("sell Requests before end of day:" + sellRequests.size());
        System.out.println("buy Requests before end of day:" + allBuyRequests.size());

        for (Iterator<Request> sellRequestIterator = sellRequests.iterator(); sellRequestIterator.hasNext();) {
            Request sellRequest = sellRequestIterator.next();
            List<Request> buyRequests =   requestRepository.findQuery(sellRequest.getProduct(), sellRequest.getPrice(),sellRequest.getItemsCount(),Request.BUY);



            if (buyRequests.size()>0) {
                Request buyRequest = buyRequests.get(0);
                sellRequest.takeResult();
                buyRequest.takeResult();

                requestRepository.delete(buyRequest);
                requestRepository.delete(sellRequest);
            }
            System.out.println("sell Requests after end of day:" + sellRequests.size());
            System.out.println("buy Requests after end of day:" + buyRequests.size());

        }
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("Request processed","" )).build();
    }
}
