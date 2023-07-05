package likelion.market.repository;

import likelion.market.entity.NegotiationEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NegotiationRepository extends JpaRepository<NegotiationEntity, Integer>{

}
