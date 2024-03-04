package com.example.swd392.serviceimplement;

import com.example.swd392.Request.PreorderRequestRequest.CreatePreorderRequestRequest;
import com.example.swd392.Request.PreorderRequestRequest.UpdatePreorderRequestRequest;
import com.example.swd392.Response.PreorderRequestResponse.*;
import com.example.swd392.model.PreorderRequest;
import com.example.swd392.model.User;
import com.example.swd392.repository.PreorderRequestRepository;
import com.example.swd392.repository.UserRepo;
import com.example.swd392.service.PreorderRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PreorderRequestServiceImpl implements PreorderRequestService {

    private final PreorderRequestRepository preorderRequestRepository;
    private final UserRepo userRepository;

    @Autowired
    public PreorderRequestServiceImpl(PreorderRequestRepository preorderRequestRepository, UserRepo userRepository) {
        this.preorderRequestRepository = preorderRequestRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<CreatePreorderRequestResponse> createPreorderRequest(CreatePreorderRequestRequest request) {
        try {
            // Kiểm tra dữ liệu đầu vào
            if (request.getCreatorId() == 0 || request.getAudienceId() == 0 ||
                    request.getDescription() == null || request.getDescription().isEmpty()) {
                return ResponseEntity.badRequest().body(new CreatePreorderRequestResponse("Fail", "Dữ liệu không hợp lệ", null));
            }

            // Kiểm tra xem yêu cầu có tồn tại không


            // Tìm kiếm người tạo và người nghe
            Optional<User> creatorOptional = userRepository.findById(request.getCreatorId());
            Optional<User> audienceOptional = userRepository.findById(request.getAudienceId());

            if (creatorOptional.isEmpty() || audienceOptional.isEmpty()) {
                return ResponseEntity.badRequest().body(new CreatePreorderRequestResponse("Fail", "Người tạo hoặc người nghe không tồn tại", null));
            }

            User creator = creatorOptional.get();
            User audience = audienceOptional.get();

            // Tạo đối tượng PreorderRequest từ dữ liệu yêu cầu và lưu vào cơ sở dữ liệu
            PreorderRequest preorderRequest = new PreorderRequest();
            preorderRequest.setCreator(creator);
            preorderRequest.setAudience(audience);
            preorderRequest.setDescription(request.getDescription());
            preorderRequest.setOrderPlacedAt(new Date());

            PreorderRequest savedPreorderRequest = preorderRequestRepository.save(preorderRequest);

            // Trả về phản hồi thành công
            return ResponseEntity.ok(new CreatePreorderRequestResponse("Success", "Tạo yêu cầu đặt hàng trước thành công", savedPreorderRequest));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new CreatePreorderRequestResponse("Fail", "Lỗi máy chủ nội bộ", null));
        }
    }

    @Override
    public ResponseEntity<CreatePreorderRequestResponse> updatePreorderRequest(int id, CreatePreorderRequestRequest request) {
        try {
            // Tìm kiếm yêu cầu đặt hàng trước cần cập nhật
            Optional<PreorderRequest> existingPreorderRequestOptional = preorderRequestRepository.findById(id);
            if (existingPreorderRequestOptional.isEmpty()) {
                return ResponseEntity.badRequest().body(new CreatePreorderRequestResponse("Fail", "Yêu cầu đặt hàng trước không tồn tại", null));
            }
            PreorderRequest existingPreorderRequest = existingPreorderRequestOptional.get();

            // Kiểm tra dữ liệu đầu vào
            if (request.getDescription() == null || request.getDescription().isEmpty()) {
                return ResponseEntity.badRequest().body(new CreatePreorderRequestResponse("Fail", "Dữ liệu không hợp lệ", null));
            }

            // Kiểm tra xem có yêu cầu khác đã tồn tại với mô tả giống như yêu cầu mới không


            // Tìm kiếm người tạo và người nghe
            Optional<User> creatorOptional = userRepository.findById(request.getCreatorId());
            Optional<User> audienceOptional = userRepository.findById(request.getAudienceId());

            if (creatorOptional.isEmpty() || audienceOptional.isEmpty()) {
                return ResponseEntity.badRequest().body(new CreatePreorderRequestResponse("Fail", "Người tạo hoặc người nghe không tồn tại", null));
            }

            User creator = creatorOptional.get();
            User audience = audienceOptional.get();

            // Cập nhật thông tin yêu cầu
            existingPreorderRequest.setCreator(creator);
            existingPreorderRequest.setAudience(audience);
            existingPreorderRequest.setDescription(request.getDescription());
            existingPreorderRequest.setOrderPlacedAt(new Date());

            PreorderRequest updatedPreorderRequest = preorderRequestRepository.save(existingPreorderRequest);

            // Trả về phản hồi thành công
            return ResponseEntity.ok(new CreatePreorderRequestResponse("Success", "Cập nhật yêu cầu đặt hàng trước thành công", updatedPreorderRequest));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new CreatePreorderRequestResponse("Fail", "Lỗi máy chủ nội bộ", null));
        }
    }

    @Override
    public ResponseEntity<FindPreorderRequestResponse> findPreorderRequestById(int preorderRequestId) {
        try {
            Optional<PreorderRequest> preorderRequestOptional = preorderRequestRepository.findById(preorderRequestId);
            if (preorderRequestOptional.isEmpty()) {
                return ResponseEntity.badRequest().body(new FindPreorderRequestResponse("Fail", "Yêu cầu đặt hàng trước không tồn tại", null));
            }

            PreorderRequest preorderRequest = preorderRequestOptional.get();
            return ResponseEntity.ok(new FindPreorderRequestResponse("Success", "Tìm thấy yêu cầu đặt hàng trước", preorderRequest));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new FindPreorderRequestResponse("Fail", "Lỗi máy chủ nội bộ", null));
        }
    }

    @Override
    public ResponseEntity<ListPreorderRequestResponse> findAllPreorderRequests() {
        try {
            List<PreorderRequest> preorderRequests = preorderRequestRepository.findAll();
            if (preorderRequests.isEmpty()) {
                return ResponseEntity.ok(new ListPreorderRequestResponse("Success", "Danh sách trống", null));
            }
            return ResponseEntity.ok(new ListPreorderRequestResponse("Success", "Danh sách yêu cầu đặt hàng trước", preorderRequests));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ListPreorderRequestResponse("Fail", "Lỗi máy chủ nội bộ", null));
        }
    }

    @Override
    public ResponseEntity<DeletePreorderRequestResponse> deletePreorderRequest(int id) {
        try {
            // Tìm kiếm yêu cầu đặt hàng trước cần xóa
            Optional<PreorderRequest> existingPreorderRequestOptional = preorderRequestRepository.findById(id);
            if (existingPreorderRequestOptional.isEmpty()) {
                return ResponseEntity.badRequest().body(new DeletePreorderRequestResponse("Fail", "Yêu cầu đặt hàng trước không tồn tại"));
            }
            PreorderRequest existingPreorderRequest = existingPreorderRequestOptional.get();

            // Xóa yêu cầu đặt hàng trước
            preorderRequestRepository.delete(existingPreorderRequest);

            // Trả về phản hồi thành công
            return ResponseEntity.ok(new DeletePreorderRequestResponse("Success", "Xóa yêu cầu đặt hàng trước thành công"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new DeletePreorderRequestResponse("Fail", "Lỗi máy chủ nội bộ"));
        }
    }
}