package com.example.swd392.serviceimplement;

import com.example.swd392.Request.PreorderResponseRequest.CreatePreorderResponseRequest;
import com.example.swd392.Response.PreorderResponseResponse.CreatePreorderResponseResponse;
import com.example.swd392.model.PreorderRequest;
import com.example.swd392.model.PreorderResponse;
import com.example.swd392.repository.PreorderRequestRepository;
import com.example.swd392.repository.PreorderResponseRepository;
import com.example.swd392.service.PreorderResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PreorderResponseServiceImpl implements PreorderResponseService {
    private final PreorderResponseRepository preorderResponseRepository;

    private final PreorderRequestRepository preorderRequestRepository;

    @Autowired
    public PreorderResponseServiceImpl(PreorderResponseRepository preorderResponseRepository, PreorderRequestRepository preorderRequestRepository) {
        this.preorderResponseRepository = preorderResponseRepository;
        this.preorderRequestRepository = preorderRequestRepository;
    }


    @Override
    public ResponseEntity<CreatePreorderResponseResponse> createPreorderResponse(CreatePreorderResponseRequest request) {
        try {
            // Kiểm tra dữ liệu đầu vào
            if (request.getPreorderRequestId() == 0 || request.getDescription() == null || request.getDescription().isEmpty()) {
                return ResponseEntity.badRequest().body(new CreatePreorderResponseResponse("Fail", "Dữ liệu không hợp lệ", null));
            }

            // Tìm kiếm yêu cầu đặt hàng trước tương ứng
            Optional<PreorderRequest> preorderRequestOptional = preorderRequestRepository.findById(request.getPreorderRequestId());
            if (preorderRequestOptional.isEmpty()) {
                return ResponseEntity.badRequest().body(new CreatePreorderResponseResponse("Fail", "Yêu cầu đặt hàng trước không tồn tại", null));
            }

            // Tạo preorder response từ dữ liệu yêu cầu và lưu vào cơ sở dữ liệu
            PreorderResponse preorderResponse = new PreorderResponse();
            preorderResponse.setPreorderRequest(preorderRequestOptional.get());
            preorderResponse.setPrice(request.getPrice());
            preorderResponse.setDescription(request.getDescription());
            preorderResponse.setTimeResponse(new Date());

            // Lưu preorder response vào cơ sở dữ liệu
            PreorderResponse savedPreorderResponse = preorderResponseRepository.save(preorderResponse);

            // Trả về phản hồi thành công
            return ResponseEntity.ok(new CreatePreorderResponseResponse("Success", "Tạo phản hồi đặt hàng trước thành công", savedPreorderResponse));
        } catch (Exception e) {
            // Xử lý lỗi máy chủ nội bộ
            return ResponseEntity.status(500).body(new CreatePreorderResponseResponse("Fail", "Lỗi máy chủ nội bộ", null));
        }
    }
}

