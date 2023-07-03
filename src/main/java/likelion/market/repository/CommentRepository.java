package likelion.market.repository;

import likelion.market.entity.CommentEntity;

import org.springframework.data.jpa.repository.JpaRepository;



public interface CommentRepository extends JpaRepository<CommentEntity, Integer>{

}
