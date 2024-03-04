package com.example.swd392.repository;

import com.example.swd392.model.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface PackageRepo extends JpaRepository<Package,Integer> {

}