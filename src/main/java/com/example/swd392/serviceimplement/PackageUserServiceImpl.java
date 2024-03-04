package com.example.swd392.serviceimplement;

import com.example.swd392.Request.PackageUser.createPackageUserRequest;
import com.example.swd392.Response.PackageUserResponse.CreatePackageUserResponse;

import com.example.swd392.enums.Role;
import com.example.swd392.model.PackageUser;
import com.example.swd392.model.User;
import com.example.swd392.repository.PakageUserRepository;
import com.example.swd392.repository.UserRepo;
import com.example.swd392.service.PackageUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PackageUserServiceImpl implements PackageUserService {

    private final PakageUserRepository packageRepository;
    private final UserRepo userRepository;

    @Autowired
    public PackageUserServiceImpl(PakageUserRepository packageRepository, UserRepo userRepository) {
        this.packageRepository = packageRepository;
        this.userRepository = userRepository;
    }


    @Override
    public ResponseEntity<CreatePackageUserResponse> createPackageUser(createPackageUserRequest request) {
        try {
            // Kiểm tra dữ liệu đầu vào
            if (request.getUserId() == 0 || request.getPackageId() == 0 ||
                    request.getStartDate() == null || request.getMaxTime() == 0) {
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

            // Tạo đối tượng PackageUser từ dữ liệu yêu cầu và tính toán ngày kết thúc
            PackageUser packageUser = new PackageUser();
            packageUser.setStartDate(request.getStartDate());
            packageUser.setEndDate(request.getStartDate().plusDays(request.getMaxTime()));
            packageUser.setUser(user);

            // Tìm kiếm gói
            Optional<Package> packageOptional = packageRepository.findById(request.getPackageId());

            if (packageOptional.isEmpty()) {
                return ResponseEntity.badRequest().body(new CreatePackageUserResponse("Fail", "Gói không tồn tại", null));
            }

            Package aPackage = packageOptional.get();
            packageUser.setAPackage(aPackage);

            // Lưu packageUser vào cơ sở dữ liệu
            PackageUser savedPackageUser = packageUserRepository.save(packageUser);

            // Trả về phản hồi thành công
            return ResponseEntity.ok(new CreatePackageUserResponse("Success", "Tạo người dùng gói thành công", savedPackageUser));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new CreatePackageUserResponse("Fail", "Lỗi máy chủ nội bộ", null));
        }
    }
}
