package com.example.swd392.service;

import com.example.swd392.Request.PreorderRequestRequest.CreatePreorderRequestRequest;
import com.example.swd392.Response.PreorderRequestResponse.CreatePreorderRequestResponse;
import com.example.swd392.Response.PreorderRequestResponse.DeletePreorderRequestResponse;
import com.example.swd392.Response.PreorderRequestResponse.FindPreorderRequestResponse;
import com.example.swd392.Response.PreorderRequestResponse.ListPreorderRequestResponse;
import org.springframework.http.ResponseEntity;

public interface PreorderRequestService {

    ResponseEntity<CreatePreorderRequestResponse> createPreorderRequest(CreatePreorderRequestRequest request);

    ResponseEntity<CreatePreorderRequestResponse> updatePreorderRequest(int preorderRequestId, CreatePreorderRequestRequest request);

    ResponseEntity<DeletePreorderRequestResponse> deletePreorderRequest(int preorderRequestId);

    ResponseEntity<FindPreorderRequestResponse> findPreorderRequestById(int preorderRequestId);

    ResponseEntity<ListPreorderRequestResponse> findAllPreorderRequests();


}