/**
 * @project MiniProject_Basic_JungSyHyeon
 * @since 2023-06-29
 */

package likelion.market.repository;

import likelion.market.entity.SalesItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * SalesItemRepository class
 */
public interface SalesItemRepository extends JpaRepository<SalesItemEntity, Integer>{

}
