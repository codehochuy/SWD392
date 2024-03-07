package com.example.swd392.serviceimplement;

import com.example.swd392.Request.PackageUser.createPackageUserRequest;
import com.example.swd392.Response.PackageUserResponse.CreatePackageUserResponse;

import com.example.swd392.enums.Role;
import com.example.swd392.model.Package;
import com.example.swd392.model.PackageUser;
import com.example.swd392.model.User;
import com.example.swd392.repository.PackageRepo;
import com.example.swd392.repository.PakageUserRepository;
import com.example.swd392.repository.UserRepo;
import com.example.swd392.service.PackageUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PackageUserServiceImpl implements PackageUserService {

    private final PakageUserRepository packageRepository;
    private final UserRepo userRepository;

    private final PackageRepo pakageRepo;

    @Autowired
    public PackageUserServiceImpl(PakageUserRepository packageRepository, UserRepo userRepository, PackageRepo pakageRepo) {
        this.packageRepository = packageRepository;
        this.userRepository = userRepository;
        this.pakageRepo = pakageRepo;
    }


    @Override
    public ResponseEntity<CreatePackageUserResponse> createPackageUser(createPackageUserRequest request) {
        try {
            // Kiểm tra dữ liệu đầu vào
            if (request.getUserId() == 0 || request.getPackageId() == 0 ||
                    request.getStartDate() == null) {
                return ResponseEntity.badRequest().body(new CreatePackageUserResponse("Fail", "Dữ liệu không hợp lệ", null));
            }

            // Tìm kiếm người dùng
            Optional<User> userOptional = userRepository.findById(request.getUserId());

            if (userOptional.isEmpty()) {
                return ResponseEntity.badRequest().body(new CreatePackageUserResponse("Fail", "Người dùng không tồn tại", null));
            }

            User user = userOptional.get();

            // Kiểm tra vai trò của người dùng
            if (user.getRole() != Role.AUDIENCE) {
                return ResponseEntity.badRequest().body(new CreatePackageUserResponse("Fail", "Vai trò người dùng không hợp lệ", null));
            }

            // Tìm kiếm gói
            Optional<Package> packageOptional = pakageRepo.findById(request.getPackageId());

            if (packageOptional.isEmpty()) {
                return ResponseEntity.badRequest().body(new CreatePackageUserResponse("Fail", "Gói không tồn tại", null));
            }

            Package aPackage = packageOptional.get();

            // Tạo đối tượng PackageUser từ dữ liệu yêu cầu và tính toán ngày kết thúc
            LocalDateTime endDate = request.getStartDate().plusDays(aPackage.getPackageTime());
            PackageUser packageUser = new PackageUser();
            packageUser.setStartDate(request.getStartDate());
            packageUser.setEndDate(endDate);
            packageUser.setUser(user);
            packageUser.setAPackage(aPackage);

            // Lưu packageUser vào cơ sở dữ liệu
            PackageUser savedPackageUser = packageRepository.save(packageUser);

            // Trả về phản hồi thành công
            return ResponseEntity.ok(new CreatePackageUserResponse("Success", "Tạo người dùng gói thành công", savedPackageUser));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new CreatePackageUserResponse("Fail", "Lỗi máy chủ nội bộ", null));
        }
    }
}
