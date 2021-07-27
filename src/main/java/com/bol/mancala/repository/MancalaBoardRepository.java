package com.bol.mancala.repository;

import com.bol.mancala.model.MancalaBoard;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MancalaBoardRepository extends MongoRepository<MancalaBoard, String> {

}
