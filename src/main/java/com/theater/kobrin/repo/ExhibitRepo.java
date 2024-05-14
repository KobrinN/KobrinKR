package com.theater.kobrin.repo;

import com.theater.kobrin.entity.Exhibit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExhibitRepo extends JpaRepository<Exhibit, Long> {
}
