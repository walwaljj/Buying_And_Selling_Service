/**
 * @project MiniProject_Basic_JungSyHyeon
 * @since 2023-07-04
 */

package likelion.market.repository;

import likelion.market.entity.NegotiationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * NegotiationRepository
 */
public interface NegotiationRepository extends JpaRepository<NegotiationEntity, Integer>{

}
