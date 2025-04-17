package by.cargocontractservice.repository;

import by.cargocontractservice.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Long> {
    Optional<Contract> findByName(String name);

    void deleteByName(String name);
}
