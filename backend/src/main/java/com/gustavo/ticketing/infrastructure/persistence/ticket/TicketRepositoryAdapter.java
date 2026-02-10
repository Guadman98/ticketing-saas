package com.gustavo.ticketing.infrastructure.persistence.ticket;

import com.gustavo.ticketing.application.ticket.TicketFilters;
import com.gustavo.ticketing.domain.ticket.Ticket;
import com.gustavo.ticketing.domain.ticket.TicketRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class TicketRepositoryAdapter implements TicketRepository {

  private final TicketJpaRepository jpa;

  public TicketRepositoryAdapter(TicketJpaRepository jpa) {
    this.jpa = jpa;
  }

  @Override
  public Ticket save(Ticket ticket) {
    var saved = jpa.save(TicketMapper.toEntity(ticket));
    return TicketMapper.toDomain(saved);
  }

  @Override
  public Page<Ticket> findByOrg(UUID orgId, TicketFilters filters, Pageable pageable) {
    var spec = TicketSpecifications.byOrgAndFilters(orgId, filters);
    return jpa.findAll(spec, pageable).map(TicketMapper::toDomain);
  }
}