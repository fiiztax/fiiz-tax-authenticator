package com.jaimedantas.fiiztaxauthenticator.repository;

import com.jaimedantas.fiiztaxauthenticator.table.Subscricoes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscricoesRepository extends JpaRepository<Subscricoes, Long> {

  boolean existsById(Long id);

  Optional<Subscricoes> findById(Long id);

}
