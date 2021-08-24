package com.jaimedantas.fiiztaxauthenticator.repository;

import com.jaimedantas.fiiztaxauthenticator.table.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

  boolean existsById(Long id);

  Optional<Wallet> findById(Long id);

}
