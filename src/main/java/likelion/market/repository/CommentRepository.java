/**
 * @project MiniProject_Basic_JungSyHyeon
 * @since 2023-07-03
 */
package likelion.market.repository;

import likelion.market.entity.CommentEntity;

import org.springframework.data.jpa.repository.JpaRepository;



public interface CommentRepository extends JpaRepository<CommentEntity, Integer>{

}
