package com.assignment.utils;

import com.assignment.constants.AccountConstants;
import com.assignment.entities.Account;
import com.assignment.exceptions.TransactionException;
import com.assignment.exceptions.URINotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static com.assignment.constants.AccountConstants.AMOUNT;
import static com.assignment.constants.AccountConstants.CORRELATION_ID;

@Slf4j
@Component
public class Utility {

    /**
     * Method to fetch the AP details from Service Registry
     *

     * @param discoveryClient
     * @param appName
     * @param apiUser
     * @param apiPass
     * @param apiBasePath

     */
    public WebClient  fetchProxyDetails( DiscoveryClient discoveryClient, String appName, String apiUser,
                                   String apiPass, String apiBasePath)
    {
        String hostPort= discoveryClient.getInstances(appName).
                stream().map(serviceInstance -> serviceInstance.getUri().toString())
                .findAny().orElseThrow(()-> new URINotFoundException("No such uri available"));
        return getWebClient(hostPort+apiBasePath, apiUser,apiPass);

    }

    /**
     * Builder for the webclient
     *
     * @param url
     * @param apiUser
     * @param apiPass
     * @return
     */

    private WebClient getWebClient(String url,String apiUser, String apiPass) {
        String encoded=Base64.getEncoder().
                encodeToString((apiUser+":"+apiPass).getBytes(StandardCharsets.UTF_8));
        return WebClient.builder().
                baseUrl(url)
                .defaultHeaders(header->header.setBasicAuth(encoded))
                .build();
    }

    /**
     * Another microservice call to execute a transaction
     *
     * @param createdAcc
     * @param creditamt
     * @param transactionClient
     * @param apiEndpoint
     * @param traceId
     */


    public void createTransaction(Account createdAcc,BigDecimal creditamt, WebClient transactionClient,
                                  String apiEndpoint, String traceId)
    {
        transactionClient.post().uri(uriBuilder -> uriBuilder.
                path(apiEndpoint).queryParam(AMOUNT,creditamt).build()).
                headers(httpHeaders ->{
                    httpHeaders.set(CORRELATION_ID, traceId);
                })
                .accept(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE)).
                bodyValue(createdAcc).exchangeToMono(
                        clientResponse ->
                                Mono.just(String.valueOf(clientResponse.statusCode().value())))
                .doOnError(e-> {
                    log.error(e.getMessage());
                    throw new TransactionException("Technical Error when creating a transaction entry");})
                .blockOptional().ifPresent(response->{
                    log.info("Response received for the transaction API"+response);
                    if(response.equals("201"))
                    {
                        log.info("Transaction created for the transaction for requestId: {}" , traceId );
                    }
                    else {
                        log.error("Error while transaction insertion for requestId: {}" , traceId );
                        throw new TransactionException("Error while transaction insertion for the newly account"+createdAcc.getAccountId());

                    }
                });
    }
}
