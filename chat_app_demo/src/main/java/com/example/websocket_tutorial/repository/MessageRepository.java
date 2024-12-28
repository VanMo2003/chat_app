package com.example.websocket_tutorial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.websocket_tutorial.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = "SELECT * FROM message WHERE receiver = :receiver OR sender = :receiver", nativeQuery = true)
    List<Message> findAllMySelf(@Param("receiver") String receiver);

    List<Message> findAllBySenderAndReceiver(String sender, String receiver);
}
