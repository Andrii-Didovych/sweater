package com.example.sweater.repos;


import com.example.sweater.domain.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MessageRepo extends JpaRepository<Message, Integer> {

    Page<Message> findAll(Pageable pageable);

    Page<Message> findByTag(String tag, Pageable pageable);

    @Query("select m from Message m join m.author a where a.id =?1")
    Page<Message> findByUserId(Integer id, Pageable pageable);
}
